/*
 * Copyright (c) NeoForged and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.neoforged.neoforge.common.crafting.outgredient;

import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.neoforged.neoforge.fluids.FluidStackTemplate;
import net.neoforged.neoforge.fluids.crafting.display.FluidStackSlotDisplay;

/// Represents a generic recipe outgredient. It can be [resolve]d to a `T` when required.
///
/// The outgredient system closely mirrors the [net.minecraft.world.item.crafting.Ingredient] system, but manages recipe _outputs_ rather than _inputs_.
/// For more information, please consider the docs.
///
/// @param <T> The type of the outgredient, i.e., what type the outgredient resolves to.
public interface Outgredient<T> {
    /// Creates a new [ItemStackTemplate]-backed outgredient.
    /// 
    /// @param stack The [ItemStackTemplate] backing the outgredient.
    /// @return A new outgredient.
    static Outgredient<ItemStackTemplate> ofItem(ItemStackTemplate stack) {
        return new OutgredientWrapper<>(stack, new SlotDisplay.ItemStackSlotDisplay(stack));
    }

    /// Creates a new [FluidStackTemplate]-backed outgredient.
    ///
    /// @param stack The [FluidStackTemplate] backing the outgredient.
    /// @return A new outgredient.
    static Outgredient<FluidStackTemplate> ofFluid(FluidStackTemplate stack) {
        return new OutgredientWrapper<>(stack, new FluidStackSlotDisplay(stack));
    }

    /// Resolves the outgredient into a specific `T`.
    ///
    /// @return The result of resolving the outgredient.
    T resolve();

    /// The registered [OutgredientType] associated with the outgredient.
    OutgredientType<? extends Outgredient<T>> type();

    /// The [SlotDisplay] to use for the outgredient. The [SlotDisplay] should,
    /// in similar fashion to the outgredient itself, only be resolved when required.
    ///
    /// @return A [SlotDisplay].
    /// @see ItemOutgredientSlotDisplay
    /// @see FluidOutgredientSlotDisplay
    SlotDisplay display();
}
