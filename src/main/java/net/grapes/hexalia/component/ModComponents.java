package net.grapes.hexalia.component;

import net.grapes.hexalia.HexaliaMod;
import net.grapes.hexalia.component.item.MothData;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class ModComponents {
    public static final DeferredRegister<DataComponentType<?>> COMPONENT_TYPES =
            DeferredRegister.create(BuiltInRegistries.DATA_COMPONENT_TYPE, HexaliaMod.MODID);

    public static final Supplier<DataComponentType<MothData>> MOTH =
            COMPONENT_TYPES.register("moth", () ->
                    DataComponentType.<MothData>builder()
                            .persistent(MothData.CODEC)
                            .networkSynchronized(MothData.STREAM_CODEC)
                            .build());

    public static void register(IEventBus modBus) {
        COMPONENT_TYPES.register(modBus);
    }
}
