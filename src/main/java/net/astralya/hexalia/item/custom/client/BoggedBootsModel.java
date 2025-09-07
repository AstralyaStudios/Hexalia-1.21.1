package net.astralya.hexalia.item.custom.client;

import net.astralya.hexalia.HexaliaMod;
import net.astralya.hexalia.item.custom.BoggedBootsItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BoggedBootsModel extends GeoModel<BoggedBootsItem> {

    @Override
    public ResourceLocation getModelResource(BoggedBootsItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(HexaliaMod.MODID, "geo/bogged_boots.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BoggedBootsItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(HexaliaMod.MODID, "textures/armor/bogged_boots.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BoggedBootsItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(HexaliaMod.MODID, "animations/bogged_boots.animation.json");
    }
}
