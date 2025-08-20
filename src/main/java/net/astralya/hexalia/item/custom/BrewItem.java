package net.astralya.hexalia.item.custom;

import net.astralya.hexalia.Configuration;
import net.astralya.hexalia.item.ModItems;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Supplier;

public class BrewItem extends Item {

    private final int baseAmplifier;
    private final Text tooltip;
    private final Supplier<RegistryEntry<StatusEffect>> effectSupplier;

    public BrewItem(Settings settings, Supplier<RegistryEntry<StatusEffect>> effectSupplier, int baseAmplifier, Text tooltip) {
        super(settings);
        this.effectSupplier = effectSupplier;
        this.baseAmplifier = baseAmplifier;
        this.tooltip = tooltip;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        super.finishUsing(stack, world, user);

        if (user instanceof ServerPlayerEntity serverPlayer) {
            serverPlayer.incrementStat(Stats.USED.getOrCreateStat(this));
            Criteria.CONSUME_ITEM.trigger(serverPlayer, stack);
        }

        if (!world.isClient) {
            int fixedDuration = Configuration.get().brewEffectDuration;
            int adjustedAmplifier = Configuration.get().brewAmplifierBonus;
            user.addStatusEffect(new StatusEffectInstance(
                    effectSupplier.get(),
                    fixedDuration,
                    Math.max(baseAmplifier, adjustedAmplifier)
            ));
        }

        if (user instanceof PlayerEntity player) {
            if (player.getAbilities().creativeMode) {
                return stack;
            }

            stack.decrement(1);
            ItemStack bottle = new ItemStack(ModItems.RUSTIC_BOTTLE);
            if (stack.isEmpty()) {
                return bottle;
            } else {
                if (!player.getInventory().insertStack(bottle)) {
                    player.dropItem(bottle, false);
                }
            }
        }

        return stack;
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 32;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(this.tooltip);
    }
}
