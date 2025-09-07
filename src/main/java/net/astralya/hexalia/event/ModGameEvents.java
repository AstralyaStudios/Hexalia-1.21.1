package net.astralya.hexalia.event;

import net.astralya.hexalia.HexaliaMod;
import net.astralya.hexalia.item.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;

@EventBusSubscriber(modid = HexaliaMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ModGameEvents {

    @SubscribeEvent
    public static void onExperiencePickup(PlayerXpEvent.PickupXp event) {
        Player player = event.getEntity();
        ItemStack offhand = player.getOffhandItem();

        if (!offhand.isEmpty() && offhand.getItem() == ModItems.SAGE_PENDANT.get()) {
            ExperienceOrb orb = event.getOrb();
            int baseXp = orb.value;

            int bonus = (int) Math.floor(baseXp * 2.0);
            orb.value += bonus;

            if (!player.level().isClientSide && !player.isCreative() && offhand.isDamageableItem()) {
                if (player instanceof ServerPlayer serverPlayer && player.level() instanceof ServerLevel serverLevel) {
                    offhand.hurtAndBreak(1, serverLevel, serverPlayer,
                            brokenStack -> serverPlayer.onEquippedItemBroken(brokenStack, EquipmentSlot.OFFHAND));
                }
            }
        }
    }
}
