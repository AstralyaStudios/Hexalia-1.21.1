package net.astralya.hexalia.block.entity.custom;

import net.astralya.hexalia.util.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface HeatingBlock {
    default boolean isHeated(World world, BlockPos pos) {
        BlockState below = world.getBlockState(pos.down());
        if (!below.isIn(ModTags.Blocks.HEATING_BLOCKS)) return false;
        if (below.contains(Properties.LIT)) return below.get(Properties.LIT);
        return true;
    }
}
