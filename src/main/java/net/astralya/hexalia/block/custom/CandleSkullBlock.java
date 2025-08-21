package net.astralya.hexalia.block.custom;

import com.google.common.collect.ImmutableList;
import net.astralya.hexalia.block.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class CandleSkullBlock extends Block {

    public static final VoxelShape SHAPE = VoxelShapes.union(
            VoxelShapes.cuboid(0.25, 0, 0.25, 0.75, 0.5, 0.75),
            VoxelShapes.cuboid(0.4375, 0.453125, 0.4375, 0.5625, 0.640625, 0.5625)
    );
    public static final BooleanProperty LIT = AbstractCandleBlock.LIT;
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    private static final Iterable<Vec3d> PARTICLE_OFFSETS = ImmutableList.of(new Vec3d(0.5, 0.75, 0.5));

    public CandleSkullBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(LIT, false).with(WATERLOGGED, false));
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        World world = ctx.getWorld();
        FluidState fluidState = world.getFluidState(blockPos);
        return getDefaultState()
                .with(FACING, ctx.getHorizontalPlayerFacing().getOpposite())
                .with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER)
                .with(LIT, false);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return Boolean.TRUE.equals(state.get(WATERLOGGED)) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.DOWN && !state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isSolid();
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, LIT);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }


    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos,
                                             PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack inHand = player.getStackInHand(hand);

        if (inHand.getItem() instanceof FlintAndSteelItem) {
            if (!state.get(LIT) && !state.get(WATERLOGGED)) {
                lightCandleSkull(state, world, pos, player, hand, inHand);
                return ItemActionResult.success(world.isClient);
            }
        } else if (inHand.isEmpty()) {
            if (state.get(LIT)) {
                extinguishCandleSkull(state, world, pos);
                return ItemActionResult.success(world.isClient);
            }
        }

        return ItemActionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
    }

    private static void lightCandleSkull(BlockState state, World world, BlockPos pos,
                                         PlayerEntity player, Hand hand, ItemStack inHand) {
        world.setBlockState(pos, state.with(LIT, true), Block.NOTIFY_ALL);
        inHand.damage(1, player, LivingEntity.getSlotForHand(hand));
        world.playSound(null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS,
                1.0f, world.random.nextFloat() * 0.4F + 0.8F);
    }

    private static void extinguishCandleSkull(BlockState state, World world, BlockPos pos) {
        world.setBlockState(pos, state.with(LIT, false), Block.NOTIFY_ALL);
        world.playSound(null, pos, SoundEvents.BLOCK_CANDLE_EXTINGUISH, SoundCategory.BLOCKS,
                1.0f, world.random.nextFloat() * 0.4F + 0.8F);
    }


    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (!state.get(LIT)) {
            return;
        }
        getParticleOffsets(state).forEach(offset -> CandleSkullBlock.spawnCandleParticles(world, offset.add(pos.getX(), pos.getY(), pos.getZ()), random));
    }

    public static Iterable<Vec3d> getParticleOffsets(BlockState state) {
        return PARTICLE_OFFSETS;
    }

    private static void spawnCandleParticles(World world, Vec3d vec3, Random random) {
        float chance = random.nextFloat();
        if (chance < 0.3f) {
            world.addParticle(ParticleTypes.SMOKE, vec3.x, vec3.y, vec3.z, 0.0, 0.0, 0.0);
            if (chance < 0.17f) {
                world.playSound(vec3.x + 0.5, vec3.y + 0.5, vec3.z + 0.5,
                        SoundEvents.BLOCK_CANDLE_AMBIENT, SoundCategory.BLOCKS,
                        1.0f + random.nextFloat(), random.nextFloat() * 0.7f + 0.3f, false);
            }
        }

        BlockPos pos = new BlockPos((int)Math.floor(vec3.x), (int)Math.floor(vec3.y), (int)Math.floor(vec3.z));
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if (block == ModBlocks.WITHER_CANDLE_SKULL) {
            world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, vec3.x, vec3.y, vec3.z, 0.0, 0.0, 0.0);
        } else {
            world.addParticle(ParticleTypes.FLAME, vec3.x, vec3.y, vec3.z, 0.0, 0.0, 0.0);
        }
    }
}
