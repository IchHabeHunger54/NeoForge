package net.neoforged.neoforge.common.extensions;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.crafting.SmokingRecipe;
import net.neoforged.neoforge.common.crafting.outgredient.ItemTagOutgredient;
import net.neoforged.neoforge.common.crafting.outgredient.Outgredient;

public interface ISimpleCookingRecipeBuilderExtension {
    /// @param ingredient       The [Ingredient] of the campfire cooking recipe.
    /// @param craftingCategory The [RecipeCategory] to use.
    /// @param result           The result [Outgredient] to use.
    /// @param experience       The experience awarded when completing the recipe.
    /// @param cookingTime      The cooking time of the campfire cooking recipe. All vanilla campfire cooking recipes use 600 ticks (30 seconds).
    static SimpleCookingRecipeBuilder campfireCooking(Ingredient ingredient, RecipeCategory craftingCategory, Outgredient<ItemStackTemplate> result, float experience, int cookingTime) {
        return SimpleCookingRecipeBuilder.generic(ingredient, craftingCategory, CookingBookCategory.FOOD, result, experience, cookingTime, CampfireCookingRecipe::new);
    }

    /// @param ingredient       The [Ingredient] of the blasting recipe.
    /// @param craftingCategory The [RecipeCategory] to use.
    /// @param cookingCategory  The [CookingBookCategory] to use.
    /// @param result           The result [Outgredient] to use.
    /// @param experience       The experience awarded when completing the recipe.
    /// @param cookingTime      The cooking time of the blasting recipe. All vanilla blasting recipes use 100 ticks (5 seconds).
    static SimpleCookingRecipeBuilder blasting(Ingredient ingredient, RecipeCategory craftingCategory, CookingBookCategory cookingCategory, Outgredient<ItemStackTemplate> result, float experience, int cookingTime) {
        return SimpleCookingRecipeBuilder.generic(ingredient, craftingCategory, cookingCategory, result, experience, cookingTime, BlastingRecipe::new);
    }

    /// @param ingredient       The [Ingredient] of the smelting recipe.
    /// @param craftingCategory The [RecipeCategory] to use.
    /// @param cookingCategory  The [CookingBookCategory] to use.
    /// @param result           The result [Outgredient] to use.
    /// @param experience       The experience awarded when completing the recipe.
    /// @param cookingTime      The cooking time of the smelting recipe. All vanilla smelting recipes use 200 ticks (10 seconds).
    static SimpleCookingRecipeBuilder smelting(Ingredient ingredient, RecipeCategory craftingCategory, CookingBookCategory cookingCategory, Outgredient<ItemStackTemplate> result, float experience, int cookingTime) {
        return SimpleCookingRecipeBuilder.generic(ingredient, craftingCategory, cookingCategory, result, experience, cookingTime, SmeltingRecipe::new);
    }

    /// @param ingredient       The [Ingredient] of the smoking recipe.
    /// @param craftingCategory The [RecipeCategory] to use.
    /// @param result           The result [Outgredient] to use.
    /// @param experience       The experience awarded when completing the recipe.
    /// @param cookingTime      The cooking time of the smoking recipe. All vanilla smoking recipes use 100 ticks (5 seconds).
    static SimpleCookingRecipeBuilder smoking(Ingredient ingredient, RecipeCategory craftingCategory, Outgredient<ItemStackTemplate> result, float experience, int cookingTime) {
        return SimpleCookingRecipeBuilder.generic(ingredient, craftingCategory, CookingBookCategory.FOOD, result, experience, cookingTime, SmokingRecipe::new);
    }

