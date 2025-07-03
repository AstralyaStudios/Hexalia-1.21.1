package net.grapes.hexalia.mixin;

import net.grapes.hexalia.block.custom.censer.CenserEffectHandler;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilMenu.class)
public abstract class AnvilMixin {

    @Inject(
            method = "createResult",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/inventory/ResultContainer;setItem(ILnet/minecraft/world/item/ItemStack;)V",
                    ordinal = 0
            )
    )

    private void hexalia$adjustFinalCost(CallbackInfo ci) {
        AnvilMenu menu = (AnvilMenu)(Object)this;
        Player player = ((ItemCombinerMenuAccessor) menu).getPlayer();

        if (player != null && CenserEffectHandler.isEffectActiveInArea(
                player.level(),
                player.blockPosition(),
                CenserEffectHandler.EffectType.ANVIL_HARMONY)) {

            menu.setMaximumCost(Math.max(1, menu.getCost() - 1));

            ItemStack result = menu.getSlot(2).getItem();
            if (!result.isEmpty()) {
                int baseCost = result.getOrDefault(DataComponents.REPAIR_COST, 0);
                result.set(DataComponents.REPAIR_COST, Math.max(1, baseCost - 1));
            }
        }
    }
}
