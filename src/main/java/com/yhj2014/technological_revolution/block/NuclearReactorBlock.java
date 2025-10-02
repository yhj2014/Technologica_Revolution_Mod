package com.yhj2014.technological_revolution.block;

import com.yhj2014.technological_revolution.block.entity.AbstractMachineBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class NuclearReactorBlock extends AbstractMachineBlock {
    public NuclearReactorBlock() {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).strength(4.0F, 4.0F));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new com.yhj2014.technological_revolution.block.entity.NuclearReactorBlockEntity(pos, state);
    }
}