    /// @param ingredient       The [Ingredient] of the recipe.
    /// @param craftingCategory The [RecipeCategory] to use.
    /// @param cookingCategory  The [CookingBookCategory] to use.
    /// @param tag              The [TagKey] to use to resolve the result [Outgredient].
    /// @param fallback         The fallback to use if resolving the tag did not yield a result.
    /// @param count            The count to use. Corresponds to [ItemStackTemplate#count()].
    /// @param components       The data components to use. Corresponds to [ItemStackTemplate#components()].
    /// @param experience       The experience awarded when completing this recipe.
    /// @param cookingTime      The cooking time of the recipe.
    /// @param factory          The [AbstractCookingRecipe.Factory] used to create the recipe instance.
    /// @param <T>              The type of the recipe instance created by the builder.
    static <T extends AbstractCookingRecipe> SimpleCookingRecipeBuilder generic(Ingredient ingredient, RecipeCategory craftingCategory, CookingBookCategory cookingCategory, TagKey<Item> tag, Holder<Item> fallback, int count, DataComponentPatch components, float experience, int cookingTime, AbstractCookingRecipe.Factory<T> factory) {
        return SimpleCookingRecipeBuilder.generic(ingredient, craftingCategory, cookingCategory, ItemTagOutgredient.of(tag, fallback, count, components), experience, cookingTime, factory);
    }

    /// @param ingredient       The [Ingredient] of the campfire cooking recipe.
    /// @param craftingCategory The [RecipeCategory] to use.
    /// @param tag              The [TagKey] to use to resolve the result [Outgredient].
    /// @param fallback         The fallback to use if resolving the tag did not yield a result.
    /// @param count            The count to use. Corresponds to [ItemStackTemplate#count()].
    /// @param components       The data components to use. Corresponds to [ItemStackTemplate#components()].
    /// @param experience       The experience awarded when completing the recipe.
    /// @param cookingTime      The cooking time of the campfire cooking recipe. All vanilla campfire cooking recipes use 600 ticks (30 seconds).
    static SimpleCookingRecipeBuilder campfireCooking(Ingredient ingredient, RecipeCategory craftingCategory, TagKey<Item> tag, Holder<Item> fallback, int count, DataComponentPatch components, float experience, int cookingTime) {
        return campfireCooking(ingredient, craftingCategory, ItemTagOutgredient.of(tag, fallback, count, components), experience, cookingTime);
    }

    /// @param ingredient       The [Ingredient] of the blasting recipe.
    /// @param craftingCategory The [RecipeCategory] to use.
    /// @param cookingCategory  The [CookingBookCategory] to use.
    /// @param tag              The [TagKey] to use to resolve the result [Outgredient].
    /// @param fallback         The fallback to use if resolving the tag did not yield a result.
    /// @param count            The count to use. Corresponds to [ItemStackTemplate#count()].
    /// @param components       The data components to use. Corresponds to [ItemStackTemplate#components()].
    /// @param experience       The experience awarded when completing the recipe.
    /// @param cookingTime      The cooking time of the blasting recipe. All vanilla blasting recipes use 100 ticks (5 seconds).
    static SimpleCookingRecipeBuilder blasting(Ingredient ingredient, RecipeCategory craftingCategory, CookingBookCategory cookingCategory, TagKey<Item> tag, Holder<Item> fallback, int count, DataComponentPatch components, float experience, int cookingTime) {
        return blasting(ingredient, craftingCategory, cookingCategory, ItemTagOutgredient.of(tag, fallback, count, components), experience, cookingTime);
    }

    /// @param ingredient       The [Ingredient] of the smelting recipe.
    /// @param craftingCategory The [RecipeCategory] to use.
    /// @param cookingCategory  The [CookingBookCategory] to use.
    /// @param tag              The [TagKey] to use to resolve the result [Outgredient].
    /// @param fallback         The fallback to use if resolving the tag did not yield a result.
    /// @param count            The count to use. Corresponds to [ItemStackTemplate#count()].
    /// @param components       The data components to use. Corresponds to [ItemStackTemplate#components()].
    /// @param experience       The experience awarded when completing the recipe.
    /// @param cookingTime      The cooking time of the smelting recipe. All vanilla smelting recipes use 200 ticks (10 seconds).
    static SimpleCookingRecipeBuilder smelting(Ingredient ingredient, RecipeCategory craftingCategory, CookingBookCategory cookingCategory, TagKey<Item> tag, Holder<Item> fallback, int count, DataComponentPatch components, float experience, int cookingTime) {
        return smelting(ingredient, craftingCategory, cookingCategory, ItemTagOutgredient.of(tag, fallback, count, components), experience, cookingTime);
    }

