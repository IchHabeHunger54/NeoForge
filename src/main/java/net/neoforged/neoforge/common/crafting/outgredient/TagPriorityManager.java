package net.neoforged.neoforge.common.crafting.outgredient;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.neoforged.neoforge.common.config.NeoForgeServerConfig;

import java.util.List;
import java.util.Optional;

//TODO
public class TagPriorityManager {
    @SuppressWarnings("unchecked") // unchecked cast to Registry<T>
    public static <T> Optional<T> resolve(ResourceKey<? extends Registry<T>> registryKey, TagKey<T> tagKey) {
        Registry<T> registry = (Registry<T>) BuiltInRegistries.REGISTRY.getValue(registryKey.identifier());
        if (registry != null) {
            List<? extends String> modIds = NeoForgeServerConfig.INSTANCE.tagPriorityModIds.get();
            List<Holder<T>> tagContents = registry.getOrThrow(tagKey).contents();
            for (String modId : modIds) {
                List<Holder<T>> filteredTagContents = tagContents
                    .stream()
                    .filter(e -> e.unwrapKey().map(key -> key.identifier().getNamespace().equals(modId)).orElse(false))
                    .toList();
                if (filteredTagContents.size() == 1) return Optional.of(tagContents.getFirst().value());
            }
        }
        return Optional.empty();
    }
}
