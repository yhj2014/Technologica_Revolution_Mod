package com.yhj2014.technological_revolution.item;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;

import javax.annotation.Nullable;
import java.util.List;

public class EnergyArmorItem extends ArmorItem {
    private final int maxEnergy;
    private final int energyPerDamage;

    public EnergyArmorItem(int maxEnergy, int energyPerDamage, ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties.stacksTo(1));
        this.maxEnergy = maxEnergy;
        this.energyPerDamage = energyPerDamage;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public int getEnchantmentValue() {
        return 15;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable net.minecraft.world.level.Level level, List<Component> tooltip, net.minecraft.client.gui.screens.Screen.TooltipFlag flag) {
        stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(energyStorage -> {
            tooltip.add(Component.literal("Energy: " + energyStorage.getEnergyStored() + "/" + energyStorage.getMaxEnergyStored() + " FE"));
        });
        super.appendHoverText(stack, level, tooltip, flag);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new ICapabilityProvider() {
            private final EnergyStorage energyStorage = new EnergyStorage(maxEnergy, maxEnergy, maxEnergy, maxEnergy);

            @Nullable
            @Override
            public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> cap, @Nullable net.minecraft.core.Direction side) {
                if (cap == CapabilityEnergy.ENERGY) {
                    return net.minecraftforge.common.util.LazyOptional.of(() -> energyStorage).cast();
                }
                return net.minecraftforge.common.util.LazyOptional.empty();
            }
        };
    }
    
    public int getMaxEnergy() {
        return maxEnergy;
    }
    
    public int getEnergyPerDamage() {
        return energyPerDamage;
    }
}