    /// @param ingredient       The [Ingredient] of the smoking recipe.
    /// @param craftingCategory The [RecipeCategory] to use.
    /// @param tag              The [TagKey] to use to resolve the result [Outgredient].
    /// @param fallback         The fallback to use if resolving the tag did not yield a result.
    /// @param count            The count to use. Corresponds to [ItemStackTemplate#count()].
    /// @param components       The data components to use. Corresponds to [ItemStackTemplate#components()].
    /// @param experience       The experience awarded when completing the recipe.
    /// @param cookingTime      The cooking time of the smoking recipe. All vanilla smoking recipes use 100 ticks (5 seconds).
    static SimpleCookingRecipeBuilder smoking(Ingredient ingredient, RecipeCategory craftingCategory, TagKey<Item> tag, Holder<Item> fallback, int count, DataComponentPatch components, float experience, int cookingTime) {
        return smoking(ingredient, craftingCategory, ItemTagOutgredient.of(tag, fallback, count, components), experience, cookingTime);
    }

    /// @param ingredient       The [Ingredient] of the recipe.
    /// @param craftingCategory The [RecipeCategory] to use.
    /// @param cookingCategory  The [CookingBookCategory] to use.
    /// @param tag              The [TagKey] to use to resolve the result [Outgredient].
    /// @param count            The count to use. Corresponds to [ItemStackTemplate#count()].
    /// @param components       The data components to use. Corresponds to [ItemStackTemplate#components()].
    /// @param experience       The experience awarded when completing this recipe.
    /// @param cookingTime      The cooking time of the recipe.
    /// @param factory          The [AbstractCookingRecipe.Factory] used to create the recipe instance.
    /// @param <T>              The type of the recipe instance created by the builder.
    static <T extends AbstractCookingRecipe> SimpleCookingRecipeBuilder generic(Ingredient ingredient, RecipeCategory craftingCategory, CookingBookCategory cookingCategory, TagKey<Item> tag, int count, DataComponentPatch components, float experience, int cookingTime, AbstractCookingRecipe.Factory<T> factory) {
        return SimpleCookingRecipeBuilder.generic(ingredient, craftingCategory, cookingCategory, ItemTagOutgredient.of(tag, count, components), experience, cookingTime, factory);
    }

    /// @param ingredient       The [Ingredient] of the campfire cooking recipe.
    /// @param craftingCategory The [RecipeCategory] to use.
    /// @param tag              The [TagKey] to use to resolve the result [Outgredient].
    /// @param count            The count to use. Corresponds to [ItemStackTemplate#count()].
    /// @param components       The data components to use. Corresponds to [ItemStackTemplate#components()].
    /// @param experience       The experience awarded when completing the recipe.
    /// @param cookingTime      The cooking time of the campfire cooking recipe. All vanilla campfire cooking recipes use 600 ticks (30 seconds).
    static SimpleCookingRecipeBuilder campfireCooking(Ingredient ingredient, RecipeCategory craftingCategory, TagKey<Item> tag, int count, DataComponentPatch components, float experience, int cookingTime) {
        return campfireCooking(ingredient, craftingCategory, ItemTagOutgredient.of(tag, count, components), experience, cookingTime);
    }

    /// @param ingredient       The [Ingredient] of the blasting recipe.
    /// @param craftingCategory The [RecipeCategory] to use.
    /// @param cookingCategory  The [CookingBookCategory] to use.
    /// @param tag              The [TagKey] to use to resolve the result [Outgredient].
    /// @param count            The count to use. Corresponds to [ItemStackTemplate#count()].
    /// @param components       The data components to use. Corresponds to [ItemStackTemplate#components()].
    /// @param experience       The experience awarded when completing the recipe.
    /// @param cookingTime      The cooking time of the blasting recipe. All vanilla blasting recipes use 100 ticks (5 seconds).
    static SimpleCookingRecipeBuilder blasting(Ingredient ingredient, RecipeCategory craftingCategory, CookingBookCategory cookingCategory, TagKey<Item> tag, int count, DataComponentPatch components, float experience, int cookingTime) {
        return blasting(ingredient, craftingCategory, cookingCategory, ItemTagOutgredient.of(tag, count, components), experience, cookingTime);
    }

