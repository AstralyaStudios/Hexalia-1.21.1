package net.astralya.hexalia.block.custom.censer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.world.ServerWorld;

public class CenserServerTickHandler {
    private static final int UPDATE_INTERVAL = 5;

    public static void register() {
        ServerTickEvents.END_WORLD_TICK.register(CenserServerTickHandler::onWorldTick);
    }

    private static void onWorldTick(ServerWorld world) {
        if (world.getTime() % UPDATE_INTERVAL == 0) {
            CenserEffectHandler.updateEffects(world);
        }
    }
}
