/*
 * Copyright (c) NeoForged and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.neoforged.neoforge.common.crafting.outgredient;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.tags.TagKey;
import net.minecraft.util.StrictJsonParser;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.neoforge.common.conditions.ConditionalOps;
import net.neoforged.neoforge.common.config.NeoForgeServerConfig;
import org.slf4j.Logger;

/// Reload listener for [TagPriorities].
public class TagPriorityManager extends SimplePreparableReloadListener<Map<ResourceKey<? extends Registry<?>>, TagPriorities<?>>> {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final FileToIdConverter CONVERTER = FileToIdConverter.json("neoforge/tag_priority");
    public static final TagPriorityManager INSTANCE = new TagPriorityManager();
    private Map<ResourceKey<? extends Registry<?>>, TagPriorities<?>> tagPriorities = new HashMap<>();

    private TagPriorityManager() {}

    /// Resolves a [TagPriorities] into an entry, or into an empty [Optional].
    /// First, the [TagPriorities] loaded from datapacks are checked.
    /// If this does not yield a result, the mod id list in the config is used (see [resolveFromModIdConfig(Registry, TagKey)]).
    ///
    /// @param registry The [Registry] to query.
    /// @param tagKey   The [TagKey] to query.
    /// @param <T>      The type of the [Registry] and the [TagKey].
    /// @return An [Optional] containing the entry, or an empty [Optional].
    @SuppressWarnings("unchecked") // unchecked cast to TagPriorities<T>
    public <T> Optional<T> resolve(Registry<T> registry, TagKey<T> tagKey) {
        ResourceKey<? extends Registry<T>> key = registry.key();
        if (tagPriorities.containsKey(key)) {
            Optional<T> priority = ((TagPriorities<T>) tagPriorities.get(key)).resolve(registry, tagKey);
            if (priority.isPresent()) return priority;
        }
        return resolveFromModIdConfig(registry, tagKey);
    }

    /// Resolves a [TagPriorities] into an entry using the mod id list at [NeoForgeServerConfig#tagPriorityModIds].
    /// If the tag contains **exactly one** entry with the given mod id, that entry is returned.
    /// If no such entry is found, [Optional#empty()] is returned.
    ///
    /// @param registry The [Registry] to query.
    /// @param tagKey   The [TagKey] to query.
    /// @param <T>      The type of the [Registry] and the [TagKey].
    /// @return An [Optional] containing the entry, or an empty [Optional].
    public static <T> Optional<T> resolveFromModIdConfig(Registry<T> registry, TagKey<T> tagKey) {
        List<? extends String> modIds = NeoForgeServerConfig.INSTANCE.tagPriorityModIds.get();
        List<Holder<T>> tagContents = registry.getOrThrow(tagKey).contents();
        for (String modId : modIds) {
            List<Holder<T>> filteredTagContents = tagContents
                    .stream()
                    .filter(e -> e.unwrapKey().map(key -> key.identifier().getNamespace().equals(modId)).orElse(false))
                    .toList();
            if (filteredTagContents.size() == 1) return Optional.of(tagContents.getFirst().value());
        }
        return Optional.empty();
    }

    @Override
    protected Map<ResourceKey<? extends Registry<?>>, TagPriorities<?>> prepare(ResourceManager manager, ProfilerFiller profiler) {
        Map<ResourceKey<? extends Registry<?>>, TagPriorities<?>> map = new HashMap<>();
        for (Identifier location : CONVERTER.listMatchingResources(manager).keySet()) {
            Identifier id = CONVERTER.fileToId(location);
            parse(ResourceKey.createRegistryKey(id), manager.getResourceStack(location), location, id, map);
        }
        return map;
    }

    @Override
    protected void apply(Map<ResourceKey<? extends Registry<?>>, TagPriorities<?>> preparations, ResourceManager manager, ProfilerFiller profiler) {
        this.tagPriorities = preparations;
        LOGGER.info("Loaded {} tag priorities for {} registries", this.tagPriorities.values().stream().mapToInt(e -> e.values().size()).sum(), this.tagPriorities.size());
    }

    // This is in a separate method because generics
    @SuppressWarnings("unchecked") // unchecked cast to TagPriorities<T>
    private <T> void parse(ResourceKey<? extends Registry<T>> registryKey, List<Resource> resourceStack, Identifier location, Identifier id, Map<ResourceKey<? extends Registry<?>>, TagPriorities<?>> map) {
        TagPriorities<T> priorities = (TagPriorities<T>) map.getOrDefault(registryKey, new TagPriorities<>());
        Codec<Optional<TagPriorities<T>>> codec = ConditionalOps.createConditionalCodec(TagPriorities.codec(registryKey));
        // We read all data files because we do layering
        for (Resource resource : resourceStack) {
            try (Reader reader = resource.openAsReader()) {
                // Parse the file
                priorities = codec.parse(JsonOps.INSTANCE, StrictJsonParser.parse(reader))
                        // Map to an Optional<TagPriorities<T>>, warning if we're empty or encountered a load error
                        .<Optional<TagPriorities<T>>>mapOrElse(
                                parsed -> {
                                        if (parsed.isEmpty()) {
                                            LOGGER.debug("Skipping loading data file '{}' from '{}' as its conditions were not met", id, location);
                                        }
                                        return parsed;
                                }, error -> {
                                        LOGGER.error("Couldn't parse data file '{}' from '{}': {}", id, location, error);
                                        return Optional.empty();
                                })
                        // If present, merge with the previous values. If empty, keep the old values.
                        .map(priorities::merge)
                        .orElse(priorities);
            } catch (IOException e) {
                LOGGER.error("Couldn't parse data file '{}' from '{}'", id, location, e);
            }
        }
        map.put(registryKey, priorities);
    }
}
