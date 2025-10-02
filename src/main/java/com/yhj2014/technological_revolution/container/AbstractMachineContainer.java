package com.yhj2014.technological_revolution.container;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nullable;

public abstract class AbstractMachineContainer extends AbstractContainerMenu {
    protected final IItemHandler itemHandler;
    protected final ContainerData data;

    protected AbstractMachineContainer(@Nullable MenuType<?> menuType, int id, IItemHandler itemHandler, ContainerData data) {
        super(menuType, id);
        this.itemHandler = itemHandler;
        this.data = data;
    }

    protected void addPlayerInventory(Inventory playerInventory) {
        // 添加玩家物品栏
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        // 添加玩家快捷栏
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot sourceSlot = slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // 如果点击的是玩家物品栏的槽位
        if (index < 36) {
            // 尝试将物品移动到机器槽位
            if (!moveItemStackTo(sourceStack, 36, slots.size(), false)) {
                return ItemStack.EMPTY;
            }
        } 
        // 如果点击的是机器槽位
        else if (index < slots.size()) {
            // 将物品移回玩家物品栏
            if (!moveItemStackTo(sourceStack, 0, 36, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            return ItemStack.EMPTY;
        }

        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }

        sourceSlot.onTake(player, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    public int getEnergyStored() {
        return data.get(0);
    }

    public int getMaxEnergyStored() {
        return data.get(1);
    }

    public int getProgress() {
        return data.get(2);
    }

    public int getMaxProgress() {
        return data.get(3);
    }
    
    public int getHeat() {
        return data.getCount() > 4 ? data.get(4) : 0;
    }
    
    public int getMaxHeat() {
        return data.getCount() > 5 ? data.get(5) : 0;
    }
}