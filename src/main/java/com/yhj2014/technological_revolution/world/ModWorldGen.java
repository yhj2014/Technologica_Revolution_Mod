package com.yhj2014.technological_revolution.world;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.regitries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;
import com.yhj2014.technological_revolution.TechnologicalRevolutionMod;

import java.util.List;

@Mod.EventBusSubscriber(modid = TechnologicalRevolutionMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModWorldGen {
    @SubscribeEvent
    public static void registerWorldGen(RegisterEvent event) {
        event.register(Registries.CONFIGURED_FEATURE, helper -> {
            ModOreGeneration.bootstrapConfigured(new BootstapContext<ConfiguredFeature<?, ?>>() {
                @Override
                public <S, T> void register(ResourceKey<T> key, S value, net.minecraft.resources.ResourceKey<net.minecraft.core.Registry<T>> registryKey) {
                    helper.register(key, (T) value);
                }
                
                @Override
                public <T> HolderGetter<T> lookup(ResourceKey<net.minecraft.core.Registry<T>> registryKey) {
                    return helper.getRegistryDataPackRegistries().lookup(registryKey).orElseThrow();
                }
            });
        });
        
        event.register(Registries.PLACED_FEATURE, helper -> {
            ModOreGeneration.bootstrapPlaced(new BootstapContext<PlacedFeature>() {
                @Override
                public <S, T> void register(ResourceKey<T> key, S value, net.minecraft.resources.ResourceKey<net.minecraft.core.Registry<T>> registryKey) {
                    helper.register(key, (T) value);
                }
                
                @Override
                public <T> HolderGetter<T> lookup(ResourceKey<net.minecraft.core.Registry<T>> registryKey) {
                    return helper.getRegistryDataPackRegistries().lookup(registryKey).orElseThrow();
                }
            });
        });
    }
}