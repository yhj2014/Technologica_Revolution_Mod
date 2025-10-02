package com.yhj2014.technological_revolution.container;

import com.yhj2014.technological_revolution.TechnologicalRevolutionMod;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class SolarPanelContainer extends AbstractMachineContainer {
    public SolarPanelContainer(int id, Inventory playerInventory, IItemHandler itemHandler, ContainerData data) {
        super(TechnologicalRevolutionMod.SOLAR_PANEL_CONTAINER.get(), id, itemHandler, data);
        
        // 添加机器槽位（如果有需要的话）
        // 这里根据具体需求添加槽位
        
        // 添加玩家物品栏
        addPlayerInventory(playerInventory);
    }

    public SolarPanelContainer(int id, Inventory playerInventory) {
        this(id, playerInventory, null, new SimpleContainerData(4));
    }
}