    /// @param ingredient       The [Ingredient] of the smelting recipe.
    /// @param craftingCategory The [RecipeCategory] to use.
    /// @param cookingCategory  The [CookingBookCategory] to use.
    /// @param tag              The [TagKey] to use to resolve the result [Outgredient].
    /// @param count            The count to use. Corresponds to [ItemStackTemplate#count()].
    /// @param components       The data components to use. Corresponds to [ItemStackTemplate#components()].
    /// @param experience       The experience awarded when completing the recipe.
    /// @param cookingTime      The cooking time of the smelting recipe. All vanilla smelting recipes use 200 ticks (10 seconds).
    static SimpleCookingRecipeBuilder smelting(Ingredient ingredient, RecipeCategory craftingCategory, CookingBookCategory cookingCategory, TagKey<Item> tag, int count, DataComponentPatch components, float experience, int cookingTime) {
        return smelting(ingredient, craftingCategory, cookingCategory, ItemTagOutgredient.of(tag, count, components), experience, cookingTime);
    }

    /// @param ingredient       The [Ingredient] of the smoking recipe.
    /// @param craftingCategory The [RecipeCategory] to use.
    /// @param tag              The [TagKey] to use to resolve the result [Outgredient].
    /// @param count            The count to use. Corresponds to [ItemStackTemplate#count()].
    /// @param components       The data components to use. Corresponds to [ItemStackTemplate#components()].
    /// @param experience       The experience awarded when completing the recipe.
    /// @param cookingTime      The cooking time of the smoking recipe. All vanilla smoking recipes use 100 ticks (5 seconds).
    static SimpleCookingRecipeBuilder smoking(Ingredient ingredient, RecipeCategory craftingCategory, TagKey<Item> tag, int count, DataComponentPatch components, float experience, int cookingTime) {
        return smoking(ingredient, craftingCategory, ItemTagOutgredient.of(tag, count, components), experience, cookingTime);
    }

    /// @param ingredient       The [Ingredient] of the recipe.
    /// @param craftingCategory The [RecipeCategory] to use.
    /// @param cookingCategory  The [CookingBookCategory] to use.
    /// @param tag              The [TagKey] to use to resolve the result [Outgredient].
    /// @param fallback         The fallback to use if resolving the tag did not yield a result.
    /// @param count            The count to use. Corresponds to [ItemStackTemplate#count()].
    /// @param experience       The experience awarded when completing this recipe.
    /// @param cookingTime      The cooking time of the recipe.
    /// @param factory          The [AbstractCookingRecipe.Factory] used to create the recipe instance.
    /// @param <T>              The type of the recipe instance created by the builder.
    static <T extends AbstractCookingRecipe> SimpleCookingRecipeBuilder generic(Ingredient ingredient, RecipeCategory craftingCategory, CookingBookCategory cookingCategory, TagKey<Item> tag, Holder<Item> fallback, int count, float experience, int cookingTime, AbstractCookingRecipe.Factory<T> factory) {
        return SimpleCookingRecipeBuilder.generic(ingredient, craftingCategory, cookingCategory, ItemTagOutgredient.of(tag, fallback, count), experience, cookingTime, factory);
    }

    /// @param ingredient       The [Ingredient] of the campfire cooking recipe.
    /// @param craftingCategory The [RecipeCategory] to use.
    /// @param tag              The [TagKey] to use to resolve the result [Outgredient].
    /// @param fallback         The fallback to use if resolving the tag did not yield a result.
    /// @param count            The count to use. Corresponds to [ItemStackTemplate#count()].
    /// @param experience       The experience awarded when completing the recipe.
    /// @param cookingTime      The cooking time of the campfire cooking recipe. All vanilla campfire cooking recipes use 600 ticks (30 seconds).
    static SimpleCookingRecipeBuilder campfireCooking(Ingredient ingredient, RecipeCategory craftingCategory, TagKey<Item> tag, Holder<Item> fallback, int count, float experience, int cookingTime) {
        return campfireCooking(ingredient, craftingCategory, ItemTagOutgredient.of(tag, fallback, count), experience, cookingTime);
    }

