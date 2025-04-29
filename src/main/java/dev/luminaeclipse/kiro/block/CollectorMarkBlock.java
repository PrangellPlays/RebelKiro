package dev.luminaeclipse.kiro.block;

import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class CollectorMarkBlock extends FacingBlock implements Waterloggable {
    public static final BooleanProperty WATERLOGGED;
    public static final VoxelShape NORTH_SHAPE;
    public static final VoxelShape NORTH_COLLISION_SHAPE;
    public static final VoxelShape EAST_SHAPE;
    public static final VoxelShape EAST_COLLISION_SHAPE;
    public static final VoxelShape SOUTH_SHAPE;
    public static final VoxelShape SOUTH_COLLISION_SHAPE;
    public static final VoxelShape WEST_SHAPE;
    public static final VoxelShape WEST_COLLISION_SHAPE;
    public static final VoxelShape UP_SHAPE;
    public static final VoxelShape UP_COLLISION_SHAPE;
    public static final VoxelShape DOWN_SHAPE;
    public static final VoxelShape DOWN_COLLISION_SHAPE;

    public CollectorMarkBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)super.getDefaultState().with(WATERLOGGED, false)).with(FACING, Direction.SOUTH));
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (context.isHolding(this.asItem())) {
            Direction direction = state.get(FACING);
            return switch (direction) {
                case DOWN -> DOWN_SHAPE;
                case UP -> UP_SHAPE;
                case NORTH -> NORTH_SHAPE;
                case EAST -> EAST_SHAPE;
                case SOUTH -> SOUTH_SHAPE;
                case WEST -> WEST_SHAPE;
            };
        } else {
            return this.getCollisionShape(state, world, pos, context);
        }
    }

    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(FACING);
        return switch (direction) {
            case DOWN -> DOWN_COLLISION_SHAPE;
            case UP -> UP_COLLISION_SHAPE;
            case NORTH -> NORTH_COLLISION_SHAPE;
            case EAST -> EAST_COLLISION_SHAPE;
            case SOUTH -> SOUTH_COLLISION_SHAPE;
            case WEST -> WEST_COLLISION_SHAPE;
        };
    }

    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.empty();
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if ((Boolean)state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        WorldAccess world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        Direction facing = ctx.getSide();
        BlockState neighborState = world.getBlockState(pos.offset(facing.getOpposite()));
        if (!ctx.shouldCancelInteraction() && neighborState.isOf(this)) {
            Direction neighborFacing = (Direction)neighborState.get(FACING);
            if (!neighborFacing.getAxis().equals(facing.getAxis())) {
                facing = neighborFacing;
            }
        }

        return (BlockState)((BlockState)this.getDefaultState().with(FACING, facing)).with(WATERLOGGED, world.getFluidState(pos).getFluid().equals(Fluids.WATER));
    }

    public FluidState getFluidState(BlockState state) {
        return (Boolean)state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING, WATERLOGGED});
    }

    static {
        WATERLOGGED = Properties.WATERLOGGED;
        NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 12.0, 16.0, 16.0, 16.0);
        NORTH_COLLISION_SHAPE = Block.createCuboidShape(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
        EAST_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 4.0, 16.0, 16.0);
        EAST_COLLISION_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
        SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 4.0);
        SOUTH_COLLISION_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
        WEST_SHAPE = Block.createCuboidShape(12.0, 0.0, 0.0, 16.0, 16.0, 16.0);
        WEST_COLLISION_SHAPE = Block.createCuboidShape(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
        UP_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 4.0, 16.0);
        UP_COLLISION_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);
        DOWN_SHAPE = Block.createCuboidShape(0.0, 12.0, 0.0, 16.0, 16.0, 16.0);
        DOWN_COLLISION_SHAPE = Block.createCuboidShape(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
    }
}