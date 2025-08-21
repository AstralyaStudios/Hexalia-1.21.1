package net.astralya.hexalia;

import net.astralya.hexalia.block.ModBlocks;
import net.astralya.hexalia.particle.ModParticleType;
import net.astralya.hexalia.particle.custom.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.world.biome.FoliageColors;

public class HexaliaModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        registerBlockRenderLayers();
        registerParticles();
        registerColorProviders();
    }

    private void registerBlockRenderLayers() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                ModBlocks.SPIRIT_BLOOM, ModBlocks.POTTED_SPIRIT_BLOOM,
                ModBlocks.DREAMSHROOM, ModBlocks.POTTED_DREAMSHROOM,
                ModBlocks.SIREN_KELP, ModBlocks.MANDRAKE_CROP,
                ModBlocks.CHILLBERRY_BUSH, ModBlocks.SUNFIRE_TOMATO_CROP,
                ModBlocks.WILD_MANDRAKE, ModBlocks.WILD_SUNFIRE_TOMATO,
                ModBlocks.RABBAGE_CROP,
                ModBlocks.DREAMCATCHER,
                ModBlocks.POTTED_HENBANE, ModBlocks.HENBANE,
                ModBlocks.CANDLE_SKULL, ModBlocks.SALT_LAMP,
                ModBlocks.SILKWORM_COCOON,
                ModBlocks.LOTUS_FLOWER, ModBlocks.PALE_MUSHROOM,
                ModBlocks.POTTED_PALE_MUSHROOM, ModBlocks.WITCHWEED,
                ModBlocks.GHOST_FERN, ModBlocks.HEXED_BULRUSH,
                ModBlocks.NIGHTSHADE_BUSH, ModBlocks.POTTED_NIGHTSHADE_BUSH,
                ModBlocks.SALTSPROUT, ModBlocks.DUCKWEED,
                ModBlocks.GALEBERRIES_VINE, ModBlocks.GALEBERRIES_VINE_PLANT,
                ModBlocks.GRIMSHADE, ModBlocks.POTTED_GRIMSHADE,
                ModBlocks.BEGONIA, ModBlocks.POTTED_MORPHORA,
                ModBlocks.RITUAL_BRAZIER, ModBlocks.MORPHORA,
                ModBlocks.LAVENDER, ModBlocks.POTTED_LAVENDER,
                ModBlocks.NAUTILITE, ModBlocks.WINDSONG, ModBlocks.ASTRYLIS,
                ModBlocks.POTTED_WINDSONG, ModBlocks.POTTED_ASTRYLIS,
                ModBlocks.POTTED_BEGONIA, ModBlocks.WITHER_CANDLE_SKULL,
                ModBlocks.DAHLIA, ModBlocks.POTTED_DAHLIA,
                ModBlocks.CELESTIAL_BLOOM, ModBlocks.POTTED_CELESTIAL_BLOOM,
                ModBlocks.POTTED_GHOST_FERN
        );
    }

    private void registerParticles() {
        ParticleFactoryRegistry.getInstance().register(ModParticleType.SPORE, SporeParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticleType.INFUSED_BUBBLE, InfusedBubbleParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticleType.MOTE, MoteParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticleType.GHOST, GhostParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticleType.LEAVES, LeavesParticle.Factory::new);
    }

    private void registerColorProviders() {
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) ->
                        world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefaultColor(),
                ModBlocks.COTTONWOOD_LEAVES, ModBlocks.WILLOW_LEAVES);

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> FoliageColors.getDefaultColor(),
                ModBlocks.COTTONWOOD_LEAVES, ModBlocks.WILLOW_LEAVES);
    }
}
