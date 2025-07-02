package net.grapes.hexalia.entity.layers;

import net.grapes.hexalia.HexaliaMod;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModModelLayers {
    public static final ModelLayerLocation COTTONWOOD_BOAT_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(HexaliaMod.MODID, "boat/cottonwood"), "main");
    public static final ModelLayerLocation COTTONWOOD_CHEST_BOAT_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(HexaliaMod.MODID, "chest_boat/cottonwood"), "main");

    public static final ModelLayerLocation WILLOW_BOAT_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(HexaliaMod.MODID, "boat/willow"), "main");
    public static final ModelLayerLocation WILLOW_CHEST_BOAT_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(HexaliaMod.MODID, "chest_boat/willow"), "main");
}
