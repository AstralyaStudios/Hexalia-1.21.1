package net.grapes.hexalia.block.custom;

import com.mojang.serialization.MapCodec;
import net.grapes.hexalia.block.custom.censer.CenserEffectHandler;
import net.grapes.hexalia.block.custom.censer.HerbCombination;
import net.grapes.hexalia.block.entity.custom.CenserBlockEntity;
import net.grapes.hexalia.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class CenserBlock extends BaseEntityBlock {

    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final MapCodec<CenserBlock> CODEC = simpleCodec(CenserBlock::new);


    public CenserBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(LIT, false));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Vec3 vec3 = pState.getOffset(pLevel, pPos);
        return SHAPE.move(vec3.x, vec3.y, vec3.z);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack heldItem = player.getItemInHand(hand);
        BlockEntity blockEntity = level.getBlockEntity(pos);

        if (!(blockEntity instanceof CenserBlockEntity censer)) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

        if (heldItem.getItem() instanceof FlintAndSteelItem && !state.getValue(LIT)) {
            ItemStack herb1 = censer.getItem(0);
            ItemStack herb2 = censer.getItem(1);

            if (herb1.isEmpty() || herb2.isEmpty()) {
                if (level.isClientSide()) {
                    player.displayClientMessage(Component.translatable("message.hexalia.censer_not_full"), true);
                }
                return ItemInteractionResult.FAIL;
            }

            HerbCombination combo = new HerbCombination(herb1.getItem(), herb2.getItem());
            if (!CenserEffectHandler.isValidCombination(herb1.getItem(), herb2.getItem())) {
                if (level.isClientSide()) {
                    player.displayClientMessage(Component.translatable("message.hexalia.invalid_herb_combination"), true);
                }
                return ItemInteractionResult.FAIL;
            }

            if (!level.isClientSide()) {
                sendEffectActivationMessage(level, pos, combo, player);
                censer.setActiveCombination(combo);
                censer.clearItems();
                level.setBlockAndUpdate(pos, state.setValue(LIT, true));
                censer.setBurnTime(CenserBlockEntity.MAX_BURN_TIME);
                CenserEffectHandler.startEffect(level, pos, combo);
            }

            heldItem.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
            level.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0f, level.random.nextFloat() * 0.4F + 0.8F);
            return ItemInteractionResult.SUCCESS;
        }

        if (heldItem.getItem() instanceof ShovelItem && state.getValue(LIT)) {
            if (!level.isClientSide()) {
                level.setBlockAndUpdate(pos, state.setValue(LIT, false));
                censer.setBurnTime(0);
            }
            return ItemInteractionResult.SUCCESS;
        }

        if (!state.getValue(LIT)) {
            if (heldItem.isEmpty()) {
                for (int i = 0; i < censer.getItems().size(); i++) {
                    ItemStack stackInSlot = censer.getItem(i);
                    if (!stackInSlot.isEmpty()) {
                        ItemStack removedStack = censer.removeStack(i);
                        if (!player.getInventory().add(removedStack)) {
                            player.drop(removedStack, false);
                        }
                        level.playSound(null, pos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 0.5f, 1.0f);
                        return ItemInteractionResult.SUCCESS;
                    }
                }
            } else if (heldItem.is(ModTags.Items.HERBS)) {
                for (int i = 0; i < censer.getItems().size(); i++) {
                    if (censer.getItem(i).isEmpty()) {
                        ItemStack stackToInsert = heldItem.copy();
                        stackToInsert.setCount(1);
                        censer.setItem(i, stackToInsert);
                        if (!player.isCreative()) {
                            heldItem.shrink(1);
                        }
                        level.playSound(null, pos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 0.5f, 1.0f);
                        return ItemInteractionResult.SUCCESS;
                    }
                }
            } else {
                if (level.isClientSide()) {
                    player.displayClientMessage(Component.translatable("message.hexalia.invalid_item"), true);
                }
                return ItemInteractionResult.FAIL;
            }
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }


    private void sendEffectActivationMessage(Level level, BlockPos pos, HerbCombination combo, Player activatingPlayer) {
        String messageKey = CenserEffectHandler.getMessageKeyForCombination(combo);
        AABB area = new AABB(pos).inflate(CenserEffectHandler.AREA_RADIUS);

        for (Player player : level.getEntitiesOfClass(Player.class, area)) {
            if (!player.getUUID().equals(activatingPlayer.getUUID()) && player instanceof ServerPlayer serverPlayer) {
                serverPlayer.displayClientMessage(Component.translatable(messageKey), true);
            }
        }

        if (!level.isClientSide() && activatingPlayer instanceof ServerPlayer serverPlayer) {
            serverPlayer.displayClientMessage(Component.translatable(messageKey), true);
        }
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pState.getValue(LIT)) {
            if (pRandom.nextInt(10) == 0) {
                pLevel.playLocalSound(
                        pPos.getX() + 0.5D, pPos.getY() + 0.5D, pPos.getZ() + 0.5D,
                        SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS,
                        0.5F + pRandom.nextFloat(), pRandom.nextFloat() * 0.7F + 0.6F, false
                );
            }

            pLevel.addAlwaysVisibleParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, true,
                    pPos.getX() + 0.5D + pRandom.nextDouble() / 3.0D * (pRandom.nextBoolean() ? 1 : -1),
                    pPos.getY() + pRandom.nextDouble() + pRandom.nextDouble(),
                    pPos.getZ() + 0.5D + pRandom.nextDouble() / 3.0D * (pRandom.nextBoolean() ? 1 : -1),
                    0.0D, 0.07D, 0.0D
            );

            pLevel.addParticle(ParticleTypes.SMOKE,
                    pPos.getX() + 0.5D + pRandom.nextDouble() / 4.0D * (pRandom.nextBoolean() ? 1 : -1),
                    pPos.getY() + 0.4D,
                    pPos.getZ() + 0.5D + pRandom.nextDouble() / 4.0D * (pRandom.nextBoolean() ? 1 : -1),
                    0.0D, 0.005D, 0.0D
            );

            if (pRandom.nextInt(25) == 0) {
                pLevel.addParticle(ParticleTypes.LAVA,
                        pPos.getX() + 0.5D, pPos.getY() + 0.3D, pPos.getZ() + 0.5D,
                        pRandom.nextFloat() / 8.0F, 0.0D, pRandom.nextFloat() / 8.0F
                );
            }
        }
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CenserBlockEntity censer) {
                if (world instanceof ServerLevel) {
                    Containers.dropContents(world, pos, censer.getItems());
                }
                world.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, world, pos, newState, moved);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return defaultBlockState()
                .setValue(FACING, pContext.getHorizontalDirection().getOpposite())
                .setValue(LIT, false);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, LIT);
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CenserBlockEntity(pos, state);
    }
}
