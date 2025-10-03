package com.yhj2014.technological_revolution.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import com.yhj2014.technological_revolution.container.OreCrusherContainer;

public class OreCrusherScreen extends AbstractMachineScreen<OreCrusherContainer> {
    public OreCrusherScreen(OreCrusherContainer menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        // 绘制GUI标题
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 0x404040);
        
        // 绘制玩家物品栏标题
        guiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 0x404040);
        
        // 显示能量信息
        String energyText = menu.getEnergyStored() + "/" + menu.getMaxEnergyStored() + " FE";
        guiGraphics.drawString(this.font, energyText, 10, 10, 0xFFFFFF);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        super.renderBg(guiGraphics, partialTick, mouseX, mouseY);
        
        // 渲染能量条
        renderEnergyBar(guiGraphics, leftPos + 10, topPos + 20, menu.getEnergyStored(), menu.getMaxEnergyStored());
        
        // 渲染进度条
        renderProgressBar(guiGraphics, leftPos + 79, topPos + 35, menu.getProgress(), menu.getMaxProgress());
    }
}