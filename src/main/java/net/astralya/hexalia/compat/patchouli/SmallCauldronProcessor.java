package net.astralya.hexalia.compat.patchouli;

import net.astralya.hexalia.recipe.ModRecipes;
import net.astralya.hexalia.recipe.SmallCauldronRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

import java.util.List;

public class SmallCauldronProcessor implements IComponentProcessor {

    private SmallCauldronRecipe recipe;

    @Override
    public void setup(World level, IVariableProvider variables) {
        String recipeIdStr = variables.get("recipe", level.getRegistryManager()).asString();
        Identifier recipeId = Identifier.of(recipeIdStr);

        List<RecipeEntry<SmallCauldronRecipe>> all =
                level.getRecipeManager().listAllOfType(ModRecipes.SMALL_CAULDRON_TYPE);

        this.recipe = all.stream()
                .filter(entry -> entry.id().equals(recipeId))
                .findFirst()
                .map(RecipeEntry::value)
                .orElseThrow(() -> new IllegalArgumentException("Small Cauldron recipe not found: " + recipeId));
    }

    @Override
    public IVariable process(World level, String key) {
        if (recipe == null) return null;

        if ("output".equals(key)) {
            return IVariable.from(recipe.getOutputStack().copy(), level.getRegistryManager());
        }
        if ("header".equals(key)) {
            return IVariable.from(recipe.getOutputStack().getName(), level.getRegistryManager());
        }

        List<Ingredient> ings = recipe.getIngredientList();
        if (key.startsWith("ingredients")) {
            try {
                int idx = Integer.parseInt(key.substring("ingredients".length()));
                if (idx >= 0 && idx < ings.size()) {
                    ItemStack[] stacks = ings.get(idx).getMatchingStacks();
                    return stacks.length > 0
                            ? IVariable.from(stacks[0].copy(), level.getRegistryManager())
                            : null;
                }
            } catch (NumberFormatException ignored) {}
        }
        return null;
    }
}