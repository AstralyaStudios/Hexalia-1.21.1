package net.grapes.hexalia.compat.patchouli;

import net.grapes.hexalia.recipe.ModRecipes;
import net.grapes.hexalia.recipe.RitualBrazierRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

import java.util.List;

public class RitualBrazierProcessor implements IComponentProcessor {

    private RitualBrazierRecipe recipe;

    @Override
    public void setup(Level level, IVariableProvider variables) {
        String recipeId = variables.get("recipe", level.registryAccess()).asString();
        ResourceLocation id = ResourceLocation.parse(recipeId);

        List<RecipeHolder<RitualBrazierRecipe>> allRecipes = Minecraft.getInstance()
                .level.getRecipeManager()
                .getAllRecipesFor(ModRecipes.RITUAL_BRAZIER_TYPE.get());

        this.recipe = allRecipes.stream()
                .filter(holder -> holder.id().equals(id))
                .findFirst()
                .map(RecipeHolder::value)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Ritual Brazier recipe: " + id));
    }

    @Override
    public IVariable process(Level level, String key) {
        return switch (key) {
            case "output" -> {
                ItemStack result = recipe.getResultItem(level.registryAccess());
                yield IVariable.from(result, level.registryAccess());
            }
            case "header" -> {
                ItemStack result = recipe.getResultItem(level.registryAccess());
                yield IVariable.from(result.getHoverName(), level.registryAccess());
            }
            case "input_main" -> {
                ItemStack[] inputs = recipe.inputItem().getItems();
                yield inputs.length > 0 ? IVariable.from(inputs[0], level.registryAccess()) : null;
            }
            default -> null;
        };
    }
}
