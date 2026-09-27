/*
 * Copyright (c) NeoForged and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.neoforged.neoforge.common.crafting.outgredient;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.FluidStackTemplate;

import java.util.Optional;

/// An [Outgredient] for [FluidStackTemplate]s which are created from a [TagKey] and an optional fallback [Fluid].
/// Use [resolve] to get a concrete [FluidStackTemplate].
///
/// @param tag        The [TagKey] to use to resolve the outgredient.
/// @param fallback   The fallback to use if resolving the tag did not yield a result.
/// @param amount     The count to use. Corresponds to [FluidStackTemplate#amount()].
/// @param components The data components to use. Corresponds to [FluidStackTemplate#components()].
public record FluidTagOutgredient(TagKey<Fluid> tag, Optional<Holder<Fluid>> fallback, int amount, DataComponentPatch components) implements Outgredient<FluidStackTemplate> {
    public static final MapCodec<FluidTagOutgredient> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            TagKey.codec(Registries.FLUID).fieldOf("tag").forGetter(FluidTagOutgredient::tag),
            BuiltInRegistries.FLUID.holderByNameCodec().optionalFieldOf("fallback").forGetter(FluidTagOutgredient::fallback),
            ExtraCodecs.POSITIVE_INT.fieldOf("amount").forGetter(FluidTagOutgredient::amount),
            DataComponentPatch.CODEC.optionalFieldOf("components", DataComponentPatch.EMPTY).forGetter(FluidTagOutgredient::components)
    ).apply(inst, FluidTagOutgredient::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, FluidTagOutgredient> STREAM_CODEC = StreamCodec.composite(
            TagKey.streamCodec(Registries.FLUID), FluidTagOutgredient::tag,
            ByteBufCodecs.holderRegistry(Registries.FLUID).apply(ByteBufCodecs::optional), FluidTagOutgredient::fallback,
            ByteBufCodecs.VAR_INT, FluidTagOutgredient::amount,
            DataComponentPatch.STREAM_CODEC, FluidTagOutgredient::components,
            FluidTagOutgredient::new);

    /// @param tag        The [TagKey] to use to resolve the outgredient.
    /// @param fallback   The fallback to use if resolving the tag did not yield a result.
    /// @param amount     The count to use. Corresponds to [FluidStackTemplate#amount()].
    /// @param components The data components to use. Corresponds to [FluidStackTemplate#components()].
    public FluidTagOutgredient of(TagKey<Fluid> tag, Holder<Fluid> fallback, int amount, DataComponentPatch components) {
        return new FluidTagOutgredient(tag, Optional.of(fallback), amount, components);
    }

    /// @param tag        The [TagKey] to use to resolve the outgredient.
    /// @param amount     The count to use. Corresponds to [FluidStackTemplate#amount()].
    /// @param components The data components to use. Corresponds to [FluidStackTemplate#components()].
    public FluidTagOutgredient of(TagKey<Fluid> tag, int amount, DataComponentPatch components) {
        return new FluidTagOutgredient(tag, Optional.empty(), amount, components);
    }

    /// @param tag      The [TagKey] to use to resolve the outgredient.
    /// @param fallback The fallback to use if resolving the tag did not yield a result.
    /// @param amount   The count to use. Corresponds to [FluidStackTemplate#amount()].
    public FluidTagOutgredient of(TagKey<Fluid> tag, Holder<Fluid> fallback, int amount) {
        return of(tag, fallback, amount, DataComponentPatch.EMPTY);
    }

    /// @param tag    The [TagKey] to use to resolve the outgredient.
    /// @param amount The count to use. Corresponds to [FluidStackTemplate#amount()].
    public FluidTagOutgredient of(TagKey<Fluid> tag, int amount) {
        return of(tag, amount, DataComponentPatch.EMPTY);
    }

    @Override
    public FluidStackTemplate resolve() {
        return new FluidStackTemplate(fallback.map(Holder::value).orElse(Fluids.EMPTY), amount, components); //TODO
    }

    @Override
    public OutgredientType<? extends Outgredient<FluidStackTemplate>> type() {
        return NeoForgeMod.FLUID_TAG_OUTGREDIENT.get();
    }

    @Override
    public SlotDisplay display() {
        return new Display(this);
    }

    /// The [OutgredientSlotDisplay] associated with [FluidTagOutgredient]s.
    public record Display(FluidTagOutgredient outgredient) implements FluidOutgredientSlotDisplay {
        public static final MapCodec<Display> CODEC = FluidTagOutgredient.CODEC.xmap(Display::new, Display::outgredient);
        public static final StreamCodec<RegistryFriendlyByteBuf, Display> STREAM_CODEC = FluidTagOutgredient.STREAM_CODEC.map(Display::new, Display::outgredient);

        @Override
        public Type<? extends SlotDisplay> type() {
            return NeoForgeMod.FLUID_TAG_OUTGREDIENT_SLOT_DISPLAY.get();
        }
    }
}
