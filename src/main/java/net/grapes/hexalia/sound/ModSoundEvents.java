package net.grapes.hexalia.sound;

import net.grapes.hexalia.HexaliaMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, HexaliaMod.MOD_ID);

    public static final Supplier<SoundEvent> MANDRAKE_SCREAM = registerSoundEvent("mandrake_scream");
    public static final Supplier<SoundEvent> RITUAL_SUCCESS = registerSoundEvent("ritual_success");
    public static final Supplier<SoundEvent> CONVERSION = registerSoundEvent("conversion");
    public static final Supplier<SoundEvent> WIND_BURST = registerSoundEvent("wind_burst");
    public static final Supplier<SoundEvent> WIND_DEFLECT = registerSoundEvent("wind_deflect");

    private static Supplier<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(HexaliaMod.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
