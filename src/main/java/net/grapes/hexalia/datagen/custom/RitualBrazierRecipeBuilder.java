package net.grapes.hexalia.datagen.custom;

import net.grapes.hexalia.recipe.RitualBrazierRecipe;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class RitualBrazierRecipeBuilder implements RecipeBuilder {

    private final Ingredient input;
    private final ItemStack output;
    private final Item result;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    public RitualBrazierRecipeBuilder(Ingredient input, ItemStack output) {
        this.input = input;
        this.output = output;
        this.result = output.getItem();
    }

    public static RitualBrazierRecipeBuilder ritualBrazierRecipe(Ingredient input, ItemStack output) {
        return new RitualBrazierRecipeBuilder(input, output);
    }

    @Override
    public RitualBrazierRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String s) {
        return this;
    }

    public RitualBrazierRecipeBuilder unlockedByItem(String name, Item item) {
        return this.unlockedBy(name, InventoryChangeTrigger.TriggerInstance.hasItems(item));
    }

    @Override
    public Item getResult() {
        return this.result;
    }

    @Override
    public void save(RecipeOutput output, ResourceLocation id) {
        Advancement.Builder advancementBuilder = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancementBuilder::addCriterion);

        RitualBrazierRecipe recipe = new RitualBrazierRecipe(input, this.output);

        output.accept(id, recipe, advancementBuilder.build(id.withPrefix("recipes/ritual_brazier/")));
    }
}