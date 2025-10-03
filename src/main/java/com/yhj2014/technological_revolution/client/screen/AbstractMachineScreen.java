package com.yhj2014.technological_revolution.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public abstract class AbstractMachineScreen<T extends net.minecraft.world.inventory.AbstractContainerMenu> extends AbstractContainerScreen<T> {
    public AbstractMachineScreen(T menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        // 渲染背景
        renderBackground(guiGraphics);
        
        // 渲染机器GUI背景（使用纯色）
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        
        // 绘制背景框（深灰色）
        guiGraphics.fill(x, y, x + imageWidth, y + imageHeight, 0xFF404040);
        
        // 绘制标题栏（稍浅的灰色）
        guiGraphics.fill(x, y, x + imageWidth, y + 15, 0xFF606060);
        
        // 绘制玩家物品栏区域（稍浅的灰色）
        guiGraphics.fill(x + 7, y + 70, x + imageWidth - 7, y + imageHeight - 7, 0xFF505050);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    protected void renderEnergyBar(GuiGraphics guiGraphics, int x, int y, int energyStored, int maxEnergyStored) {
        // 渲染能量条
        int energyHeight = 52;
        int energyWidth = 14;
        int energyLevel = maxEnergyStored > 0 ? (energyStored * energyHeight / maxEnergyStored) : 0;
        
        // 背景
        guiGraphics.fill(x, y, x + energyWidth, y + energyHeight, 0xFF555555);
        
        // 能量条
        guiGraphics.fill(x, y + energyHeight - energyLevel, x + energyWidth, y + energyHeight, 0xFF00FF00);
    }

    protected void renderProgressBar(GuiGraphics guiGraphics, int x, int y, int progress, int maxProgress) {
        // 渲染进度条
        int progressBarWidth = 24;
        int progressBarHeight = 16;
        int progressLevel = maxProgress > 0 ? (progress * progressBarWidth / maxProgress) : 0;
        
        // 背景
        guiGraphics.fill(x, y, x + progressBarWidth, y + progressBarHeight, 0xFF555555);
        
        // 进度条
        guiGraphics.fill(x, y, x + progressLevel, y + progressBarHeight, 0xFFFF0000);
    }
}