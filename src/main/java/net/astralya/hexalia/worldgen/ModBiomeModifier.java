package net.astralya.hexalia.worldgen;

import net.astralya.hexalia.HexaliaMod;
import net.astralya.hexalia.util.ModTags;
import net.astralya.hexalia.worldgen.biome.ModBiomes;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Optional;

public class ModBiomeModifier {

    public static final ResourceKey<BiomeModifier> ADD_SPIRIT_BLOOM = registerKey("add_spirit_bloom");
    public static final ResourceKey<BiomeModifier> ADD_DREAMSHROOM = registerKey("add_dreamshroom");
    public static final ResourceKey<BiomeModifier> ADD_SIREN_KELP = registerKey("add_siren_kelp");
    public static final ResourceKey<BiomeModifier> ADD_GHOST_FERN = registerKey("add_ghost_fern");
    public static final ResourceKey<BiomeModifier> ADD_CELESTIAL_BLOOM = registerKey("add_celestial_bloom");

    public static final ResourceKey<BiomeModifier> ADD_CHILLBERRY = registerKey("add_chillberry");
    public static final ResourceKey<BiomeModifier> ADD_WILD_SUNFIRE_TOMATO = registerKey("add_wild_sunfire_tomato");
    public static final ResourceKey<BiomeModifier> ADD_WILD_MANDRAKE = registerKey("add_wild_mandrake");

    public static final ResourceKey<BiomeModifier> ADD_DARK_OAK_COCOON = registerKey("add_dark_oak_cocoon");

    public static final ResourceKey<BiomeModifier> ADD_HENBANE = registerKey("add_henbane");
    public static final ResourceKey<BiomeModifier> ADD_BEGONIA = registerKey("add_begonia");
    public static final ResourceKey<BiomeModifier> ADD_LAVENDER = registerKey("add_lavender");
    public static final ResourceKey<BiomeModifier> ADD_DAHLIA = registerKey("add_dahlia");

    public static final ResourceKey<BiomeModifier> ADD_BAYOU_FEATURES = registerKey("add_bayou_features");

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);
        Optional<Holder.Reference<Biome>> taigaBiome = biomes.get(Biomes.TAIGA);
        Optional<Holder.Reference<Biome>> flowerForest = biomes.get(Biomes.FLOWER_FOREST);
        Optional<Holder.Reference<Biome>> savannaBiome = biomes.get(Biomes.SAVANNA);
        Optional<Holder.Reference<Biome>> darkForestBiome = biomes.get(Biomes.DARK_FOREST);
        Optional<Holder.Reference<Biome>> meadowBiome = biomes.get(Biomes.MEADOW);
        Optional<Holder.Reference<Biome>> enchantedBayou = biomes.get(ModBiomes.ENCHANTED_BAYOU);

        // Functional Plants
        context.register(ADD_SPIRIT_BLOOM, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ModTags.Biomes.HAS_SPIRIT_BLOOMS),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.SPIRIT_BLOOM_PLACED)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_DREAMSHROOM, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ModTags.Biomes.HAS_DREAMSHROOMS),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.DREAMSHROOM_PLACED)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_SIREN_KELP, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ModTags.Biomes.HAS_SIREN_KELP),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.SIREN_KELP_PLACED)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_CHILLBERRY, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(taigaBiome.get()),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.CHILLBERRY_PLACED)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_WILD_SUNFIRE_TOMATO, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(savannaBiome.get()),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.WILD_SUNFIRE_TOMATO_PLACED)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_WILD_MANDRAKE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ModTags.Biomes.HAS_MANDRAKES),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.WILD_MANDRAKE_PLACED)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_CELESTIAL_BLOOM, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(meadowBiome.get()),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.CELESTIAL_BLOOM_PLACED)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_GHOST_FERN, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ModTags.Biomes.HAS_GHOST_FERNS),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.GHOST_FERN_PLACED)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        // Decorative Plants
        context.register(ADD_HENBANE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ModTags.Biomes.HAS_DECORATIVE_FLOWERS),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.HENBANE_PLACED)),
                GenerationStep.Decoration.VEGETAL_DECORATION));
        context.register(ADD_BEGONIA, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ModTags.Biomes.HAS_DECORATIVE_FLOWERS),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.BEGONIA_PLACED)),
                GenerationStep.Decoration.VEGETAL_DECORATION));
        context.register(ADD_LAVENDER, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(taigaBiome.get()),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.LAVENDER_PLACED)),
                GenerationStep.Decoration.VEGETAL_DECORATION));
        context.register(ADD_DAHLIA, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(flowerForest.get()),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.DAHLIA_PLACED)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        // Trees
        context.register(ADD_DARK_OAK_COCOON, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(darkForestBiome.get()),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.DARK_OAK_COCOON_PLACED)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        // Biomes
        context.register(ADD_BAYOU_FEATURES, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(enchantedBayou.get()),
                HolderSet.direct(
                        placedFeatures.getOrThrow(ModPlacedFeatures.WILLOW_PLACED),
                        placedFeatures.getOrThrow(ModPlacedFeatures.COTTONWOOD_PLACED),
                        placedFeatures.getOrThrow(ModPlacedFeatures.COTTONWOOD_COCOON_PLACED),
                        placedFeatures.getOrThrow(ModPlacedFeatures.LOTUS_FLOWER_PLACED),
                        placedFeatures.getOrThrow(ModPlacedFeatures.DUCKWEED_PLACED),
                        placedFeatures.getOrThrow(ModPlacedFeatures.HEXED_BULRUSH_PLACED),
                        placedFeatures.getOrThrow(ModPlacedFeatures.PALE_MUSHROOM_PLACED),
                        placedFeatures.getOrThrow(ModPlacedFeatures.NIGHTSHADE_BUSH_PLACED),
                        placedFeatures.getOrThrow(ModPlacedFeatures.WITCHWEED_PLACED)
                ),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(HexaliaMod.MODID, name));
    }
}