package com.yhj2014.technological_revolution.client.render;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.ItemStack;

public class ModItemColor implements ItemColor {
    // 颜色定义
    private static final int METAL_BASE = 0xFF808080; // 灰色金属基色
    private static final int ENERGY_INDICATOR = 0xFF00FF00; // 能量指示色（绿色）
    private static final int URANIUM_COLOR = 0xFF00FF00; // 铀颜色
    private static final int LITHIUM_COLOR = 0xFFFFC0CB; // 锂颜色
    private static final int TITANIUM_COLOR = 0xFF00FFFF; // 钛颜色

    @Override
    public int getColor(ItemStack stack, int tintIndex) {
        // 根据物品类型返回不同颜色
        String itemName = stack.getItem().getDescriptionId();
        
        switch (tintIndex) {
            case 0: // 基础色
                if (itemName.contains("uranium")) {
                    return URANIUM_COLOR;
                } else if (itemName.contains("lithium")) {
                    return LITHIUM_COLOR;
                } else if (itemName.contains("titanium")) {
                    return TITANIUM_COLOR;
                } else {
                    // 工具和护甲使用金属基色
                    return METAL_BASE;
                }
            case 1: // 能量指示色
                // 用于显示能量状态的颜色
                return ENERGY_INDICATOR;
            default:
                return 0xFFFFFFFF; // 默认白色
        }
    }
}