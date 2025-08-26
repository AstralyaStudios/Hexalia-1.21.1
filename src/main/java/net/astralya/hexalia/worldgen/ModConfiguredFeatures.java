package net.astralya.hexalia.worldgen;

import net.astralya.hexalia.HexaliaMod;
import net.astralya.hexalia.block.ModBlocks;
import net.astralya.hexalia.block.custom.ChillberryBushBlock;
import net.astralya.hexalia.worldgen.gen.decorator.CatkinTreeDecorator;
import net.astralya.hexalia.worldgen.gen.decorator.CocoonTreeDecorator;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.*;
import net.minecraft.world.gen.root.AboveRootPlacement;
import net.minecraft.world.gen.root.MangroveRootPlacement;
import net.minecraft.world.gen.root.MangroveRootPlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.treedecorator.LeavesVineTreeDecorator;
import net.minecraft.world.gen.trunk.DarkOakTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import net.minecraft.world.gen.trunk.UpwardsBranchingTrunkPlacer;

import java.util.List;
import java.util.Optional;

public class ModConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?, ?>> SPIRIT_BLOOM_KEY = registerKey("spirit_bloom");
    public static final RegistryKey<ConfiguredFeature<?, ?>> CHILLBERRY_KEY = registerKey("chillberry");
    public static final RegistryKey<ConfiguredFeature<?, ?>> DREAMSHROOM_KEY = registerKey("dreamshroom");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SIREN_KELP_KEY = registerKey("siren_kelp");
    public static final RegistryKey<ConfiguredFeature<?, ?>> CELESTIAL_BLOOM_KEY = registerKey("celestial_bloom");

    public static final RegistryKey<ConfiguredFeature<?, ?>> WILD_SUNFIRE_TOMATO_KEY = registerKey("wild_sunfire_tomato");
    public static final RegistryKey<ConfiguredFeature<?, ?>> WILD_MANDRAKE_KEY = registerKey("wild_mandrake");

    public static final RegistryKey<ConfiguredFeature<?, ?>> HENBANE_KEY = registerKey("henbane");
    public static final RegistryKey<ConfiguredFeature<?, ?>> BEGONIA_KEY = registerKey("begonia");
    public static final RegistryKey<ConfiguredFeature<?, ?>> LAVENDER_KEY = registerKey("lavender");
    public static final RegistryKey<ConfiguredFeature<?, ?>> DAHLIA_KEY = registerKey("dahlia");

    public static final RegistryKey<ConfiguredFeature<?, ?>> DARK_OAK_COCOON_KEY = registerKey("dark_oak_cocoon_tree");
    public static final RegistryKey<ConfiguredFeature<?, ?>> COTTONWOOD_KEY = registerKey("cottonwood");
    public static final RegistryKey<ConfiguredFeature<?, ?>> COTTONWOOD_COCOON_KEY = registerKey("cottonwood_cocoon");
    public static final RegistryKey<ConfiguredFeature<?, ?>> WILLOW_KEY = registerKey("willow_key");

    public static final RegistryKey<ConfiguredFeature<?, ?>> LOTUS_FLOWER_KEY = registerKey("lotus_flower_key");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PALE_MUSHROOM_KEY = registerKey("pale_mushroom_key");
    public static final RegistryKey<ConfiguredFeature<?, ?>> WITCHWEED_KEY = registerKey("witchweed_key");
    public static final RegistryKey<ConfiguredFeature<?, ?>> GHOST_FERN_KEY = registerKey("ghost_fern_key");
    public static final RegistryKey<ConfiguredFeature<?, ?>> HEXED_BULRUSH_KEY = registerKey("hexed_bulrush_key");
    public static final RegistryKey<ConfiguredFeature<?, ?>> NIGHTSHADE_BUSH_KEY = registerKey("nightshade_bush_key");
    public static final RegistryKey<ConfiguredFeature<?, ?>> DUCKWEED_KEY = registerKey("duckweed_key");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        RegistryEntryLookup<Block> registryEntryLookup = context.getRegistryLookup(RegistryKeys.BLOCK);

        // Functional Plants
        register(context, SPIRIT_BLOOM_KEY, Feature.FLOWER, ConfiguredFeatures.createRandomPatchFeatureConfig(3,
                PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.SPIRIT_BLOOM)))));

        register(context, CHILLBERRY_KEY, Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(10,
                PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.CHILLBERRY_BUSH.getDefaultState().with(ChillberryBushBlock.AGE, 3))))));

        register(context, DREAMSHROOM_KEY, Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(10,
                PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.DREAMSHROOM)))));

        register(context, WILD_SUNFIRE_TOMATO_KEY, Feature.FLOWER, ConfiguredFeatures.createRandomPatchFeatureConfig(3,
                PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.WILD_SUNFIRE_TOMATO)))));

        register(context, SIREN_KELP_KEY, Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(12,
                PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.SIREN_KELP)))));

        register(context, WILD_MANDRAKE_KEY, Feature.FLOWER, ConfiguredFeatures.createRandomPatchFeatureConfig(3,
                PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.WILD_MANDRAKE)))));

        register(context, GHOST_FERN_KEY, Feature.FLOWER, ConfiguredFeatures.createRandomPatchFeatureConfig(1,
                PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.GHOST_FERN)))));

        register(context, CELESTIAL_BLOOM_KEY, Feature.FLOWER, ConfiguredFeatures.createRandomPatchFeatureConfig(1,
                PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.CELESTIAL_BLOOM)))));

        // Decorative Plants
        register(context, HENBANE_KEY, Feature.FLOWER, ConfiguredFeatures.createRandomPatchFeatureConfig(3,
                PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.HENBANE)))));

        register(context, BEGONIA_KEY, Feature.FLOWER, ConfiguredFeatures.createRandomPatchFeatureConfig(3,
                PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.BEGONIA)))));

        register(context, LAVENDER_KEY, Feature.FLOWER, ConfiguredFeatures.createRandomPatchFeatureConfig(3,
                PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.LAVENDER)))));

        register(context, DAHLIA_KEY, Feature.FLOWER, ConfiguredFeatures.createRandomPatchFeatureConfig(3,
                PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.DAHLIA)))));

        register(context, LOTUS_FLOWER_KEY, Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(5,
                PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.LOTUS_FLOWER)))));

        register(context, PALE_MUSHROOM_KEY, Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(5,
                PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.PALE_MUSHROOM)))));

        register(context, WITCHWEED_KEY, Feature.FLOWER, ConfiguredFeatures.createRandomPatchFeatureConfig(5,
                PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.WITCHWEED)))));

        register(context, HEXED_BULRUSH_KEY, Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(5,
                PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.HEXED_BULRUSH)))));

        register(context, NIGHTSHADE_BUSH_KEY, Feature.FLOWER, ConfiguredFeatures.createRandomPatchFeatureConfig(3,
                PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.NIGHTSHADE_BUSH)))));

        register(context, DUCKWEED_KEY, Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(5,
                PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.DUCKWEED)))));

        // Trees
        register(context, DARK_OAK_COCOON_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(Blocks.DARK_OAK_LOG),
                new DarkOakTrunkPlacer(5, 2, 1),
                BlockStateProvider.of(Blocks.DARK_OAK_LEAVES),
                new DarkOakFoliagePlacer(ConstantIntProvider.create(1), ConstantIntProvider.create(0)),
                new TwoLayersFeatureSize(1, 0, 1))
                .decorators(List.of(new CocoonTreeDecorator(0.2f)))
                .build());

        register(context, COTTONWOOD_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(ModBlocks.COTTONWOOD_LOG),
                new StraightTrunkPlacer(6, 2, 1),
                BlockStateProvider.of(ModBlocks.COTTONWOOD_LEAVES),
                new LargeOakFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(1), 3) {},
                new TwoLayersFeatureSize(1, 0, 2))
                .decorators(List.of(new CatkinTreeDecorator()))
                .build());

        register(context, COTTONWOOD_COCOON_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(ModBlocks.COTTONWOOD_LOG),
                new StraightTrunkPlacer(6, 2, 1),
                BlockStateProvider.of(ModBlocks.COTTONWOOD_LEAVES),
                new LargeOakFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(1), 3) {},
                new TwoLayersFeatureSize(1, 0, 2))
                .decorators(List.of(new CatkinTreeDecorator(), new CocoonTreeDecorator(0.2f)))
                .build());

        register(context, WILLOW_KEY, net.minecraft.world.gen.feature.Feature.TREE,
                new net.minecraft.world.gen.feature.TreeFeatureConfig.Builder(
                        net.minecraft.world.gen.stateprovider.BlockStateProvider.of(net.astralya.hexalia.block.ModBlocks.WILLOW_LOG),
                        new net.minecraft.world.gen.trunk.StraightTrunkPlacer(4, 2, 1),
                        net.minecraft.world.gen.stateprovider.BlockStateProvider.of(net.astralya.hexalia.block.ModBlocks.WILLOW_LEAVES),
                        new net.minecraft.world.gen.foliage.CherryFoliagePlacer(
                                ConstantIntProvider.create(4),
                                ConstantIntProvider.create(0),
                                ConstantIntProvider.create(5),
                                0.3F, 0.7F, 0.25F, 0.5F
                        ),
                        new net.minecraft.world.gen.feature.size.TwoLayersFeatureSize(1, 0, 2)
                )
                        .ignoreVines()
                        .build()
        );
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(HexaliaMod.MODID, name));

    }
    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
