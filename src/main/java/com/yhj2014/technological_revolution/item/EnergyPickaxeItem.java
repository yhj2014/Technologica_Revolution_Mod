package com.yhj2014.technological_revolution.item;

import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;
import java.util.List;

public class EnergyPickaxeItem extends PickaxeItem {
    private final int maxEnergy;
    private final int energyPerUse;

    public EnergyPickaxeItem(int maxEnergy, int energyPerUse, Tier tier, int attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties.stacksTo(1));
        this.maxEnergy = maxEnergy;
        this.energyPerUse = energyPerUse;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public int getEnchantmentValue() {
        return Tiers.DIAMOND.getEnchantmentValue();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable net.minecraft.world.level.Level level, List<Component> tooltip, net.minecraft.client.gui.screens.Screen.TooltipFlag flag) {
        stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(energyStorage -> {
            tooltip.add(Component.literal("Energy: " + energyStorage.getEnergyStored() + "/" + energyStorage.getMaxEnergyStored() + " FE"));
        });
        super.appendHoverText(stack, level, tooltip, flag);
    }

    @Override
    public <T extends net.minecraft.world.entity.LivingEntity> int damageItem(ItemStack stack, int amount, T entity, java.util.function.Consumer<T> onBroken) {
        // 消耗能量而不是耐久度
        stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(energyStorage -> {
            energyStorage.extractEnergy(energyPerUse, false);
        });
        
        // 如果能量不足，才消耗耐久度
        int energyStored = stack.getCapability(CapabilityEnergy.ENERGY)
            .map(IEnergyStorage::getEnergyStored)
            .orElse(0);
            
        if (energyStored < energyPerUse) {
            return super.damageItem(stack, amount, entity, onBroken);
        }
        
        return 0; // 不消耗耐久度
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
}