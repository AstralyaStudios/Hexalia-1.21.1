package net.astralya.hexalia.compat.patchouli;

import net.astralya.hexalia.recipe.ModRecipes;
import net.astralya.hexalia.recipe.RitualBrazierRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

import java.util.List;

public class RitualBrazierProcessor implements IComponentProcessor {

    private RitualBrazierRecipe recipe;

    @Override
    public void setup(World level, IVariableProvider variables) {
        String recipeIdStr = variables.get("recipe", level.getRegistryManager()).asString();
        Identifier id = Identifier.of(recipeIdStr);

        List<RecipeEntry<RitualBrazierRecipe>> all =
                level.getRecipeManager().listAllOfType(ModRecipes.RITUAL_BRAZIER_TYPE);

        this.recipe = all.stream()
                .filter(entry -> entry.id().equals(id))
                .findFirst()
                .map(RecipeEntry::value)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Ritual Brazier recipe: " + id));
    }

    @Override
    public IVariable process(World level, String key) {
        return switch (key) {
            case "output" -> {
                ItemStack result = recipe.output().copy();
                yield IVariable.from(result, level.getRegistryManager());
            }
            case "header" -> {
                ItemStack result = recipe.output();
                yield IVariable.from(result.getName(), level.getRegistryManager());
            }
            case "input_main" -> {
                ItemStack[] inputs = recipe.inputItem().getMatchingStacks();
                yield inputs.length > 0
                        ? IVariable.from(inputs[0].copy(), level.getRegistryManager())
                        : null;
            }
            default -> null;
        };
    }
}