    /// @param ingredient       The [Ingredient] of the blasting recipe.
    /// @param craftingCategory The [RecipeCategory] to use.
    /// @param cookingCategory  The [CookingBookCategory] to use.
    /// @param tag              The [TagKey] to use to resolve the result [Outgredient].
    /// @param fallback         The fallback to use if resolving the tag did not yield a result.
    /// @param count            The count to use. Corresponds to [ItemStackTemplate#count()].
    /// @param experience       The experience awarded when completing the recipe.
    /// @param cookingTime      The cooking time of the blasting recipe. All vanilla blasting recipes use 100 ticks (5 seconds).
    static SimpleCookingRecipeBuilder blasting(Ingredient ingredient, RecipeCategory craftingCategory, CookingBookCategory cookingCategory, TagKey<Item> tag, Holder<Item> fallback, int count, float experience, int cookingTime) {
        return blasting(ingredient, craftingCategory, cookingCategory, ItemTagOutgredient.of(tag, fallback, count), experience, cookingTime);
    }

    /// @param ingredient       The [Ingredient] of the smelting recipe.
    /// @param craftingCategory The [RecipeCategory] to use.
    /// @param cookingCategory  The [CookingBookCategory] to use.
    /// @param tag              The [TagKey] to use to resolve the result [Outgredient].
    /// @param fallback         The fallback to use if resolving the tag did not yield a result.
    /// @param count            The count to use. Corresponds to [ItemStackTemplate#count()].
    /// @param experience       The experience awarded when completing the recipe.
    /// @param cookingTime      The cooking time of the smelting recipe. All vanilla smelting recipes use 200 ticks (10 seconds).
    static SimpleCookingRecipeBuilder smelting(Ingredient ingredient, RecipeCategory craftingCategory, CookingBookCategory cookingCategory, TagKey<Item> tag, Holder<Item> fallback, int count, float experience, int cookingTime) {
        return smelting(ingredient, craftingCategory, cookingCategory, ItemTagOutgredient.of(tag, fallback, count), experience, cookingTime);
    }

    /// @param ingredient       The [Ingredient] of the smoking recipe.
    /// @param craftingCategory The [RecipeCategory] to use.
    /// @param tag              The [TagKey] to use to resolve the result [Outgredient].
    /// @param fallback         The fallback to use if resolving the tag did not yield a result.
    /// @param count            The count to use. Corresponds to [ItemStackTemplate#count()].
    /// @param experience       The experience awarded when completing the recipe.
    /// @param cookingTime      The cooking time of the smoking recipe. All vanilla smoking recipes use 100 ticks (5 seconds).
    static SimpleCookingRecipeBuilder smoking(Ingredient ingredient, RecipeCategory craftingCategory, TagKey<Item> tag, Holder<Item> fallback, int count, float experience, int cookingTime) {
        return smoking(ingredient, craftingCategory, ItemTagOutgredient.of(tag, fallback, count), experience, cookingTime);
    }

    /// @param ingredient       The [Ingredient] of the recipe.
    /// @param craftingCategory The [RecipeCategory] to use.
    /// @param cookingCategory  The [CookingBookCategory] to use.
    /// @param tag              The [TagKey] to use to resolve the result [Outgredient].
    /// @param count            The count to use. Corresponds to [ItemStackTemplate#count()].
    /// @param experience       The experience awarded when completing this recipe.
    /// @param cookingTime      The cooking time of the recipe.
    /// @param factory          The [AbstractCookingRecipe.Factory] used to create the recipe instance.
    /// @param <T>              The type of the recipe instance created by the builder.
    static <T extends AbstractCookingRecipe> SimpleCookingRecipeBuilder generic(Ingredient ingredient, RecipeCategory craftingCategory, CookingBookCategory cookingCategory, TagKey<Item> tag, int count, float experience, int cookingTime, AbstractCookingRecipe.Factory<T> factory) {
        return SimpleCookingRecipeBuilder.generic(ingredient, craftingCategory, cookingCategory, ItemTagOutgredient.of(tag, count), experience, cookingTime, factory);
    }

