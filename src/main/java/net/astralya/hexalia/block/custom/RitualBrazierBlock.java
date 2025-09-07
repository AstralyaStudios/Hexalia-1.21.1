package net.astralya.hexalia.block.custom;

import com.mojang.serialization.MapCodec;
import net.astralya.hexalia.block.entity.custom.RitualBrazierBlockEntity;
import net.astralya.hexalia.item.ModItems;
import net.astralya.hexalia.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
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
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class RitualBrazierBlock extends BaseEntityBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty SALTED = BooleanProperty.create("salted");

    protected static final VoxelShape SHAPE = Shapes.or(Block.box(3.0, 0.0, 3.0, 13.0, 1.0, 13.0));
    public static final MapCodec<RitualBrazierBlock> CODEC = simpleCodec(RitualBrazierBlock::new);

    public RitualBrazierBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(SALTED, false));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(SALTED, false);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, SALTED);
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
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new RitualBrazierBlockEntity(pos, state);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof RitualBrazierBlockEntity brazier) {
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), brazier.getStoredItem());
                if (state.getValue(SALTED)) {
                    Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModItems.SALT.get()));
                }
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        BlockEntity tileEntity = level.getBlockEntity(pos);
        if (!(tileEntity instanceof RitualBrazierBlockEntity brazier)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        ItemStack heldStack = player.getItemInHand(hand);
        ItemStack offhandStack = player.getOffhandItem();

        if (hand == InteractionHand.MAIN_HAND && !state.getValue(SALTED)) {
            if (stack.is(ModTags.Items.SALT) && !state.getValue(SALTED)) {
                level.setBlock(pos, state.setValue(SALTED, true), Block.UPDATE_ALL);
                if (!player.isCreative()) {
                    heldStack.shrink(1);
                }
                level.playSound(null, pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0f, 1.0f);
                return ItemInteractionResult.SUCCESS;
            }
        }

        if (brazier.isEmpty()) {
            if (!offhandStack.isEmpty()) {
                if (hand == InteractionHand.MAIN_HAND && !offhandStack.is(ModTags.Items.OFFHAND_EQUIPMENT) && !(heldStack.getItem() instanceof BlockItem)) {
                    return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
                }
                if (hand == InteractionHand.OFF_HAND && offhandStack.is(ModTags.Items.OFFHAND_EQUIPMENT)) {
                    return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
                }
            }

            if (heldStack.isEmpty()) {
                return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            } else if (brazier.addItem(player.getAbilities().instabuild ? heldStack.copy() : heldStack)) {
                playItemSound(level, pos);
                return ItemInteractionResult.SUCCESS;
            }

        } else if (!heldStack.isEmpty() || !offhandStack.isEmpty()) {
            ItemStack focusStack = heldStack.is(ModItems.HEX_FOCUS) ? heldStack :
                    offhandStack.is(ModItems.HEX_FOCUS) ? offhandStack :
                            ItemStack.EMPTY;

            if (!focusStack.isEmpty()) {
                if (!level.isClientSide()) {
                    RitualBrazierBlockEntity.RitualResult result = brazier.tryCelestialRitual();
                    switch (result) {
                        case SUCCESS -> {
                            spawnPoofParticles(level, pos);
                            level.playSound(null, pos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 0.25f, 0.25f);
                        }
                        case NO_CELESTIAL_BLOOMS -> player.displayClientMessage(Component.translatable("message.hexalia.ritual_brazier.no_celestial_blooms"), true);
                        case INVALID_ITEM -> player.displayClientMessage(Component.translatable("message.hexalia.ritual_brazier.invalid_item"), true);
                    }
                }
                return ItemInteractionResult.SUCCESS;
            }


        } else if (hand == InteractionHand.MAIN_HAND) {
            if (!player.isCreative()) {
                if (!player.getInventory().add(brazier.removeItem())) {
                    Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), brazier.removeItem());
                }
            } else {
                brazier.removeItem();
            }
            playItemSound(level, pos);
            return ItemInteractionResult.SUCCESS;
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    private static void spawnPoofParticles(Level level, BlockPos pos) {
        if (level instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.POOF,
                    pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5,
                    10, 0.2, 0.2, 0.2, 0.02);
        }
    }

    private void playItemSound(Level level, BlockPos pos) {
        level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.25f, 0.25f);
    }
}
