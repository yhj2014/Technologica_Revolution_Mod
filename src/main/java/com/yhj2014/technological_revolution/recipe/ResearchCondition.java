package com.yhj2014.technological_revolution.recipe;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
import com.yhj2014.technological_revolution.research.ResearchManager;

public class ResearchCondition implements ICondition {
    private final String researchId;
    
    public ResearchCondition(String researchId) {
        this.researchId = researchId;
    }
    
    @Override
    public boolean test(IContext context) {
        // 在服务器端检查玩家是否解锁了研究
        Player player = context.getPlayer();
        if (player != null) {
            return ResearchManager.isResearchUnlocked(player, researchId);
        }
        // 在客户端或其他情况下，默认允许配方显示
        return true;
    }
    
    @Override
    public String toString() {
        return "research_unlocked(\"" + researchId + "\")";
    }
    
    public static class Serializer implements IConditionSerializer<ResearchCondition> {
        public static final Serializer INSTANCE = new Serializer();
        
        @Override
        public void write(FriendlyByteBuf buffer, ResearchCondition condition) {
            buffer.writeUtf(condition.researchId);
        }
        
        @Override
        public ResearchCondition read(FriendlyByteBuf buffer) {
            return new ResearchCondition(buffer.readUtf());
        }
        
        @Override
        public ResourceLocation getID() {
            return new ResourceLocation("technological_revolution:research_unlocked");
        }
        
        @Override
        public ResearchCondition read(JsonObject json) {
            return new ResearchCondition(GsonHelper.getAsString(json, "research"));
        }
    }
}