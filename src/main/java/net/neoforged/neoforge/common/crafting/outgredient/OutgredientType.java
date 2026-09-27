/*
 * Copyright (c) NeoForged and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.neoforged.neoforge.common.crafting.outgredient;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

/// Registered type record that encapsulates an [Outgredient]'s [MapCodec] and [StreamCodec].
///
/// @param codec       The [MapCodec] to use.
/// @param streamCodec The [StreamCodec] to use.
/// @param <T>         The exact type of the encapsulated [Outgredient].
public record OutgredientType<T extends Outgredient<?>>(MapCodec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
    /// Constructor for outgredient types that use a regular codec for network syncing.
    public OutgredientType(MapCodec<T> codec) {
        this(codec, ByteBufCodecs.fromCodecWithRegistries(codec.codec()));
    }
}
