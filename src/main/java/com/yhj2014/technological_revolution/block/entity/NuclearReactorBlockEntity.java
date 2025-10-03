package com.yhj2014.technological_revolution.block.entity;

import com.yhj2014.technological_revolution.TechnologicalRevolutionMod;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;

import javax.annotation.Nullable;

public class NuclearReactorBlockEntity extends AbstractMachineBlockEntity {
    private int progress = 0;
    private static final int MAX_PROGRESS = 200; // 核反应堆工作周期更长
    private int heat = 0; // 热量值，过高会导致爆炸
    private static final int MAX_HEAT = 1000;
    private static final int HEAT_PER_TICK = 5; // 每tick产生的热量
    private static final int COOLING_PER_TICK = 3; // 每tick冷却的热量

    public NuclearReactorBlockEntity(BlockPos pos, BlockState state) {
        super(TechnologicalRevolutionMod.NUCLEAR_REACTOR_ENTITY.get(), pos, state, 100000); // 更大的能量存储
    }

    @Override
    protected void process() {
        // 核反应堆逻辑：消耗铀燃料产生大量能量，但会产生热量
        if (level != null && !level.isClientSide) {
            ItemStack fuel = itemHandler.getStackInSlot(0); // 燃料槽位
            ItemStack coolant = itemHandler.getStackInSlot(1); // 冷却剂槽位
            
            // 检查是否有燃料
            if (!fuel.isEmpty() && fuel.getItem() == TechnologicalRevolutionMod.URANIUM_INGOT.get()) {
                // 每5tick增加一次热量，减少计算量
                if (level.getGameTime() % 5 == 0) {
                    heat += HEAT_PER_TICK;
                }
                
                // 检查是否有冷却剂
                if (!coolant.isEmpty()) {
                    // 每5tick冷却一次热量，减少计算量
                    if (level.getGameTime() % 5 == 0) {
                        // 冷却热量
                        heat -= COOLING_PER_TICK;
                        if (heat < 0) heat = 0;
                    }
                    
                    // 消耗冷却剂
                    if (level.getGameTime() % 20 == 0) { // 每秒消耗一次冷却剂
                        coolant.shrink(1);
                    }
                }
                
                // 如果热量过高，有爆炸风险
                if (heat > MAX_HEAT * 0.8) {
                    // 每20tick更新一次警告方块，减少计算量
                    if (level.getGameTime() % 20 == 0) {
                        // 红石灯闪烁警告
                        level.setBlockAndUpdate(worldPosition.above(), net.minecraft.world.level.block.Blocks.REDSTONE_LAMP.defaultBlockState());
                    }
                }
                
                // 如果热量过高，爆炸
                if (heat > MAX_HEAT) {
                    explode();
                    return;
                }
                
                // 每tick产生100FE能量
                energyStorage.receiveEnergy(100, false);
                
                // 每100tick消耗一次燃料
                if (level.getGameTime() % 100 == 0) {
                    fuel.shrink(1);
                }
                
                // 每100tick触发一次核能进度，减少触发频率
                if (level.getGameTime() % 100 == 0) {
                    net.minecraft.world.entity.player.Player player = level.getNearestPlayer(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), 10, false);
                    if (player instanceof ServerPlayer) {
                        com.yhj2014.technological_revolution.advancement.ModAdvancements.NUCLEAR_POWER.trigger((ServerPlayer) player);
                    }
                }
            }
        }
    }
    
    private void explode() {
        if (level != null && !level.isClientSide) {
            // 移除反应堆方块
            level.removeBlock(worldPosition, false);
            
            // 创建爆炸
            level.explode(null, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), 4.0F, Level.ExplosionInteraction.BLOCK);
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
    
    public int getHeat() {
        return heat;
    }
    
    public int getMaxHeat() {
        return MAX_HEAT;
    }
    
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
        // 核反应堆使用专门的容器
        return new com.yhj2014.technological_revolution.container.NuclearReactorContainer(id, playerInventory, itemHandler, data);
    }
}