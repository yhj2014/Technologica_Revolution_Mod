package com.yhj2014.technological_revolution.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
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
    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        blit(poseStack, x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTick);
        renderTooltip(poseStack, mouseX, mouseY);
    }

    protected void renderEnergyBar(PoseStack poseStack, int x, int y, int energyStored, int maxEnergyStored) {
        // 渲染能量条
        int energyHeight = 52;
        int energyWidth = 14;
        int energyLevel = maxEnergyStored > 0 ? (energyStored * energyHeight / maxEnergyStored) : 0;
        
        // 背景
        fill(poseStack, x, y, x + energyWidth, y + energyHeight, 0xFF555555);
        
        // 能量条
        fill(poseStack, x, y + energyHeight - energyLevel, x + energyWidth, y + energyHeight, 0xFF00FF00);
    }

    protected void renderProgressBar(PoseStack poseStack, int x, int y, int progress, int maxProgress) {
        // 渲染进度条
        int progressBarWidth = 24;
        int progressBarHeight = 16;
        int progressLevel = maxProgress > 0 ? (progress * progressBarWidth / maxProgress) : 0;
        
        // 背景
        fill(poseStack, x, y, x + progressBarWidth, y + progressBarHeight, 0xFF555555);
        
        // 进度条
        fill(poseStack, x, y, x + progressLevel, y + progressBarHeight, 0xFFFF0000);
    }
}