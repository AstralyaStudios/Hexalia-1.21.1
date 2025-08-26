package net.astralya.hexalia.worldgen.gen.decorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.astralya.hexalia.block.ModBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import java.util.List;

public class CatkinTreeDecorator extends TreeDecorator {
    public static final MapCodec<CatkinTreeDecorator> CODEC = MapCodec.unit(new CatkinTreeDecorator());

    public CatkinTreeDecorator() {
    }

    @Override
    protected TreeDecoratorType<?> getType() {
        return ModTreeDecorators.CATKIN;
    }

    @Override
    public void generate(Generator generator) {
        Random random = generator.getRandom();
        List<BlockPos> leavesPositions = generator.getLeavesPositions();
        int catkinsPlaced = 0;
        int maxCatkins = 3 + random.nextInt(2);
        for (BlockPos pos : leavesPositions) {
            if (catkinsPlaced >= maxCatkins) {
                break;
            }
            BlockPos blockPosBelow = pos.down();
            if (generator.isAir(blockPosBelow)) {
                generator.replace(blockPosBelow, ModBlocks.COTTONWOOD_CATKIN.getDefaultState());
                catkinsPlaced++;
            }
        }
    }
}