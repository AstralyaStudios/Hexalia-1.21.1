package net.astralya.hexalia.worldgen.biome;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class ModSurfaceRules {

    private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT);
    private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
    private static final SurfaceRules.RuleSource MUD = makeStateRule(Blocks.MUD);

    public static SurfaceRules.RuleSource makeRules() {
        SurfaceRules.ConditionSource isBayou = SurfaceRules.isBiome(ModBiomes.ENCHANTED_BAYOU);
        SurfaceRules.ConditionSource isAtOrAboveWaterLevel = SurfaceRules.waterBlockCheck(-1, 0);

        SurfaceRules.ConditionSource mudNoise = SurfaceRules.noiseCondition(Noises.SURFACE, -0.2, 0.2);

        SurfaceRules.RuleSource bayouSurface = SurfaceRules.sequence(
                SurfaceRules.ifTrue(mudNoise, MUD),
                SurfaceRules.ifTrue(isAtOrAboveWaterLevel, GRASS_BLOCK),
                DIRT
        );

        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(isBayou, bayouSurface)
        );
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}
