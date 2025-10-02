package com.yhj2014.technological_revolution.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.energy.EnergyStorage;

public class EnergyToolItem extends Item {
    private final int maxEnergy;
    private final int energyPerUse;
    private final Tier tier;
    private final float attackDamage;
    private final float attackSpeed;

    public EnergyToolItem(int maxEnergy, int energyPerUse, Tier tier, float attackDamage, float attackSpeed, Properties properties) {
        super(properties.stacksTo(1));
        this.maxEnergy = maxEnergy;
        this.energyPerUse = energyPerUse;
        this.tier = tier;
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public int getEnergyPerUse() {
        return energyPerUse;
    }

    public Tier getTier() {
        return tier;
    }

    public float getAttackDamage() {
        return attackDamage;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public int getEnchantmentValue() {
        return tier.getEnchantmentValue();
    }
}