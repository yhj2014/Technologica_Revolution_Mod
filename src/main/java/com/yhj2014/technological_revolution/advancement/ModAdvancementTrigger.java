package com.yhj2014.technological_revolution.advancement;

import com.google.gson.JsonObject;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;

public class ModAdvancementTrigger extends SimpleCriterionTrigger<ModAdvancementTrigger.TriggerInstance> {
    private final ResourceLocation id;

    public ModAdvancementTrigger(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, (instance) -> {
            return instance.test(player);
        });
    }

    @Override
    protected TriggerInstance createInstance(JsonObject json, ContextAwarePredicate predicate, DeserializationContext context) {
        return new TriggerInstance(id, predicate);
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        public TriggerInstance(ResourceLocation id, ContextAwarePredicate predicate) {
            super(id, predicate);
        }
        
        public boolean test(ServerPlayer player) {
            return true;
        }
    }
}