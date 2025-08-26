package net.astralya.hexalia.worldgen.gen;

import net.astralya.hexalia.worldgen.ModPlacedFeatures;
import net.astralya.hexalia.worldgen.biome.ModBiomes;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;

public class ModTreeGeneration {
    public static void generateTrees() {

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.DARK_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.DARK_OAK_COCOON_PLACED_KEY);

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.ENCHANTED_BAYOU),
                GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.COTTONWOOD_PLACED_KEY);

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.ENCHANTED_BAYOU),
                GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.COTTONWOOD_COCOON_PLACED_KEY);

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiomes.ENCHANTED_BAYOU),
                GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.WILLOW_PLACED_KEY);
    }
}
