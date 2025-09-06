package net.astralya.hexalia.compat.patchouli;

import net.astralya.hexalia.recipe.ModRecipes;
import net.astralya.hexalia.recipe.RitualTableRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

import java.util.List;

public class RitualTableProcessor implements IComponentProcessor {

    private RitualTableRecipe recipe;

    @Override
    public void setup(World level, IVariableProvider variables) {
        String recipeIdStr = variables.get("recipe", level.getRegistryManager()).asString();
        Identifier recipeId = Identifier.of(recipeIdStr);

        List<RecipeEntry<RitualTableRecipe>> all =
                level.getRecipeManager().listAllOfType(ModRecipes.RITUAL_TABLE_TYPE);

        this.recipe = all.stream()
                .filter(entry -> entry.id().equals(recipeId))
                .findFirst()
                .map(RecipeEntry::value)
                .orElseThrow(() -> new IllegalArgumentException("Ritual Table recipe not found: " + recipeId));
    }

    @Override
    public IVariable process(World level, String key) {
        if (recipe == null) return null;

        if ("output".equals(key)) {
            return IVariable.from(recipe.output().copy(), level.getRegistryManager());
        }
        if ("header".equals(key)) {
            return IVariable.from(recipe.output().getName(), level.getRegistryManager());
        }

        List<Ingredient> ings = recipe.ingredients();
        if ("input".equals(key)) {
            if (!ings.isEmpty()) {
                ItemStack[] stacks = ings.get(0).getMatchingStacks();
                return stacks.length > 0
                        ? IVariable.from(stacks[0].copy(), level.getRegistryManager())
                        : null;
            }
            return null;
        }

        if (key.startsWith("salt_items")) {
            try {
                int n = Integer.parseInt(key.substring("salt_items".length()));
                int idx = 1 + n;
                if (idx >= 1 && idx < ings.size()) {
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