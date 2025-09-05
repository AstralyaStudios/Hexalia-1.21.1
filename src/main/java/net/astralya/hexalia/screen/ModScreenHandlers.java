package net.astralya.hexalia.screen;

import net.astralya.hexalia.HexaliaMod;
import net.astralya.hexalia.screen.custom.SmallCauldronScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class ModScreenHandlers {

    public static final ScreenHandlerType<SmallCauldronScreenHandler> SMALL_CAULDRON_SCREEN =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(HexaliaMod.MODID, "small_cauldron_screen"),
                    new ExtendedScreenHandlerType<>(SmallCauldronScreenHandler::new, BlockPos.PACKET_CODEC));

    public static void registerScreenHandlers() {
        HexaliaMod.LOGGER.info("Registering Screen Handlers for " + HexaliaMod.MODID);
    }
}
