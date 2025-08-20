package net.astralya.hexalia.item;

import net.astralya.hexalia.HexaliaMod;
import net.astralya.hexalia.effect.ModEffects;
import net.astralya.hexalia.item.custom.*;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {

    // Resources
    public static final Item SALT = registerItem("salt",
            new Item(new Item.Settings()));
    public static final Item TREE_RESIN = registerItem("tree_resin",
            new Item(new Item.Settings()));
    public static final Item ANCIENT_SEED = registerItem("ancient_seed",
            new Item(new Item.Settings().rarity(Rarity.RARE)));
    public static final Item SILK_FIBER = registerItem("silk_fiber",
            new Item(new Item.Settings()));
    public static final Item SILKWORM = registerItem("silkworm",
            new Item(new Item.Settings()));
    public static final Item CELESTIAL_CRYSTAL = registerItem("celestial_crystal",
            new Item(new Item.Settings()));
    public static final Item FIRE_NODE = registerItem("fire_node",
            new Item(new Item.Settings()));
    public static final Item WATER_NODE = registerItem("water_node",
            new Item(new Item.Settings()));
    public static final Item AIR_NODE = registerItem("air_node",
            new Item(new Item.Settings()));
    public static final Item EARTH_NODE = registerItem("earth_node",
            new Item(new Item.Settings()));

    // Crops, Plants & Seeds
    /*public static final Item SIREN_KELP = registerItem("siren_kelp",
            new BlockItem(ModBlocks.SIREN_KELP, new Item.Settings().food(ModFoodComponents.SIREN_KELP)));*/
    public static final Item MANDRAKE = registerItem("mandrake",
            new MandrakeItem(new Item.Settings()));
    /*public static final Item MANDRAKE_SEEDS = registerItem("mandrake_seeds",
            new AliasedBlockItem(ModBlocks.MANDRAKE_CROP, new Item.Settings()));
    public static final Item CHILLBERRIES = registerItem("chillberries",
            new BlockItem(ModBlocks.CHILLBERRY_BUSH, new Item.Settings().food(ModFoodComponents.CHILLBERRIES)));*/
    public static final Item SUNFIRE_TOMATO = registerItem("sunfire_tomato",
            new Item(new Item.Settings().food(ModFoodComponents.SUNFIRE_TOMATO)));
    /*public static final Item SUNFIRE_TOMATO_SEEDS = registerItem("sunfire_tomato_seeds",
            new AliasedBlockItem(ModBlocks.SUNFIRE_TOMATO_CROP, new Item.Settings()));
    public static final Item GALEBERRIES = registerItem("galeberries",
            new AliasedBlockItem(ModBlocks.GALEBERRIES_VINE, new Item.Settings().food(ModFoodComponents.GALEBERRIES)));
    public static final Item RABBAGE_SEEDS = registerItem("rabbage_seeds",
            new AliasedBlockItem(ModBlocks.RABBAGE_CROP, new Item.Settings()));
    public static final Item LOTUS_FLOWER = registerItem("lotus_flower",
            new PlaceableOnWaterItem(ModBlocks.LOTUS_FLOWER, new Item.Settings()));
    public static final Item SALTSPROUT = registerItem("saltsprout",
            new BlockItem(ModBlocks.SALTSPROUT, new Item.Settings().food(ModFoodComponents.SALTSPROUT)));
    public static final Item DUCKWEED = registerItem("duckweed",
            new PlaceableOnWaterItem(ModBlocks.DUCKWEED, new Item.Settings()));*/

    // Refined Resources
    public static final Item SIREN_PASTE = registerItem("siren_paste",
            new Item(new Item.Settings()));
    public static final Item SPIRIT_POWDER = registerItem("spirit_powder",
            new Item(new Item.Settings()));
    public static final Item DREAM_PASTE = registerItem("dream_paste",
            new Item(new Item.Settings()));
    public static final Item GHOST_POWDER = registerItem("ghost_powder",
            new Item(new Item.Settings()));
    public static final Item PURIFYING_SALTS = registerItem("purifying_salts",
            new PurifyingSaltsItem(new Item.Settings().maxCount(16)));
    public static final Item SPICY_SANDWICH = registerItem("spicy_sandwich",
            new Item(new Item.Settings().food(ModFoodComponents.SPICY_SANDWICH)));
    public static final Item CHILLBERRY_PIE = registerItem("chillberry_pie",
            new Item(new Item.Settings().food(ModFoodComponents.CHILLBERRY_PIE)));
    public static final Item MANDRAKE_STEW = registerItem("mandrake_stew",
            new Item(new Item.Settings().food(ModFoodComponents.MANDRAKE_STEW).maxCount(1)));
    public static final Item GALEBERRIES_COOKIE = registerItem("galeberries_cookie",
            new Item(new Item.Settings().food(ModFoodComponents.GALEBERRIES_COOKIE)));

    // TODO: Add Custom Effects
    // Brews
    public static final Item RUSTIC_BOTTLE = registerItem("rustic_bottle",
            new Item(new Item.Settings()));
    public static final Item BREW_OF_SPIKESKIN = registerItem("brew_of_spikeskin",
            new BrewItem(new Item.Settings().maxCount(16), () -> ModEffects.SPIKESKIN, 0,
                    Text.translatable("tooltip.hexalia.spikeskin_brew").formatted(Formatting.BLUE)));
    public static final Item BREW_OF_BLOODLUST = registerItem("brew_of_bloodlust",
            new BrewItem(new Item.Settings().maxCount(16), () -> ModEffects.BLOODLUST, 0,
                    Text.translatable("tooltip.hexalia.spikeskin_brew").formatted(Formatting.BLUE)));
    public static final Item BREW_OF_SLIMEWALKER = registerItem("brew_of_slimewalker",
            new BrewItem(new Item.Settings().maxCount(16), () -> ModEffects.SLIMEWALKER, 0,
                    Text.translatable("tooltip.hexalia.spikeskin_brew").formatted(Formatting.BLUE)));
    public static final Item BREW_OF_SIPHON = registerItem("brew_of_siphon",
            new BrewItem(new Item.Settings().maxCount(16), () -> ModEffects.SIPHON, 0,
                    Text.translatable("tooltip.hexalia.spikeskin_brew").formatted(Formatting.BLUE)));
    public static final Item BREW_OF_DAYBLOOM = registerItem("brew_of_daybloom",
            new BrewItem(new Item.Settings().maxCount(16), () -> ModEffects.DAYBLOOM, 0,
                    Text.translatable("tooltip.hexalia.spikeskin_brew").formatted(Formatting.BLUE)));
    public static final Item BREW_OF_HOMESTEAD = registerItem("brew_of_homestead",
            new HomesteadBrewItem(new Item.Settings().maxCount(16)));
    public static final Item BREW_OF_ARACHNID_GRACE = registerItem("brew_of_arachnid_grace",
            new BrewItem(new Item.Settings().maxCount(16), () -> ModEffects.ARACHNID_GRACE, 0,
                    Text.translatable("tooltip.hexalia.spikeskin_brew").formatted(Formatting.BLUE)));

    // Tools & Others
    public static final Item MORTAR_AND_PESTLE = registerItem("mortar_and_pestle",
            new MortarAndPestleItem(new Item.Settings().maxDamage(64)));
    /*public static final Item SMALL_CAULDRON = registerItem("small_cauldron",
            new BlockItem(ModBlocks.SMALL_CAULDRON, new Item.Settings()));
    public static final Item SALT_LAMP = registerItem("salt_lamp",
            new BlockItem(ModBlocks.SALT_LAMP, new Item.Settings()));*/
    public static final Item STONE_DAGGER = registerItem("stone_dagger",
            new StoneDaggerItem(new Item.Settings().maxDamage(16)));
    /*public static final Item RITUAL_TABLE = registerItem("ritual_table",
           new BlockItem(ModBlocks.RITUAL_TABLE, new Item.Settings()));
   public static final Item CANDLE_SKULL = registerItem("candle_skull",
           new BlockItem(ModBlocks.CANDLE_SKULL, new Item.Settings().rarity(Rarity.UNCOMMON)));
   public static final Item WITHER_CANDLE_SKULL = registerItem("wither_candle_skull",
           new BlockItem(ModBlocks.WITHER_CANDLE_SKULL, new Item.Settings().rarity(Rarity.UNCOMMON)));*/
    public static final Item HEX_FOCUS = registerItem("hex_focus",
            new HexFocusItem(new Item.Settings().maxCount(1)));
    public static final Item SAGE_PENDANT = registerItem("sage_pendant",
            new Item(new Item.Settings().maxCount(1).maxDamage(60).rarity(Rarity.UNCOMMON)));
    public static final Item SILK_IDOL = registerItem("silk_idol",
            new Item(new Item.Settings()));
    public static final Item RAIN_IDOL = registerItem("rain_idol",
            new WeatherIdol(new Item.Settings()));
    public static final Item CLEAR_IDOL = registerItem("clear_idol",
            new WeatherIdol(new Item.Settings()));
    public static final Item STORM_IDOL = registerItem("storm_idol",
            new WeatherIdol(new Item.Settings()));

    // Entity
    /*public static final Item BOTTLED_MOTH = registerItem("bottled_moth",
            new BottledMothItem(new Item.Settings().maxCount(1)));
    public static final Item SILK_MOTH_SPAWN_EGG = registerItem("silk_moth_spawn_egg",
            new SpawnEggItem(ModEntities.SILK_MOTH,
                    0xAE8f7A, 0x846552, new Item.Settings()));*/

    // Weapons & Armor
    public static final Item KELPWEAVE_BLADE = registerItem("kelpweave_blade",
            new KelpweaveBladeItem(ModToolMaterials.ANCIENT, new Item.Settings().rarity(Rarity.RARE)
                    .attributeModifiers(SwordItem.createAttributeModifiers(ModToolMaterials.ANCIENT, 3, -2.0f))));
    /*public static final Item RABBAGE = registerItem("rabbage",
            new RabbageItem(new Item.Settings().maxCount(16)));
    public static final Item GHOSTVEIL = registerItem("ghostveil",
            new GhostVeilItem(ModArmorMaterials.GHOST, ArmorItem.Type.CHESTPLATE, new Item.Settings()));
    public static final Item EARPLUGS = registerItem("earplugs",
            new ArmorItem(ModArmorMaterials.EARPLUGS, ArmorItem.Type.HELMET, new Item.Settings()));
    public static final Item BOGGED_BOOTS = registerItem("bogged_boots",
            new BoggedBootsItem(ModArmorMaterials.BOGGED, ArmorItem.Type.BOOTS, new Item.Settings()));*/

    // Wooden-related Items
    /*public static final Item COTTONWOOD_BOAT = TerraformBoatItemHelper.registerBoatItem(ModBoats.COTTONWOOD_BOAT_ID,
            ModBoats.COTTONWOOD_BOAT_KEY, false);
    public static final Item COTTONWOOD_CHEST_BOAT = TerraformBoatItemHelper.registerBoatItem(ModBoats.COTTONWOOD_CHEST_BOAT_ID,
            ModBoats.COTTONWOOD_BOAT_KEY, true);
    public static final Item COTTONWOOD_SIGN = registerItem("cottonwood_sign",
            new SignItem(new Item.Settings().maxCount(16), ModBlocks.COTTONWOOD_SIGN, ModBlocks.COTTONWOOD_WALL_SIGN));
    public static final Item COTTONWOOD_HANGING_SIGN = registerItem("cottonwood_hanging_sign",
            new HangingSignItem(ModBlocks.COTTONWOOD_HANGING_SIGN, ModBlocks.COTTONWOOD_HANGING_WALL_SIGN, new Item.Settings().maxCount(16)));

    public static final Item WILLOW_BOAT = TerraformBoatItemHelper.registerBoatItem(ModBoats.WILLOW_BOAT_ID,
            ModBoats.WILLOW_BOAT_KEY, false);
    public static final Item WILLOW_CHEST_BOAT = TerraformBoatItemHelper.registerBoatItem(ModBoats.WILLOW_CHEST_BOAT_ID,
            ModBoats.WILLOW_BOAT_KEY, true);
    public static final Item WILLOW_SIGN = registerItem("willow_sign",
            new SignItem(new Item.Settings().maxCount(16), ModBlocks.WILLOW_SIGN, ModBlocks.WILLOW_WALL_SIGN));
    public static final Item WILLOW_HANGING_SIGN = registerItem("willow_hanging_sign",
            new HangingSignItem(ModBlocks.WILLOW_HANGING_SIGN, ModBlocks.WILLOW_HANGING_WALL_SIGN, new Item.Settings().maxCount(16)));*/

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(HexaliaMod.MODID, name), item);
    }

    public static void registerModItems() {
        HexaliaMod.LOGGER.info("Registering Items for " + HexaliaMod.MODID);
    }
}
