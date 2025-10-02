package com.yhj2014.technological_revolution.container;

import com.yhj2014.technological_revolution.TechnologicalRevolutionMod;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraftforge.items.IItemHandler;

public class NuclearReactorContainer extends AbstractMachineContainer {
    public NuclearReactorContainer(int id, Inventory playerInventory, IItemHandler itemHandler, ContainerData data) {
        super(TechnologicalRevolutionMod.NUCLEAR_REACTOR_CONTAINER.get(), id, itemHandler, data);
        
        // 添加玩家物品栏
        addPlayerInventory(playerInventory);
    }

    public NuclearReactorContainer(int id, Inventory playerInventory) {
        this(id, playerInventory, null, new SimpleContainerData(6));
    }
    
    public int getHeat() {
        return data.get(4);
    }
    
    public int getMaxHeat() {
        return data.get(5);
    }
}