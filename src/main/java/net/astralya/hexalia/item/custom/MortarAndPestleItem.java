package net.astralya.hexalia.item.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MortarAndPestleItem extends Item {

    // TODO: Test When Recipes are Done

    public MortarAndPestleItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasRecipeRemainder() {
        return true;
    }

    @Override
    public ItemStack getRecipeRemainder(ItemStack stack) {
        ItemStack result = stack.copy();
        result.setDamage(result.getDamage() - 1);

        if (result.getDamage() >= result.getMaxDamage()) {
            return ItemStack.EMPTY;
        }
        return result;
    }
}
