package com.yhj2014.technological_revolution.event;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.yhj2014.technological_revolution.TechnologicalRevolutionMod;
import com.yhj2014.technological_revolution.item.EnergyArmorItem;

@Mod.EventBusSubscriber(modid = TechnologicalRevolutionMod.MODID)
public class ModEvents {
    
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        // 只处理玩家受到的伤害
        if (event.getEntity() instanceof Player player) {
            // 计算可以吸收的伤害
            float damageToAbsorb = event.getAmount();
            float remainingDamage = damageToAbsorb;
            
            // 检查玩家身上的能量护甲
            for (ItemStack stack : player.getArmorSlots()) {
                if (stack.getItem() instanceof EnergyArmorItem energyArmor) {
                    // 检查护甲是否有足够的能量
                    IEnergyStorage energyStorage = stack.getCapability(ForgeCapabilities.ENERGY).orElse(null);
                    if (energyStorage != null) {
                        int energyPerDamage = energyArmor.getEnergyPerDamage();
                        int energyNeeded = (int) (remainingDamage * energyPerDamage);
                        
                        // 消耗能量并减少伤害
                        int energyExtracted = energyStorage.extractEnergy(energyNeeded, false);
                        float damageAbsorbed = (float) energyExtracted / energyPerDamage;
                        
                        // 更新剩余伤害
                        remainingDamage -= damageAbsorbed;
                    }
                }
            }
            
            // 设置新的伤害值
            event.setAmount(remainingDamage);
        }
    }
}