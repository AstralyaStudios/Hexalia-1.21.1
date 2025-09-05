package net.astralya.hexalia.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.RecipeInput;

import java.util.List;

public record SmallCauldronRecipeInput(List<ItemStack> stacks) implements RecipeInput {

    public SmallCauldronRecipeInput(ItemStack input1, ItemStack input2, ItemStack input3, ItemStack bottle) {
        this(List.of(input1, input2, input3, bottle));
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return stacks.get(slot);
    }

    @Override
    public int getSize() {
        return stacks.size();
    }

    public ItemStack getBottle() {
        return stacks.get(3);
    }

    public List<ItemStack> getInputs() {
        return stacks.subList(0, 3);
    }
}