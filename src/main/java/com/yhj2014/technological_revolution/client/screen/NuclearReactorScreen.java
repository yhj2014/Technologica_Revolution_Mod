package com.yhj2014.technological_revolution.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import com.yhj2014.technological_revolution.TechnologicalRevolutionMod;
import com.yhj2014.technological_revolution.container.NuclearReactorContainer;

public class NuclearReactorScreen extends AbstractMachineScreen<NuclearReactorContainer> {
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(TechnologicalRevolutionMod.MODID, "textures/gui/advanced_furnace.png");

    public NuclearReactorScreen(NuclearReactorContainer menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, GUI_TEXTURE);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
        // 绘制GUI标题
        drawString(poseStack, this.font, this.title, this.titleLabelX, this.titleLabelY, 0x404040);
        
        // 绘制玩家物品栏标题
        drawString(poseStack, this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 0x404040);
        
        // 显示能量信息
        String energyText = menu.getEnergyStored() + "/" + menu.getMaxEnergyStored() + " FE";
        drawString(poseStack, this.font, energyText, 10, 10, 0xFFFFFF);
        
        // 显示热量信息
        String heatText = menu.getHeat() + "/" + menu.getMaxHeat() + " Heat";
        drawString(poseStack, this.font, heatText, 10, 20, 0xFFFFFF);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        super.renderBg(poseStack, partialTick, mouseX, mouseY);
        
        // 渲染能量条
        renderEnergyBar(poseStack, leftPos + 10, topPos + 30, menu.getEnergyStored(), menu.getMaxEnergyStored());
        
        // 渲染进度条
        renderProgressBar(poseStack, leftPos + 79, topPos + 35, menu.getProgress(), menu.getMaxProgress());
        
        // 渲染热量条（红色）
        int heatHeight = 52;
        int heatWidth = 14;
        int heatLevel = menu.getMaxHeat() > 0 ? (menu.getHeat() * heatHeight / menu.getMaxHeat()) : 0;
        
        // 背景
        fill(poseStack, leftPos + 30, topPos + 30, leftPos + 30 + heatWidth, topPos + 30 + heatHeight, 0xFF555555);
        
        // 热量条（根据热量级别改变颜色）
        int heatColor = 0xFFFF0000; // 默认红色
        if (menu.getHeat() < menu.getMaxHeat() * 0.5) {
            heatColor = 0xFFFFA500; // 橙色
        } else if (menu.getHeat() < menu.getMaxHeat() * 0.8) {
            heatColor = 0xFFFFFF00; // 黄色
        }
        
        fill(poseStack, leftPos + 30, topPos + 30 + heatHeight - heatLevel, leftPos + 30 + heatWidth, topPos + 30 + heatHeight, heatColor);
    }
}