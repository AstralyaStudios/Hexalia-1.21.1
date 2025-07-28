package net.grapes.hexalia.item.custom.client;

import net.grapes.hexalia.item.custom.BoggedBootsItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class BoggedBootsRenderer extends GeoArmorRenderer<BoggedBootsItem> {
    public BoggedBootsRenderer() {
        super(new BoggedBootsModel());
    }
}
