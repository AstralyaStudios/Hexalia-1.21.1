package net.grapes.hexalia.datagen;

import net.grapes.hexalia.HexaliaMod;
import net.grapes.hexalia.util.ModTags;
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
                .add(Biomes.MUSHROOM_FIELDS);
        this.tag(ModTags.Biomes.HAS_SIREN_KELP)
                .addTag(BiomeTags.IS_OCEAN);
        this.tag(ModTags.Biomes.HAS_GHOST_FERNS)
                .add(Biomes.DARK_FOREST);
    }
}
