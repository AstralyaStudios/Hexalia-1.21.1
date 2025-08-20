package net.astralya.hexalia.effect;


import net.astralya.hexalia.HexaliaMod;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModEffects {

    private static RegistryEntry<StatusEffect> registerStatusEffect(String name, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of( HexaliaMod.MODID, name), statusEffect);
    }

    public static void registerEffects() {
        HexaliaMod.LOGGER.info("Registering Mod Effects for " + HexaliaMod.MODID);
    }
}
