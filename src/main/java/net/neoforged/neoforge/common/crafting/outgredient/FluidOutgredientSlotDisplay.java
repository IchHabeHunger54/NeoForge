/*
 * Copyright (c) NeoForged and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.neoforged.neoforge.common.crafting.outgredient;

import java.util.stream.Stream;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.item.crafting.display.DisplayContentsFactory;
import net.neoforged.neoforge.fluids.FluidStackTemplate;
import net.neoforged.neoforge.fluids.crafting.display.ForFluidStacks;

/// Convenience interface for [OutgredientSlotDisplay]s of type [FluidStackTemplate].
/// Automatically resolves the display for [FluidStackTemplate]s.
public interface FluidOutgredientSlotDisplay extends OutgredientSlotDisplay<FluidStackTemplate> {
    @Override
    default <T> Stream<T> resolve(ContextMap context, DisplayContentsFactory<T> builder) {
        return builder instanceof ForFluidStacks<T> forStacks
                ? Stream.of(forStacks.forStack(outgredient().resolve().create()))
                : Stream.empty();
    }
}
