package dev.luminaeclipse.kiro.block;

import dev.luminaeclipse.kiro.block.entity.LargeItemFrameBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.WallMountLocation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.util.math.Direction.DOWN;
import static net.minecraft.util.math.Direction.UP;

public class LargeItemFrameBlock extends WallMountedBlock implements Waterloggable, BlockEntityProvider {
    private static final BooleanProperty WATERLOGGED;
    private static final VoxelShape DOWN_SHAPE;
    private static final VoxelShape UP_SHAPE;
    private static final VoxelShape EAST_SHAPE;
    private static final VoxelShape WEST_SHAPE;
    private static final VoxelShape SOUTH_SHAPE;
    private static final VoxelShape NORTH_SHAPE;

    public LargeItemFrameBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.getDefaultState().with(FACING, Direction.NORTH)).with(FACE, WallMountLocation.WALL)).with(WATERLOGGED, false));
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape var10000 = null;
        Direction direction = state.get(FACING);
        WallMountLocation face = state.get(FACE);
        switch (((WallMountLocation)state.get(FACE))) {
            case CEILING:
                var10000 = DOWN_SHAPE;
                break;
            case FLOOR:
                var10000 = UP_SHAPE;
                break;
            case WALL:
                return switch (((Direction) state.get(FACING))) {
                    case NORTH -> {
                        var10000 = NORTH_SHAPE;
                        yield var10000;
                    }
                    case EAST -> {
                        var10000 = EAST_SHAPE;
                        yield var10000;
                    }
                    case SOUTH -> {
                        var10000 = SOUTH_SHAPE;
                        yield var10000;
                    }
                    default -> {
                        var10000 = WEST_SHAPE;
                        yield var10000;
                    }
                };
        }

        return var10000;

        /*return switch (face) {
            case FLOOR -> DOWN_SHAPE;
            case CEILING -> UP_SHAPE;
            case WALL -> return switch (direction) {
                case NORTH -> NORTH_SHAPE;
                case EAST -> EAST_SHAPE;
                case SOUTH -> SOUTH_SHAPE;
                case WEST -> WEST_SHAPE;
                }
            }
        };*/
    }

    public FluidState getFluidState(BlockState state) {
        return (Boolean)state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if ((Boolean)state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return state;
    }

    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = super.getPlacementState(ctx);
        return state != null ? (BlockState)state.with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).isOf(Fluids.WATER)) : null;
    }

    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new LargeItemFrameBlockEntity(pos, state);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACE, FACING, WATERLOGGED});
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity displayedStack = world.getBlockEntity(pos);
        if (displayedStack instanceof LargeItemFrameBlockEntity entity) {
            ItemStack heldStack = player.getStackInHand(hand);
            ItemStack displayStack = entity.getStack();
            if (!heldStack.isEmpty() && displayStack.isEmpty()) {
                entity.setStack(heldStack.copy());
                world.updateComparators(pos, this);
                world.playSound((PlayerEntity)null, pos, SoundEvents.ENTITY_ITEM_FRAME_ADD_ITEM, SoundCategory.BLOCKS, 1.0F, 1.0F);
                if (!player.getAbilities().creativeMode) {
                    player.setStackInHand(hand, ItemStack.EMPTY);
                }

                return ActionResult.success(world.isClient);
            }

            if (heldStack.isEmpty() && !displayStack.isEmpty()) {
                player.setStackInHand(hand, displayStack.copy());
                entity.setStack(ItemStack.EMPTY);
                world.updateComparators(pos, this);
                world.playSound((PlayerEntity)null, pos, SoundEvents.ENTITY_ITEM_FRAME_REMOVE_ITEM, SoundCategory.BLOCKS, 1.0F, 1.0F);
                return ActionResult.success(world.isClient);
            }
        }

        return ActionResult.PASS;
    }

    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        BlockEntity var5 = world.getBlockEntity(pos);
        if (var5 instanceof LargeItemFrameBlockEntity entity) {
            return entity.getStack().isEmpty() ? 0 : 1;
        } else {
            return 0;
        }
    }

    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity var7 = world.getBlockEntity(pos);
            if (var7 instanceof LargeItemFrameBlockEntity) {
                LargeItemFrameBlockEntity entity = (LargeItemFrameBlockEntity)var7;
                if (!entity.getStack().isEmpty()) {
                    ItemScatterer.spawn(world, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), entity.getStack());
                    world.updateComparators(pos, this);
                    world.playSound((PlayerEntity)null, pos, SoundEvents.ENTITY_ITEM_FRAME_REMOVE_ITEM, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
            }
        }

        super.onStateReplaced(state, world, pos, newState, moved);
    }

    static {
        WATERLOGGED = Properties.WATERLOGGED;
        DOWN_SHAPE = Block.createCuboidShape((double)0.0F, (double)13.0F, (double)0.0F, (double)16.0F, (double)16.0F, (double)16.0F);
        UP_SHAPE = Block.createCuboidShape((double)0.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)3.0F, (double)16.0F);
        EAST_SHAPE = Block.createCuboidShape((double)0.0F, (double)0.0F, (double)0.0F, (double)3.0F, (double)16.0F, (double)16.0F);
        WEST_SHAPE = Block.createCuboidShape((double)13.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)16.0F, (double)16.0F);
        SOUTH_SHAPE = Block.createCuboidShape((double)0.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)16.0F, (double)3.0F);
        NORTH_SHAPE = Block.createCuboidShape((double)0.0F, (double)0.0F, (double)13.0F, (double)16.0F, (double)16.0F, (double)16.0F);
    }
}