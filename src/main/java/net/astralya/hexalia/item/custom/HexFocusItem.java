package net.astralya.hexalia.item.custom;

import net.astralya.hexalia.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Set;

public class HexFocusItem extends Item {

    private static final int BLOCK_BREAK_EVENT_ID = 2001;
    private static final Set<Block> VALID_BLOCKS = Set.of(Blocks.COBBLED_DEEPSLATE, Blocks.DEEPSLATE);

    public HexFocusItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();

        if (player != null && !level.isClientSide()) {
            if (player.getCooldowns().isOnCooldown(this)) {
                return InteractionResult.FAIL;
            }

            if (VALID_BLOCKS.contains(block)) {
                level.levelEvent(BLOCK_BREAK_EVENT_ID, pos, Block.getId(state));
                level.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_HIT,
                        SoundSource.BLOCKS, 1.0f, 1.0f);
                level.setBlockAndUpdate(pos, ModBlocks.RITUAL_TABLE.get().defaultBlockState());
                player.getCooldowns().addCooldown(this, 60);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }
}
