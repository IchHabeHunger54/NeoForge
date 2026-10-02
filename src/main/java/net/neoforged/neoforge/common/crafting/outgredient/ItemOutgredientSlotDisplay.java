/*
 * Copyright (c) NeoForged and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.neoforged.neoforge.common.crafting.outgredient;

import java.util.stream.Stream;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.display.DisplayContentsFactory;

/// Convenience interface for [OutgredientSlotDisplay]s of type [ItemStackTemplate].
/// Automatically resolves the display for [ItemStackTemplate]s.
public interface ItemOutgredientSlotDisplay extends OutgredientSlotDisplay<ItemStackTemplate> {
    @Override
    default <T> Stream<T> resolve(ContextMap context, DisplayContentsFactory<T> builder) {
        return builder instanceof DisplayContentsFactory.ForStacks<T> forStacks
                ? Stream.of(forStacks.forStack(outgredient().resolve().create()))
                : Stream.empty();
    }
}
