package net.grapes.hexalia.item.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class MortarAndPestleItem extends Item {
    public MortarAndPestleItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public @NotNull ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        ItemStack result = itemStack.copy();
        result.setDamageValue(result.getDamageValue() - 1);

        if (result.getDamageValue() >= result.getMaxDamage()) {
            return ItemStack.EMPTY;
        }
        return result;
    }
}
