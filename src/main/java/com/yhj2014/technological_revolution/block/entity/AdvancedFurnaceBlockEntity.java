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
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import java.util.Optional;

import javax.annotation.Nullable;

public class AdvancedFurnaceBlockEntity extends AbstractMachineBlockEntity {
    private int progress = 0;
    private static final int MAX_PROGRESS = 33; // 3倍速度，原版熔炉需要100tick

    public AdvancedFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(TechnologicalRevolutionMod.ADVANCED_FURNACE_ENTITY.get(), pos, state, 25000);
    }

    @Override
    protected void process() {
        // 高效熔炉逻辑：消耗能量快速熔炼物品
        if (energyStorage.getEnergyStored() >= 30) {
            if (progress < MAX_PROGRESS) {
                progress++;
                energyStorage.extractEnergy(5, false);
            } else {
                // 处理完成
                progress = 0;
                processSmelting();
            }
        }
    }
    
    private void processSmelting() {
        Level level = this.level;
        if (level == null) return;
        
        ItemStack input = itemHandler.getStackInSlot(0);
        if (input.isEmpty()) return;
        
        // 查找熔炼配方
        Optional<SmeltingRecipe> recipe = level.getRecipeManager()
            .getRecipeFor(RecipeType.SMELTING, new net.minecraft.world.item.crafting.SimpleContainer(input), level);
            
        if (recipe.isPresent()) {
            ItemStack result = recipe.get().getResultItem(level.registryAccess()).copy();
            ItemStack output = itemHandler.getStackInSlot(1);
            
            // 检查输出槽位是否有足够空间
            if (output.isEmpty() || (output.getItem() == result.getItem() && output.getCount() + result.getCount() <= output.getMaxStackSize())) {
                input.shrink(1);
                if (output.isEmpty()) {
                    itemHandler.setStackInSlot(1, result);
                } else {
                    output.grow(result.getCount());
                }
                
                // 给予经验（如果需要的话）
                // 这里可以添加经验球的生成逻辑
                
                // 触发进度
                if (level instanceof net.minecraft.server.level.ServerLevel) {
                    net.minecraft.world.entity.player.Player player = level.getNearestPlayer(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), 10, false);
                    if (player instanceof net.minecraft.server.level.ServerPlayer) {
                        com.yhj2014.technological_revolution.advancement.ModAdvancements.SMELT_FAST.trigger((net.minecraft.server.level.ServerPlayer) player);
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
        return new com.yhj2014.technological_revolution.container.AdvancedFurnaceContainer(id, playerInventory, itemHandler, data);
    }
}