package net.grapes.hexalia.item.custom;

import net.grapes.hexalia.Configuration;
import net.grapes.hexalia.item.ModItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Supplier;

public class BrewItem extends Item {

    private final int baseAmplifier;
    private final Component tooltip;
    private final Supplier<Holder<MobEffect>> effectSupplier;

    public BrewItem(Properties properties, Supplier<Holder<MobEffect>> effectSupplier, int amplifier, Component tooltip) {
        super(properties);
        this.effectSupplier = effectSupplier;
        this.baseAmplifier = amplifier;
        this.tooltip = tooltip;
    }


    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        super.finishUsingItem(stack, level, livingEntity);

        if (livingEntity instanceof ServerPlayer serverPlayer) {
            serverPlayer.awardStat(Stats.ITEM_USED.get(this));
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
        }

        if (!level.isClientSide) {
            int fixedDuration = Configuration.BREW_EFFECT_DURATION.get();
            int adjustedAmplifier = baseAmplifier + Configuration.BREW_AMPLIFIER_BONUS.get();

            livingEntity.addEffect(new MobEffectInstance(effectSupplier.get(), fixedDuration, Math.max(0, adjustedAmplifier)));
        }

        if (stack.isEmpty()) {
            return new ItemStack(ModItems.RUSTIC_BOTTLE.get());
        }

        if (livingEntity instanceof Player player && !player.getAbilities().instabuild) {
            ItemStack itemStack = new ItemStack(ModItems.RUSTIC_BOTTLE.get());
            stack.shrink(1);
            if (!player.getInventory().add(itemStack)) {
                player.drop(itemStack, false);
            }
        }

        return stack;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        player.getItemInHand(usedHand);
        player.startUsingItem(usedHand);
        return super.use(level, player, usedHand);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 32;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(tooltip);
    }
}
