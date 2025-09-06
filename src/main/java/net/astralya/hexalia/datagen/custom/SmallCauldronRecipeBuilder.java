package net.astralya.hexalia.datagen.custom;

import net.astralya.hexalia.recipe.SmallCauldronRecipe;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;

public class SmallCauldronRecipeBuilder implements CraftingRecipeJsonBuilder {
    private final DefaultedList<Ingredient> ingredients = DefaultedList.of();
    private Ingredient bottle = Ingredient.EMPTY;
    private ItemStack result = ItemStack.EMPTY;
    private float experience = 0.0f;
    private int brewTime = 200;

    public SmallCauldronRecipeBuilder() {}

    public static SmallCauldronRecipeBuilder smallCauldron() {
        return new SmallCauldronRecipeBuilder();
    }

    public SmallCauldronRecipeBuilder addIngredient(ItemConvertible item) {
        this.ingredients.add(Ingredient.ofItems(item));
        return this;
    }

    public SmallCauldronRecipeBuilder bottle(ItemConvertible item) {
        this.bottle = Ingredient.ofItems(item);
        return this;
    }

    public SmallCauldronRecipeBuilder result(ItemConvertible item, int count) {
        this.result = new ItemStack(item, count);
        return this;
    }

    public SmallCauldronRecipeBuilder experience(float xp) {
        this.experience = xp;
        return this;
    }

    public SmallCauldronRecipeBuilder brewTime(int ticks) {
        this.brewTime = ticks;
        return this;
    }

    @Override
    public SmallCauldronRecipeBuilder criterion(String name, net.minecraft.advancement.AdvancementCriterion<?> criterion) {
        return this;
    }

    @Override
    public SmallCauldronRecipeBuilder group(@Nullable String group) {
        return this;
    }

    @Override
    public Item getOutputItem() {
        return this.result.getItem();
    }

    @Override
    public void offerTo(RecipeExporter exporter, Identifier recipeId) {
        Advancement.Builder adv = Advancement.Builder.createUntelemetered()
                .criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId))
                .rewards(AdvancementRewards.Builder.recipe(recipeId))
                .criteriaMerger(AdvancementRequirements.CriterionMerger.OR);

        Identifier advId = Identifier.of(
                recipeId.getNamespace(),
                "recipes/small_cauldron/" + recipeId.getPath()
        );
        AdvancementEntry advEntry = adv.build(advId);

        SmallCauldronRecipe recipe = new SmallCauldronRecipe(
                this.ingredients,
                this.bottle,
                this.result,
                this.experience,
                this.brewTime
        );
        exporter.accept(recipeId, recipe, advEntry);
    }
}
