package net.grapes.hexalia;

import net.grapes.hexalia.block.entity.ModBlockEntityTypes;
import net.grapes.hexalia.block.ModBlocks;
import net.grapes.hexalia.block.entity.renderer.RitualBrazierBlockEntityRenderer;
import net.grapes.hexalia.block.entity.renderer.RitualTableBlockEntityRenderer;
import net.grapes.hexalia.block.entity.renderer.ShelfBlockEntityRenderer;
import net.grapes.hexalia.effect.ModMobEffects;
import net.grapes.hexalia.item.ModCreativeModeTabs;
import net.grapes.hexalia.item.ModItems;
import net.grapes.hexalia.effect.ModEffectCure;
import net.grapes.hexalia.particle.ModParticleType;
import net.grapes.hexalia.recipe.ModRecipes;
import net.grapes.hexalia.screen.ModMenuTypes;
import net.grapes.hexalia.screen.custom.SmallCauldronScreen;
import net.grapes.hexalia.sound.ModSoundEvents;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(HexaliaMod.MODID)
public class HexaliaMod {
    public static final String MODID = "hexalia";
    private static final Logger LOGGER = LogUtils.getLogger();

    public HexaliaMod(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEffectCure.register();
        ModSoundEvents.register(modEventBus);
        ModMobEffects.register(modEventBus);
        ModParticleType.register(modEventBus);
        ModBlockEntityTypes.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModRecipes.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
        modContainer.registerConfig(ModConfig.Type.COMMON, Configuration.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }

        @SubscribeEvent
        public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ModBlockEntityTypes.RITUAL_TABLE.get(), RitualTableBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntityTypes.RITUAL_BRAZIER.get(), RitualBrazierBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntityTypes.SHELF.get(), ShelfBlockEntityRenderer::new);
        }

        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(ModMenuTypes.SMALL_CAULDRON_MENU.get(), SmallCauldronScreen::new);
        }
    }
}