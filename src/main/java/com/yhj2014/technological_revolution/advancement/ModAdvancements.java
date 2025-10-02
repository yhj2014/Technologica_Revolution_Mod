package com.yhj2014.technological_revolution.advancement;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.resources.ResourceLocation;
import com.yhj2014.technological_revolution.TechnologicalRevolutionMod;

public class ModAdvancements {
    public static final ModAdvancementTrigger GENERATE_POWER = new ModAdvancementTrigger(
        new ResourceLocation(TechnologicalRevolutionMod.MODID, "generate_power"));
        
    public static final ModAdvancementTrigger CRUSH_ORE = new ModAdvancementTrigger(
        new ResourceLocation(TechnologicalRevolutionMod.MODID, "crush_ore"));
        
    public static final ModAdvancementTrigger SMELT_FAST = new ModAdvancementTrigger(
        new ResourceLocation(TechnologicalRevolutionMod.MODID, "smelt_fast"));
        
    public static final ModAdvancementTrigger TRANSMIT_ENERGY = new ModAdvancementTrigger(
        new ResourceLocation(TechnologicalRevolutionMod.MODID, "transmit_energy"));
        
    public static final ModAdvancementTrigger NUCLEAR_POWER = new ModAdvancementTrigger(
        new ResourceLocation(TechnologicalRevolutionMod.MODID, "nuclear_power"));

    public static void register() {
        CriteriaTriggers.register(GENERATE_POWER);
        CriteriaTriggers.register(CRUSH_ORE);
        CriteriaTriggers.register(SMELT_FAST);
        CriteriaTriggers.register(TRANSMIT_ENERGY);
        CriteriaTriggers.register(NUCLEAR_POWER);
    }
}