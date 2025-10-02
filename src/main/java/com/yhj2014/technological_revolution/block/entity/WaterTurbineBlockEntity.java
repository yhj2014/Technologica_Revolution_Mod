package com.yhj2014.technological_revolution.block.entity;

import com.yhj2014.technological_revolution.TechnologicalRevolutionMod;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

import javax.annotation.Nullable;

public class WaterTurbineBlockEntity extends AbstractMachineBlockEntity {
    public WaterTurbineBlockEntity(BlockPos pos, BlockState state) {
        super(TechnologicalRevolutionMod.WATER_TURBINE_ENTITY.get(), pos, state, 12000);
    }

    @Override
    protected void process() {
        // 水流发电机逻辑：检测周围的水流
        if (level != null) {
            // 检查周围是否有水流
            boolean hasWaterFlow = false;
            if (level.getFluidState(worldPosition.east()).getType() == Fluids.FLOWING_WATER ||
                level.getFluidState(worldPosition.west()).getType() == Fluids.FLOWING_WATER ||
                level.getFluidState(worldPosition.north()).getType() == Fluids.FLOWING_WATER ||
                level.getFluidState(worldPosition.south()).getType() == Fluids.FLOWING_WATER) {
                hasWaterFlow = true;
            }
            
            if (hasWaterFlow) {
                // 每tick产生12FE能量
                energyStorage.receiveEnergy(12, false);
            }
        }
    }
    
    @Override
    protected int getProgress() {
        // 水力发电机没有进度概念，返回0
        return 0;
    }
    
    @Override
    protected int getMaxProgress() {
        // 水力发电机没有进度概念，返回0
        return 0;
    }
    
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
        // 水力发电机使用太阳能发电机的容器，因为它们都没有物品槽位
        return new com.yhj2014.technological_revolution.container.SolarPanelContainer(id, playerInventory, itemHandler, data);
    }
}