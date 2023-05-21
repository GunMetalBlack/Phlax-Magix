package phlaxmod.container.data.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class PhlaxModBaseRecipe implements IRecipe<IInventory> {

    Supplier<ItemStack> toastSymbolSupplier;
    IRecipeSerializer<?> serializer;

    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;
    public final int productTime;
    public PhlaxModBaseRecipe(Supplier<ItemStack> toastSymbolSupplier, IRecipeSerializer<?> serializer, ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItems, int productTime) {
        this.toastSymbolSupplier = toastSymbolSupplier;
        this.serializer = serializer;
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
        this.productTime = productTime;
    }

    @Override
    public IRecipeType<?> getType(){
        return Registry.RECIPE_TYPE.getOptional(getId()).get();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= recipeItems.size();
    }

    @Override
    public boolean matches(IInventory inventory, World level) {
        return recipeItems.get(0).test(inventory.getItem(0));
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }

    @Override
    public ItemStack getToastSymbol() {
        return toastSymbolSupplier.get();
    }

    @Override
    public ItemStack assemble(IInventory pInv) {
        return getResultItem().copy();
    }

    @Override
    public ItemStack getResultItem() {
        return output.copy();
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return serializer;
    }

    public static class RecipeType implements IRecipeType<PhlaxModBaseRecipe> {

        ResourceLocation id;

        RecipeType(ResourceLocation id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return id.toString();
        }

    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<PhlaxModBaseRecipe> {

        private final Supplier<ItemStack> toastSymbolSupplier;
        private final ResourceLocation recipeTypeId;

        public Serializer(Supplier<ItemStack> toastSymbolSupplier, ResourceLocation recipeTypeId) {
            this.toastSymbolSupplier = toastSymbolSupplier;
            this.recipeTypeId = recipeTypeId;
        }

        @Override
        public void toNetwork(PacketBuffer buffer, PhlaxModBaseRecipe recipe) {
            buffer.writeItemStack(recipe.getResultItem().copy(), false);
            buffer.writeInt(recipe.productTime);
            buffer.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buffer);
            }
        }

        @Nullable
        @Override
        public PhlaxModBaseRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
            ItemStack output = buffer.readItem();
            int productTime = buffer.readInt();
            int ingredientsSize = buffer.readInt();
            NonNullList<Ingredient> inputs = NonNullList.withSize(ingredientsSize, Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buffer));
            }
            return new PhlaxModBaseRecipe(toastSymbolSupplier, this, recipeTypeId, output, inputs, productTime);
        }

        @Override
        public PhlaxModBaseRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            ItemStack output = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(json, "output"));

            int productTime = JSONUtils.getAsInt(json,"producttime");

            JsonArray ingredients = JSONUtils.getAsJsonArray(json, "ingredient");
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new PhlaxModBaseRecipe(toastSymbolSupplier, this, recipeTypeId, output, inputs, productTime);
        }
    }
}
