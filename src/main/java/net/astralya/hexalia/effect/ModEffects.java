package net.astralya.hexalia.effect;


import net.astralya.hexalia.HexaliaMod;
import net.astralya.hexalia.effect.custom.*;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModEffects {

    public static final RegistryEntry<StatusEffect> OVERFED = registerStatusEffect("overfed",
            new OverfedEffect(StatusEffectCategory.NEUTRAL, 0xB02B2B)
                    .addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED,
                            Identifier.of(HexaliaMod.MODID, "overfed"), -0.1f,
                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    public static final RegistryEntry<StatusEffect> DAYBLOOM = registerStatusEffect("daybloom",
            new DaybloomEffect(StatusEffectCategory.NEUTRAL, 0x8BFF8B));

    public static final RegistryEntry<StatusEffect> BLOODLUST = registerStatusEffect("bloodlust",
            new BloodlustEffect(StatusEffectCategory.BENEFICIAL, 0xB02B2B, 3.0).addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE,
                    Identifier.of(HexaliaMod.MODID, "bloodlust"), 0.0f, EntityAttributeModifier.Operation.ADD_VALUE));
    public static final RegistryEntry<StatusEffect> SPIKESKIN = registerStatusEffect("spikeskin",
            new SpikeskinEffect(StatusEffectCategory.BENEFICIAL, 0x39581A, 3.0).addAttributeModifier(EntityAttributes.GENERIC_ARMOR,
                    Identifier.of(HexaliaMod.MODID, "spikeskin"), 0.2f, EntityAttributeModifier.Operation.ADD_VALUE));
    public static final RegistryEntry<StatusEffect> SIPHON = registerStatusEffect("siphon",
            new SiphonEffect(StatusEffectCategory.BENEFICIAL, 0xEAEAEA, 3.0).addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED,
                    Identifier.of(HexaliaMod.MODID, "siphon"), 0.4f, EntityAttributeModifier.Operation.ADD_VALUE));
    public static final RegistryEntry<StatusEffect> SLIMEWALKER = registerStatusEffect("slimewalker",
            new SlimewalkerEffect(StatusEffectCategory.BENEFICIAL, 0x2CFB03));
    public static final RegistryEntry<StatusEffect> ARACHNID_GRACE = registerStatusEffect("arachnid_grace",
            new ArachnidGraceEffect(StatusEffectCategory.BENEFICIAL, 0xE0E0E0));

    public static final RegistryEntry<StatusEffect> STUNNED = registerStatusEffect("stunned",
            new StunnedEffect(StatusEffectCategory.HARMFUL, 0xFFFFDD));
    public static final RegistryEntry<StatusEffect> BLEEDING = registerStatusEffect("bleeding",
            new BleedingEffect(StatusEffectCategory.HARMFUL, 0x8B0000));
    
    private static RegistryEntry<StatusEffect> registerStatusEffect(String name, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of( HexaliaMod.MODID, name), statusEffect);
    }

    public static void registerEffects() {
        HexaliaMod.LOGGER.info("Registering Mod Effects for " + HexaliaMod.MODID);
    }
}
