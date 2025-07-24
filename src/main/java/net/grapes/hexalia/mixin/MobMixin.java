package net.grapes.hexalia.mixin;

import net.grapes.hexalia.block.custom.censer.CenserEffectHandler;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.grapes.hexalia.block.custom.censer.CenserEffectHandler.AREA_RADIUS;

@Mixin(Mob.class)
public abstract class MobMixin extends LivingEntity {
    @Unique
    private int hexalia$lastCheckTick = -100;
    @Unique
    private boolean hexalia$lastCheckResult = false;

    protected MobMixin(EntityType<? extends LivingEntity> type, Level level) {
        super(type, level);
    }

    @Inject(method = "getTarget", at = @At("RETURN"), cancellable = true)
    private void hexalia$preventTargetGetting(CallbackInfoReturnable<LivingEntity> cir) {
        if (shouldIgnorePlayers()) {
            LivingEntity target = cir.getReturnValue();
            if (target instanceof Player) {
                cir.setReturnValue(null);
            }
        }
    }

    @Inject(method = "setTarget", at = @At("HEAD"), cancellable = true)
    private void hexalia$preventTargetSetting(LivingEntity target, CallbackInfo ci) {
        if (shouldIgnorePlayers() && target instanceof Player) {
            ci.cancel();
        }
    }

    @Unique
    private boolean shouldIgnorePlayers() {
        // Skip check entirely for non-monsters or bosses
        if (!((Object) this instanceof Monster) || isExcludedBoss((Object) this)) {
            return false;
        }

        // Only re-check every 10 ticks (0.5 seconds)
        int currentTick = this.tickCount;
        if (currentTick - hexalia$lastCheckTick < 10) {
            return hexalia$lastCheckResult;
        }

        hexalia$lastCheckTick = currentTick;

        // Only check for nearest player if we don't already know about UNDEAD_VEIL
        if (!hexalia$lastCheckResult) {
            Player nearestPlayer = this.level().getNearestPlayer(
                    this.getX(),
                    this.getY(),
                    this.getZ(),
                    AREA_RADIUS,
                    false
            );

            hexalia$lastCheckResult = CenserEffectHandler.isUndeadVeilActiveInArea(
                    this.level(),
                    this.blockPosition()
            ) || (nearestPlayer != null && isGhostVeilSneaking(nearestPlayer));
        }

        return hexalia$lastCheckResult;
    }

    // Periodic reset to ensure mobs don't stay passive forever
    @Inject(method = "tick", at = @At("HEAD"))
    private void hexalia$resetCheck(CallbackInfo ci) {
        if (this.tickCount % 100 == 0) { // Every 5 seconds
            hexalia$lastCheckTick = -100;
            hexalia$lastCheckResult = false;
        }
    }

    @Unique
    private boolean isGhostVeilSneaking(Player player) {
        return player.isCrouching() &&
                player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ArmorItem;
    }

    @Unique
    private boolean isExcludedBoss(Object entity) {
        return entity instanceof EnderDragon ||
                entity instanceof WitherBoss ||
                entity instanceof Warden;
    }
}
