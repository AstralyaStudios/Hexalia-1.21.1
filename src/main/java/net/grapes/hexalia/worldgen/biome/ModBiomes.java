package net.grapes.hexalia.worldgen.biome;

import net.grapes.hexalia.HexaliaMod;
import net.grapes.hexalia.worldgen.biome.region.OverworldRegion;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import terrablender.api.Regions;

public class ModBiomes {
    public static final ResourceKey<Biome> ENCHANTED_BAYOU = registerBiomeKey("enchanted_bayou");

    public static void registerBiomes() {
        Regions.register(new OverworldRegion(ResourceLocation.fromNamespaceAndPath(HexaliaMod.MODID, "hexalia_overworld"),
                40));
    }

    public static void bootstrap(BootstrapContext<Biome> context) {
        var carver = context.lookup(Registries.CONFIGURED_CARVER);
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);

        register(context, ENCHANTED_BAYOU, ModOverworldBiomes.enchantedBayou(placedFeatures, carver));
    }


    private static void register(BootstrapContext<Biome> context, ResourceKey<Biome> key, Biome biome) {
        context.register(key, biome);
    }

    private static ResourceKey<Biome> registerBiomeKey(String name) {
        return ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(HexaliaMod.MODID, name));
    }
}
