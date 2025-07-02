package net.grapes.hexalia.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.grapes.hexalia.HexaliaMod;
import net.grapes.hexalia.block.ModBlocks;
import net.grapes.hexalia.compat.jei.category.RitualBrazierRecipeCategory;
import net.grapes.hexalia.compat.jei.category.SmallCauldronRecipeCategory;
import net.grapes.hexalia.item.ModItems;
import net.grapes.hexalia.recipe.ModRecipes;
import net.grapes.hexalia.recipe.RitualBrazierRecipe;
import net.grapes.hexalia.recipe.SmallCauldronRecipe;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@JeiPlugin
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@SuppressWarnings("unused")
public class JEIPlugin implements IModPlugin {

    private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(HexaliaMod.MODID, "jei_plugin");

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new SmallCauldronRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new RitualBrazierRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.SMALL_CAULDRON.get()), SmallCauldronRecipeCategory.SMALL_CAULDRON_RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.RITUAL_BRAZIER.get()), RitualBrazierRecipeCategory.RITUAL_BRAZIER_RECIPE_TYPE);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<SmallCauldronRecipe> smallCauldronRecipes = recipeManager
                .getAllRecipesFor(ModRecipes.SMALL_CAULDRON_TYPE.get()).stream().map(RecipeHolder::value).toList();
        registration.addRecipes(SmallCauldronRecipeCategory.SMALL_CAULDRON_RECIPE_TYPE, smallCauldronRecipes);

        List<RitualBrazierRecipe> ritualBrazierRecipes = recipeManager
                .getAllRecipesFor(ModRecipes.RITUAL_BRAZIER_TYPE.get()).stream().map(RecipeHolder::value).toList();
        registration.addRecipes(RitualBrazierRecipeCategory.RITUAL_BRAZIER_RECIPE_TYPE, ritualBrazierRecipes);

        registration.addIngredientInfo(List.of(new ItemStack(ModBlocks.WILD_SUNFIRE_TOMATO.get()), new ItemStack(ModItems.SUNFIRE_TOMATO.get()),
                new ItemStack(ModItems.SUNFIRE_TOMATO_SEEDS.get())), VanillaTypes.ITEM_STACK, Component.translatable("jei.info.wild_sunfire_tomatoes"));
        registration.addIngredientInfo(List.of(new ItemStack(ModBlocks.WILD_MANDRAKE.get()), new ItemStack(ModItems.MANDRAKE.get()), new ItemStack(ModItems.MANDRAKE_SEEDS.get())),
                VanillaTypes.ITEM_STACK, Component.translatable("jei.info.wild_mandrakes"));
        registration.addIngredientInfo(List.of(new ItemStack(ModBlocks.CHILLBERRY_BUSH.get()), new ItemStack(ModItems.CHILLBERRIES.get())),
                VanillaTypes.ITEM_STACK, Component.translatable("jei.info.chillberry_bushes"));
        registration.addIngredientInfo(List.of(new ItemStack(ModItems.SALTSPROUT.get())),
                VanillaTypes.ITEM_STACK, Component.translatable("jei.info.saltsprout"));

    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        IModPlugin.super.registerGuiHandlers(registration);
    }

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }
}
