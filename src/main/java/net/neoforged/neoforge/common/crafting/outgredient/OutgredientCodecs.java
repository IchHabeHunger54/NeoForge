/*
 * Copyright (c) NeoForged and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.neoforged.neoforge.common.crafting.outgredient;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStackTemplate;
import net.neoforged.neoforge.fluids.FluidStackTemplate;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Function;

/// Utility class that contains various codecs related to outgredients.
public final class OutgredientCodecs {
    public static final Codec<Outgredient<ItemStackTemplate>> ITEM_OUTGREDIENT_CODEC = codec(ItemStackTemplate.CODEC, NeoForgeRegistries.ITEM_OUTGREDIENT_TYPES.byNameCodec(), Outgredient::of);
    public static final Codec<Outgredient<FluidStackTemplate>> FLUID_OUTGREDIENT_CODEC = codec(FluidStackTemplate.CODEC, NeoForgeRegistries.FLUID_OUTGREDIENT_TYPES.byNameCodec(), Outgredient::of);
    public static final StreamCodec<RegistryFriendlyByteBuf, Outgredient<ItemStackTemplate>> ITEM_OUTGREDIENT_STREAM_CODEC = streamCodec(
        ItemStackTemplate.STREAM_CODEC,
        ByteBufCodecs.registry(NeoForgeRegistries.Keys.ITEM_OUTGREDIENT_TYPES).dispatch(Outgredient::type, OutgredientType::streamCodec),
        Outgredient::of);
    public static final StreamCodec<RegistryFriendlyByteBuf, Outgredient<FluidStackTemplate>> FLUID_OUTGREDIENT_STREAM_CODEC = streamCodec(
        FluidStackTemplate.STREAM_CODEC,
        ByteBufCodecs.registry(NeoForgeRegistries.Keys.FLUID_OUTGREDIENT_TYPES).dispatch(Outgredient::type, OutgredientType::streamCodec),
        Outgredient::of);
    private static final int CUSTOM_OUTGREDIENT_MARKER = -1000;

    private OutgredientCodecs() {}

    /// Creates a [Codec] for [Outgredient]s. Assumes the presence of "vanilla" elements with no specified type, that the codec falls back to if no type was found.
    ///
    /// @param vanillaCodec     The "vanilla" codec that is fallen back to if no type was found, and that is used to serialize "vanilla" [Outgredient]s.
    /// @param outgredientCodec The codec of the [OutgredientType] registry.
    /// @param toOutgredient    A [Function] to transform "vanilla" elements into [Outgredient]s.
    /// @param <T>              The generic type of the [Outgredient]s.
    /// @return A [Codec] for [Outgredient]s of type `T`.
    /// @see net.neoforged.neoforge.common.crafting.IngredientCodecs#codec(Codec)
    public static <T> Codec<Outgredient<T>> codec(Codec<T> vanillaCodec, Codec<OutgredientType<? extends Outgredient<T>>> outgredientCodec, Function<T, ? extends Outgredient<T>> toOutgredient) {
        return Codec.xor(vanillaCodec, outgredientCodec.<Outgredient<T>>dispatch("neoforge:outgredient_type", Outgredient::type, OutgredientType::codec)).xmap(
                either -> Either.unwrap(either.mapLeft(toOutgredient)),
                outgredient -> outgredient instanceof OutgredientWrapper<T> ? Either.left(outgredient.resolve()) : Either.right(outgredient));
    }

    /// Creates a [StreamCodec] for [Outgredient]s. Assumes the presence of "vanilla" elements with no specified type, that the stream codec falls back to if no type was found.
    ///
    /// @param vanillaCodec     The "vanilla" stream codec that is fallen back to if no type was found, and that is used to serialize "vanilla" [Outgredient]s.
    /// @param outgredientCodec A dispatch stream codec of the [OutgredientType] registry.
    /// @param toOutgredient    A [Function] to transform "vanilla" elements into [Outgredient]s.
    /// @param <T>              The generic type of the [Outgredient]s.
    /// @return A [StreamCodec] for [Outgredient]s of type `T`.
    /// @see net.neoforged.neoforge.common.crafting.IngredientCodecs#streamCodec(StreamCodec)
    public static <T> StreamCodec<RegistryFriendlyByteBuf, Outgredient<T>> streamCodec(
        StreamCodec<RegistryFriendlyByteBuf, T> vanillaCodec,
        StreamCodec<RegistryFriendlyByteBuf, Outgredient<T>> outgredientCodec,
        Function<T, ? extends Outgredient<T>> toOutgredient) {
        return new StreamCodec<>() {
            @Override
            public Outgredient<T> decode(RegistryFriendlyByteBuf buf) {
                int readerIndex = buf.readerIndex();
                int length = buf.readVarInt();
                if (length == CUSTOM_OUTGREDIENT_MARKER) {
                    return outgredientCodec.decode(buf);
                } else {
                    buf.readerIndex(readerIndex);
                    return toOutgredient.apply(vanillaCodec.decode(buf));
                }
            };

            @Override
            public void encode(RegistryFriendlyByteBuf buf, Outgredient<T> outgredient) {
                if (!(outgredient instanceof OutgredientWrapper<T>) && buf.getConnectionType().isNeoForge()) {
                    buf.writeVarInt(CUSTOM_OUTGREDIENT_MARKER);
                    outgredientCodec.encode(buf, outgredient);
                } else {
                    vanillaCodec.encode(buf, outgredient.resolve());
                }
            }
        };
    }
}