    /// @param ingredient       The [Ingredient] of the campfire cooking recipe.
    /// @param craftingCategory The [RecipeCategory] to use.
    /// @param tag              The [TagKey] to use to resolve the result [Outgredient].
    /// @param count            The count to use. Corresponds to [ItemStackTemplate#count()].
    /// @param experience       The experience awarded when completing the recipe.
    /// @param cookingTime      The cooking time of the campfire cooking recipe. All vanilla campfire cooking recipes use 600 ticks (30 seconds).
    static SimpleCookingRecipeBuilder campfireCooking(Ingredient ingredient, RecipeCategory craftingCategory, TagKey<Item> tag, int count, float experience, int cookingTime) {
        return campfireCooking(ingredient, craftingCategory, ItemTagOutgredient.of(tag, count), experience, cookingTime);
    }

    /// @param ingredient       The [Ingredient] of the blasting recipe.
    /// @param craftingCategory The [RecipeCategory] to use.
    /// @param cookingCategory  The [CookingBookCategory] to use.
    /// @param tag              The [TagKey] to use to resolve the result [Outgredient].
    /// @param count            The count to use. Corresponds to [ItemStackTemplate#count()].
    /// @param experience       The experience awarded when completing the recipe.
    /// @param cookingTime      The cooking time of the blasting recipe. All vanilla blasting recipes use 100 ticks (5 seconds).
    static SimpleCookingRecipeBuilder blasting(Ingredient ingredient, RecipeCategory craftingCategory, CookingBookCategory cookingCategory, TagKey<Item> tag, int count, float experience, int cookingTime) {
        return blasting(ingredient, craftingCategory, cookingCategory, ItemTagOutgredient.of(tag, count), experience, cookingTime);
    }

    /// @param ingredient       The [Ingredient] of the smelting recipe.
    /// @param craftingCategory The [RecipeCategory] to use.
    /// @param cookingCategory  The [CookingBookCategory] to use.
    /// @param tag              The [TagKey] to use to resolve the result [Outgredient].
    /// @param count            The count to use. Corresponds to [ItemStackTemplate#count()].
    /// @param experience       The experience awarded when completing the recipe.
    /// @param cookingTime      The cooking time of the smelting recipe. All vanilla smelting recipes use 200 ticks (10 seconds).
    static SimpleCookingRecipeBuilder smelting(Ingredient ingredient, RecipeCategory craftingCategory, CookingBookCategory cookingCategory, TagKey<Item> tag, int count, float experience, int cookingTime) {
        return smelting(ingredient, craftingCategory, cookingCategory, ItemTagOutgredient.of(tag, count), experience, cookingTime);
    }

    /// @param ingredient       The [Ingredient] of the smoking recipe.
    /// @param craftingCategory The [RecipeCategory] to use.
    /// @param tag              The [TagKey] to use to resolve the result [Outgredient].
    /// @param count            The count to use. Corresponds to [ItemStackTemplate#count()].
    /// @param experience       The experience awarded when completing the recipe.
    /// @param cookingTime      The cooking time of the smoking recipe. All vanilla smoking recipes use 100 ticks (5 seconds).
    static SimpleCookingRecipeBuilder smoking(Ingredient ingredient, RecipeCategory craftingCategory, TagKey<Item> tag, int count, float experience, int cookingTime) {
        return smoking(ingredient, craftingCategory, ItemTagOutgredient.of(tag, count), experience, cookingTime);
    }

