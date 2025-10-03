package com.yhj2014.technological_revolution.client.render;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class ModBlockColor implements BlockColor {
    // 颜色定义
    private static final int METAL_BASE = 0xFF808080; // 灰色金属基色
    private static final int ENERGY_INDICATOR = 0xFF00FF00; // 能量指示色（绿色）
    private static final int URANIUM_COLOR = 0xFF00FF00; // 铀矿颜色
    private static final int LITHIUM_COLOR = 0xFFFFC0CB; // 锂矿颜色
    private static final int TITANIUM_COLOR = 0xFF00FFFF; // 钛矿颜色

    @Override
    public int getColor(BlockState state, @Nullable BlockAndTintGetter level, @Nullable BlockPos pos, int tintIndex) {
        // 根据方块类型返回不同颜色
        String blockName = state.getBlock().getDescriptionId();
        
        switch (tintIndex) {
            case 0: // 基础色
                if (blockName.contains("uranium")) {
                    return URANIUM_COLOR;
                } else if (blockName.contains("lithium")) {
                    return LITHIUM_COLOR;
                } else if (blockName.contains("titanium")) {
                    return TITANIUM_COLOR;
                } else {
                    // 机器和电线使用金属基色
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