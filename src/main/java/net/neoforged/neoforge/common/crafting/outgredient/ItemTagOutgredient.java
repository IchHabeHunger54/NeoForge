/*
 * Copyright (c) NeoForged and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.neoforged.neoforge.common.crafting.outgredient;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.neoforged.neoforge.common.NeoForgeMod;

import java.util.Optional;

/// An [Outgredient] for [ItemStackTemplate]s which are created from a [TagKey] and an optional fallback [Item].
/// Use [resolve] to get a concrete [ItemStackTemplate].
///
/// @param tag        The [TagKey] to use to resolve the outgredient.
/// @param fallback   The fallback to use if resolving the tag did not yield a result.
/// @param count      The count to use. Corresponds to [ItemStackTemplate#count()].
/// @param components The data components to use. Corresponds to [ItemStackTemplate#components()].
public record ItemTagOutgredient(TagKey<Item> tag, Optional<Holder<Item>> fallback, int count, DataComponentPatch components) implements Outgredient<ItemStackTemplate> {
    public static final MapCodec<ItemTagOutgredient> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            TagKey.codec(Registries.ITEM).fieldOf("tag").forGetter(ItemTagOutgredient::tag),
            BuiltInRegistries.ITEM.holderByNameCodec().optionalFieldOf("fallback").forGetter(ItemTagOutgredient::fallback),
            ExtraCodecs.POSITIVE_INT.fieldOf("count").forGetter(ItemTagOutgredient::count),
            DataComponentPatch.CODEC.optionalFieldOf("components", DataComponentPatch.EMPTY).forGetter(ItemTagOutgredient::components)
    ).apply(inst, ItemTagOutgredient::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, ItemTagOutgredient> STREAM_CODEC = StreamCodec.composite(
            TagKey.streamCodec(Registries.ITEM), ItemTagOutgredient::tag,
            ByteBufCodecs.holderRegistry(Registries.ITEM).apply(ByteBufCodecs::optional), ItemTagOutgredient::fallback,
            ByteBufCodecs.VAR_INT, ItemTagOutgredient::count,
            DataComponentPatch.STREAM_CODEC, ItemTagOutgredient::components,
            ItemTagOutgredient::new);

    /// @param tag        The [TagKey] to use to resolve the outgredient.
    /// @param fallback   The fallback to use if resolving the tag did not yield a result.
    /// @param count      The count to use. Corresponds to [ItemStackTemplate#count()].
    /// @param components The data components to use. Corresponds to [ItemStackTemplate#components()].
    public static ItemTagOutgredient of(TagKey<Item> tag, Holder<Item> fallback, int count, DataComponentPatch components) {
        return new ItemTagOutgredient(tag, Optional.of(fallback), count, components);
    }

    /// @param tag        The [TagKey] to use to resolve the outgredient.
    /// @param count      The count to use. Corresponds to [ItemStackTemplate#count()].
    /// @param components The data components to use. Corresponds to [ItemStackTemplate#components()].
    public static ItemTagOutgredient of(TagKey<Item> tag, int count, DataComponentPatch components) {
        return new ItemTagOutgredient(tag, Optional.empty(), count, components);
    }

    /// @param tag      The [TagKey] to use to resolve the outgredient.
    /// @param fallback The fallback to use if resolving the tag did not yield a result.
    /// @param count    The count to use. Corresponds to [ItemStackTemplate#count()].
    public static ItemTagOutgredient of(TagKey<Item> tag, Holder<Item> fallback, int count) {
        return of(tag, fallback, count, DataComponentPatch.EMPTY);
    }

    /// @param tag   The [TagKey] to use to resolve the outgredient.
    /// @param count The count to use. Corresponds to [ItemStackTemplate#count()].
    public static ItemTagOutgredient of(TagKey<Item> tag, int count) {
        return of(tag, count, DataComponentPatch.EMPTY);
    }

    /// @param tag      The [TagKey] to use to resolve the outgredient.
    /// @param fallback The fallback to use if resolving the tag did not yield a result.
    public static ItemTagOutgredient of(TagKey<Item> tag, Holder<Item> fallback) {
        return of(tag, fallback, 1);
    }

    /// @param tag The [TagKey] to use to resolve the outgredient.
    public static ItemTagOutgredient of(TagKey<Item> tag) {
        return of(tag, 1);
    }

    @Override
    public ItemStackTemplate resolve() {
        return new ItemStackTemplate(fallback.map(Holder::value).orElse(Items.AIR), count, components); //TODO
    }

    @Override
    public OutgredientType<? extends Outgredient<ItemStackTemplate>> type() {
        return NeoForgeMod.ITEM_TAG_OUTGREDIENT.get();
    }

    @Override
    public SlotDisplay display() {
        return new Display(this);
    }

    /// The [OutgredientSlotDisplay] associated with [ItemTagOutgredient]s.
    public record Display(ItemTagOutgredient outgredient) implements ItemOutgredientSlotDisplay {
        public static final MapCodec<Display> CODEC = ItemTagOutgredient.CODEC.xmap(Display::new, Display::outgredient);
        public static final StreamCodec<RegistryFriendlyByteBuf, Display> STREAM_CODEC = ItemTagOutgredient.STREAM_CODEC.map(Display::new, Display::outgredient);

        @Override
        public Type<? extends SlotDisplay> type() {
            return NeoForgeMod.ITEM_TAG_OUTGREDIENT_SLOT_DISPLAY.get();
        }
    }
}
