package net.grapes.hexalia.item;

import net.grapes.hexalia.HexaliaMod;
import net.grapes.hexalia.block.ModBlocks;
import net.grapes.hexalia.block.custom.wood.ModBoatItem;
import net.grapes.hexalia.effect.ModMobEffects;
import net.grapes.hexalia.entity.ModEntities;
import net.grapes.hexalia.entity.boat.ModBoatEntity;
import net.grapes.hexalia.item.custom.*;
import net.grapes.hexalia.util.ModArmorMaterials;
import net.grapes.hexalia.util.ModToolTiers;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(HexaliaMod.MODID);

    // Resources
    public static final DeferredItem<Item> SALT = ITEMS.registerSimpleItem("salt");
    public static final DeferredItem<Item> TREE_RESIN = ITEMS.registerSimpleItem("tree_resin");
    public static final DeferredItem<Item> SILK_FIBER = ITEMS.registerSimpleItem("silk_fiber");
    public static final DeferredItem<Item> SILKWORM = ITEMS.registerSimpleItem("silkworm");
    public static final DeferredItem<Item> CELESTIAL_CRYSTAL = ITEMS.registerSimpleItem("celestial_crystal");
    public static final DeferredItem<Item> FIRE_NODE = ITEMS.registerSimpleItem("fire_node");
    public static final DeferredItem<Item> WATER_NODE = ITEMS.registerSimpleItem("water_node");
    public static final DeferredItem<Item> AIR_NODE = ITEMS.registerSimpleItem("air_node");
    public static final DeferredItem<Item> EARTH_NODE = ITEMS.registerSimpleItem("earth_node");
    public static final DeferredItem<Item> ANCIENT_SEED = ITEMS.registerItem("ancient_seed",
            Item::new, new Item.Properties().rarity(Rarity.RARE));

    // Crops & Seeds
    public static final DeferredItem<Item> SUNFIRE_TOMATO = ITEMS.registerItem("sunfire_tomato",
            Item::new, new Item.Properties().food(ModFoodProperties.SUNFIRE_TOMATO));
    public static final DeferredItem<Item> SUNFIRE_TOMATO_SEEDS = ITEMS.register("sunfire_tomato_seeds",
            () -> new BlockItem(ModBlocks.SUNFIRE_TOMATO_CROP.get(), new Item.Properties()));
    public static final DeferredItem<Item> MANDRAKE = ITEMS.registerItem("mandrake",
            MandrakeItem::new, new Item.Properties());
    public static final DeferredItem<Item> MANDRAKE_SEEDS = ITEMS.register("mandrake_seeds",
            () -> new BlockItem(ModBlocks.MANDRAKE_CROP.get(), new Item.Properties()));
    public static final DeferredItem<Item> RABBAGE = ITEMS.registerItem("rabbage",
            RabbageItem::new, new Item.Properties());
    public static final DeferredItem<Item> RABBAGE_SEEDS = ITEMS.register("rabbage_seeds",
            () -> new BlockItem(ModBlocks.RABBAGE_CROP.get(), new Item.Properties()));
    public static final DeferredItem<Item> CHILLBERRIES = ITEMS.register("chillberries",
            () -> new BlockItem(ModBlocks.CHILLBERRY_BUSH.get(), new Item.Properties().food(ModFoodProperties.CHILLBERRIES)));
    public static final DeferredItem<Item> SALTSPROUT = ITEMS.register("saltsprout",
            () -> new BlockItem(ModBlocks.SALTSPROUT.get(), new Item.Properties().food(ModFoodProperties.SALTSPROUT)));
    public static final DeferredItem<Item> GALEBERRIES = ITEMS.register("galeberries",
            () -> new ItemNameBlockItem(ModBlocks.GALEBERRIES_VINE.get(), new Item.Properties().food(ModFoodProperties.GALEBERRIES)));

    // Plants
    public static final DeferredItem<Item> SIREN_KELP = ITEMS.register("siren_kelp",
            () -> new BlockItem(ModBlocks.SIREN_KELP.get(), new Item.Properties().food(ModFoodProperties.SIREN_KELP)));
    public static final DeferredItem<BlockItem> LOTUS_FLOWER = ITEMS.register("lotus_flower",
            () -> new PlaceOnWaterBlockItem(ModBlocks.LOTUS_FLOWER.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> DUCKWEED = ITEMS.register("duckweed",
            () -> new PlaceOnWaterBlockItem(ModBlocks.DUCKWEED.get(), new Item.Properties()));

    // Refined Resources
    public static final DeferredItem<Item> SPIRIT_POWDER = ITEMS.registerSimpleItem("spirit_powder");
    public static final DeferredItem<Item> SIREN_PASTE = ITEMS.registerSimpleItem("siren_paste");
    public static final DeferredItem<Item> DREAM_PASTE = ITEMS.registerSimpleItem("dream_paste");
    public static final DeferredItem<Item> GHOST_POWDER = ITEMS.registerSimpleItem("ghost_powder");
    
    // Food Items
    public static final DeferredItem<Item> SPICY_SANDWICH = ITEMS.registerItem("spicy_sandwich",
            Item::new, new Item.Properties().food(ModFoodProperties.SPICY_SANDWICH));
    public static final DeferredItem<Item> CHILLBERRY_PIE = ITEMS.registerItem("chillberry_pie",
            Item::new, new Item.Properties().food(ModFoodProperties.CHILLBERRY_PIE));
    public static final DeferredItem<Item> MANDRAKE_STEW = ITEMS.registerItem("mandrake_stew",
            Item::new, new Item.Properties().food(ModFoodProperties.MANDRAKE_STEW));
    public static final DeferredItem<Item> GALEBERRIES_COOKIE = ITEMS.registerItem("galeberries_cookie",
            Item::new, new Item.Properties().food(ModFoodProperties.GALEBERRIES_COOKIE));

    // Tools
    public static final DeferredItem<Item> HEX_FOCUS = ITEMS.registerItem("hex_focus",
            HexFocusItem::new, new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> MORTAR_AND_PESTLE = ITEMS.registerItem("mortar_and_pestle",
            MortarAndPestleItem::new, new Item.Properties().durability(64));
    public static final DeferredItem<Item> STONE_DAGGER = ITEMS.registerItem("stone_dagger",
            StoneDaggerItem::new, new Item.Properties().durability(16));
    public static final DeferredItem<Item> PURIFYING_SALTS = ITEMS.registerItem("purifying_salts",
            PurifyingSaltsItem::new, new Item.Properties().durability(6));
    public static final DeferredItem<Item> SAGE_PENDANT = ITEMS.registerItem("sage_pendant",
            Item::new, new Item.Properties().rarity(Rarity.UNCOMMON).durability(64));
    public static final DeferredItem<Item> SILK_IDOL = ITEMS.registerSimpleItem("silk_idol");
    public static final DeferredItem<Item> CLEAR_IDOL = ITEMS.registerItem("clear_idol",
            WeatherIdol::new, new Item.Properties());
    public static final DeferredItem<Item> RAIN_IDOL = ITEMS.registerItem("rain_idol",
            WeatherIdol::new, new Item.Properties());
    public static final DeferredItem<Item> STORM_IDOL = ITEMS.registerItem("storm_idol",
            WeatherIdol::new, new Item.Properties());

    // Block Items
    public static final DeferredItem<Item> CANDLE_SKULL = ITEMS.register("candle_skull",
            () -> new BlockItem(ModBlocks.CANDLE_SKULL.get(), new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> WITHER_CANDLE_SKULL = ITEMS.register("wither_candle_skull",
            () -> new BlockItem(ModBlocks.WITHER_CANDLE_SKULL.get(), new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> SALT_LAMP = ITEMS.register("salt_lamp",
            () -> new BlockItem(ModBlocks.SALT_LAMP.get(), new Item.Properties()));
    public static final DeferredItem<Item> SMALL_CAULDRON = ITEMS.register("small_cauldron",
            () -> new BlockItem(ModBlocks.SMALL_CAULDRON.get(), new Item.Properties()));

    // Weapons & Armor
    public static final DeferredItem<Item> KELPWEAVE_BLADE = ITEMS.register("kelpweave_blade",
            () -> new KelpweaveBlade(ModToolTiers.ANCIENT,
                    new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.ANCIENT, 3, -2f))));
    public static final DeferredItem<Item> GHOSTVEIL = ITEMS.register("ghostveil",
            () -> new GhostVeilItem(ModArmorMaterials.BOGGED, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(96)));
    public static final DeferredItem<Item> EARPLUGS = ITEMS.register("earplugs",
            () -> new ArmorItem(ArmorMaterials.LEATHER, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final DeferredItem<Item> BOGGED_BOOTS = ITEMS.register("bogged_boots",
            () -> new BoggedBootsItem(ModArmorMaterials.BOGGED, ArmorItem.Type.BOOTS, new Item.Properties().durability(96)));

    // Brews
    public static final DeferredItem<Item> RUSTIC_BOTTLE = ITEMS.registerSimpleItem("rustic_bottle");
    public static final DeferredItem<Item> BREW_OF_SPIKESKIN = ITEMS.register("brew_of_spikeskin",
            () -> new BrewItem(new Item.Properties(), () -> ModMobEffects.SPIKESKIN, 2400, 0,
                    Component.translatable("tooltip.hexalia.spikeskin_brew").withStyle(ChatFormatting.BLUE)));
    public static final DeferredItem<Item> BREW_OF_BLOODLUST = ITEMS.register("brew_of_bloodlust",
            () -> new BrewItem(new Item.Properties(), () -> ModMobEffects.BLOODLUST, 2400, 0,
                    Component.translatable("tooltip.hexalia.bloodlust_brew").withStyle(ChatFormatting.BLUE)));
    public static final DeferredItem<Item> BREW_OF_SLIMEWALKER = ITEMS.register("brew_of_slimewalker",
            () -> new BrewItem(new Item.Properties(), () -> ModMobEffects.SLIMEWALKER, 2400, 0,
                    Component.translatable("tooltip.hexalia.slimewalker_brew").withStyle(ChatFormatting.BLUE)));
    public static final DeferredItem<Item> BREW_OF_SIPHON = ITEMS.register("brew_of_siphon",
            () -> new BrewItem(new Item.Properties(), () -> ModMobEffects.SIPHON, 2400, 0,
                    Component.translatable("tooltip.hexalia.siphon_brew").withStyle(ChatFormatting.BLUE)));
    public static final DeferredItem<Item> BREW_OF_DAYBLOOM = ITEMS.register("brew_of_daybloom",
            () -> new BrewItem(new Item.Properties(), () -> ModMobEffects.DAYBLOOM, 2400, 0,
                    Component.translatable("tooltip.hexalia.daybloom").withStyle(ChatFormatting.BLUE)));
    public static final DeferredItem<Item> BREW_OF_ARACHNID_GRACE = ITEMS.register("brew_of_arachnid_grace",
            () -> new BrewItem(new Item.Properties(), () -> ModMobEffects.ARACHNID_GRACE, 2400, 0,
                    Component.translatable("tooltip.hexalia.arachnid_grace").withStyle(ChatFormatting.BLUE)));
    public static final DeferredItem<Item> BREW_OF_HOMESTEAD = ITEMS.registerItem("brew_of_homestead",
            HomesteadBrewItem::new, new Item.Properties());

    // Entity Related Items
    public static final DeferredItem<Item> BOTTLED_MOTH = ITEMS.register("bottled_moth",
            () -> new BottleMothItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> SILK_MOTH_SPAWN_EGG = ITEMS.register("silk_moth_spawn_egg",
            () -> new SpawnEggItem(ModEntities.SILK_MOTH_ENTITY.get(), 0xAE8f7A, 0x846552, new Item.Properties()));

    // Wood-Related Items
    public static final DeferredItem<Item> WILLOW_BOAT = ITEMS.register("willow_boat",
            () -> new ModBoatItem(false, ModBoatEntity.Type.WILLOW, new Item.Properties()));
    public static final DeferredItem<Item> WILLOW_CHEST_BOAT = ITEMS.register("willow_chest_boat",
            () -> new ModBoatItem(true, ModBoatEntity.Type.WILLOW, new Item.Properties()));
    public static final DeferredItem<Item> COTTONWOOD_BOAT = ITEMS.register("cottonwood_boat",
            () -> new ModBoatItem(false, ModBoatEntity.Type.COTTONWOOD, new Item.Properties()));
    public static final DeferredItem<Item> COTTONWOOD_CHEST_BOAT = ITEMS.register("cottonwood_chest_boat",
            () -> new ModBoatItem(true, ModBoatEntity.Type.COTTONWOOD, new Item.Properties()));

    public static final DeferredItem<Item> WILLOW_SIGN = ITEMS.register("willow_sign",
            () -> new SignItem(new Item.Properties().stacksTo(16), ModBlocks.WILLOW_SIGN.get(), ModBlocks.WILLOW_WALL_SIGN.get()));
    public static final DeferredItem<Item> COTTONWOOD_SIGN = ITEMS.register("cottonwood_sign",
            () -> new SignItem(new Item.Properties().stacksTo(16), ModBlocks.COTTONWOOD_SIGN.get(), ModBlocks.COTTONWOOD_WALL_SIGN.get()));

    public static final DeferredItem<Item> WILLOW_HANGING_SIGN = ITEMS.register("willow_hanging_sign",
            () -> new SignItem(new Item.Properties().stacksTo(16), ModBlocks.WILLOW_HANGING_SIGN.get(), ModBlocks.WILLOW_HANGING_WALL_SIGN.get()));
    public static final DeferredItem<Item> COTTONWOOD_HANGING_SIGN = ITEMS.register("cottonwood_hanging_sign",
            () -> new SignItem(new Item.Properties().stacksTo(16), ModBlocks.COTTONWOOD_HANGING_SIGN.get(), ModBlocks.COTTONWOOD_HANGING_WALL_SIGN.get()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
