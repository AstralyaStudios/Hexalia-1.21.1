package net.astralya.hexalia.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.astralya.hexalia.item.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.List;
import java.util.Objects;

public class SmallCauldronRecipe implements Recipe<SmallCauldronRecipeInput> {

    public static final int INPUT_SLOTS = 3;

    private final DefaultedList<Ingredient> ingredients;
    private final ItemStack output;
    private final Ingredient bottleSlot;
    private final float experience;
    private final int brewTime;

    public SmallCauldronRecipe(DefaultedList<Ingredient> ingredients,
                               Ingredient bottleSlot,
                               ItemStack output,
                               float experience,
                               int brewTime) {
        this.ingredients = ingredients;
        this.bottleSlot = bottleSlot;
        this.output = output;
        this.experience = experience;
        this.brewTime = brewTime;
    }

    public DefaultedList<Ingredient> getIngredientList() { return ingredients; }
    public Ingredient getBottleSlot() { return bottleSlot; }
    public ItemStack getOutputStack() { return output; }
    public float getExperience() { return experience; }
    public int getBrewTime() { return brewTime; }

    @Override
    public boolean matches(SmallCauldronRecipeInput input, World world) {
        int inputsCount = 0;
        for (int j = 0; j < 3; j++) {
            if (!input.getStackInSlot(j).isEmpty()) inputsCount++;
        }
        if (inputsCount != this.ingredients.size()) return false;

        boolean[] used = new boolean[3];
        for (Ingredient ing : ingredients) {
            boolean found = false;
            for (int j = 0; j < 3; j++) {
                if (!used[j] && ing.test(input.getStackInSlot(j))) {
                    used[j] = true;
                    found = true;
                    break;
                }
            }
            if (!found) return false;
        }

        ItemStack bottle = input.getStackInSlot(3);
        return bottleSlot.test(bottle);
    }

    @Override
    public ItemStack craft(SmallCauldronRecipeInput input, RegistryWrapper.WrapperLookup registries) {
        return output.copy();
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registries) {
        return output;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= this.ingredients.size();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.SMALL_CAULDRON_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.SMALL_CAULDRON_TYPE;
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ModItems.SMALL_CAULDRON);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SmallCauldronRecipe that)) return false;
        return Float.compare(that.getExperience(), getExperience()) == 0
                && getBrewTime() == that.getBrewTime()
                && Objects.equals(getGroup(), that.getGroup())
                && ingredients.equals(that.ingredients)
                && output.equals(that.output)
                && bottleSlot.equals(that.bottleSlot);
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

    public static class Serializer implements RecipeSerializer<SmallCauldronRecipe> {
        private static DefaultedList<Ingredient> toDefaulted(List<Ingredient> list) {
            DefaultedList<Ingredient> dl = DefaultedList.ofSize(list.size(), Ingredient.EMPTY);
            for (int i = 0; i < list.size(); i++) dl.set(i, list.get(i));
            return dl;
        }

        private static final MapCodec<SmallCauldronRecipe> JSON_CODEC =
                RecordCodecBuilder.mapCodec(instance -> instance.group(
                        Ingredient.DISALLOW_EMPTY_CODEC.listOf()
                                .fieldOf("ingredients")
                                .xmap(Serializer::toDefaulted, list -> list)
                                .forGetter(SmallCauldronRecipe::getIngredientList),
                        ItemStack.CODEC
                                .fieldOf("result")
                                .forGetter(SmallCauldronRecipe::getOutputStack),
                        Ingredient.DISALLOW_EMPTY_CODEC
                                .fieldOf("bottle_slot")
                                .forGetter(SmallCauldronRecipe::getBottleSlot),
                        Codec.FLOAT.optionalFieldOf("experience", 0.0F)
                                .forGetter(SmallCauldronRecipe::getExperience),
                        Codec.INT.optionalFieldOf("brewtime", 200)
                                .forGetter(SmallCauldronRecipe::getBrewTime)
                ).apply(instance, (ings, result, bottle, xp, time) ->
                        new SmallCauldronRecipe(ings, bottle, result, xp, time)));

        private static final PacketCodec<RegistryByteBuf, SmallCauldronRecipe> NET_CODEC =
                new PacketCodec<>() {
                    @Override
                    public void encode(RegistryByteBuf buf, SmallCauldronRecipe value) {
                        buf.writeVarInt(value.ingredients.size());
                        for (Ingredient ing : value.ingredients) {
                            Ingredient.PACKET_CODEC.encode(buf, ing);
                        }
                        ItemStack.PACKET_CODEC.encode(buf, value.output);
                        Ingredient.PACKET_CODEC.encode(buf, value.bottleSlot);
                        buf.writeFloat(value.experience);
                        buf.writeVarInt(value.brewTime);
                    }

                    @Override
                    public SmallCauldronRecipe decode(RegistryByteBuf buf) {
                        int count = buf.readVarInt();
                        DefaultedList<Ingredient> ingreds = DefaultedList.ofSize(count, Ingredient.EMPTY);
                        for (int i = 0; i < count; i++) {
                            ingreds.set(i, Ingredient.PACKET_CODEC.decode(buf));
                        }
                        ItemStack out = ItemStack.PACKET_CODEC.decode(buf);
                        Ingredient bottle = Ingredient.PACKET_CODEC.decode(buf);
                        float xp = buf.readFloat();
                        int time = buf.readVarInt();
                        return new SmallCauldronRecipe(ingreds, bottle, out, xp, time);
                    }
                };

        @Override
        public MapCodec<SmallCauldronRecipe> codec() {
            return JSON_CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, SmallCauldronRecipe> packetCodec() {
            return NET_CODEC;
        }
    }
}
