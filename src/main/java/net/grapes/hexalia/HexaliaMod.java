package net.grapes.hexalia;

import net.grapes.hexalia.block.entity.ModBlockEntityTypes;
import net.grapes.hexalia.block.ModBlocks;
import net.grapes.hexalia.block.entity.renderer.CenserBlockEntityRenderer;
import net.grapes.hexalia.block.entity.renderer.RitualBrazierBlockEntityRenderer;
import net.grapes.hexalia.block.entity.renderer.RitualTableBlockEntityRenderer;
import net.grapes.hexalia.block.entity.renderer.ShelfBlockEntityRenderer;
import net.grapes.hexalia.component.ModComponents;
import net.grapes.hexalia.component.item.MothData;
import net.grapes.hexalia.effect.ModMobEffects;
import net.grapes.hexalia.entity.ModEntities;
import net.grapes.hexalia.entity.boat.ModBoatRenderer;
import net.grapes.hexalia.entity.custom.client.SilkMothRenderer;
import net.grapes.hexalia.item.ModCreativeModeTabs;
import net.grapes.hexalia.item.ModItems;
import net.grapes.hexalia.effect.ModEffectCure;
import net.grapes.hexalia.loot.ModLootModifiers;
import net.grapes.hexalia.particle.ModParticleType;
import net.grapes.hexalia.recipe.ModRecipes;
import net.grapes.hexalia.screen.ModMenuTypes;
import net.grapes.hexalia.screen.custom.SmallCauldronScreen;
import net.grapes.hexalia.sound.ModSoundEvents;
import net.grapes.hexalia.util.ModArmorMaterials;
import net.grapes.hexalia.util.ModWoodTypes;
import net.grapes.hexalia.worldgen.biome.ModBiomes;
import net.grapes.hexalia.worldgen.biome.ModSurfaceRules;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.ModList;
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
import terrablender.api.SurfaceRuleManager;

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
        ModEntities.register(modEventBus);
        ModLootModifiers.register(modEventBus);
        ModArmorMaterials.register(modEventBus);
        ModComponents.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
        modContainer.registerConfig(ModConfig.Type.COMMON, Configuration.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModBiomes.registerBiomes();
            SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MODID, ModSurfaceRules.makeRules());
        });
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == ModCreativeModeTabs.HEXALIA_TAB.get()) {
            if (ModList.get().isLoaded("patchouli")) {
                event.accept(ModItems.VERDANT_GRIMOIRE);
            }
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.RABBAGE.get(), ThrownItemRenderer::new);
            EntityRenderers.register(ModEntities.MOD_BOAT.get(), context -> new ModBoatRenderer(context, false));
            EntityRenderers.register(ModEntities.MOD_CHEST_BOAT.get(), context -> new ModBoatRenderer(context, true));

            EntityRenderers.register(ModEntities.SILK_MOTH_ENTITY.get(), SilkMothRenderer::new);

            Sheets.addWoodType(ModWoodTypes.COTTONWOOD);
            Sheets.addWoodType(ModWoodTypes.WILLOW);

            ItemProperties.register(ModItems.BOTTLED_MOTH.get(),
                    ResourceLocation.fromNamespaceAndPath(HexaliaMod.MODID, "variant"),
                    (stack, level, entity, seed) -> {
                        MothData data = stack.get(ModComponents.MOTH.get());
                        return data != null ? (float) data.variantId() : 0f;
                    });
        }

        @SubscribeEvent
        public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ModBlockEntityTypes.RITUAL_TABLE.get(), RitualTableBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntityTypes.RITUAL_BRAZIER.get(), RitualBrazierBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntityTypes.SHELF.get(), ShelfBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntityTypes.CENSER.get(), CenserBlockEntityRenderer::new);

            event.registerBlockEntityRenderer(ModBlockEntityTypes.MOD_SIGN.get(), SignRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntityTypes.MOD_HANGING_SIGN.get(), HangingSignRenderer::new);
        }

        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(ModMenuTypes.SMALL_CAULDRON_MENU.get(), SmallCauldronScreen::new);
        }
    }
}