package net.astralya.hexalia.item.custom;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class PurifyingSaltsItem extends Item {

    public PurifyingSaltsItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof ServerPlayerEntity serverPlayer) {
            Criteria.CONSUME_ITEM.trigger(serverPlayer, stack);
            serverPlayer.incrementStat(Stats.USED.getOrCreateStat(this));
        }

        if (!world.isClient) {
            clearHarmfulEffects(user);
        }

        stack.decrementUnlessCreative(1, user);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_BONE_MEAL_USE,
                SoundCategory.PLAYERS, 0.5f, 1.0f);

        return stack.isEmpty() ? ItemStack.EMPTY : stack;
    }

    private void clearHarmfulEffects(LivingEntity entity) {
        List<RegistryEntry<StatusEffect>> harmfulEffects = new ArrayList<>();

        for (StatusEffectInstance effect : entity.getStatusEffects()) {
            RegistryEntry<StatusEffect> statusEffectEntry = effect.getEffectType();

            if (statusEffectEntry.value().getCategory() == StatusEffectCategory.HARMFUL) {
                harmfulEffects.add(statusEffectEntry);
            }
        }

        for (RegistryEntry<StatusEffect> effectEntry : harmfulEffects) {
            entity.removeStatusEffect(effectEntry);
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 32;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("tooltip.hexalia.purifying_salts").formatted(Formatting.GRAY));
    }
}
