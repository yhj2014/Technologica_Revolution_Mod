package com.yhj2014.technological_revolution.block.entity;

import com.yhj2014.technological_revolution.TechnologicalRevolutionMod;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class WindTurbineBlockEntity extends AbstractMachineBlockEntity {
    public WindTurbineBlockEntity(BlockPos pos, BlockState state) {
        super(TechnologicalRevolutionMod.WIND_TURBINE_ENTITY.get(), pos, state, 15000);
    }

    @Override
    protected void process() {
        // 风力发电机逻辑：在Y坐标大于80时效率更高
        if (level != null) {
            int y = worldPosition.getY();
            if (y > 80) {
                // 在高海拔每tick产生15-25FE能量（根据高度）
                int energy = 15 + (y - 80) / 10;
                if (energy > 25) energy = 25;
                energyStorage.receiveEnergy(energy, false);
            } else {
                // 在低海拔每tick产生5FE能量
                energyStorage.receiveEnergy(5, false);
            }
        }
    }
    
    @Override
    protected int getProgress() {
        // 风力发电机没有进度概念，返回0
        return 0;
    }
    
    @Override
    protected int getMaxProgress() {
        // 风力发电机没有进度概念，返回0
        return 0;
    }
    
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
        // 风力发电机使用太阳能发电机的容器，因为它们都没有物品槽位
        return new com.yhj2014.technological_revolution.container.SolarPanelContainer(id, playerInventory, itemHandler, data);
    }
}