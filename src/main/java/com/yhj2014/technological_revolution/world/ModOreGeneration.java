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
import com.yhj2014.technological_revolution.TechnologicalRevolutionMod;

import java.util.List;

public class ModOreGeneration {
    public static final ResourceKey<ConfiguredFeature<?, ?>> URANIUM_ORE_KEY = registerKey("uranium_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LITHIUM_ORE_KEY = registerKey("lithium_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TITANIUM_ORE_KEY = registerKey("titanium_ore");

    public static final ResourceKey<PlacedFeature> URANIUM_ORE_PLACED_KEY = registerPlacedKey("uranium_ore_placed");
    public static final ResourceKey<PlacedFeature> LITHIUM_ORE_PLACED_KEY = registerPlacedKey("lithium_ore_placed");
    public static final ResourceKey<PlacedFeature> TITANIUM_ORE_PLACED_KEY = registerPlacedKey("titanium_ore_placed");

    public static void bootstrapConfigured(BootstapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<net.minecraft.world.level.block.Block> blocks = context.lookup(Registries.BLOCK);
        
        RuleTest stoneReplaceable = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceable = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        
        List<OreConfiguration.TargetBlockState> uraniumOres = List.of(
            OreConfiguration.target(stoneReplaceable, TechnologicalRevolutionMod.URANIUM_ORE.get().defaultBlockState()),
            OreConfiguration.target(deepslateReplaceable, TechnologicalRevolutionMod.URANIUM_ORE.get().defaultBlockState())
        );
        
        List<OreConfiguration.TargetBlockState> lithiumOres = List.of(
            OreConfiguration.target(stoneReplaceable, TechnologicalRevolutionMod.LITHIUM_ORE.get().defaultBlockState()),
            OreConfiguration.target(deepslateReplaceable, TechnologicalRevolutionMod.LITHIUM_ORE.get().defaultBlockState())
        );
        
        List<OreConfiguration.TargetBlockState> titaniumOres = List.of(
            OreConfiguration.target(stoneReplaceable, TechnologicalRevolutionMod.TITANIUM_ORE.get().defaultBlockState()),
            OreConfiguration.target(deepslateReplaceable, TechnologicalRevolutionMod.TITANIUM_ORE.get().defaultBlockState())
        );
        
        context.register(URANIUM_ORE_KEY, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(uraniumOres, 4)));
        context.register(LITHIUM_ORE_KEY, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(lithiumOres, 6)));
        context.register(TITANIUM_ORE_KEY, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(titaniumOres, 3)));
    }
    
    public static void bootstrapPlaced(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        
        Holder<ConfiguredFeature<?, ?>> uraniumOre = configuredFeatures.getOrThrow(URANIUM_ORE_KEY);
        Holder<ConfiguredFeature<?, ?>> lithiumOre = configuredFeatures.getOrThrow(LITHIUM_ORE_KEY);
        Holder<ConfiguredFeature<?, ?>> titaniumOre = configuredFeatures.getOrThrow(TITANIUM_ORE_KEY);
        
        context.register(URANIUM_ORE_PLACED_KEY, new PlacedFeature(uraniumOre, commonOrePlacement(4, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(20)))));
        context.register(LITHIUM_ORE_PLACED_KEY, new PlacedFeature(lithiumOre, commonOrePlacement(6, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(40)))));
        context.register(TITANIUM_ORE_PLACED_KEY, new PlacedFeature(titaniumOre, commonOrePlacement(2, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(64)))));
    }
    
    private static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier height) {
        return List.of(CountPlacement.of(count), InSquarePlacement.spread(), height, BiomeFilter.placement());
    }
    
    private static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(TechnologicalRevolutionMod.MODID, name));
    }
    
    private static ResourceKey<PlacedFeature> registerPlacedKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(TechnologicalRevolutionMod.MODID, name));
    }
}