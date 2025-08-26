package net.astralya.hexalia.component;

import net.astralya.hexalia.HexaliaMod;
import net.astralya.hexalia.component.item.MothData;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class ModComponents {
    private ModComponents() {}

    public static final ComponentType<MothData> MOTH =
            Registry.register(
                    Registries.DATA_COMPONENT_TYPE,
                    Identifier.of(HexaliaMod.MODID, "moth"),
                    ComponentType.<MothData>builder()
                            .codec(MothData.CODEC)
                            .packetCodec(MothData.PACKET_CODEC)
                            .build()
            );

    public static void registerComponents() {
        HexaliaMod.LOGGER.info("Registering Data Components for " + HexaliaMod.MODID);
    }
}

