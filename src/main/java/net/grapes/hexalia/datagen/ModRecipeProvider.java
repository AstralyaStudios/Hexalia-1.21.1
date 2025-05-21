package net.grapes.hexalia.datagen;

import net.grapes.hexalia.block.ModBlocks;
import net.grapes.hexalia.item.ModItems;
import net.grapes.hexalia.util.ModTags;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput pWriter) {
        // Shaped Recipe for Items & Blocks
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SMALL_CAULDRON.get())
                .pattern("S S")
                .pattern("P P")
                .pattern("SSS")
                .define('P', Items.COPPER_INGOT)
                .define('S', Items.COBBLED_DEEPSLATE)
                .unlockedBy("has_copper_ingot",
                        inventoryTrigger(ItemPredicate.Builder.item().of(Items.COPPER_INGOT).build()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.DREAMCATCHER.get())
                .pattern(" S ")
                .pattern("SPS")
                .pattern("ATA")
                .define('P', Items.STRING)
                .define('S', Items.STICK)
                .define('A', Items.FEATHER)
                .define('T', ModItems.FIRE_NODE.get())
                .unlockedBy("has_stick",
                        inventoryTrigger(ItemPredicate.Builder.item().of(Items.STICK).build()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.SALT_LAMP.get())
                .pattern(" A ")
                .pattern(" P ")
                .pattern(" S ")
                .define('P', Items.TORCH)
                .define('S', ModTags.Items.SALT)
                .define('A', Items.COPPER_INGOT)
                .unlockedBy("has_salt",
                        inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.SALT.get()).build()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BREWING, ModItems.RUSTIC_BOTTLE.get(), 3)
                .pattern("S S")
                .pattern(" P ")
                .define('P', Items.CLAY_BALL)
                .define('S', Blocks.GLASS)
                .unlockedBy("has_clay_ball",
                        inventoryTrigger(ItemPredicate.Builder.item().of(Items.CLAY_BALL).build()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.INFUSED_DIRT.get(), 2)
                .pattern("SP")
                .pattern("PS")
                .define('P', Blocks.DIRT)
                .define('S', ModItems.SIREN_KELP.get())
                .unlockedBy("has_siren_kelp",
                        inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.SIREN_KELP.get()).build()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.STONE_DAGGER.get())
                .pattern(" S")
                .pattern("P ")
                .define('S', Blocks.COBBLESTONE)
                .define('P', Items.STICK)
                .unlockedBy("has_cobblestone",
                        inventoryTrigger(ItemPredicate.Builder.item().of(Blocks.COBBLESTONE).build()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.RUSTIC_OVEN.get())
                .pattern("PPP")
                .pattern("SAS")
                .pattern("SSS")
                .define('P', Items.IRON_INGOT)
                .define('S', Items.COBBLED_DEEPSLATE)
                .define('A', ItemTags.COALS)
                .unlockedBy("has_cobbled_deepslate",
                        inventoryTrigger(ItemPredicate.Builder.item().of(Blocks.COBBLED_DEEPSLATE).build()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.SHELF.get())
                .pattern(" P ")
                .pattern("S S")
                .define('P', Items.COBBLED_DEEPSLATE_SLAB)
                .define('S', Items.STICK)
                .unlockedBy("has_cobbled_deepslate",
                        inventoryTrigger(ItemPredicate.Builder.item().of(Blocks.COBBLED_DEEPSLATE_SLAB).build()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.CENSER.get())
                .pattern(" P ")
                .pattern("PAP")
                .pattern("SSS")
                .define('P', Items.BRICK)
                .define('S', ItemTags.LOGS)
                .define('A', ItemTags.COALS)
                .unlockedBy("has_brick",
                        inventoryTrigger(ItemPredicate.Builder.item().of(Items.BRICK).build()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.HEX_FOCUS.get())
                .pattern("  S")
                .pattern(" P ")
                .pattern("A  ")
                .define('P', ItemTags.LEAVES)
                .define('S', Items.AMETHYST_SHARD)
                .define('A', Items.STICK)
                .unlockedBy("has_amethyst_shard",
                        inventoryTrigger(ItemPredicate.Builder.item().of(Items.AMETHYST_SHARD).build()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.CANDLE_SKULL.get())
                .pattern("P")
                .pattern("S")
                .define('P', Items.CANDLE)
                .define('S', Items.SKELETON_SKULL)
                .unlockedBy("has_skeleton_skull",
                        inventoryTrigger(ItemPredicate.Builder.item().of(Items.SKELETON_SKULL).build()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.WITHER_CANDLE_SKULL.get())
                .pattern("P")
                .pattern("S")
                .define('P', Items.CANDLE)
                .define('S', Items.WITHER_SKELETON_SKULL)
                .unlockedBy("has_skeleton_skull",
                        inventoryTrigger(ItemPredicate.Builder.item().of(Items.WITHER_SKELETON_SKULL).build()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.SILK_IDOL.get())
                .pattern(" S ")
                .pattern("SPS")
                .pattern(" S ")
                .define('S', ModItems.SILK_FIBER.get())
                .define('P', ModTags.Items.CRUSHED_HERBS)
                .unlockedBy("has_silk_fiber",
                        inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.SILK_FIBER.get()).build()))
                .save(pWriter);

        // Recipes for Vanilla Items & Blocks
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.LEATHER)
                .pattern(" S ")
                .pattern("SPS")
                .pattern(" S ")
                .define('P', Items.ROTTEN_FLESH)
                .define('S', ModTags.Items.SALT)
                .unlockedBy("has_salt",
                        inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.SALT.get()).build()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath("hexalia", "leather_from_salt"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Blocks.COBWEB)
                .pattern(" S ")
                .pattern("SPS")
                .pattern(" S ")
                .define('P', ModItems.SILK_FIBER.get())
                .define('S', Items.STRING)
                .unlockedBy("has_silk_fiber",
                        inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.SILK_FIBER.get()).build()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath("hexalia", "cobweb_from_fiber"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.YELLOW_DYE)
                .requires(ModBlocks.HENBANE.get())
                .unlockedBy("has_henbane",
                        inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.HENBANE.get()).build()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath("hexalia", "yellow_dye_from_henbane"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.PURPLE_DYE)
                .requires(ModBlocks.LAVENDER.get())
                .unlockedBy("has_lavender",
                        inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.LAVENDER.get()).build()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath("hexalia", "purple_dye_from_begonia"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.PINK_DYE)
                .requires(ModBlocks.BEGONIA.get())
                .unlockedBy("has_begonia",
                        inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.BEGONIA.get()).build()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath("hexalia", "pink_dye_from_begonia"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.BLACK_DYE)
                .requires(ModBlocks.NIGHTSHADE_BUSH.get())
                .unlockedBy("has_nightshade_bush",
                        inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.NIGHTSHADE_BUSH.get()).build()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath("hexalia", "black_dye_from_nightshade"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.ORANGE_DYE)
                .requires(ModBlocks.DAHLIA.get())
                .unlockedBy("has_dahlia",
                        inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.DAHLIA.get()).build()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath("hexalia", "orange_dye_from_dahlia"));

        // Reversible Compacting Recipes for Blocks
        nineBlockStorageRecipes(pWriter, RecipeCategory.BUILDING_BLOCKS, ModItems.SALT.get(),
                RecipeCategory.BUILDING_BLOCKS, ModBlocks.SALT_BLOCK.get(),
                "hexalia:salt", "salt","hexalia:salt_block", "salt");

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.MOON_CRYSTAL_BLOCK.get())
                .pattern("PP")
                .pattern("PP")
                .define('P', ModItems.MOON_CRYSTAL.get())
                .unlockedBy("has_moon_crystal",
                        inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.MOON_CRYSTAL.get()).build()))
                .save(pWriter);

        // Armor Recipes
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.GHOSTVEIL.get())
                .pattern("A A")
                .pattern("PAP")
                .pattern("SSS")
                .define('P', ModItems.SILK_FIBER.get())
                .define('S', ModBlocks.GHOST_FERN.get())
                .define('A', Items.LEATHER)
                .unlockedBy("has_ghost_fern",
                        inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.GHOST_FERN.get()).build()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.EARPLUGS.get())
                .pattern("P P")
                .define('P', Items.LEATHER)
                .unlockedBy("has_leather",
                        inventoryTrigger(ItemPredicate.Builder.item().of(Items.LEATHER).build()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.BOGGED_BOOTS.get())
                .pattern("PSP")
                .pattern("A A")
                .define('S', ModItems.SILK_FIBER.get())
                .define('P', ModBlocks.WITCHWEED.get())
                .define('A', Items.DRIED_KELP)
                .unlockedBy("has_ghost_fern",
                        inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.WITCHWEED.get()).build()))
                .save(pWriter);

        // Shapeless Recipes for Seeds
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.MANDRAKE_SEEDS.get())
                .requires(ModItems.MANDRAKE.get())
                .unlockedBy("has_mandrake",
                        inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.MANDRAKE.get()).build()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SUNFIRE_TOMATO_SEEDS.get())
                .requires(ModItems.SUNFIRE_TOMATO.get())
                .unlockedBy("has_sunfire_tomato",
                        inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.SUNFIRE_TOMATO.get()).build()))
                .save(pWriter);

        // Shapeless Recipes for Items & Blocks
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.CHILLBERRY_PIE.get())
                .requires(ModItems.CHILLBERRIES.get())
                .requires(Items.EGG)
                .requires(Items.SUGAR)
                .requires(Items.WHEAT)
                .unlockedBy("has_chillberries",
                        inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.CHILLBERRIES.get()).build()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.SPICY_SANDWICH.get())
                .requires(ModItems.SUNFIRE_TOMATO.get())
                .requires(ModTags.Items.FOODS_BREADS)
                .requires(ModTags.Items.FOODS_COOKED_MEATS)
                .unlockedBy("has_sunfire_tomato",
                        inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.SUNFIRE_TOMATO.get()).build()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.MANDRAKE_STEW.get())
                .requires(ModItems.MANDRAKE.get())
                .requires(Items.BOWL)
                .requires(ModTags.Items.FOODS_VEGETABLES)
                .requires(ModTags.Items.FOODS_VEGETABLES)
                .unlockedBy("has_mandrake",
                        inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.MANDRAKE.get()).build()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.PURIFYING_SALTS.get())
                .requires(ModTags.Items.SALT)
                .requires(ModTags.Items.CRUSHED_HERBS)
                .requires(ModTags.Items.CRUSHED_HERBS)
                .requires(Items.LEATHER)
                .unlockedBy("has_salt",
                        inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.SALT.get()).build()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SILK_FIBER.get(), 2)
                .requires(ModItems.SILKWORM.get())
                .requires(ItemTags.LEAVES)
                .unlockedBy("has_silkworm",
                        inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.SILKWORM.get()).build()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.MOON_BERRY_COOKIE.get(), 4)
                .requires(ModItems.MOON_BERRIES.get())
                .requires(Items.WHEAT)
                .requires(Items.WHEAT)
                .requires(Items.SUGAR)
                .unlockedBy("has_moon_berries",
                        inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.MOON_BERRIES.get()).build()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ModItems.RAIN_IDOL.get(), 1)
                .requires(ModItems.SILK_IDOL.get())
                .requires(Items.STRING)
                .requires(ModItems.MOON_CRYSTAL.get())
                .requires(ModItems.WATER_NODE.get())
                .unlockedBy("has_silk_idol",
                        inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.SILK_IDOL.get()).build()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ModItems.CLEAR_IDOL.get(), 1)
                .requires(ModItems.SILK_IDOL.get())
                .requires(Items.STRING)
                .requires(ModItems.MOON_CRYSTAL.get())
                .requires(ModItems.FIRE_NODE.get())
                .unlockedBy("has_silk_idol",
                        inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.SILK_IDOL.get()).build()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ModItems.STORM_IDOL.get(), 1)
                .requires(ModItems.SILK_IDOL.get())
                .requires(Items.STRING)
                .requires(ModItems.AIR_NODE.get())
                .requires(ModItems.WATER_NODE.get())
                .requires(ModItems.FIRE_NODE.get())
                .unlockedBy("has_silk_idol",
                        inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.SILK_IDOL.get()).build()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.MOON_CRYSTAL.get(), 4)
                .requires(ModBlocks.MOON_CRYSTAL_BLOCK.get())
                .unlockedBy("has_moon_crystal_block",
                        inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.MOON_CRYSTAL_BLOCK.get()).build()))
                .save(pWriter);

        // Shapeless Recipes for Mortar & Pestle and Refined Resources
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.MORTAR_AND_PESTLE.get())
                .requires(Items.BOWL)
                .requires(Items.STONE)
                .unlockedBy("has_bowl",
                        inventoryTrigger(ItemPredicate.Builder.item().of(Items.BOWL).build()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SPIRIT_POWDER.get())
                .requires(ModBlocks.SPIRIT_BLOOM.get())
                .requires(ModItems.MORTAR_AND_PESTLE.get())
                .unlockedBy("has_mortar_and_pestle",
                        inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.MORTAR_AND_PESTLE.get()).build()))
                .save(pWriter);

        /*ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.VERDANT_GRIMOIRE.get())
                .requires(Items.BOOK)
                .requires(ModTags.Items.HERBS)
                .unlockedBy("has_book",
                        inventoryTrigger(ItemPredicate.Builder.item().of(Items.BOOK).build()))
                .save(pWriter);*/

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SIREN_PASTE.get())
                .requires(ModItems.SIREN_KELP.get())
                .requires(ModItems.MORTAR_AND_PESTLE.get())
                .unlockedBy("has_mortar_and_pestle",
                        inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.MORTAR_AND_PESTLE.get()).build()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DREAM_PASTE.get())
                .requires(ModBlocks.DREAMSHROOM.get())
                .requires(ModItems.MORTAR_AND_PESTLE.get())
                .unlockedBy("has_mortar_and_pestle",
                        inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.MORTAR_AND_PESTLE.get()).build()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GHOST_POWDER.get())
                .requires(ModBlocks.GHOST_FERN.get())
                .requires(ModItems.MORTAR_AND_PESTLE.get())
                .unlockedBy("has_mortar_and_pestle",
                        inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.MORTAR_AND_PESTLE.get()).build()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.STRING, 3)
                .requires(ModItems.SILK_FIBER.get())
                .requires(ModItems.MORTAR_AND_PESTLE.get())
                .unlockedBy("has_mortar_and_pestle",
                        inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.MORTAR_AND_PESTLE.get()).build()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath("hexalia", "string_from_mortar_and_pestle"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SALT.get())
                .requires(ModItems.SALTSPROUT.get())
                .requires(ModItems.MORTAR_AND_PESTLE.get())
                .unlockedBy("has_mortar_and_pestle",
                        inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.MORTAR_AND_PESTLE.get()).build()))
                .save(pWriter, ResourceLocation.fromNamespaceAndPath("hexalia", "salt_from_mortar_and_pestle"));

        // Recipes for Wood-related Blocks
        planksFromLog(pWriter, ModBlocks.COTTONWOOD_PLANKS.get(), ModTags.Items.COTTONWOOD_LOGS, 4);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.COTTONWOOD_BUTTON.get())
                .requires(ModBlocks.COTTONWOOD_PLANKS.get())
                .unlockedBy("has_cottonwood_planks",
                        inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.COTTONWOOD_PLANKS.get()).build()))
                .save(pWriter);

        trapdoorBuilder(ModBlocks.COTTONWOOD_TRAPDOOR.get(), Ingredient.of(ModBlocks.COTTONWOOD_PLANKS.get()))
                .unlockedBy("has_planks", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.COTTONWOOD_PLANKS.get()).build()))
                .save(pWriter);
        doorBuilder(ModBlocks.COTTONWOOD_DOOR.get(), Ingredient.of(ModBlocks.COTTONWOOD_PLANKS.get()))
                .unlockedBy("has_planks", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.COTTONWOOD_PLANKS.get()).build()))
                .save(pWriter);
        pressurePlateBuilder(RecipeCategory.REDSTONE, ModBlocks.COTTONWOOD_PRESSURE_PLATE.get(), Ingredient.of(ModBlocks.COTTONWOOD_PLANKS.get()))
                .unlockedBy("has_planks", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.COTTONWOOD_PLANKS.get()).build()))
                .save(pWriter);
        stairBuilder(ModBlocks.COTTONWOOD_STAIRS.get(), Ingredient.of(ModBlocks.COTTONWOOD_PLANKS.get()))
                .unlockedBy("has_planks", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.COTTONWOOD_PLANKS.get()).build()))
                .save(pWriter);
        slabBuilder(RecipeCategory.DECORATIONS, ModBlocks.COTTONWOOD_SLAB.get(), Ingredient.of(ModBlocks.COTTONWOOD_PLANKS.get()))
                .unlockedBy("has_planks", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.COTTONWOOD_PLANKS.get()).build()))
                .save(pWriter);
        fenceBuilder(ModBlocks.COTTONWOOD_FENCE.get(), Ingredient.of(ModBlocks.COTTONWOOD_PLANKS.get()))
                .unlockedBy("has_planks", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.COTTONWOOD_PLANKS.get()).build()))
                .save(pWriter);
        fenceGateBuilder(ModBlocks.COTTONWOOD_FENCE_GATE.get(), Ingredient.of(ModBlocks.COTTONWOOD_PLANKS.get()))
                .unlockedBy("has_planks", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.COTTONWOOD_PLANKS.get()).build()))
                .save(pWriter);
        /*signBuilder(ModBlocks.COTTONWOOD_SIGN.get(), Ingredient.of(ModBlocks.COTTONWOOD_PLANKS.get()))
                .unlockedBy("has_planks", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.COTTONWOOD_PLANKS.get()).build()))
                .save(pWriter);*/
        // hangingSign(pWriter, ModItems.COTTONWOOD_HANGING_SIGN.get(), ModBlocks.STRIPPED_COTTONWOOD_LOG.get());

        planksFromLog(pWriter, ModBlocks.WILLOW_PLANKS.get(), ModTags.Items.WILLOW_LOGS, 4);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.WILLOW_BUTTON.get())
                .requires(ModBlocks.WILLOW_PLANKS.get())
                .unlockedBy("has_willow_planks",
                        inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.WILLOW_PLANKS.get()).build()))
                .save(pWriter);

        trapdoorBuilder(ModBlocks.WILLOW_TRAPDOOR.get(), Ingredient.of(ModBlocks.WILLOW_PLANKS.get()))
                .unlockedBy("has_planks", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.WILLOW_PLANKS.get()).build()))
                .save(pWriter);
        doorBuilder(ModBlocks.WILLOW_DOOR.get(), Ingredient.of(ModBlocks.WILLOW_PLANKS.get()))
                .unlockedBy("has_planks", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.WILLOW_PLANKS.get()).build()))
                .save(pWriter);
        pressurePlateBuilder(RecipeCategory.REDSTONE, ModBlocks.WILLOW_PRESSURE_PLATE.get(), Ingredient.of(ModBlocks.WILLOW_PLANKS.get()))
                .unlockedBy("has_planks", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.WILLOW_PLANKS.get()).build()))
                .save(pWriter);
        stairBuilder(ModBlocks.WILLOW_STAIRS.get(), Ingredient.of(ModBlocks.WILLOW_PLANKS.get()))
                .unlockedBy("has_planks", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.WILLOW_PLANKS.get()).build()))
                .save(pWriter);
        slabBuilder(RecipeCategory.DECORATIONS, ModBlocks.WILLOW_SLAB.get(), Ingredient.of(ModBlocks.WILLOW_PLANKS.get()))
                .unlockedBy("has_planks", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.WILLOW_PLANKS.get()).build()))
                .save(pWriter);
        fenceBuilder(ModBlocks.WILLOW_FENCE.get(), Ingredient.of(ModBlocks.WILLOW_PLANKS.get()))
                .unlockedBy("has_planks", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.WILLOW_PLANKS.get()).build()))
                .save(pWriter);
        fenceGateBuilder(ModBlocks.WILLOW_FENCE_GATE.get(), Ingredient.of(ModBlocks.WILLOW_PLANKS.get()))
                .unlockedBy("has_planks", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.WILLOW_PLANKS.get()).build()))
                .save(pWriter);
        /*signBuilder(ModBlocks.WILLOW_SIGN.get(), Ingredient.of(ModBlocks.WILLOW_PLANKS.get()))
                .unlockedBy("has_planks", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.WILLOW_PLANKS.get()).build()))
                .save(pWriter);*/
        // hangingSign(pWriter, ModItems.WILLOW_HANGING_SIGN.get(), ModBlocks.STRIPPED_WILLOW_LOG.get());
    }
}
