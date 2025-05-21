package net.grapes.hexalia.item.custom;

import net.grapes.hexalia.item.ModItems;
import net.grapes.hexalia.util.TeleportUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.FakePlayer;

import java.util.List;

public class HomesteadBrewItem extends Item {
    public HomesteadBrewItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide && TeleportUtil.canReturn(level, player, true)) {
            return new InteractionResultHolder<>(net.minecraft.world.InteractionResult.FAIL, player.getItemInHand(hand));
        }
        player.startUsingItem(hand);
        return new InteractionResultHolder<>(net.minecraft.world.InteractionResult.SUCCESS, player.getItemInHand(hand));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        Player player = entity instanceof Player ? (Player) entity : null;
        if (player == null || !player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        if (player != null && !(player instanceof FakePlayer)) {
            TeleportUtil.teleportPlayerToSpawn(level, player, true);
        }
        if (player == null || !player.getAbilities().instabuild) {
            if (stack.isEmpty()) {
                return new ItemStack(ModItems.RUSTIC_BOTTLE.get());
            }

            if (player != null) {
                player.getInventory().add(new ItemStack(ModItems.RUSTIC_BOTTLE.get()));
            }
        }
        return stack;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 32;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.hexalia.homestead_brew").withStyle(ChatFormatting.DARK_BLUE));
    }
}
