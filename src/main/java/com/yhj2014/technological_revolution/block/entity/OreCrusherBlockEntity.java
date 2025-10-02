package com.yhj2014.technological_revolution.block.entity;

import com.yhj2014.technological_revolution.TechnologicalRevolutionMod;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class OreCrusherBlockEntity extends AbstractMachineBlockEntity {
    private int progress = 0;
    private static final int MAX_PROGRESS = 100;

    public OreCrusherBlockEntity(BlockPos pos, BlockState state) {
        super(TechnologicalRevolutionMod.ORE_CRUSHER_ENTITY.get(), pos, state, 20000);
    }

    @Override
    protected void process() {
        // 矿石粉碎机逻辑：消耗能量粉碎矿石
        if (energyStorage.getEnergyStored() >= 50) {
            if (progress < MAX_PROGRESS) {
                progress++;
                energyStorage.extractEnergy(10, false);
            } else {
                // 处理完成，产出2倍矿物
                progress = 0;
                processItems();
            }
        }
    }
    
    private void processItems() {
        // 处理物品：输入槽位的物品转换为输出槽位的物品（2倍数量）
        ItemStack input = itemHandler.getStackInSlot(0);
        if (!input.isEmpty()) {
            ItemStack output = itemHandler.getStackInSlot(1);
            ItemStack result = input.copy();
            result.setCount(input.getCount() * 2);
            
            // 检查输出槽位是否有足够空间
            if (output.isEmpty() || (output.getItem() == result.getItem() && output.getCount() + result.getCount() <= output.getMaxStackSize())) {
                input.shrink(1);
                if (output.isEmpty()) {
                    itemHandler.setStackInSlot(1, result);
                } else {
                    output.grow(result.getCount());
                }
                
                // 触发进度
                if (level instanceof net.minecraft.server.level.ServerLevel) {
                    net.minecraft.world.entity.player.Player player = level.getNearestPlayer(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), 10, false);
                    if (player instanceof net.minecraft.server.level.ServerPlayer) {
                        com.yhj2014.technological_revolution.advancement.ModAdvancements.CRUSH_ORE.trigger((net.minecraft.server.level.ServerPlayer) player);
                    }
                }
            }
        }
    }

    @Override
    protected int getProgress() {
        return progress;
    }

    @Override
    protected int getMaxProgress() {
        return MAX_PROGRESS;
    }
    
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
        return new com.yhj2014.technological_revolution.container.OreCrusherContainer(id, playerInventory, itemHandler, data);
    }
}