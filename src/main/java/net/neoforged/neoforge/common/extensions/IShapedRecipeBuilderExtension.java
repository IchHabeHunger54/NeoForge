package net.neoforged.neoforge.common.extensions;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.neoforged.neoforge.common.crafting.outgredient.ItemTagOutgredient;
import net.neoforged.neoforge.common.crafting.outgredient.Outgredient;

public interface IShapedRecipeBuilderExtension {
    /// @param items    The [HolderGetter] provided by the recipe context.
    /// @param category The [RecipeCategory] to use.
    /// @param result   The result [Outgredient].
    static ShapedRecipeBuilder shaped(HolderGetter<Item> items, RecipeCategory category, Outgredient<ItemStackTemplate> result) {
        return new ShapedRecipeBuilder(items, category, result);
    }

    /// @param items      The [HolderGetter] provided by the recipe context.
    /// @param category   The [RecipeCategory] to use.
    /// @param tag        The [TagKey] to use to resolve the outgredient.
    /// @param fallback   The fallback to use if resolving the tag did not yield a result.
    /// @param count      The count to use. Corresponds to [ItemStackTemplate#count()].
    /// @param components The data components to use. Corresponds to [ItemStackTemplate#components()].
    static ShapedRecipeBuilder shaped(HolderGetter<Item> items, RecipeCategory category, TagKey<Item> tag, Holder<Item> fallback, int count, DataComponentPatch components) {
        return shaped(items, category, ItemTagOutgredient.of(tag, fallback, count, components));
    }

    /// @param items      The [HolderGetter] provided by the recipe context.
    /// @param category   The [RecipeCategory] to use.
    /// @param tag        The [TagKey] to use to resolve the outgredient.
    /// @param count      The count to use. Corresponds to [ItemStackTemplate#count()].
    /// @param components The data components to use. Corresponds to [ItemStackTemplate#components()].
    static ShapedRecipeBuilder shaped(HolderGetter<Item> items, RecipeCategory category, TagKey<Item> tag, int count, DataComponentPatch components) {
        return shaped(items, category, ItemTagOutgredient.of(tag, count, components));
    }

    /// @param items    The [HolderGetter] provided by the recipe context.
    /// @param category The [RecipeCategory] to use.
    /// @param tag      The [TagKey] to use to resolve the outgredient.
    /// @param fallback The fallback to use if resolving the tag did not yield a result.
    /// @param count    The count to use. Corresponds to [ItemStackTemplate#count()].
    static ShapedRecipeBuilder shaped(HolderGetter<Item> items, RecipeCategory category, TagKey<Item> tag, Holder<Item> fallback, int count) {
        return shaped(items, category, ItemTagOutgredient.of(tag, fallback, count));
    }

    /// @param items    The [HolderGetter] provided by the recipe context.
    /// @param category The [RecipeCategory] to use.
    /// @param tag      The [TagKey] to use to resolve the outgredient.
    /// @param count    The count to use. Corresponds to [ItemStackTemplate#count()].
    static ShapedRecipeBuilder shaped(HolderGetter<Item> items, RecipeCategory category, TagKey<Item> tag, int count) {
        return shaped(items, category, ItemTagOutgredient.of(tag, count));
    }

    /// @param items    The [HolderGetter] provided by the recipe context.
    /// @param category The [RecipeCategory] to use.
    /// @param tag      The [TagKey] to use to resolve the outgredient.
    /// @param fallback The fallback to use if resolving the tag did not yield a result.
    static ShapedRecipeBuilder shaped(HolderGetter<Item> items, RecipeCategory category, TagKey<Item> tag, Holder<Item> fallback) {
        return shaped(items, category, ItemTagOutgredient.of(tag, fallback));
    }

    /// @param items    The [HolderGetter] provided by the recipe context.
    /// @param category The [RecipeCategory] to use.
    /// @param tag      The [TagKey] to use to resolve the outgredient.
    static ShapedRecipeBuilder shaped(HolderGetter<Item> items, RecipeCategory category, TagKey<Item> tag) {
        return shaped(items, category, ItemTagOutgredient.of(tag));
    }
}
