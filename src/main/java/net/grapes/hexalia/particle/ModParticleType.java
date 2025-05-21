package net.grapes.hexalia.particle;

import net.grapes.hexalia.HexaliaMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModParticleType {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(Registries.PARTICLE_TYPE, HexaliaMod.MOD_ID);

    public static final Supplier<SimpleParticleType> SPORE = PARTICLE_TYPES.register("spore",
            () -> new SimpleParticleType(true));
    public static final Supplier<SimpleParticleType> GHOST = PARTICLE_TYPES.register("ghost",
            () -> new SimpleParticleType(true));
    public static final Supplier<SimpleParticleType> MOTE = PARTICLE_TYPES.register("mote",
            () -> new SimpleParticleType(true));
    public static final Supplier<SimpleParticleType> LEAVES = PARTICLE_TYPES.register("leaves",
            () -> new SimpleParticleType(true));
    public static final Supplier<SimpleParticleType> INFUSED_BUBBLES = PARTICLE_TYPES.register("infused_bubbles",
            () -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }

}
