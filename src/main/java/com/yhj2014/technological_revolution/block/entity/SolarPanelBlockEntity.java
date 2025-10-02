package com.yhj2014.technological_revolution.block.entity;

import com.yhj2014.technological_revolution.TechnologicalRevolutionMod;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class SolarPanelBlockEntity extends AbstractMachineBlockEntity {
    public SolarPanelBlockEntity(BlockPos pos, BlockState state) {
        super(TechnologicalRevolutionMod.SOLAR_PANEL_ENTITY.get(), pos, state, 10000);
    }

    @Override
    protected void process() {
        // 太阳能发电机逻辑：在白天且有光照时发电
        if (level != null && level.isDay() && level.canSeeSky(worldPosition.above())) {
            // 每tick产生10FE能量
            int energy = energyStorage.receiveEnergy(10, false);
            
            // 如果成功产生能量，触发进度
            if (energy > 0 && level instanceof net.minecraft.server.level.ServerLevel) {
                // 获取附近的玩家并触发进度
                net.minecraft.world.entity.player.Player player = level.getNearestPlayer(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), 10, false);
                if (player instanceof net.minecraft.server.level.ServerPlayer) {
                    com.yhj2014.technological_revolution.advancement.ModAdvancements.GENERATE_POWER.trigger((net.minecraft.server.level.ServerPlayer) player);
                }
            }
        }
    }
    
    @Override
    protected int getProgress() {
        // 太阳能发电机没有进度概念，返回0
        return 0;
    }
    
    @Override
    protected int getMaxProgress() {
        // 太阳能发电机没有进度概念，返回0
        return 0;
    }
    
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
        return new com.yhj2014.technological_revolution.container.SolarPanelContainer(id, playerInventory, itemHandler, data);
    }
}