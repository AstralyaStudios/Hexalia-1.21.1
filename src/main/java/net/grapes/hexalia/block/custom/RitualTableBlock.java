package net.grapes.hexalia.block.custom;

import com.mojang.serialization.MapCodec;
import net.grapes.hexalia.block.entity.custom.RitualTableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ThreadLocalRandom;

public class RitualTableBlock extends BaseEntityBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private static final VoxelShape SHAPE = createShape();

    public static final MapCodec<RitualTableBlock> CODEC = simpleCodec(RitualTableBlock::new);

    public RitualTableBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    private static VoxelShape createShape() {
        return Shapes.or(
                Shapes.box(0.1875, 0, 0.1875, 0.8125, 0.125, 0.8125),
                Shapes.box(0.25, 0.125, 0.25, 0.75, 0.625, 0.75),
                Shapes.box(0.1875, 0.625, 0.1875, 0.8125, 0.6875, 0.8125),
                Shapes.box(0.125, 0.6875, 0.125, 0.875, 0.8125, 0.875)
        );
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new RitualTableBlockEntity(blockPos, blockState);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (state.getBlock() != newState.getBlock()) {
            if (level.getBlockEntity(pos) instanceof RitualTableBlockEntity ritualTableBlockEntity) {
                Containers.dropContents(level, pos, ritualTableBlockEntity);
                level.updateNeighbourForOutputSignal(pos, this);
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (hand != InteractionHand.MAIN_HAND) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (level.getBlockEntity(pos) instanceof RitualTableBlockEntity ritualTableBlockEntity) {
            if (ritualTableBlockEntity.isEmpty() && !stack.isEmpty()) {
                addItemToBlock(stack, level, pos, ritualTableBlockEntity);
                return ItemInteractionResult.SUCCESS;
            } else if (stack.isEmpty() && !ritualTableBlockEntity.getItem(0).isEmpty()) {
                removeItemFromBlock(level, pos, player, ritualTableBlockEntity);
                return ItemInteractionResult.SUCCESS;
            }
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    private void addItemToBlock(ItemStack stack, Level level, BlockPos pos, RitualTableBlockEntity ritualTableBlockEntity) {
        ritualTableBlockEntity.setItem(0, stack);
        stack.shrink(1);
        playInteractionSound(level, pos);
        spawnParticleEffects(level, pos, ParticleTypes.POOF, 5, 10);
    }

    private void removeItemFromBlock(Level level, BlockPos pos, Player player, RitualTableBlockEntity ritualTableBlockEntity) {
        ItemStack stackOnBlock = ritualTableBlockEntity.getItem(0);
        player.setItemInHand(InteractionHand.MAIN_HAND, stackOnBlock);
        ritualTableBlockEntity.clearContent();
        playInteractionSound(level, pos);
    }

    private void spawnParticleEffects(Level level, BlockPos pos, SimpleParticleType particleType, int minParticles, int maxParticles) {
        int particleCount = ThreadLocalRandom.current().nextInt(minParticles, maxParticles);
        for (int i = 0; i < particleCount; i++) {
            double offsetX = ThreadLocalRandom.current().nextDouble(-0.5, 0.5);
            double offsetY = ThreadLocalRandom.current().nextDouble(0, 0.5);
            double offsetZ = ThreadLocalRandom.current().nextDouble(-0.5, 0.5);
            level.addParticle(particleType, pos.getX() + 0.5 + offsetX, pos.getY() + 1.0 + offsetY, pos.getZ() + 0.5 + offsetZ, 0, 0, 0);
        }
    }
    
    private void playInteractionSound(Level level, BlockPos pos) { 
        level.playSound(null, pos, SoundEvents.CHISELED_BOOKSHELF_PICKUP_ENCHANTED, SoundSource.BLOCKS, 0.8f, 0.5f);
        level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.8f, 0.5f);
    }
}
