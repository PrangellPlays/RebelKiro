package dev.luminaeclipse.rl.block;

import net.minecraft.block.*;
import net.minecraft.block.enums.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class LayerBlock extends Block {
    public static final EnumProperty<LayerShape> SHAPE = EnumProperty.of("shape", LayerShape.class);
    public static final BooleanProperty UP;
    public static final BooleanProperty NORTH;
    public static final BooleanProperty EAST;
    public static final BooleanProperty SOUTH;
    public static final BooleanProperty WEST;

    public LayerBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.getDefaultState().with(SHAPE, LayerShape.SLAB)).with(UP, true)).with(NORTH, false)).with(EAST, false)).with(SOUTH, false)).with(WEST, false));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{SHAPE, UP, NORTH, EAST, SOUTH, WEST});
    }

    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = super.getPlacementState(ctx);
        if (blockState != null) {
            BlockPos pos = ctx.getBlockPos().down();
            BlockState belowState = ctx.getWorld().getBlockState(pos);
            return this.getUpdatedBlockState(blockState, belowState, ctx.getWorld(), pos);
        } else {
            return null;
        }
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        BlockPos downPos = pos.down();
        BlockState newState = this.getUpdatedBlockState(state, world.getBlockState(downPos), (World)world, downPos);
        return newState != null ? newState : Blocks.AIR.getDefaultState();
    }



    public @Nullable BlockState getUpdatedBlockState(BlockState blockState, BlockState belowState, World world, BlockPos pos) {
        if (belowState.getBlock() instanceof SlabBlock) {
            return (BlockState)((BlockState)blockState.with(SHAPE, LayerShape.SLAB)).with(UP, !((SlabType)belowState.get(Properties.SLAB_TYPE)).equals(SlabType.BOTTOM));
        } else if (belowState.getBlock() instanceof StairsBlock) {
            return ((BlockHalf)belowState.get(Properties.BLOCK_HALF)).equals(BlockHalf.TOP) ? (BlockState)((BlockState)blockState.with(SHAPE, LayerShape.SLAB)).with(UP, true) : (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)blockState.with(NORTH, this.stairsDirection(belowState, Direction.NORTH))).with(EAST, this.stairsDirection(belowState, Direction.EAST))).with(SOUTH, this.stairsDirection(belowState, Direction.SOUTH))).with(WEST, this.stairsDirection(belowState, Direction.WEST))).with(SHAPE, LayerShape.STAIR);
        } else if (!(belowState.getBlock() instanceof FenceBlock) && !(belowState.getBlock() instanceof PaneBlock)) {
            if (belowState.getBlock() instanceof WallBlock) {
                return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)blockState.with(SHAPE, LayerShape.WALL)).with(NORTH, !((WallShape)belowState.get(Properties.NORTH_WALL_SHAPE)).equals(WallShape.NONE))).with(EAST, !((WallShape)belowState.get(Properties.EAST_WALL_SHAPE)).equals(WallShape.NONE))).with(SOUTH, !((WallShape)belowState.get(Properties.SOUTH_WALL_SHAPE)).equals(WallShape.NONE))).with(WEST, !((WallShape)belowState.get(Properties.WEST_WALL_SHAPE)).equals(WallShape.NONE))).with(UP, (Boolean)belowState.get(Properties.UP));
            } else if (belowState.getBlock() instanceof TrapdoorBlock) {
                return (Boolean)belowState.get(Properties.OPEN) ? (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)blockState.with(NORTH, ((Direction)belowState.get(HorizontalFacingBlock.FACING)).equals(Direction.NORTH.getOpposite()))).with(EAST, ((Direction)belowState.get(HorizontalFacingBlock.FACING)).equals(Direction.EAST.getOpposite()))).with(SOUTH, ((Direction)belowState.get(HorizontalFacingBlock.FACING)).equals(Direction.SOUTH.getOpposite()))).with(WEST, ((Direction)belowState.get(HorizontalFacingBlock.FACING)).equals(Direction.WEST.getOpposite()))).with(SHAPE, LayerShape.DOOR) : (BlockState)((BlockState)blockState.with(SHAPE, LayerShape.TRAPDOOR)).with(UP, ((BlockHalf)belowState.get(Properties.BLOCK_HALF)).equals(BlockHalf.TOP));
            } else if (belowState.getBlock() instanceof DoorBlock) {
                Direction d = (Direction)belowState.get(HorizontalFacingBlock.FACING);
                if ((Boolean)belowState.get(Properties.OPEN)) {
                    if (((DoorHinge)belowState.get(Properties.DOOR_HINGE)).equals(DoorHinge.LEFT)) {
                        d = d.rotateYClockwise();
                    } else {
                        d = d.rotateYCounterclockwise();
                    }
                }

                return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)blockState.with(NORTH, d.equals(Direction.NORTH.getOpposite()))).with(EAST, d.equals(Direction.EAST.getOpposite()))).with(SOUTH, d.equals(Direction.SOUTH.getOpposite()))).with(WEST, d.equals(Direction.WEST.getOpposite()))).with(SHAPE, LayerShape.DOOR);
            } else if (belowState.getBlock() instanceof FenceGateBlock) {
                return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)blockState.with(NORTH, ((Direction)belowState.get(HorizontalFacingBlock.FACING)).equals(Direction.NORTH.getOpposite()))).with(EAST, ((Direction)belowState.get(HorizontalFacingBlock.FACING)).equals(Direction.EAST.getOpposite()))).with(SOUTH, ((Direction)belowState.get(HorizontalFacingBlock.FACING)).equals(Direction.SOUTH.getOpposite()))).with(WEST, ((Direction)belowState.get(HorizontalFacingBlock.FACING)).equals(Direction.WEST.getOpposite()))).with(UP, !(Boolean)belowState.get(Properties.IN_WALL))).with(SHAPE, (Boolean)belowState.get(Properties.OPEN) ? LayerShape.OPEN_FENCE_GATE : LayerShape.FENCE_GATE);
            } else {
                return !belowState.isSideSolidFullSquare(world, pos, Direction.UP) && !(belowState.getBlock() instanceof LeavesBlock) ? null : (BlockState)((BlockState)blockState.with(SHAPE, LayerShape.SLAB)).with(UP, true);
            }
        } else {
            return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)blockState.with(SHAPE, belowState.getBlock() instanceof FenceBlock ? LayerShape.FENCE : LayerShape.PANE)).with(NORTH, (Boolean)belowState.get(Properties.NORTH))).with(EAST, (Boolean)belowState.get(Properties.EAST))).with(SOUTH, (Boolean)belowState.get(Properties.SOUTH))).with(WEST, (Boolean)belowState.get(Properties.WEST));
        }
    }

    public boolean stairsDirection(BlockState state, Direction direction) {
        if (state.getBlock() instanceof StairsBlock) {
            if (((Direction)state.get(HorizontalFacingBlock.FACING)).equals(direction) && !((StairShape)state.get(Properties.STAIR_SHAPE)).equals(StairShape.OUTER_LEFT)) {
                return true;
            } else if (((Direction)state.get(HorizontalFacingBlock.FACING)).equals(direction.rotateYClockwise()) && !((StairShape)state.get(Properties.STAIR_SHAPE)).equals(StairShape.OUTER_RIGHT)) {
                return true;
            } else if (((Direction)state.get(HorizontalFacingBlock.FACING)).equals(direction.rotateYCounterclockwise()) && ((StairShape)state.get(Properties.STAIR_SHAPE)).equals(StairShape.INNER_RIGHT)) {
                return true;
            } else {
                return ((Direction)state.get(HorizontalFacingBlock.FACING)).equals(direction.getOpposite()) && ((StairShape)state.get(Properties.STAIR_SHAPE)).equals(StairShape.INNER_LEFT);
            }
        } else {
            return false;
        }
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState belowState = world.getBlockState(pos.down());
        if (!belowState.isSideSolidFullSquare(world, pos.down(), Direction.UP) && !(belowState.getBlock() instanceof LeavesBlock)) {
            Block b = belowState.getBlock();
            return b instanceof SlabBlock || b instanceof FenceBlock || b instanceof WallBlock || b instanceof StairsBlock || b instanceof TrapdoorBlock || b instanceof DoorBlock || b instanceof FenceGateBlock || b instanceof PaneBlock;
        } else {
            return true;
        }
    }

    /*public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch ((LayerShape)state.get(SHAPE)) {
            case 1:
                UP_SHAPE = (Boolean)state.get(UP) ? Block.createCuboidShape((double)0.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)1.0F, (double)16.0F) : Block.createCuboidShape((double)0.0F, (double)-8.0F, (double)0.0F, (double)16.0F, (double)-7.0F, (double)16.0F);
            case 2:
                NORTH_WEST_SHAPE = VoxelShapes.union((Boolean)state.get(WEST) ? Block.createCuboidShape((double)0.0F, (double)0.0F, (double)0.0F, (double)8.0F, (double)1.0F, (double)8.0F) : Block.createCuboidShape((double)0.0F, (double)-8.0F, (double)0.0F, (double)8.0F, (double)-7.0F, (double)8.0F), new VoxelShape[]{(Boolean)state.get(NORTH) ? Block.createCuboidShape((double)8.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)1.0F, (double)8.0F) : Block.createCuboidShape((double)8.0F, (double)-8.0F, (double)0.0F, (double)16.0F, (double)-7.0F, (double)8.0F), (Boolean)state.get(EAST) ? Block.createCuboidShape((double)8.0F, (double)0.0F, (double)8.0F, (double)16.0F, (double)1.0F, (double)16.0F) : Block.createCuboidShape((double)8.0F, (double)-8.0F, (double)8.0F, (double)16.0F, (double)-7.0F, (double)16.0F), (Boolean)state.get(SOUTH) ? Block.createCuboidShape((double)0.0F, (double)0.0F, (double)8.0F, (double)8.0F, (double)1.0F, (double)16.0F) : Block.createCuboidShape((double)0.0F, (double)-8.0F, (double)8.0F, (double)8.0F, (double)-7.0F, (double)16.0F)});
            case 3:
                UP_SHAPE = (Boolean)state.get(UP) ? Block.createCuboidShape((double)0.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)1.0F, (double)16.0F) : Block.createCuboidShape((double)0.0F, (double)-13.0F, (double)0.0F, (double)16.0F, (double)-12.0F, (double)16.0F);
            case 4:
                NORTH_EAST_SOUTH_SHAPE = (Boolean)state.get(NORTH) ? Block.createCuboidShape((double)0.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)1.0F, (double)3.0F) : ((Boolean)state.get(EAST) ? Block.createCuboidShape((double)13.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)1.0F, (double)16.0F) : ((Boolean)state.get(SOUTH) ? Block.createCuboidShape((double)0.0F, (double)0.0F, (double)13.0F, (double)16.0F, (double)1.0F, (double)16.0F) : Block.createCuboidShape((double)0.0F, (double)0.0F, (double)0.0F, (double)3.0F, (double)1.0F, (double)16.0F)));
            case 5:
                UP_WEST_EAST_NORTH_SHAPE = VoxelShapes.union((Boolean)state.get(UP) ? Block.createCuboidShape((double)4.0F, (double)0.0F, (double)4.0F, (double)12.0F, (double)1.0F, (double)12.0F) : VoxelShapes.empty(), new VoxelShape[]{(Boolean)state.get(WEST) ? Block.createCuboidShape((double)0.0F, (double)-2.0F, (double)5.0F, (double)11.0F, (double)-1.0F, (double)11.0F) : VoxelShapes.empty(), (Boolean)state.get(EAST) ? Block.createCuboidShape((double)11.0F, (double)-2.0F, (double)5.0F, (double)16.0F, (double)-1.0F, (double)11.0F) : VoxelShapes.empty(), (Boolean)state.get(NORTH) ? Block.createCuboidShape((double)5.0F, (double)-2.0F, (double)0.0F, (double)11.0F, (double)-1.0F, (double)11.0F) : VoxelShapes.empty(), (Boolean)state.get(SOUTH) ? Block.createCuboidShape((double)5.0F, (double)-2.0F, (double)11.0F, (double)11.0F, (double)-1.0F, (double)16.0F) : VoxelShapes.empty()});
            case 6:
                WEST_EAST_NORTH_SOUTH_SHAPE = VoxelShapes.union(Block.createCuboidShape((double)6.0F, (double)0.0F, (double)6.0F, (double)10.0F, (double)1.0F, (double)10.0F), new VoxelShape[]{(Boolean)state.get(WEST) ? Block.createCuboidShape((double)0.0F, (double)0.0F, (double)6.0F, (double)10.0F, (double)1.0F, (double)10.0F) : VoxelShapes.empty(), (Boolean)state.get(EAST) ? Block.createCuboidShape((double)10.0F, (double)0.0F, (double)6.0F, (double)16.0F, (double)1.0F, (double)10.0F) : VoxelShapes.empty(), (Boolean)state.get(NORTH) ? Block.createCuboidShape((double)6.0F, (double)0.0F, (double)0.0F, (double)10.0F, (double)1.0F, (double)10.0F) : VoxelShapes.empty(), (Boolean)state.get(SOUTH) ? Block.createCuboidShape((double)6.0F, (double)0.0F, (double)10.0F, (double)10.0F, (double)1.0F, (double)16.0F) : VoxelShapes.empty()});
            case 7:
            case 8:
                NORTH_SOUTH_UP_UP_UP_SHAPE = !(Boolean)state.get(NORTH) && !(Boolean)state.get(SOUTH) ? Block.createCuboidShape((double)6.0F, (Boolean)state.get(UP) ? (double)0.0F : (double)-3.0F, (double)0.0F, (double)10.0F, (Boolean)state.get(UP) ? (double)1.0F : (double)-2.0F, (double)16.0F) : Block.createCuboidShape((double)0.0F, (Boolean)state.get(UP) ? (double)0.0F : (double)-3.0F, (double)6.0F, (double)16.0F, (Boolean)state.get(UP) ? (double)1.0F : (double)-2.0F, (double)10.0F);
            case 9:
                WEST_EAST_NORTH_SHAPE = VoxelShapes.union(Block.createCuboidShape((double)7.0F, (double)0.0F, (double)7.0F, (double)9.0F, (double)1.0F, (double)9.0F), new VoxelShape[]{(Boolean)state.get(WEST) ? Block.createCuboidShape((double)0.0F, (double)0.0F, (double)7.0F, (double)9.0F, (double)1.0F, (double)9.0F) : VoxelShapes.empty(), (Boolean)state.get(EAST) ? Block.createCuboidShape((double)9.0F, (double)0.0F, (double)7.0F, (double)16.0F, (double)1.0F, (double)9.0F) : VoxelShapes.empty(), (Boolean)state.get(NORTH) ? Block.createCuboidShape((double)7.0F, (double)0.0F, (double)0.0F, (double)9.0F, (double)1.0F, (double)9.0F) : VoxelShapes.empty(), (Boolean)state.get(SOUTH) ? Block.createCuboidShape((double)7.0F, (double)0.0F, (double)9.0F, (double)9.0F, (double)1.0F, (double)16.0F) : VoxelShapes.empty()});
        };
    }*/

    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return state.getFluidState().isEmpty();
    }

    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return true;
    }

    static {
        UP = Properties.UP;
        NORTH = Properties.NORTH;
        EAST = Properties.EAST;
        SOUTH = Properties.SOUTH;
        WEST = Properties.WEST;
    }

    public enum LayerShape implements StringIdentifiable {
        SLAB("slab"),
        STAIR("stair"),
        WALL("wall"),
        FENCE("fence"),
        TRAPDOOR("trapdoor"),
        DOOR("door"),
        FENCE_GATE("fence_gate"),
        OPEN_FENCE_GATE("open_fence_gate"),
        PANE("pane");

        private final String name;

        private LayerShape(String name) {
            this.name = name;
        }

        public String asString() {
            return this.name;
        }

        public String toString() {
            return this.name;
        }
    }
}