package net.grapes.hexalia.compat.patchouli;

import net.grapes.hexalia.recipe.ModRecipes;
import net.grapes.hexalia.recipe.RitualTableRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

import java.util.List;

public class RitualTableProcessor implements IComponentProcessor {

    private RitualTableRecipe recipe;

    @Override
    public void setup(Level level, IVariableProvider variables) {
        String recipeIdStr = variables.get("recipe", level.registryAccess()).asString();
        ResourceLocation recipeId = ResourceLocation.parse(recipeIdStr);

        List<RecipeHolder<RitualTableRecipe>> allRecipes =
                Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(ModRecipes.RITUAL_TABLE_TYPE.get());

        this.recipe = allRecipes.stream()
                .filter(holder -> holder.id().equals(recipeId))
                .findFirst()
                .map(RecipeHolder::value)
                .orElseThrow(() -> new IllegalArgumentException("Ritual Table recipe not found: " + recipeId));
    }

    @Override
    public IVariable process(Level level, String key) {
        if (recipe == null) return null;

        // Handle output and header
        if (key.equals("output")) {
            return IVariable.from(recipe.getResultItem(level.registryAccess()), level.registryAccess());
        } else if (key.equals("header")) {
            return IVariable.from(recipe.getResultItem(level.registryAccess()).getHoverName(), level.registryAccess());
        }

        // Main input (first ingredient)
        if (key.equals("input_main") && !recipe.getIngredients().isEmpty()) {
            Ingredient main = recipe.getIngredients().getFirst();
            return !main.isEmpty() && main.getItems().length > 0
                    ? IVariable.from(main.getItems()[0], level.registryAccess())
                    : null;
        }

        // Supporting (brazier) ingredients: input_brazier1..4
        if (key.startsWith("input_brazier")) {
            try {
                int index = Integer.parseInt(key.substring("input_brazier".length())); // 1..4
                if (index >= 1 && index <= recipe.getIngredients().size() - 1) {
                    Ingredient brazier = recipe.getIngredients().get(index);
                    return !brazier.isEmpty() && brazier.getItems().length > 0
                            ? IVariable.from(brazier.getItems()[0], level.registryAccess())
                            : null;
                }
            } catch (NumberFormatException ignored) {}
        }

        return null;
    }
}