    /// @param ingredient       The [Ingredient] of the recipe.
    /// @param craftingCategory The [RecipeCategory] to use.
    /// @param cookingCategory  The [CookingBookCategory] to use.
    /// @param tag              The [TagKey] to use to resolve the result [Outgredient].
    /// @param fallback         The fallback to use if resolving the tag did not yield a result.
    /// @param experience       The experience awarded when completing this recipe.
    /// @param cookingTime      The cooking time of the recipe.
    /// @param factory          The [AbstractCookingRecipe.Factory] used to create the recipe instance.
    /// @param <T>              The type of the recipe instance created by the builder.
    static <T extends AbstractCookingRecipe> SimpleCookingRecipeBuilder generic(Ingredient ingredient, RecipeCategory craftingCategory, CookingBookCategory cookingCategory, TagKey<Item> tag, Holder<Item> fallback, float experience, int cookingTime, AbstractCookingRecipe.Factory<T> factory) {
        return SimpleCookingRecipeBuilder.generic(ingredient, craftingCategory, cookingCategory, ItemTagOutgredient.of(tag, fallback), experience, cookingTime, factory);
    }

    /// @param ingredient       The [Ingredient] of the campfire cooking recipe.
    /// @param craftingCategory The [RecipeCategory] to use.
    /// @param tag              The [TagKey] to use to resolve the result [Outgredient].
    /// @param fallback         The fallback to use if resolving the tag did not yield a result.
    /// @param experience       The experience awarded when completing the recipe.
    /// @param cookingTime      The cooking time of the campfire cooking recipe. All vanilla campfire cooking recipes use 600 ticks (30 seconds).
    static SimpleCookingRecipeBuilder campfireCooking(Ingredient ingredient, RecipeCategory craftingCategory, TagKey<Item> tag, Holder<Item> fallback, float experience, int cookingTime) {
        return campfireCooking(ingredient, craftingCategory, ItemTagOutgredient.of(tag, fallback), experience, cookingTime);
    }

    /// @param ingredient       The [Ingredient] of the blasting recipe.
    /// @param craftingCategory The [RecipeCategory] to use.
    /// @param cookingCategory  The [CookingBookCategory] to use.
    /// @param tag              The [TagKey] to use to resolve the result [Outgredient].
    /// @param fallback         The fallback to use if resolving the tag did not yield a result.
    /// @param experience       The experience awarded when completing the recipe.
    /// @param cookingTime      The cooking time of the blasting recipe. All vanilla blasting recipes use 100 ticks (5 seconds).
    static SimpleCookingRecipeBuilder blasting(Ingredient ingredient, RecipeCategory craftingCategory, CookingBookCategory cookingCategory, TagKey<Item> tag, Holder<Item> fallback, float experience, int cookingTime) {
        return blasting(ingredient, craftingCategory, cookingCategory, ItemTagOutgredient.of(tag, fallback), experience, cookingTime);
    }

    /// @param ingredient       The [Ingredient] of the smelting recipe.
    /// @param craftingCategory The [RecipeCategory] to use.
    /// @param cookingCategory  The [CookingBookCategory] to use.
    /// @param tag              The [TagKey] to use to resolve the result [Outgredient].
    /// @param fallback         The fallback to use if resolving the tag did not yield a result.
    /// @param experience       The experience awarded when completing the recipe.
    /// @param cookingTime      The cooking time of the smelting recipe. All vanilla smelting recipes use 200 ticks (10 seconds).
    static SimpleCookingRecipeBuilder smelting(Ingredient ingredient, RecipeCategory craftingCategory, CookingBookCategory cookingCategory, TagKey<Item> tag, Holder<Item> fallback, float experience, int cookingTime) {
        return smelting(ingredient, craftingCategory, cookingCategory, ItemTagOutgredient.of(tag, fallback), experience, cookingTime);
    }

    /// @param ingredient       The [Ingredient] of the smoking recipe.
    /// @param craftingCategory The [RecipeCategory] to use.
    /// @param tag              The [TagKey] to use to resolve the result [Outgredient].
    /// @param fallback         The fallback to use if resolving the tag did not yield a result.
    /// @param experience       The experience awarded when completing the recipe.
    /// @param cookingTime      The cooking time of the smoking recipe. All vanilla smoking recipes use 100 ticks (5 seconds).
    static SimpleCookingRecipeBuilder smoking(Ingredient ingredient, RecipeCategory craftingCategory, TagKey<Item> tag, Holder<Item> fallback, float experience, int cookingTime) {
        return smoking(ingredient, craftingCategory, ItemTagOutgredient.of(tag, fallback), experience, cookingTime);
    }

