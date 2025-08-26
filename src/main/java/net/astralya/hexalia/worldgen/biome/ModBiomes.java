package net.astralya.hexalia.worldgen.biome;

import net.astralya.hexalia.HexaliaMod;
import net.astralya.hexalia.worldgen.biome.surface.OverworldRegion;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.*;
import terrablender.api.Regions;

public class ModBiomes {

    public static final RegistryKey<Biome> ENCHANTED_BAYOU = registerBiomeKey("enchanted_bayou");

    public static void registerBiomes() {
        Regions.register(new OverworldRegion(Identifier.of(HexaliaMod.MODID, "hexalia_overworld"), 20));
    }

    public static void bootstrap(Registerable<Biome> context) {
        var carver = context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER);
        var placedFeatures = context.getRegistryLookup(RegistryKeys.PLACED_FEATURE);
        register(context, ENCHANTED_BAYOU, ModOverworldBiomes.enchantedBayou(placedFeatures, carver));
    }

    private static void register(Registerable<Biome> context, RegistryKey<Biome> key, Biome biome) {
        context.register(key, biome);
    }

    private static RegistryKey<Biome> registerBiomeKey(String name) {
        return RegistryKey.of(RegistryKeys.BIOME, Identifier.of(HexaliaMod.MODID, name));
    }
}
