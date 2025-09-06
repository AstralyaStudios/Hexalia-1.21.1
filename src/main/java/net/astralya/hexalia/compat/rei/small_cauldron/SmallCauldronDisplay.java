package net.astralya.hexalia.compat.rei.small_cauldron;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.astralya.hexalia.recipe.SmallCauldronRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class SmallCauldronDisplay extends BasicDisplay {
    private final EntryIngredient bottleSlot;

    public SmallCauldronDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs, EntryIngredient bottleSlot) {
        super(inputs, outputs);
        this.bottleSlot = bottleSlot;
    }

    public SmallCauldronDisplay(RecipeEntry<SmallCauldronRecipe> entry) {
        this(entry.value());
    }

    public SmallCauldronDisplay(SmallCauldronRecipe recipe) {
        super(
                getInputList(recipe),
                List.of(EntryIngredient.of(EntryStacks.of(recipe.getOutputStack().copy())))
        );
        this.bottleSlot = EntryIngredients.ofIngredient(recipe.getBottleSlot());
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return SmallCauldronCategory.SMALL_CAULDRON;
    }

    public EntryIngredient getBottleSlot() {
        return bottleSlot;
    }

    private static List<EntryIngredient> getInputList(SmallCauldronRecipe recipe) {
        if (recipe == null) return Collections.emptyList();
        List<EntryIngredient> list = new ArrayList<>();
        for (Ingredient ing : recipe.getIngredientList()) {
            list.add(EntryIngredients.ofIngredient(ing));
        }
        return list;
    }
}