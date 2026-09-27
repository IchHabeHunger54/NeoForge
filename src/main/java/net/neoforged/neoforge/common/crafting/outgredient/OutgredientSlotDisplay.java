/*
 * Copyright (c) NeoForged and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.neoforged.neoforge.common.crafting.outgredient;

import net.minecraft.world.item.crafting.display.SlotDisplay;

/// A [SlotDisplay] for [Outgredient]s.
///
/// @param <T> The generic type of the [Outgredient].
public interface OutgredientSlotDisplay<T> extends SlotDisplay {
    /// @return The backing [Outgredient].
    Outgredient<T> outgredient();
}
