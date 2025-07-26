package net.grapes.hexalia.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public record RitualTableRecipe(NonNullList<Ingredient> ingredients, ItemStack output) implements Recipe<RitualTableRecipeInput> {

    // If needed in the future, you can define the maximum number of input slots:
    public static final int INPUT_SLOTS = 4;  // e.g. allow up to 4 ingredients (placeholder for expansion)

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public boolean matches(RitualTableRecipeInput inv, Level lvl) {
        if (lvl.isClientSide()) return false;

        // Always require the table item to match the first ingredient
        if (ingredients.isEmpty() || !ingredients.get(0).test(inv.getItem(0))) {
            return false;
        }

        // If there are no extra ingredients (braziers), recipe matches
        if (ingredients.size() == 1) {
            return true;
        }

        // For now, skip brazier checks here — handled in block logic
        return true;
    }


    @Override public ItemStack assemble(RitualTableRecipeInput inv,
                                        HolderLookup.Provider reg) { return output.copy(); }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        // Ensure the crafting "grid" is large enough to fit all ingredients (not usually relevant for custom stations)
        return width * height >= ingredients.size();
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.RITUAL_TABLE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.RITUAL_TABLE_TYPE.get();
    }

    // Serializer class for data generation and network (de)serialization
    public static class Serializer implements RecipeSerializer<RitualTableRecipe> {
        public static final MapCodec<RitualTableRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                // List of ingredients, but allow 1 for now
                Ingredient.LIST_CODEC_NONEMPTY.fieldOf("ingredients")
                        .xmap(list -> {
                            NonNullList<Ingredient> nn = NonNullList.create();
                            nn.addAll(list);
                            return nn;
                        }, list -> list)
                        .forGetter(RitualTableRecipe::ingredients),
                // Output: match brazier style, decode just the item and wrap in ItemStack
                BuiltInRegistries.ITEM.byNameCodec().fieldOf("output").xmap(
                        ItemStack::new, ItemStack::getItem
                ).forGetter(RitualTableRecipe::output)
        ).apply(inst, RitualTableRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, RitualTableRecipe> STREAM_CODEC =
                StreamCodec.of(
                        // Writer
                        (buf, recipe) -> {
                            buf.writeVarInt(recipe.ingredients.size());
                            for (Ingredient ing : recipe.ingredients) {
                                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ing);
                            }
                            ItemStack.STREAM_CODEC.encode(buf, recipe.output);
                        },
                        // Reader
                        (buf) -> {
                            int count = buf.readVarInt();
                            NonNullList<Ingredient> list = NonNullList.withSize(count, Ingredient.EMPTY);
                            for (int i = 0; i < count; i++) {
                                list.set(i, Ingredient.CONTENTS_STREAM_CODEC.decode(buf));
                            }
                            ItemStack output = ItemStack.STREAM_CODEC.decode(buf);
                            return new RitualTableRecipe(list, output);
                        }
                );

        @Override
        public MapCodec<RitualTableRecipe> codec() {
            return CODEC;
        }
        @Override
        public StreamCodec<RegistryFriendlyByteBuf, RitualTableRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        // Read a RitualTableRecipe from the network buffer
        private static RitualTableRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            int count = buffer.readVarInt();
            NonNullList<Ingredient> ingredientsList = NonNullList.withSize(count, Ingredient.EMPTY);
            // Decode each Ingredient from the buffer
            ingredientsList.replaceAll(ing -> Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
            // Decode the output ItemStack from the buffer
            ItemStack outputStack = ItemStack.STREAM_CODEC.decode(buffer);
            // Create and return the recipe instance
            return new RitualTableRecipe(ingredientsList, outputStack);
        }

        // Write a RitualTableRecipe to the network buffer
        private static void toNetwork(RegistryFriendlyByteBuf buffer, RitualTableRecipe recipe) {
            // Write the number of ingredients first
            buffer.writeVarInt(recipe.ingredients.size());
            // Encode each Ingredient into the buffer
            for (Ingredient ing : recipe.ingredients) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, ing);
            }
            // Encode the output ItemStack into the buffer
            ItemStack.STREAM_CODEC.encode(buffer, recipe.output);
        }
    }
}
