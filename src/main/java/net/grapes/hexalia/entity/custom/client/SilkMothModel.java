package net.grapes.hexalia.entity.custom.client;

import net.grapes.hexalia.HexaliaMod;
import net.grapes.hexalia.entity.custom.SilkMothEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SilkMothModel extends GeoModel<SilkMothEntity> {

    @Override
    public ResourceLocation getModelResource(SilkMothEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(HexaliaMod.MODID, "geo/silk_moth.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SilkMothEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(HexaliaMod.MODID, "textures/armor/silk_moth_default.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SilkMothEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(HexaliaMod.MODID, "animations/silk_moth.animation.json");
    }
}
