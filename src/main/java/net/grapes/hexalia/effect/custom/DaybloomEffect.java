package net.grapes.hexalia.effect.custom;

import net.grapes.hexalia.HexaliaMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class DaybloomEffect extends MobEffect {

    private static final int COOLDOWN = 100;
    private static final ResourceLocation RESOURCE_LOCATION = ResourceLocation.fromNamespaceAndPath(HexaliaMod.MOD_ID, "slimey");

    public DaybloomEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (livingEntity instanceof Player player) {
            Level level = player.level();

            if (isNight(level)) {
                player.hurt(player.damageSources().magic(), 1.5F);
                removeSpeedModifier(player);
            } else if (isDay(level)) {
                player.heal(2.0F);
                applySpeedModifier(player, amplifier);
            } else {
                removeSpeedModifier(player);
            }

            return true;
        }
        return super.applyEffectTick(livingEntity, amplifier);
    }

    private boolean isNight(Level level) {
        long time = level.getDayTime() % 24000;
        return time >= 13000 && time <= 23000;
    }

    private boolean isDay(Level level) {
        long time = level.getDayTime() % 24000;
        return time >= 0 && time < 13000;
    }

    private void applySpeedModifier(Player player, int amplifier) {
        var movementSpeedAttribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (movementSpeedAttribute != null) {
            removeSpeedModifier(player);

            double speedBoost = 0.05 * (amplifier + 1);
            movementSpeedAttribute.addTransientModifier(
                    new AttributeModifier(RESOURCE_LOCATION, speedBoost, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }
    }

    private void removeSpeedModifier(Player player) {
        var movementSpeedAttribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (movementSpeedAttribute != null) {
            movementSpeedAttribute.removeModifier(RESOURCE_LOCATION);
        }
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration % COOLDOWN == 0;
    }
}
