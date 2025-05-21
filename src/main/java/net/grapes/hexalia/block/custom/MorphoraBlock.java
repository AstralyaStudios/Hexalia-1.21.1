package net.grapes.hexalia.block.custom;

import net.grapes.hexalia.sound.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;

public class MorphoraBlock extends EnchantedPlantBlock{

    private static final Map<Block, Block> CONVERSION_MAP = new HashMap<>();

    static {
        CONVERSION_MAP.put(Blocks.MAGMA_BLOCK, Blocks.LAVA);
        CONVERSION_MAP.put(Blocks.SNOW_BLOCK, Blocks.PACKED_ICE);
        CONVERSION_MAP.put(Blocks.SOUL_SAND, Blocks.SOUL_SOIL);
        CONVERSION_MAP.put(Blocks.DIRT, Blocks.PODZOL);
        CONVERSION_MAP.put(Blocks.SAND, Blocks.RED_SAND);
        CONVERSION_MAP.put(Blocks.DIORITE, Blocks.GRANITE);
        CONVERSION_MAP.put(Blocks.GRANITE, Blocks.ANDESITE);
        CONVERSION_MAP.put(Blocks.ANDESITE, Blocks.DIORITE);
        CONVERSION_MAP.put(Blocks.ICE, Blocks.BLUE_ICE);
        CONVERSION_MAP.put(Blocks.BLACKSTONE, Blocks.CALCITE);
    }

    public MorphoraBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(Blocks.MAGMA_BLOCK) || state.isSolid();
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        if (!level.isClientSide) {
            level.scheduleTick(pos, this, 80);
        }
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BlockPos belowPos = pos.below();
        boolean converted = false;

        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                BlockPos targetPos = belowPos.offset(dx, 0, dz);
                BlockState targetState = level.getBlockState(targetPos);
                Block targetBlock = CONVERSION_MAP.get(targetState.getBlock());

                if (targetBlock != null) {
                    converted = true;
                    level.setBlockAndUpdate(targetPos, pushEntitiesUp(targetState,
                            targetBlock.defaultBlockState(), level, targetPos));
                    level.levelEvent(2001, targetPos, Block.getId(targetState));
                }

                if (converted) {
                    level.playSound(null, pos, ModSoundEvents.CONVERSION.get(), SoundSource.BLOCKS, 0.8F, 1.0F);
                    level.destroyBlock(pos, false);
                }
            }
        }
    }
}
