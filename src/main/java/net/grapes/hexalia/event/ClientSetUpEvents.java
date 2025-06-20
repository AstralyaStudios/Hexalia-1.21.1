package net.grapes.hexalia.event;

import net.grapes.hexalia.HexaliaMod;
import net.grapes.hexalia.particle.ModParticleType;
import net.grapes.hexalia.particle.custom.*;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(modid = HexaliaMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetUpEvents {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register(ModParticleType.GHOST.get(), GhostParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(ModParticleType.INFUSED_BUBBLES.get(), InfusedBubbleParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(ModParticleType.LEAVES.get(), LeavesParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(ModParticleType.MOTE.get(), MoteParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(ModParticleType.SPORE.get(), SporeParticle.Factory::new);
    }
}
