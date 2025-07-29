package net.grapes.hexalia.worldgen.biome.region;

import com.mojang.datafixers.util.Pair;
import net.grapes.hexalia.worldgen.biome.ModBiomes;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.ParameterUtils;
import terrablender.api.Region;
import terrablender.api.RegionType;
import terrablender.api.VanillaParameterOverlayBuilder;

import java.util.function.Consumer;

public class OverworldRegion extends Region {
    public OverworldRegion(ResourceLocation name, int weight)
    {
        super(name, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper)
    {
        VanillaParameterOverlayBuilder builder = new VanillaParameterOverlayBuilder();

        new ParameterUtils.ParameterPointListBuilder()
                .temperature(ParameterUtils.Temperature.WARM)
                .humidity(ParameterUtils.Humidity.HUMID)
                .continentalness(ParameterUtils.Continentalness.span(
                        ParameterUtils.Continentalness.COAST,
                        ParameterUtils.Continentalness.MID_INLAND))
                .erosion(ParameterUtils.Erosion.EROSION_5, ParameterUtils.Erosion.EROSION_6)
                .depth(ParameterUtils.Depth.SURFACE)
                .weirdness(ParameterUtils.Weirdness.MID_SLICE_NORMAL_DESCENDING)
                .build()
                .forEach(point -> builder.add(point, ModBiomes.ENCHANTED_BAYOU));

        builder.build().forEach(mapper);
    }
}
