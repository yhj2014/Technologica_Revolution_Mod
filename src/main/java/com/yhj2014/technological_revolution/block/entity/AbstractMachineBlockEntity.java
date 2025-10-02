package com.yhj2014.technological_revolution.block.entity;

import com.yhj2014.technological_revolution.energy.ModEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class AbstractMachineBlockEntity extends BlockEntity implements MenuProvider {
    protected ModEnergyStorage energyStorage;
    protected LazyOptional<IEnergyStorage> energyStorageLazyOptional;
    protected ItemStackHandler itemHandler;
    protected LazyOptional<IItemHandler> itemHandlerLazyOptional;
    protected final net.minecraft.world.inventory.ContainerData data;
    private int tickCounter = 0; // 用于减少计算频率的计数器

    public AbstractMachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int energyCapacity) {
        super(type, pos, state);
        this.energyStorage = new ModEnergyStorage(energyCapacity, 1000, 1000);
        this.energyStorageLazyOptional = LazyOptional.of(() -> energyStorage);
        this.itemHandler = new ItemStackHandler(2); // 默认2个槽位：输入和输出
        this.itemHandlerLazyOptional = LazyOptional.of(() -> itemHandler);
        
        // 创建容器数据访问器
        this.data = new net.minecraft.world.inventory.ContainerData() {
            @Override
            public int get(int index) {
                switch (index) {
                    case 0: return energyStorage.getEnergyStored();
                    case 1: return energyStorage.getMaxEnergyStored();
                    case 2: return getProgress();
                    case 3: return getMaxProgress();
                    default: return 0;
                }
            }

            @Override
            public void set(int index, int value) {
                // 不需要实现
            }

            @Override
            public int getCount() {
                return 4; // 能量存储、最大能量、进度、最大进度
            }
        };
    }

    public void tick() {
        if (level != null && !level.isClientSide) {
            tickCounter++;
            
            // 每tick执行机器特定逻辑
            process();
            
            // 每5tick尝试向相邻方块传输能量，减少计算量
            if (tickCounter % 5 == 0) {
                transferEnergy();
            }
            
            // 每20tick更新客户端，减少网络流量
            if (tickCounter % 20 == 0) {
                setChanged();
            }
        }
    }
    
    protected void transferEnergy() {
        // 只有当当前方块有能量时才传输
        if (energyStorage.getEnergyStored() <= 0) {
            return;
        }
        
        // 向所有方向传输能量
        for (net.minecraft.core.Direction direction : net.minecraft.core.Direction.values()) {
            net.minecraft.core.BlockPos neighborPos = worldPosition.relative(direction);
            net.minecraft.world.level.block.entity.BlockEntity neighborEntity = level.getBlockEntity(neighborPos);
            
            if (neighborEntity != null) {
                net.minecraftforge.common.util.LazyOptional<net.minecraftforge.energy.IEnergyStorage> neighborCap = 
                    neighborEntity.getCapability(net.minecraftforge.common.capabilities.ForgeCapabilities.ENERGY, direction.getOpposite());
                if (neighborCap.isPresent()) {
                    net.minecraftforge.energy.IEnergyStorage neighborStorage = neighborCap.orElse(null);
                    if (neighborStorage != null && neighborStorage.canReceive() && neighborStorage.getEnergyStored() < neighborStorage.getMaxEnergyStored()) {
                        // 从当前机器向邻居传输能量
                        int extracted = energyStorage.extractEnergy(100, true);
                        if (extracted > 0) {
                            int accepted = neighborStorage.receiveEnergy(extracted, false);
                            if (accepted > 0) {
                                energyStorage.extractEnergy(accepted, false);
                                // 一次tick只向一个方向传输能量，减少计算量
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    protected abstract void process();
    
    protected abstract int getProgress();
    
    protected abstract int getMaxProgress();

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("energy")) {
            energyStorage.setEnergy(tag.getInt("energy"));
        }
        if (tag.contains("items")) {
            itemHandler.deserializeNBT(tag.getCompound("items"));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("energy", energyStorage.getEnergyStored());
        tag.put("items", itemHandler.serializeNBT());
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable net.minecraft.core.Direction side) {
        if (cap == ForgeCapabilities.ENERGY) {
            return energyStorageLazyOptional.cast();
        }
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return itemHandlerLazyOptional.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        energyStorageLazyOptional.invalidate();
        itemHandlerLazyOptional.invalidate();
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
    
    @Override
    public Component getDisplayName() {
        return Component.translatable("container." + this.getLevel().getBlockState(this.worldPosition).getBlock().getDescriptionId());
    }
}