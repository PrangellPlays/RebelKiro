package dev.luminaeclipse.kiro.block;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import oshi.util.tuples.Triplet;

import java.util.List;

public class BrazierBlock extends IgnitableBlock {
    private static final BooleanProperty LEGS = BooleanProperty.of("legs");
    private static final BooleanProperty CHAIN = BooleanProperty.of("chain");

    public BrazierBlock(AbstractBlock.Settings settings) {
        super(settings, false, true);
        this.setDefaultState((BlockState) ((BlockState) super.getDefaultState().with(LEGS, true)).with(CHAIN, false));
    }

    public static int getLuminance(BlockState state) {
        return (Boolean) state.get(LIT) ? 15 : 0;
    }

    protected SoundEvent getSound(BlockState state) {
        return SoundEvents.BLOCK_CAMPFIRE_CRACKLE;
    }

    protected SoundEvent getExtinguishedSound() {
        return SoundEvents.BLOCK_FIRE_EXTINGUISH;
    }

    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        BlockState state = super.getPlacementState(ctx);
        return state != null ? (BlockState) ((BlockState) state.with(LEGS, this.canHaveLegs(world, pos))).with(CHAIN, this.canHaveChain(world, pos)) : null;
    }

    public boolean canHaveChain(WorldAccess world, BlockPos pos) {
        return Block.sideCoversSmallSquare(world, pos.up(), Direction.DOWN) || world.getBlockState(pos.up()).isOf(Blocks.CHAIN);
    }

    public boolean canHaveLegs(WorldAccess world, BlockPos pos) {
        BlockState neighborState = world.getBlockState(pos.down());
        return neighborState.isSideSolidFullSquare(world, pos.down(), Direction.UP);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction.equals(Direction.UP)) {
            return (BlockState) super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos).with(CHAIN, this.canHaveChain(world, pos));
        } else {
            return direction.equals(Direction.DOWN) ? (BlockState) super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos).with(LEGS, this.canHaveLegs(world, pos)) : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        }
    }

    protected List<Vec3d> getParticlePositions(BlockState state) {
        return ImmutableList.of(new Vec3d((double) 0.0F, (double) 0.5F, (double) 0.0F));
    }

    protected ImmutableList<Triplet<DefaultParticleType, Float, Float>> getParticles(BlockState state) {
        //return ImmutableList.of(new Triplet(ParticleTypes.CAMPFIRE_COSY_SMOKE, 0.4F, 0.07F), new Triplet(ContentClient.EMBER, 1.0F, 0.0F));
        return ImmutableList.of(new Triplet(ParticleTypes.CAMPFIRE_COSY_SMOKE, 0.4F, 0.07F), new Triplet(ParticleTypes.LAVA, 1.0F, 0.0F));
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.combineAndSimplify(Block.createCuboidShape((double) 1.0F, (Boolean) state.get(LEGS) ? (double) 0.0F : (double) 5.0F, (double) 1.0F, (double) 15.0F, (double) 9.0F, (double) 15.0F), Block.createCuboidShape((double) 3.0F, (double) 7.0F, (double) 3.0F, (double) 13.0F, (double) 9.0F, (double) 13.0F), BooleanBiFunction.ONLY_FIRST);
    }

    protected int getDamage() {
        return 1;
    }

    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!entity.isFireImmune() && (Boolean) state.get(LIT) && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity) entity) && entity.getY() > (double) pos.getY() + 0.4) {
            entity.damage(world.getDamageSources().inFire(), (float) this.getDamage());
        }

        super.onEntityCollision(state, world, pos, entity);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{LIT, WATERLOGGED, LEGS, CHAIN});
    }
}