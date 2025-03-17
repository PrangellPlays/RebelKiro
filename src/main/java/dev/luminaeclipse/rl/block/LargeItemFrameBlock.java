package dev.luminaeclipse.rl.block;

import dev.luminaeclipse.rl.block.entity.LargeItemFrameBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class LargeItemFrameBlock extends BlockWithEntity implements Waterloggable {
    public static final DirectionProperty FACING;
    public static final BooleanProperty WATERLOGGED;
    public static final VoxelShape NORTH_SHAPE;
    public static final VoxelShape EAST_SHAPE;
    public static final VoxelShape SOUTH_SHAPE;
    public static final VoxelShape WEST_SHAPE;
    public static final VoxelShape UP_SHAPE;
    public static final VoxelShape DOWN_SHAPE;

    public LargeItemFrameBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)super.getDefaultState().with(WATERLOGGED, false)).with(FACING, Direction.SOUTH));
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.getCollisionShape(state, world, pos, context);
    }

    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(FACING);
        return switch (direction) {
            case DOWN -> DOWN_SHAPE;
            case UP -> UP_SHAPE;
            case NORTH -> NORTH_SHAPE;
            case EAST -> EAST_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case WEST -> WEST_SHAPE;
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

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof LargeItemFrameBlockEntity) {
            LargeItemFrameBlockEntity largeItemFrame = (LargeItemFrameBlockEntity) blockEntity;
            if (largeItemFrame.isEmpty()) {
                ItemStack itemStack = player.getStackInHand(hand);
                if (itemStack.isEmpty()) {
                    return ActionResult.CONSUME;
                }
                largeItemFrame.setStack(0, itemStack);
                world.getBlockEntity(pos).toUpdatePacket();
                player.getStackInHand(hand).decrement(64);
                return ActionResult.success(world.isClient());
            } else {
                ItemStack itemStack = largeItemFrame.getStack(0);
                if (!itemStack.isEmpty()) {
                    player.giveItemStack(itemStack);
                    largeItemFrame.removeStack(0);
                    world.getBlockEntity(pos).toUpdatePacket();
                    return ActionResult.success(world.isClient());
                }
                return ActionResult.FAIL;
            }
        }
        return ActionResult.PASS;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new LargeItemFrameBlockEntity(pos, state);
    }

    static {
        FACING = Properties.FACING;
        WATERLOGGED = Properties.WATERLOGGED;
        NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 13.0, 16.0, 16.0, 16.0);
        EAST_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 3.0, 16.0, 16.0);
        SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 3.0);
        WEST_SHAPE = Block.createCuboidShape(13.0, 0.0, 0.0, 16.0, 16.0, 16.0);
        UP_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 3.0, 16.0);
        DOWN_SHAPE = Block.createCuboidShape(0.0, 13.0, 0.0, 16.0, 16.0, 16.0);
    }
}