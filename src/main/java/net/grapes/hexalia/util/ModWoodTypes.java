package net.grapes.hexalia.util;

import net.grapes.hexalia.HexaliaMod;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public class ModWoodTypes {
    public static final WoodType WILLOW = WoodType.register(
            new WoodType(HexaliaMod.MODID + ":willow", BlockSetType.OAK)
    );

    public static final WoodType COTTONWOOD = WoodType.register(
            new WoodType(HexaliaMod.MODID + ":cottonwood", BlockSetType.OAK)
    );
}
