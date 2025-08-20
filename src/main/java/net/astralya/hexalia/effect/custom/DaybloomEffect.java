package net.astralya.hexalia.effect.custom;

import net.astralya.hexalia.HexaliaMod;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class DaybloomEffect extends StatusEffect {
    private static final int COOLDOWN = 100;
    private static final Identifier SPEED_MODIFIER_ID = Identifier.of(HexaliaMod.MODID, "daybloom_speed");

    public DaybloomEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity player) {
            World world = player.getWorld();

            if (isNight(world)) {
                player.damage(player.getDamageSources().magic(), 1.5F);
                removeSpeedModifier(player);
            } else if (isDay(world)) {
                player.heal(2.0F);
                applySpeedModifier(player, amplifier);
            } else {
                removeSpeedModifier(player);
            }
            return true;
        }
        return super.applyUpdateEffect(entity, amplifier);
    }

    private boolean isNight(World world) {
        long time = world.getTimeOfDay() % 24000L;
        return time >= 13000L && time <= 23000L;
    }

    private boolean isDay(World world) {
        long time = world.getTimeOfDay() % 24000L;
        return time >= 0L && time < 13000L;
    }

    private void applySpeedModifier(PlayerEntity player, int amplifier) {
        var inst = player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        if (inst != null) {
            inst.removeModifier(SPEED_MODIFIER_ID);

            double speedBoost = 0.05 * (amplifier + 1);
            inst.addTemporaryModifier(new EntityAttributeModifier(
                    SPEED_MODIFIER_ID,
                    speedBoost,
                    EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
            ));
        }
    }

    private void removeSpeedModifier(PlayerEntity player) {
        var inst = player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        if (inst != null) {
            inst.removeModifier(SPEED_MODIFIER_ID);
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return duration % COOLDOWN == 0;
    }
}
