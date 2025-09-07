package net.astralya.hexalia.util;

import net.astralya.hexalia.HexaliaMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class ModTags {

    public static class Items {

        public static final TagKey<Item> HERBS = createItemTag("herbs");
        public static final TagKey<Item> CRUSHED_HERBS = createItemTag("crushed_herbs");
        public static final TagKey<Item> BREWS = createItemTag("brews");
        public static final TagKey<Item> COTTONWOOD_LOGS = createItemTag("cottonwood_logs");
        public static final TagKey<Item> WILLOW_LOGS = createItemTag("willow_logs");
        public static final TagKey<Item> OFFHAND_EQUIPMENT = createItemTag("offhand_equipment");

        // Common Item Tags

        public static final TagKey<Item> FOODS = createCommonItemTag("foods");

        public static final TagKey<Item> FOODS_BREADS = createCommonItemTag("foods/breads");
        public static final TagKey<Item> CROPS = createCommonItemTag("crops");
        public static final TagKey<Item> CROPS_TOMATO = createCommonItemTag("crops/tomato");
        public static final TagKey<Item> FOODS_BERRIES = createCommonItemTag("foods/berries");
        public static final TagKey<Item> FOODS_COOKED_MEATS = createCommonItemTag("foods/cooked_meats");

        public static final TagKey<Item> FOODS_VEGETABLES = createCommonItemTag("foods/vegetables");
        public static final TagKey<Item> FOODS_VEGETABLES_TOMATO = createCommonItemTag("foods/vegetables/tomatoes");

        public static final TagKey<Item> SALT = createCommonItemTag("salt");
        public static final TagKey<Item> BERRIES = createCommonItemTag("berries");
        public static final TagKey<Item> SEEDS = createCommonItemTag("seeds");
        public static final TagKey<Item> MUSHROOMS = createCommonItemTag("mushrooms");

        public static final TagKey<Item> SALT_BLOCKS = createCommonItemTag("salt_blocks");

        private static TagKey<Item> createItemTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(HexaliaMod.MODID, name));
        }

        private static TagKey<Item> createCommonItemTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", name));
        }
    }

    public static class Blocks {

        public static final TagKey<Block> HEATING_BLOCKS = createBlockTag("heating_block");
        public static final TagKey<Block> ATTRACTS_MOTH = createBlockTag("attracts_moth");
        public static final TagKey<Block> COTTONWOOD_LOGS = createBlockTag("cottonwood_logs");
        public static final TagKey<Block> WILLOW_LOGS = createBlockTag("willow_logs");
        public static final TagKey<Block> COCOON_LOGS = createBlockTag("cocoon_logs");

        // Common Block Tags
        public static final TagKey<Block> SALT_BLOCKS = createCommonBlockTag("salt_blocks");

        private static TagKey<Block> createBlockTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(HexaliaMod.MODID, name));
        }

        private static TagKey<Block> createCommonBlockTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath("c", name));
        }
    }

    public static class Compat {

        // Serene Seasons
        public static final String SERENE_SEASONS = "sereneseasons";
        public static final TagKey<Block> SERENE_SEASONS_AUTUMN_CROPS_BLOCK = externalBlockTag(SERENE_SEASONS, "autumn_crops");
        public static final TagKey<Block> SERENE_SEASONS_SPRING_CROPS_BLOCK = externalBlockTag(SERENE_SEASONS, "spring_crops");
        public static final TagKey<Block> SERENE_SEASONS_SUMMER_CROPS_BLOCK = externalBlockTag(SERENE_SEASONS, "summer_crops");
        public static final TagKey<Block> SERENE_SEASONS_WINTER_CROPS_BLOCK = externalBlockTag(SERENE_SEASONS, "winter_crops");
        public static final TagKey<Block> SERENE_SEASONS_UNBREAKABLE_FERTILE_CROPS = externalBlockTag(SERENE_SEASONS, "unbreakable_infertile_crops");
        public static final TagKey<Item> SERENE_SEASONS_AUTUMN_CROPS = externalItemTag(SERENE_SEASONS, "autumn_crops");
        public static final TagKey<Item> SERENE_SEASONS_SPRING_CROPS = externalItemTag(SERENE_SEASONS, "spring_crops");
        public static final TagKey<Item> SERENE_SEASONS_SUMMER_CROPS = externalItemTag(SERENE_SEASONS, "summer_crops");
        public static final TagKey<Item> SERENE_SEASONS_WINTER_CROPS = externalItemTag(SERENE_SEASONS, "winter_crops");

        private static TagKey<Item> externalItemTag(String modId, String path) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(modId, path));
        }

        private static TagKey<Block> externalBlockTag(String modId, String path) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(modId, path));
        }
    }

    public static class Biomes {
        public static final TagKey<Biome> HAS_MANDRAKES = TagKey.create(Registries.BIOME,
                ResourceLocation.fromNamespaceAndPath(HexaliaMod.MODID, "has_mandrakes"));
        public static final TagKey<Biome> HAS_DREAMSHROOMS = TagKey.create(Registries.BIOME,
                ResourceLocation.fromNamespaceAndPath(HexaliaMod.MODID, "has_dreamshrooms"));
        public static final TagKey<Biome> HAS_SIREN_KELP = TagKey.create(Registries.BIOME,
                ResourceLocation.fromNamespaceAndPath(HexaliaMod.MODID, "has_siren_kelp"));
        public static final TagKey<Biome> HAS_GHOST_FERNS = TagKey.create(Registries.BIOME,
                ResourceLocation.fromNamespaceAndPath(HexaliaMod.MODID, "has_ghost_ferns"));
        public static final TagKey<Biome> HAS_SPIRIT_BLOOMS = TagKey.create(Registries.BIOME,
                ResourceLocation.fromNamespaceAndPath(HexaliaMod.MODID, "has_spirit_blooms"));
        public static final TagKey<Biome> HAS_DECORATIVE_FLOWERS = TagKey.create(Registries.BIOME,
                ResourceLocation.fromNamespaceAndPath(HexaliaMod.MODID, "has_decorative_flowers"));
    }
}
