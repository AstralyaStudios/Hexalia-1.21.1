package net.astralya.hexalia.block.custom;

import net.astralya.hexalia.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class InfusedDirtBlock extends Block {
    public InfusedDirtBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (stack.getItem() instanceof HoeItem && state.is(ModBlocks.INFUSED_DIRT.get())) {
            if (hitResult.getDirection() != Direction.DOWN && level.isEmptyBlock(pos.above())) {
                if (!level.isClientSide) {
                    level.playSound(null, pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0f, 1.0f);
                    level.setBlock(pos, ModBlocks.INFUSED_FARMLAND.get().defaultBlockState(), Block.UPDATE_ALL);

                    if (!player.isCreative()) {
                        stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
                    }
                }
                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }
}
