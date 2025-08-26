package net.astralya.hexalia.worldgen.tree;

import net.astralya.hexalia.HexaliaMod;
import net.astralya.hexalia.worldgen.ModConfiguredFeatures;
import net.minecraft.block.SaplingGenerator;

import java.util.Optional;

public class ModSaplingGenerators {

    public static final SaplingGenerator WILLOW = new SaplingGenerator(HexaliaMod.MODID + ":willow",
            Optional.empty(), Optional.of(ModConfiguredFeatures.WILLOW_KEY), Optional.empty());

    public static final SaplingGenerator COTTONWOOD = new SaplingGenerator(HexaliaMod.MODID + ":cottonwood",
            Optional.empty(), Optional.of(ModConfiguredFeatures.COTTONWOOD_KEY), Optional.empty());
}