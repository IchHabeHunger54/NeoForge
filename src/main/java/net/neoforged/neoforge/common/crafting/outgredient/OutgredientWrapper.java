/*
 * Copyright (c) NeoForged and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.neoforged.neoforge.common.crafting.outgredient;

import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.neoforged.neoforge.fluids.FluidStackTemplate;

import java.util.function.Function;

/// Represents a "vanilla" `T` wrapped as an [Outgredient].
/// Common uses are `OutgredientWrapper<ItemStackTemplate>` and `OutgredientWrapper<FluidStackTemplate>`.
///
/// As it represents "vanilla" values, it receives special treatment during serialization.
/// See [OutgredientCodecs#streamCodec(StreamCodec, StreamCodec, Function)] for how that works.
///
/// Note: For construction, prefer using [Outgredient#of(ItemStackTemplate)] and [Outgredient#of(FluidStackTemplate)] where possible.
///
/// @param outgredient The `T` to wrap.
/// @param display     The [SlotDisplay] to use.
/// @param <T>         The wrapped type.
public record OutgredientWrapper<T>(T outgredient, SlotDisplay display) implements Outgredient<T> {
    @Override
    public T resolve() {
        return outgredient;
    }

    @SuppressWarnings("DataFlowIssue") // we return null in a non-null method
    @Override
    public OutgredientType<? extends Outgredient<T>> type() {
        return null;
    }
}
