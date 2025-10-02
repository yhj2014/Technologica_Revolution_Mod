package com.yhj2014.technological_revolution.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import com.yhj2014.technological_revolution.TechnologicalRevolutionMod;
import com.yhj2014.technological_revolution.container.AdvancedFurnaceContainer;

public class AdvancedFurnaceScreen extends AbstractMachineScreen<AdvancedFurnaceContainer> {
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(TechnologicalRevolutionMod.MODID, "textures/gui/advanced_furnace.png");

    public AdvancedFurnaceScreen(AdvancedFurnaceContainer menu, Inventory playerInventory, Component title) {
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
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        super.renderBg(poseStack, partialTick, mouseX, mouseY);
        
        // 渲染能量条
        renderEnergyBar(poseStack, leftPos + 10, topPos + 20, menu.getEnergyStored(), menu.getMaxEnergyStored());
        
        // 渲染进度条
        renderProgressBar(poseStack, leftPos + 79, topPos + 35, menu.getProgress(), menu.getMaxProgress());
        
        // 如果是核反应堆，渲染热量条
        // 通过标题判断是否为核反应堆
        if (this.title.getString().equals("Nuclear Reactor")) {
            // 渲染热量条（假设容器中添加了热量数据访问方法）
            // 这里需要在容器中添加热量数据访问
        }
    }
}