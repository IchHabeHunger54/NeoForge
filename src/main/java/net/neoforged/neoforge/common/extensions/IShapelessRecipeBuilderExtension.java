/*
 * Copyright (c) NeoForged and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.neoforged.neoforge.common.extensions;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.neoforged.neoforge.common.crafting.outgredient.ItemTagOutgredient;
import net.neoforged.neoforge.common.crafting.outgredient.Outgredient;

public interface IShapelessRecipeBuilderExtension {
    /// @param items The [HolderGetter] provided by the recipe context.
    /// @param category The [RecipeCategory] to use.
    /// @param result   The result [Outgredient] to use.
    static ShapelessRecipeBuilder shapeless(HolderGetter<Item> items, RecipeCategory category, Outgredient<ItemStackTemplate> result) {
        return new ShapelessRecipeBuilder(items, category, result);
    }

    /// @param items  The [HolderGetter] provided by the recipe context.
    /// @param category   The [RecipeCategory] to use.
    /// @param tag        The [TagKey] to use to resolve the result [Outgredient].
    /// @param fallback   The fallback to use if resolving the tag did not yield a result.
    /// @param count      The count to use. Corresponds to [ItemStackTemplate#count()].
    /// @param components The data components to use. Corresponds to [ItemStackTemplate#components()].
    static ShapelessRecipeBuilder shapeless(HolderGetter<Item> items, RecipeCategory category, TagKey<Item> tag, Holder<Item> fallback, int count, DataComponentPatch components) {
        return shapeless(items, category, ItemTagOutgredient.of(tag, fallback, count, components));
    }

    /// @param items  The [HolderGetter] provided by the recipe context.
    /// @param category   The [RecipeCategory] to use.
    /// @param tag        The [TagKey] to use to resolve the result [Outgredient].
    /// @param count      The count to use. Corresponds to [ItemStackTemplate#count()].
    /// @param components The data components to use. Corresponds to [ItemStackTemplate#components()].
    static ShapelessRecipeBuilder shapeless(HolderGetter<Item> items, RecipeCategory category, TagKey<Item> tag, int count, DataComponentPatch components) {
        return shapeless(items, category, ItemTagOutgredient.of(tag, count, components));
    }

    /// @param items The [HolderGetter] provided by the recipe context.
    /// @param category The [RecipeCategory] to use.
    /// @param tag      The [TagKey] to use to resolve the result [Outgredient].
    /// @param fallback The fallback to use if resolving the tag did not yield a result.
    /// @param count    The count to use. Corresponds to [ItemStackTemplate#count()].
    static ShapelessRecipeBuilder shapeless(HolderGetter<Item> items, RecipeCategory category, TagKey<Item> tag, Holder<Item> fallback, int count) {
        return shapeless(items, category, ItemTagOutgredient.of(tag, fallback, count));
    }

    /// @param items The [HolderGetter] provided by the recipe context.
    /// @param category The [RecipeCategory] to use.
    /// @param tag      The [TagKey] to use to resolve the result [Outgredient].
    /// @param count    The count to use. Corresponds to [ItemStackTemplate#count()].
    static ShapelessRecipeBuilder shapeless(HolderGetter<Item> items, RecipeCategory category, TagKey<Item> tag, int count) {
        return shapeless(items, category, ItemTagOutgredient.of(tag, count));
    }

    /// @param items The [HolderGetter] provided by the recipe context.
    /// @param category The [RecipeCategory] to use.
    /// @param tag      The [TagKey] to use to resolve the result [Outgredient].
    /// @param fallback The fallback to use if resolving the tag did not yield a result.
    static ShapelessRecipeBuilder shapeless(HolderGetter<Item> items, RecipeCategory category, TagKey<Item> tag, Holder<Item> fallback) {
        return shapeless(items, category, ItemTagOutgredient.of(tag, fallback));
    }

    /// @param items The [HolderGetter] provided by the recipe context.
    /// @param category The [RecipeCategory] to use.
    /// @param tag      The [TagKey] to use to resolve the result [Outgredient].
    static ShapelessRecipeBuilder shapeless(HolderGetter<Item> items, RecipeCategory category, TagKey<Item> tag) {
        return shapeless(items, category, ItemTagOutgredient.of(tag));
    }
}
