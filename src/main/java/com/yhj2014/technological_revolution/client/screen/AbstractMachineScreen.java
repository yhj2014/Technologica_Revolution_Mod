package com.yhj2014.technological_revolution.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public abstract class AbstractMachineScreen<T extends net.minecraft.world.inventory.AbstractContainerMenu> extends AbstractContainerScreen<T> {
    protected final ResourceLocation GUI_TEXTURE;

    public AbstractMachineScreen(T menu, Inventory playerInventory, Component title, ResourceLocation guiTexture) {
        super(menu, playerInventory, title);
        this.GUI_TEXTURE = guiTexture;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        guiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
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