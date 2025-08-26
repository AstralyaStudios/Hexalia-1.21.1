package net.astralya.hexalia.worldgen.gen.decorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.astralya.hexalia.block.ModBlocks;
import net.astralya.hexalia.block.custom.CocoonBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public class CocoonTreeDecorator extends TreeDecorator {
    public static final MapCodec<CocoonTreeDecorator> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.floatRange(0.0F, 1.0F)
                            .fieldOf("chance")
                            .forGetter(d -> d.chance)
            ).apply(instance, CocoonTreeDecorator::new)
    );


    private final float chance;

    public CocoonTreeDecorator(float chance) {
        this.chance = chance;
    }

    @Override
    protected TreeDecoratorType<?> getType() {
        return ModTreeDecorators.COCOON_TREE;
    }

    @Override
    public void generate(Generator generator) {
        Random random = generator.getRandom();
        for (BlockPos pos : generator.getLogPositions()) {
            Direction[] directions = {Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH};
            Direction direction = directions[random.nextInt(directions.length)];
            BlockPos blockPos = pos.offset(direction);
            if (generator.isAir(blockPos) && generator.isAir(blockPos.down())) {
                generator.replace(blockPos, ModBlocks.SILKWORM_COCOON.getDefaultState().with(CocoonBlock.FACING, direction));
                break;
            }
        }
    }
}
