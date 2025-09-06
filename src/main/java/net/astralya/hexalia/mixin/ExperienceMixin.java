package net.astralya.hexalia.mixin;

import net.astralya.hexalia.item.ModItems;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class ExperienceMixin {

    @Inject(method = "onDeath", at = @At("HEAD"))
    public void onEntityKilled(DamageSource source, CallbackInfo ci) {
        if (source.getAttacker() instanceof PlayerEntity player) {
            ItemStack offHandStack = player.getOffHandStack();

            if (offHandStack.getItem() == ModItems.SAGE_PENDANT) {
                System.out.println("Wisdom Gem detected in offhand");

                int experience = getExperiencePoints();
                System.out.println("Experience obtained: " + experience);

                if (experience > 0) {
                    spawnExperienceOrbs(experience, player);
                    reduceDurability(offHandStack, player);
                }
            }
        }
    }

    @Unique
    private int getExperiencePoints() {
        return 40;
    }

    @Unique
    private void spawnExperienceOrbs(int experience, PlayerEntity player) {
        int remainingExperience = experience;
        while (remainingExperience > 0) {
            int xpValue = ExperienceOrbEntity.roundToOrbSize(remainingExperience);
            remainingExperience -= xpValue;
            player.getWorld().spawnEntity(new ExperienceOrbEntity(player.getWorld(), player.getX(), player.getY(), player.getZ(), xpValue));
        }
    }

    @Unique
    private void reduceDurability(ItemStack stack, PlayerEntity player) {
        if (!player.getAbilities().creativeMode && stack.isDamageable()) {
            stack.damage(1, player, LivingEntity.getSlotForHand(Hand.OFF_HAND));
            if (stack.isEmpty()) {
                player.setStackInHand(Hand.OFF_HAND, ItemStack.EMPTY);
            }
        }
    }
}