/*
 * Copyright (c) NeoForged and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.neoforged.neoforge.common.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.entries.SingleEntryContainerBase;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.common.crafting.outgredient.ItemTagOutgredient;
import net.neoforged.neoforge.common.crafting.outgredient.Outgredient;
import net.neoforged.neoforge.common.crafting.outgredient.OutgredientCodecs;

/// A loot pool entry that resolves the item using an [Outgredient].
public class OutgredientLootEntry extends SingleEntryContainerBase {
    @SuppressWarnings("unchecked") // suppress Outgredient<? extends ItemStackTemplate> -> Outgredient<ItemStackTemplate> unchecked warning
    public static final MapCodec<OutgredientLootEntry> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            OutgredientCodecs.ITEM_OUTGREDIENT_CODEC.fieldOf("outgredient").forGetter(it -> (Outgredient<ItemStackTemplate>) it.outgredient)).and(uniformFields(inst)).apply(inst, OutgredientLootEntry::new));
    private final Outgredient<? extends ItemStackTemplate> outgredient;

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private OutgredientLootEntry(Outgredient<? extends ItemStackTemplate> outgredient, int weight, int quality, Optional<Holder<LootItemCondition>> condition, Optional<Holder<LootItemFunction>> modifier) {
        super(weight, quality, condition, modifier);
        this.outgredient = outgredient;
    }

    /// Creates a new [OutgredientLootEntry] builder from an [Outgredient].
    public static OutgredientLootEntry.Builder<?> of(Outgredient<? extends ItemStackTemplate> outgredient) {
        return simpleBuilder((weight, quality, condition, modifier) -> new OutgredientLootEntry(outgredient, weight, quality, condition, modifier));
    }

    /// Creates a new [OutgredientLootEntry] builder from a [TagKey] and a fallback [Item], via [ItemTagOutgredient].
    /// If you do not want to specify a fallback, use [OutgredientLootEntry#of(TagKey)] instead.
    public static OutgredientLootEntry.Builder<?> of(TagKey<Item> tag, Holder<Item> fallback) {
        return of(ItemTagOutgredient.of(tag, fallback));
    }

    /// Creates a new [OutgredientLootEntry] builder from a [TagKey], via [ItemTagOutgredient].
    /// If you want to specify a fallback, use [OutgredientLootEntry#of(TagKey, Holder)] instead.
    public static OutgredientLootEntry.Builder<?> of(TagKey<Item> tag) {
        return of(ItemTagOutgredient.of(tag));
    }

    @Override
    public MapCodec<? extends SingleEntryContainerBase> codec() {
        return NeoForgeMod.OUTGREDIENT_LOOT_POOL_ENTRY_TYPE.get();
    }

    @Override
    protected void createItemStack(Consumer<ItemStack> output, LootContext context) {
        output.accept(outgredient.resolve().create());
    }
}
