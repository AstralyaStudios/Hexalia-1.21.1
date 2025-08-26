package net.astralya.hexalia.item.custom.client;

import net.astralya.hexalia.HexaliaMod;
import net.astralya.hexalia.item.custom.GhostVeilItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class GhostVeilRenderer extends GeoArmorRenderer<GhostVeilItem> {
    public GhostVeilRenderer() {
        super(new DefaultedItemGeoModel<>(Identifier.of(HexaliaMod.MODID, "armor/ghostveil")) {

            @Override
            public Identifier getTextureResource(GhostVeilItem animatable) {
                return Identifier.of(HexaliaMod.MODID, "textures/armor/ghostveil.png");
            }

            @Override
            public Identifier getAnimationResource(GhostVeilItem animatable) {
                return Identifier.of(HexaliaMod.MODID, "animations/ghostveil.animation.json");
            }
        });
    }
}