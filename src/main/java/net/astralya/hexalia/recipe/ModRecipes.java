package net.astralya.hexalia.recipe;

import net.astralya.hexalia.HexaliaMod;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {

    public static final RecipeSerializer<RitualBrazierRecipe> RITUAL_BRAZIER_SERIALIZER = Registry.register(
            Registries.RECIPE_SERIALIZER, Identifier.of(HexaliaMod.MODID, "ritual_brazier"), new RitualBrazierRecipe.Serializer());
    public static final RecipeType<RitualBrazierRecipe> RITUAL_BRAZIER_TYPE = Registry.register(
            Registries.RECIPE_TYPE, Identifier.of(HexaliaMod.MODID, "ritual_brazier"), new RecipeType<>() {
                @Override
                public String toString() {
                    return "ritual_brazier";
                }
            });

    public static final RecipeSerializer<SmallCauldronRecipe> SMALL_CAULDRON_SERIALIZER = Registry.register(
            Registries.RECIPE_SERIALIZER, Identifier.of(HexaliaMod.MODID, "small_cauldron"), new SmallCauldronRecipe.Serializer());
    public static final RecipeType<SmallCauldronRecipe> SMALL_CAULDRON_TYPE = Registry.register(
            Registries.RECIPE_TYPE, Identifier.of(HexaliaMod.MODID, "small_cauldron"), new RecipeType<>() {
                @Override
                public String toString() {
                    return "small_cauldron";
                }
            });

    public static void registerRecipes() {
        HexaliaMod.LOGGER.info("Registering Custom Recipes for " + HexaliaMod.MODID);
    }
}
