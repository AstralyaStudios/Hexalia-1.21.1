package net.grapes.hexalia;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
@EventBusSubscriber(modid = HexaliaMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class Configuration {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();


    static final ModConfigSpec SPEC = BUILDER.build();


    @SubscribeEvent
    static void onLoad(final ModConfigEvent.Loading event) {
    }

    @SubscribeEvent
    static void onReload(final ModConfigEvent.Reloading event) {
    }
}
