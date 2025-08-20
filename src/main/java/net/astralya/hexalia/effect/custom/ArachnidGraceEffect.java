package net.astralya.hexalia.effect.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.Vec3d;

public class ArachnidGraceEffect extends StatusEffect {

    public ArachnidGraceEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity.horizontalCollision && !entity.isSneaking()) {
            Vec3d motion = entity.getVelocity();
            if (motion.y <= 0.2D) {
                entity.setVelocity(motion.x, 0.2D, motion.z);
                entity.fallDistance = 0.0F;
            }
        }

        if (entity.hasStatusEffect(StatusEffects.POISON)) {
            entity.removeStatusEffect(StatusEffects.POISON);
        }

        return true;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
