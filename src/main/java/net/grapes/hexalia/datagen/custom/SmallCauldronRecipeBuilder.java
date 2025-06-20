package net.grapes.hexalia.datagen.custom;

import net.grapes.hexalia.recipe.SmallCauldronRecipe;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class SmallCauldronRecipeBuilder implements RecipeBuilder {
    private final NonNullList<Ingredient> ingredients;
    private final ItemStack output;
    private final Item result;
    private Ingredient bottleSlot = Ingredient.EMPTY;
    private float experience = 0.0F;
    private int brewTime = 200;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    public SmallCauldronRecipeBuilder(ItemStack output) {
        this.ingredients = NonNullList.create();
        this.output = output;
        this.result = output.getItem();
    }

    public static SmallCauldronRecipeBuilder smallCauldronRecipe(ItemStack output) {
        return new SmallCauldronRecipeBuilder(output);
    }

    public SmallCauldronRecipeBuilder addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        return this;
    }

    public SmallCauldronRecipeBuilder addIngredient(Item item) {
        return this.addIngredient(Ingredient.of(item));
    }

    public SmallCauldronRecipeBuilder addIngredient(ItemStack itemStack) {
        return this.addIngredient(Ingredient.of(itemStack));
    }

    public SmallCauldronRecipeBuilder bottleSlot(Ingredient bottleSlot) {
        this.bottleSlot = bottleSlot;
        return this;
    }

    public SmallCauldronRecipeBuilder bottleSlot(Item item) {
        return this.bottleSlot(Ingredient.of(item));
    }

    public SmallCauldronRecipeBuilder experience(float experience) {
        this.experience = experience;
        return this;
    }

    public SmallCauldronRecipeBuilder brewTime(int brewTime) {
        this.brewTime = brewTime;
        return this;
    }

    @Override
    public SmallCauldronRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String s) {
        return this;
    }

    public SmallCauldronRecipeBuilder unlockedByItem(String name, Item item) {
        return this.unlockedBy(name, InventoryChangeTrigger.TriggerInstance.hasItems(item));
    }

    public SmallCauldronRecipeBuilder unlockedByItems(String name, Item... items) {
        return this.unlockedBy(name, InventoryChangeTrigger.TriggerInstance.hasItems(items));
    }

    @Override
    public Item getResult() {
        return this.result;
    }

    @Override
    public void save(RecipeOutput output, ResourceLocation id) {
        if (this.ingredients.isEmpty()) {
            throw new IllegalStateException("No ingredients defined for Small Cauldron recipe");
        }
        if (this.ingredients.size() > SmallCauldronRecipe.INPUT_SLOTS) {
            throw new IllegalStateException("Too many ingredients for Small Cauldron recipe (max " + SmallCauldronRecipe.INPUT_SLOTS + ")");
        }

        Advancement.Builder advancementBuilder = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);

        this.criteria.forEach(advancementBuilder::addCriterion);

        SmallCauldronRecipe recipe = new SmallCauldronRecipe(this.ingredients, this.bottleSlot, this.output, this.experience, this.brewTime);
        output.accept(id, recipe, advancementBuilder.build(id.withPrefix("recipes/small_cauldron/")));
    }
}