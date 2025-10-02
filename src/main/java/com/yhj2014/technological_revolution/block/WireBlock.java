package com.yhj2014.technological_revolution.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;

import javax.annotation.Nullable;

public class WireBlock extends Block implements EntityBlock {
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");

    private final int energyTransferRate;

    public WireBlock(int energyTransferRate) {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).noCollission().instabreak());
        this.energyTransferRate = energyTransferRate;
        this.registerDefaultState(this.defaultBlockState()
            .setValue(NORTH, false)
            .setValue(EAST, false)
            .setValue(SOUTH, false)
            .setValue(WEST, false)
            .setValue(UP, false)
            .setValue(DOWN, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        // 基础电线形状
        VoxelShape shape = Block.box(6.0D, 6.0D, 6.0D, 10.0D, 10.0D, 10.0D);
        
        // 根据连接状态添加连接部分
        if (state.getValue(NORTH)) {
            shape = Shapes.or(shape, Block.box(6.0D, 6.0D, 0.0D, 10.0D, 10.0D, 6.0D));
        }
        if (state.getValue(EAST)) {
            shape = Shapes.or(shape, Block.box(10.0D, 6.0D, 6.0D, 16.0D, 10.0D, 10.0D));
        }
        if (state.getValue(SOUTH)) {
            shape = Shapes.or(shape, Block.box(6.0D, 6.0D, 10.0D, 10.0D, 10.0D, 16.0D));
        }
        if (state.getValue(WEST)) {
            shape = Shapes.or(shape, Block.box(0.0D, 6.0D, 6.0D, 6.0D, 10.0D, 10.0D));
        }
        if (state.getValue(UP)) {
            shape = Shapes.or(shape, Block.box(6.0D, 10.0D, 6.0D, 10.0D, 16.0D, 10.0D));
        }
        if (state.getValue(DOWN)) {
            shape = Shapes.or(shape, Block.box(6.0D, 0.0D, 6.0D, 10.0D, 6.0D, 10.0D));
        }
        
        return shape;
    }

    @Override
    public boolean isCollisionShapeFullBlock(BlockState state, BlockGetter level, BlockPos pos) {
        return false;
    }

    @Override
    public boolean isOcclusionShapeFullBlock(BlockState state, BlockGetter level, BlockPos pos) {
        return false;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new com.yhj2014.technological_revolution.block.entity.WireBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(net.minecraft.world.level.Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide) {
            return null;
        }
        return (lvl, pos, blockState, t) -> {
            if (t instanceof com.yhj2014.technological_revolution.block.entity.WireBlockEntity wire) {
                wire.tick();
            }
        };
    }
}