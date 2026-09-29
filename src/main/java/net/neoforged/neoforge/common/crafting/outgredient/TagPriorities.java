/*
 * Copyright (c) NeoForged and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.neoforged.neoforge.common.crafting.outgredient;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/// Represents a map of [TagKey]s to [Identifier] lists. The lists are [Identifier]s and not `T`s in order to allow for potentially absent entries.
public record TagPriorities<T>(Map<TagKey<T>, List<Identifier>> values) {
    /// Creates a [TagPriorities] with no values.
    public TagPriorities() {
        this(Map.of());
    }

    /// Creates a `T`-typed [Codec] for [TagPriorities].
    ///
    /// @param registryKey The registry key to use.
    /// @param <T>         The exact type of the [TagPriorities].
    public static <T> Codec<TagPriorities<T>> codec(ResourceKey<? extends Registry<T>> registryKey) {
        return Codec.unboundedMap(TagKey.codec(registryKey), Identifier.CODEC.listOf()).xmap(TagPriorities::new, TagPriorities::values);
    }

    /// Merges `this` and `that` [TagPriorities] into one new [TagPriorities]. `this` and `that` are left untouched.
    ///
    /// @param that The other [TagPriorities] to merge with.
    /// @return A new [TagPriorities] containing the merged contents.
    public TagPriorities<T> merge(TagPriorities<T> that) {
        Map<TagKey<T>, List<Identifier>> map = new HashMap<>(this.values);
        for (Map.Entry<TagKey<T>, List<Identifier>> entry : that.values.entrySet()) {
            TagKey<T> key = entry.getKey();
            List<Identifier> value = entry.getValue();
            if (map.containsKey(key)) {
                List<Identifier> list = new ArrayList<>(value);
                list.addAll(map.get(key));
                map.put(key, list);
            } else {
                map.put(key, value);
            }
        }
        return new TagPriorities<>(map);
    }

    /// Resolves the [TagPriorities] into a `T`.
    ///
    /// @param registry The [Registry] to query.
    /// @param tagKey   The [TagKey] to query.
    /// @return An [Optional] containing the resolved `T`, or an empty [Optional] if resolving was unsuccessful.
    public Optional<T> resolve(Registry<T> registry, TagKey<T> tagKey) {
        if (!values.containsKey(tagKey)) return Optional.empty();
        for (Identifier id : values.get(tagKey)) {
            if (registry.containsKey(id)) return Optional.ofNullable(registry.getValue(id));
        }
        return Optional.empty();
    }
}
