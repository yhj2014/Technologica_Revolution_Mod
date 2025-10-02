package com.yhj2014.technological_revolution.research;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.HashSet;
import java.util.Set;

public class ResearchManager {
    private static final String RESEARCH_DATA_KEY = "TechnologicalRevolutionResearch";
    
    public static class PlayerResearchData implements INBTSerializable<CompoundTag> {
        private final Set<String> unlockedResearch = new HashSet<>();
        
        public boolean isResearchUnlocked(String researchId) {
            return unlockedResearch.contains(researchId);
        }
        
        public void unlockResearch(String researchId) {
            unlockedResearch.add(researchId);
        }
        
        public Set<String> getUnlockedResearch() {
            return new HashSet<>(unlockedResearch);
        }
        
        @Override
        public CompoundTag serializeNBT() {
            CompoundTag tag = new CompoundTag();
            ListTag list = new ListTag();
            for (String research : unlockedResearch) {
                CompoundTag researchTag = new CompoundTag();
                researchTag.putString("Research", research);
                list.add(researchTag);
            }
            tag.put("UnlockedResearch", list);
            return tag;
        }
        
        @Override
        public void deserializeNBT(CompoundTag tag) {
            unlockedResearch.clear();
            ListTag list = tag.getList("UnlockedResearch", 10);
            for (int i = 0; i < list.size(); i++) {
                CompoundTag researchTag = list.getCompound(i);
                unlockedResearch.add(researchTag.getString("Research"));
            }
        }
    }
    
    public static PlayerResearchData getResearchData(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            // 在实际实现中，这里需要将数据存储在玩家的持久化数据中
            // 这里简化实现，每次返回新的实例
            return new PlayerResearchData();
        }
        return new PlayerResearchData();
    }
    
    public static boolean isResearchUnlocked(Player player, String researchId) {
        return getResearchData(player).isResearchUnlocked(researchId);
    }
    
    public static void unlockResearch(Player player, String researchId) {
        if (player instanceof ServerPlayer serverPlayer) {
            PlayerResearchData data = getResearchData(player);
            data.unlockResearch(researchId);
            // 在实际实现中，这里需要保存数据到玩家的持久化数据中
        }
    }
}