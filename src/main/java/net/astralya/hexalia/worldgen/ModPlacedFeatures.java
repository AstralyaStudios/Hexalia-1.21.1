package net.astralya.hexalia.worldgen;

import net.astralya.hexalia.HexaliaMod;
import net.astralya.hexalia.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> SPIRIT_BLOOM_PLACED = registerKey("spirit_bloom_placed");
    public static final ResourceKey<PlacedFeature> DREAMSHROOM_PLACED = registerKey("dreamshroom_placed");
    public static final ResourceKey<PlacedFeature> SIREN_KELP_PLACED = registerKey("siren_kelp_placed");
    public static final ResourceKey<PlacedFeature> CHILLBERRY_PLACED = registerKey("chillberry_placed");
    public static final ResourceKey<PlacedFeature> WILD_SUNFIRE_TOMATO_PLACED = registerKey("wild_sunfire_tomato_placed");
    public static final ResourceKey<PlacedFeature> WILD_MANDRAKE_PLACED = registerKey("wild_mandrake_placed");
    public static final ResourceKey<PlacedFeature> HENBANE_PLACED = registerKey("henbane_placed");
    public static final ResourceKey<PlacedFeature> BEGONIA_PLACED = registerKey("begonia_placed");
    public static final ResourceKey<PlacedFeature> LAVENDER_PLACED = registerKey("lavender_placed");
    public static final ResourceKey<PlacedFeature> DAHLIA_PLACED = registerKey("dahlia_placed");
    public static final ResourceKey<PlacedFeature> CELESTIAL_BLOOM_PLACED = registerKey("celestial_bloom_placed");

    public static final ResourceKey<PlacedFeature> COTTONWOOD_PLACED = registerKey("cottonwood_placed");
    public static final ResourceKey<PlacedFeature> WILLOW_PLACED = registerKey("willow_placed");
    public static final ResourceKey<PlacedFeature> COTTONWOOD_COCOON_PLACED = registerKey("cottonwood_cocoon_placed");
    public static final ResourceKey<PlacedFeature> DARK_OAK_COCOON_PLACED = registerKey("dark_oak_cocoon_placed");

    public static final ResourceKey<PlacedFeature> LOTUS_FLOWER_PLACED = registerKey("lotus_flower_placed");
    public static final ResourceKey<PlacedFeature> PALE_MUSHROOM_PLACED = registerKey("pale_mushroom_placed");
    public static final ResourceKey<PlacedFeature> WITCHWEED_PLACED = registerKey("witchweed_placed");
    public static final ResourceKey<PlacedFeature> GHOST_FERN_PLACED = registerKey("ghost_fern_placed");
    public static final ResourceKey<PlacedFeature> HEXED_BULRUSH_PLACED = registerKey("hexed_bulrush_placed");
    public static final ResourceKey<PlacedFeature> NIGHTSHADE_BUSH_PLACED = registerKey("nightshade_bush_placed");
    public static final ResourceKey<PlacedFeature> DUCKWEED_PLACED = registerKey("duckweed_placed");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configured = context.lookup(Registries.CONFIGURED_FEATURE);

        // Functional Plants
        register(context, SPIRIT_BLOOM_PLACED, configured.getOrThrow(ModConfiguredFeatures.SPIRIT_BLOOM), patchModifiers(1));
        register(context, DREAMSHROOM_PLACED, configured.getOrThrow(ModConfiguredFeatures.DREAMSHROOM), heightmapSurfacePatch(1));
        register(context, SIREN_KELP_PLACED, configured.getOrThrow(ModConfiguredFeatures.SIREN_KELP), oceanFloorPatch(2));
        register(context, CHILLBERRY_PLACED, configured.getOrThrow(ModConfiguredFeatures.CHILLBERRY), rarityPatch(8));
        register(context, WILD_SUNFIRE_TOMATO_PLACED, configured.getOrThrow(ModConfiguredFeatures.WILD_SUNFIRE_TOMATO), rarityPatch(10));
        register(context, WILD_MANDRAKE_PLACED, configured.getOrThrow(ModConfiguredFeatures.WILD_MANDRAKE), rarityPatch(10));
        register(context, GHOST_FERN_PLACED, configured.getOrThrow(ModConfiguredFeatures.GHOST_FERN), heightmapSurfacePatch(1));
        register(context, CELESTIAL_BLOOM_PLACED, configured.getOrThrow(ModConfiguredFeatures.CELESTIAL_BLOOM), heightmapSurfacePatch(1));

        // Decorative Plants
        register(context, HENBANE_PLACED, configured.getOrThrow(ModConfiguredFeatures.HENBANE), rarityPatch(6));
        register(context, BEGONIA_PLACED, configured.getOrThrow(ModConfiguredFeatures.BEGONIA), rarityPatch(6));
        register(context, LAVENDER_PLACED, configured.getOrThrow(ModConfiguredFeatures.LAVENDER), rarityPatch(10));
        register(context, DAHLIA_PLACED, configured.getOrThrow(ModConfiguredFeatures.DAHLIA), rarityPatch(10));

        register(context, LOTUS_FLOWER_PLACED, configured.getOrThrow(ModConfiguredFeatures.LOTUS_FLOWER), heightmapSurfacePatch(2));
        register(context, PALE_MUSHROOM_PLACED, configured.getOrThrow(ModConfiguredFeatures.PALE_MUSHROOM), heightmapSurfacePatch(1));
        register(context, WITCHWEED_PLACED, configured.getOrThrow(ModConfiguredFeatures.WITCHWEED), rarityPatchSurface(1));
        register(context, HEXED_BULRUSH_PLACED, configured.getOrThrow(ModConfiguredFeatures.HEXED_BULRUSH), topSolidPatch(6));
        register(context, NIGHTSHADE_BUSH_PLACED, configured.getOrThrow(ModConfiguredFeatures.NIGHTSHADE_BUSH), rarityPatch(2));
        register(context, DUCKWEED_PLACED, configured.getOrThrow(ModConfiguredFeatures.DUCKWEED), List.of(
                CountPlacement.of(3),                   // 3 clusters per chunk
                InSquarePlacement.spread(),             // spread within the chunk
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE, // snap to water surface
                BiomeFilter.biome()
        ));

        // Trees
        registerTree(context, DARK_OAK_COCOON_PLACED, configured.getOrThrow(ModConfiguredFeatures.DARK_OAK_COCOON), Blocks.DARK_OAK_SAPLING, 1, 0.1f, 1);
        registerTree(context, COTTONWOOD_PLACED, configured.getOrThrow(ModConfiguredFeatures.COTTONWOOD), ModBlocks.COTTONWOOD_SAPLING.get(), 2, 0.1f, 1);
        registerTree(context, COTTONWOOD_COCOON_PLACED, configured.getOrThrow(ModConfiguredFeatures.COTTONWOOD_COCOON), ModBlocks.COTTONWOOD_SAPLING.get(), 5, 0.1f, 1);
        registerTree(context, WILLOW_PLACED, configured.getOrThrow(ModConfiguredFeatures.WILLOW), ModBlocks.WILLOW_SAPLING.get(), 10, 0.1f, 1);
    }


    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(HexaliaMod.MODID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static List<PlacementModifier> patchModifiers(int count) {
        return List.of(
                CountPlacement.of(count),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome()
        );
    }

    private static List<PlacementModifier> rarityPatch(int rarity) {
        return List.of(
                RarityFilter.onAverageOnceEvery(rarity),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome()
        );
    }

    private static List<PlacementModifier> topSolidPatch(int count) {
        return List.of(
                CountPlacement.of(count),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_TOP_SOLID,
                BiomeFilter.biome()
        );
    }

    private static List<PlacementModifier> heightmapSurfacePatch(int count) {
        return List.of(
                CountPlacement.of(count),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                BiomeFilter.biome()
        );
    }

    private static List<PlacementModifier> oceanFloorPatch(int count) {
        return List.of(
                CountPlacement.of(count),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                BiomeFilter.biome()
        );
    }

    private static List<PlacementModifier> rarityPatchSurface(int rarity) {
        return List.of(
                RarityFilter.onAverageOnceEvery(rarity),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                BiomeFilter.biome()
        );
    }

    private static void registerTree(BootstrapContext<PlacedFeature> context,
                                     ResourceKey<PlacedFeature> key,
                                     Holder<ConfiguredFeature<?, ?>> feature,
                                     Block sapling,
                                     int baseCount, float extraChance, int extraCount) {
        register(context, key, feature,
                VegetationPlacements.treePlacement(
                        PlacementUtils.countExtra(baseCount, extraChance, extraCount),
                        sapling));
    }

}