    /// @param ingredient       The [Ingredient] of the recipe.
    /// @param craftingCategory The [RecipeCategory] to use.
    /// @param cookingCategory  The [CookingBookCategory] to use.
    /// @param tag              The [TagKey] to use to resolve the result [Outgredient].
    /// @param experience       The experience awarded when completing this recipe.
    /// @param cookingTime      The cooking time of the recipe.
    /// @param factory          The [AbstractCookingRecipe.Factory] used to create the recipe instance.
    /// @param <T>              The type of the recipe instance created by the builder.
    static <T extends AbstractCookingRecipe> SimpleCookingRecipeBuilder generic(Ingredient ingredient, RecipeCategory craftingCategory, CookingBookCategory cookingCategory, TagKey<Item> tag, float experience, int cookingTime, AbstractCookingRecipe.Factory<T> factory) {
        return SimpleCookingRecipeBuilder.generic(ingredient, craftingCategory, cookingCategory, ItemTagOutgredient.of(tag), experience, cookingTime, factory);
    }

    /// @param ingredient       The [Ingredient] of the campfire cooking recipe.
    /// @param craftingCategory The [RecipeCategory] to use.
    /// @param tag              The [TagKey] to use to resolve the result [Outgredient].
    /// @param experience       The experience awarded when completing the recipe.
    /// @param cookingTime      The cooking time of the campfire cooking recipe. All vanilla campfire cooking recipes use 600 ticks (30 seconds).
    static SimpleCookingRecipeBuilder campfireCooking(Ingredient ingredient, RecipeCategory craftingCategory, TagKey<Item> tag, float experience, int cookingTime) {
        return campfireCooking(ingredient, craftingCategory, ItemTagOutgredient.of(tag), experience, cookingTime);
    }

    /// @param ingredient       The [Ingredient] of the blasting recipe.
    /// @param craftingCategory The [RecipeCategory] to use.
    /// @param cookingCategory  The [CookingBookCategory] to use.
    /// @param tag              The [TagKey] to use to resolve the result [Outgredient].
    /// @param experience       The experience awarded when completing the recipe.
    /// @param cookingTime      The cooking time of the blasting recipe. All vanilla blasting recipes use 100 ticks (5 seconds).
    static SimpleCookingRecipeBuilder blasting(Ingredient ingredient, RecipeCategory craftingCategory, CookingBookCategory cookingCategory, TagKey<Item> tag, float experience, int cookingTime) {
        return blasting(ingredient, craftingCategory, cookingCategory, ItemTagOutgredient.of(tag), experience, cookingTime);
    }

    /// @param ingredient       The [Ingredient] of the smelting recipe.
    /// @param craftingCategory The [RecipeCategory] to use.
    /// @param cookingCategory  The [CookingBookCategory] to use.
    /// @param tag              The [TagKey] to use to resolve the result [Outgredient].
    /// @param experience       The experience awarded when completing the recipe.
    /// @param cookingTime      The cooking time of the smelting recipe. All vanilla smelting recipes use 200 ticks (10 seconds).
    static SimpleCookingRecipeBuilder smelting(Ingredient ingredient, RecipeCategory craftingCategory, CookingBookCategory cookingCategory, TagKey<Item> tag, float experience, int cookingTime) {
        return smelting(ingredient, craftingCategory, cookingCategory, ItemTagOutgredient.of(tag), experience, cookingTime);
    }

    /// @param ingredient       The [Ingredient] of the smoking recipe.
    /// @param craftingCategory The [RecipeCategory] to use.
    /// @param tag              The [TagKey] to use to resolve the result [Outgredient].
    /// @param experience       The experience awarded when completing the recipe.
    /// @param cookingTime      The cooking time of the smoking recipe. All vanilla smoking recipes use 100 ticks (5 seconds).
    static SimpleCookingRecipeBuilder smoking(Ingredient ingredient, RecipeCategory craftingCategory, TagKey<Item> tag, float experience, int cookingTime) {
        return smoking(ingredient, craftingCategory, ItemTagOutgredient.of(tag), experience, cookingTime);
    }
}
