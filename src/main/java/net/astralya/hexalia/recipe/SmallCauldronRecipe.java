package net.astralya.hexalia.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.astralya.hexalia.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;

public class SmallCauldronRecipe implements Recipe<RecipeWrapper> {

    public static final int INPUT_SLOTS = 3;

    private final NonNullList<Ingredient> ingredients;
    private final ItemStack output;
    private final Ingredient bottleSlot;
    private final float experience;
    private final int brewTime;

    public SmallCauldronRecipe(NonNullList<Ingredient> ingredients, Ingredient bottleSlot, ItemStack output, float experience, int brewTime) {
        this.ingredients = ingredients;
        this.bottleSlot = bottleSlot;
        this.output = output;
        this.experience = experience;
        this.brewTime = brewTime;
    }

    public NonNullList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public Ingredient getBottleSlot() {
        return this.bottleSlot;
    }

    public float getExperience() {
        return this.experience;
    }

    public int getBrewTime() {
        return this.brewTime;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return this.output;
    }

    @Override
    public boolean matches(RecipeWrapper inv, Level level) {
        int inputsCount = 0;
        for (int j = 0; j < 3; j++) {
            if (!inv.getItem(j).isEmpty()) inputsCount++;
        }
        if (inputsCount != this.ingredients.size()) {
            return false;
        }

        boolean[] slotUsed = new boolean[3];
        for (Ingredient ingredient : ingredients) {
            boolean found = false;
            for (int j = 0; j < 3; j++) {
                if (!slotUsed[j] && ingredient.test(inv.getItem(j))) {
                    slotUsed[j] = true;
                    found = true;
                    break;
                }
            }
            if (!found) return false;
        }

        ItemStack bottleInSlot = inv.getItem(4);
        return bottleSlot.test(bottleInSlot);
    }

    @Override
    public ItemStack assemble(RecipeWrapper inv, HolderLookup.Provider provider) {
        return this.output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= this.ingredients.size();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.SMALL_CAULDRON_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.SMALL_CAULDRON_TYPE.get();
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(ModItems.SMALL_CAULDRON.get());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SmallCauldronRecipe that = (SmallCauldronRecipe) o;

        if (Float.compare(that.getExperience(), getExperience()) != 0) return false;
        if (getBrewTime() != that.getBrewTime()) return false;
        if (!getGroup().equals(that.getGroup())) return false;
        if (!ingredients.equals(that.ingredients)) return false;
        if (!output.equals(that.output)) return false;
        return bottleSlot.equals(that.bottleSlot);
    }

    @Override
    public int hashCode() {
        int result = getGroup().hashCode();
        result = 31 * result + ingredients.hashCode();
        result = 31 * result + output.hashCode();
        result = 31 * result + bottleSlot.hashCode();
        result = 31 * result + (getExperience() != 0.0f ? Float.floatToIntBits(getExperience()) : 0);
        result = 31 * result + getBrewTime();
        return result;
    }

    public static class Serializer implements RecipeSerializer<SmallCauldronRecipe>
    {
        private static final MapCodec<SmallCauldronRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.LIST_CODEC_NONEMPTY.fieldOf("ingredients").xmap(ingredients -> {
                    NonNullList<Ingredient> nonNullList = NonNullList.create();
                    nonNullList.addAll(ingredients);
                    return nonNullList;
                }, ingredients -> ingredients).forGetter(SmallCauldronRecipe::getIngredients),
                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(r -> r.output),
                Ingredient.CODEC.fieldOf("bottle_slot").forGetter(SmallCauldronRecipe::getBottleSlot),
                Codec.FLOAT.optionalFieldOf("experience", 0.0F).forGetter(SmallCauldronRecipe::getExperience),
                Codec.INT.optionalFieldOf("brewtime", 200).forGetter(SmallCauldronRecipe::getBrewTime)
        ).apply(inst, (ingredients, output, bottle, experience, time) ->
                new SmallCauldronRecipe(ingredients, bottle, output, experience, time)
        ));


        public static final StreamCodec<RegistryFriendlyByteBuf, SmallCauldronRecipe> STREAM_CODEC = StreamCodec.of(SmallCauldronRecipe.Serializer::toNetwork, SmallCauldronRecipe.Serializer::fromNetwork);

        public Serializer() {
        }

        @Override
        public MapCodec<SmallCauldronRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, SmallCauldronRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static SmallCauldronRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            int i = buffer.readVarInt();
            NonNullList<Ingredient> inputItemsIn = NonNullList.withSize(i, Ingredient.EMPTY);

            inputItemsIn.replaceAll(ignored -> Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));

            ItemStack outputIn = ItemStack.STREAM_CODEC.decode(buffer);
            Ingredient bottleSlot = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            float experienceerienceIn = buffer.readFloat();
            int brewTimeIn = buffer.readVarInt();
            return new SmallCauldronRecipe(inputItemsIn, bottleSlot, outputIn, experienceerienceIn, brewTimeIn);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, SmallCauldronRecipe recipe) {
            buffer.writeVarInt(recipe.ingredients.size());

            for (Ingredient ingredient : recipe.ingredients) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, ingredient);
            }

            ItemStack.STREAM_CODEC.encode(buffer, recipe.output);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.bottleSlot);
            buffer.writeFloat(recipe.experience);
            buffer.writeVarInt(recipe.brewTime);
        }
    }
}
