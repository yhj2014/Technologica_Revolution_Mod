package com.yhj2014.technological_revolution.container;

import com.yhj2014.technological_revolution.TechnologicalRevolutionMod;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class AdvancedFurnaceContainer extends AbstractMachineContainer {
    public AdvancedFurnaceContainer(int id, Inventory playerInventory, IItemHandler itemHandler, ContainerData data) {
        super(TechnologicalRevolutionMod.ADVANCED_FURNACE_CONTAINER.get(), id, itemHandler, data);
        
        // 添加输入槽位
        if (itemHandler != null) {
            this.addSlot(new SlotItemHandler(itemHandler, 0, 56, 35)); // 输入槽位
            this.addSlot(new SlotItemHandler(itemHandler, 1, 116, 35)); // 输出槽位
        }
        
        // 添加玩家物品栏
        addPlayerInventory(playerInventory);
    }

    public AdvancedFurnaceContainer(int id, Inventory playerInventory) {
        this(id, playerInventory, null, new SimpleContainerData(4));
    }
}