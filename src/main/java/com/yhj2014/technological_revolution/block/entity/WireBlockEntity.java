package com.yhj2014.technological_revolution.block.entity;

import com.yhj2014.technological_revolution.TechnologicalRevolutionMod;
import com.yhj2014.technological_revolution.energy.ModEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WireBlockEntity extends BlockEntity {
    private final ModEnergyStorage energyStorage;
    private final LazyOptional<IEnergyStorage> energyStorageLazyOptional;
    private boolean[] connections = new boolean[6]; // 记录六个方向的连接状态

    public WireBlockEntity(BlockPos pos, BlockState state) {
        super(TechnologicalRevolutionMod.WIRE_BLOCK_ENTITY.get(), pos, state);
        // 电线的能量存储很小，主要用于缓冲传输
        this.energyStorage = new ModEnergyStorage(1000, 1000, 1000);
        this.energyStorageLazyOptional = LazyOptional.of(() -> energyStorage);
    }

    public void tick() {
        if (level != null && !level.isClientSide) {
            // 每5tick尝试向相邻方块传输能量，减少计算量
            if (level.getGameTime() % 5 == 0) {
                transferEnergy();
            }
            
            // 每100tick更新连接状态，减少计算量
            if (level.getGameTime() % 100 == 0) {
                updateConnections();
            }
            
            // 如果数据发生变化，通知客户端更新
            if (level.getGameTime() % 20 == 0) { // 每秒更新一次
                setChanged();
            }
        }
    }
    
    protected void updateConnections() {
        if (level != null && !level.isClientSide) {
            boolean[] newConnections = new boolean[6];
            boolean changed = false;
            
            // 检查六个方向是否有可以连接的方块
            for (Direction direction : Direction.values()) {
                BlockPos neighborPos = worldPosition.relative(direction);
                BlockEntity neighborEntity = level.getBlockEntity(neighborPos);
                
                if (neighborEntity != null) {
                    LazyOptional<IEnergyStorage> neighborCap = neighborEntity.getCapability(ForgeCapabilities.ENERGY, direction.getOpposite());
                    if (neighborCap.isPresent()) {
                        newConnections[direction.ordinal()] = true;
                    }
                }
                
                // 检查连接状态是否改变
                if (newConnections[direction.ordinal()] != connections[direction.ordinal()]) {
                    changed = true;
                }
            }
            
            // 如果连接状态改变，更新方块状态以重新渲染
            if (changed) {
                connections = newConnections;
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            }
        }
    }
    
    protected void transferEnergy() {
        if (level != null && !level.isClientSide) {
            // 只有当当前电线有能量时才传输
            if (energyStorage.getEnergyStored() <= 0) {
                return;
            }
            
            boolean transferred = false;
            
            // 向所有连接的方向传输能量
            for (Direction direction : Direction.values()) {
                BlockPos neighborPos = worldPosition.relative(direction);
                BlockEntity neighborEntity = level.getBlockEntity(neighborPos);
                
                if (neighborEntity != null) {
                    LazyOptional<IEnergyStorage> neighborCap = neighborEntity.getCapability(ForgeCapabilities.ENERGY, direction.getOpposite());
                    if (neighborCap.isPresent()) {
                        IEnergyStorage neighborStorage = neighborCap.orElse(null);
                        if (neighborStorage != null && neighborStorage.canReceive() && neighborStorage.getEnergyStored() < neighborStorage.getMaxEnergyStored()) {
                            // 从当前电线向邻居传输能量
                            int extracted = energyStorage.extractEnergy(100, true);
                            if (extracted > 0) {
                                int accepted = neighborStorage.receiveEnergy(extracted, false);
                                if (accepted > 0) {
                                    energyStorage.extractEnergy(accepted, false);
                                    transferred = true;
                                }
                            }
                        }
                    }
                }
            }
            
            // 如果成功传输能量，触发进度
            if (transferred && level instanceof net.minecraft.server.level.ServerLevel) {
                net.minecraft.world.entity.player.Player player = level.getNearestPlayer(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), 10, false);
                if (player instanceof net.minecraft.server.level.ServerPlayer) {
                    com.yhj2014.technological_revolution.advancement.ModAdvancements.TRANSMIT_ENERGY.trigger((net.minecraft.server.level.ServerPlayer) player);
                }
            }
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("energy")) {
            energyStorage.setEnergy(tag.getInt("energy"));
        }
        if (tag.contains("connections")) {
            connections = new boolean[6];
            int[] connectionData = tag.getIntArray("connections");
            for (int i = 0; i < Math.min(connectionData.length, 6); i++) {
                connections[i] = connectionData[i] != 0;
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("energy", energyStorage.getEnergyStored());
        
        // 保存连接状态
        int[] connectionData = new int[6];
        for (int i = 0; i < 6; i++) {
            connectionData[i] = connections[i] ? 1 : 0;
        }
        tag.putIntArray("connections", connectionData);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY) {
            return energyStorageLazyOptional.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        energyStorageLazyOptional.invalidate();
    }
    
    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }
    
    public boolean[] getConnections() {
        return connections;
    }
}