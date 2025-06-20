package net.grapes.hexalia.block.custom;

import net.grapes.hexalia.block.entity.custom.LunarLilyBlockEntity;
import net.grapes.hexalia.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class LunarLilyBlock extends EnchantedPlantBlock implements EntityBlock {
    public LunarLilyBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (level.isClientSide) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof LunarLilyBlockEntity lunarLily && lunarLily.isActive()) {
                float progress = lunarLily.getProgress();
                int particleCount = Math.max(2, (int)(6 * (1.0f - progress * 0.5f)));

                for (int i = 0; i < particleCount; i++) {
                    double x = pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 0.3;
                    double y = pos.getY() + 0.7 + random.nextDouble() * 0.3;
                    double z = pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 0.3;
                    level.addParticle(ParticleTypes.EFFECT, x, y, z, 0, 0.01, 0);
                }
            }
        }
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!isNight(level)) {
            return ItemInteractionResult.FAIL;
        }

        ItemStack itemStack = player.getItemInHand(hand);
        BlockEntity blockEntity = level.getBlockEntity(pos);

        if (blockEntity instanceof LunarLilyBlockEntity lunarLily) {
            if (itemStack.isEmpty()) {
                if (!lunarLily.isActive()) {
                    player.displayClientMessage(
                            Component.translatable("message.hexalia.lunar_lily.inactive"),
                            true
                    );
                }
                return ItemInteractionResult.SUCCESS;
            }

            if (itemStack.getItem() == ModItems.MOON_CRYSTAL.get()) {
                if (!lunarLily.isActive()) {
                    lunarLily.activate(level.getGameTime());
                    level.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_HIT, SoundSource.BLOCKS, 1.0f, 1.0f);
                    if (!player.isCreative()) {
                        itemStack.shrink(1);
                    }

                    player.displayClientMessage(
                            Component.translatable("message.hexalia.lunar_lily.activation"),
                            true
                    );

                    return ItemInteractionResult.SUCCESS;
                }
            }
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }


    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new LunarLilyBlockEntity(blockPos, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return level.isClientSide ? null : (level1, pos, state1, blockEntity) -> {
            if (blockEntity instanceof LunarLilyBlockEntity lunarLily) {
                LunarLilyBlockEntity.tick(level1, pos, state1, lunarLily);
            }
        };
    }

    private static boolean isNight(Level level) {
        long time = level.getDayTime() % 24000;
        return time >= 13000 && time <= 23000;
    }
}