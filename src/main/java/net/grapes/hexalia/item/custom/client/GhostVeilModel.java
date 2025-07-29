package net.grapes.hexalia.item.custom.client;

import net.grapes.hexalia.HexaliaMod;
import net.grapes.hexalia.item.custom.GhostVeilItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class GhostVeilModel extends GeoModel<GhostVeilItem> {

    @Override
    public ResourceLocation getModelResource(GhostVeilItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(HexaliaMod.MODID, "geo/ghostveil.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GhostVeilItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(HexaliaMod.MODID, "textures/armor/ghostveil.png");
    }

    @Override
    public ResourceLocation getAnimationResource(GhostVeilItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(HexaliaMod.MODID, "animations/ghostveil.animation.json");
    }
}
