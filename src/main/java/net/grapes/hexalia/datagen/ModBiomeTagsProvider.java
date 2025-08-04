package net.grapes.hexalia.datagen;

import net.grapes.hexalia.HexaliaMod;
import net.grapes.hexalia.util.ModTags;
import net.grapes.hexalia.worldgen.biome.ModBiomes;
import net.grapes.hexalia.worldgen.biome.ModOverworldBiomes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModBiomeTagsProvider extends BiomeTagsProvider {

    public ModBiomeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, HexaliaMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(ModTags.Biomes.HAS_MANDRAKES)
                .add(Biomes.FOREST)
                .add(Biomes.BIRCH_FOREST);
        this.tag(ModTags.Biomes.HAS_DREAMSHROOMS)
                .add(Biomes.OLD_GROWTH_PINE_TAIGA)
                .addOptional(ModBiomes.ENCHANTED_BAYOU.location())
                .add(Biomes.MUSHROOM_FIELDS);
        this.tag(ModTags.Biomes.HAS_SIREN_KELP)
                .addOptional(ModBiomes.ENCHANTED_BAYOU.location())
                .addTag(BiomeTags.IS_OCEAN);
        this.tag(ModTags.Biomes.HAS_GHOST_FERNS)
                .addOptional(ModBiomes.ENCHANTED_BAYOU.location())
                .add(Biomes.DARK_FOREST);
        this.tag(ModTags.Biomes.HAS_DECORATIVE_FLOWERS)
                .add(Biomes.SUNFLOWER_PLAINS)
                .add(Biomes.PLAINS);
        this.tag(ModTags.Biomes.HAS_SPIRIT_BLOOMS)
                .add(Biomes.MANGROVE_SWAMP)
                .addOptional(ModBiomes.ENCHANTED_BAYOU.location())
                .add(Biomes.SWAMP);
    }
}
