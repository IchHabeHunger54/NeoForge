/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.neoforged.neoforge.event;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import java.util.Collection;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;
import org.jetbrains.annotations.Nullable;

/**
 * This event is fired when the attributes for an ItemStack are being calculated.
 * Attributes are calculated on the server when equipping and unequipping an item to add and remove attributes respectively, both must be consistent.
 * Attributes are calculated on the client when rendering an item's tooltip to show relevant attributes.
 * <br>
 * Note that this event is fired regardless of if the stack has NBT overriding attributes or not. If your attribute should be
 * ignored when attributes are overridden, you can check for the presence of the {@code AttributeModifiers} tag.
 * <br>
 * This event is fired on the {@link NeoForge#EVENT_BUS}.
 */
public class ItemAttributeModifierEvent extends Event {
    private final ItemStack stack;
    private final EquipmentSlot slotType;
    private final Multimap<Holder<Attribute>, AttributeModifier> originalModifiers;
    private Multimap<Holder<Attribute>, AttributeModifier> unmodifiableModifiers;
    @Nullable
    private Multimap<Holder<Attribute>, AttributeModifier> modifiableModifiers;

    public ItemAttributeModifierEvent(ItemStack stack, EquipmentSlot slotType, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        this.stack = stack;
        this.slotType = slotType;
        this.unmodifiableModifiers = this.originalModifiers = modifiers;
    }

    /**
     * Returns an unmodifiable view of the attribute multimap. Use other methods from this event to modify the attributes map.
     * Note that adding attributes based on existing attributes may lead to inconsistent results between the tooltip (client)
     * and the actual attributes (server) if the listener order is different. Using {@link #getOriginalModifiers()} instead will give more consistent results.
     */
    public Multimap<Holder<Attribute>, AttributeModifier> getModifiers() {
        return this.unmodifiableModifiers;
    }

    /**
     * Returns the attribute map before any changes from other event listeners was made.
     */
    public Multimap<Holder<Attribute>, AttributeModifier> getOriginalModifiers() {
        return this.originalModifiers;
    }

    /**
     * Gets a modifiable map instance, creating it if the current map is currently unmodifiable
     */
    private Multimap<Holder<Attribute>, AttributeModifier> getModifiableMap() {
        if (this.modifiableModifiers == null) {
            this.modifiableModifiers = HashMultimap.create(this.originalModifiers);
            this.unmodifiableModifiers = Multimaps.unmodifiableMultimap(this.modifiableModifiers);
        }
        return this.modifiableModifiers;
    }

    /**
     * Adds a new attribute modifier to the given stack.
     * Modifier must have a consistent UUID for consistency between equipping and unequipping items.
     * Modifier name should clearly identify the mod that added the modifier.
     * 
     * @param attribute Attribute
     * @param modifier  Modifier instance.
     * @return True if the attribute was added, false if it was already present
     */
    public boolean addModifier(Holder<Attribute> attribute, AttributeModifier modifier) {
        return getModifiableMap().put(attribute, modifier);
    }

    /**
     * Removes a single modifier for the given attribute
     * 
     * @param attribute Attribute
     * @param modifier  Modifier instance
     * @return True if an attribute was removed, false if no change
     */
    public boolean removeModifier(Holder<Attribute> attribute, AttributeModifier modifier) {
        return getModifiableMap().remove(attribute, modifier);
    }

    /**
     * Removes all modifiers for the given attribute
     * 
     * @param attribute Attribute
     * @return Collection of removed modifiers
     */
    public Collection<AttributeModifier> removeAttribute(Holder<Attribute> attribute) {
        return getModifiableMap().removeAll(attribute);
    }

    /**
     * Removes all modifiers for all attributes
     */
    public void clearModifiers() {
        getModifiableMap().clear();
    }

    /** Gets the slot containing this stack */
    public EquipmentSlot getSlotType() {
        return this.slotType;
    }

    /** Gets the item stack instance */
    public ItemStack getItemStack() {
        return this.stack;
    }
}
