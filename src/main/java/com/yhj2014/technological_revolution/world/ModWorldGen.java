package com.yhj2014.technological_revolution.world;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;
import com.yhj2014.technological_revolution.TechnologicalRevolutionMod;

import java.util.List;

@Mod.EventBusSubscriber(modid = TechnologicalRevolutionMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModWorldGen {
    @SubscribeEvent
    public static void registerWorldGen(RegisterEvent event) {
        // 世界生成将在后续版本中实现
    }
}