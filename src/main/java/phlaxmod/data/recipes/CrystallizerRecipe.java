package phlaxmod.data.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.realmsclient.util.JsonUtils;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;
import phlaxmod.PhlaxMod;
import phlaxmod.common.block.ModBlocks;

import javax.annotation.Nullable;

public class CrystallizerRecipe implements ICrystallizerRecipe{

    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;

    public final int productTime;
    public CrystallizerRecipe(ResourceLocation id, ItemStack output,
                                    NonNullList<Ingredient> recipeItems, int productTime) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
        this.productTime = productTime;
        PhlaxMod.logger.debug("IS THIS THING MOVEING!!!!");
    }

    @Override
    public boolean matches(IInventory pInv, World pLevel) {
        // Checks for correct focus (Glass Pane)
        if(recipeItems.get(0).test(pInv.getItem(0))) {
            return recipeItems.get(1).test(pInv.getItem(1));
        }

        return false;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }
    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(ModBlocks.CRYSTALLIZER.get());
    }

    @Override
    public ItemStack assemble(IInventory pInv) {
        return null;
    }

    @Override
    public ItemStack getResultItem() {
        return output;
    }

    @Override
    public ResourceLocation getId() {
        return null;
    }

    public ItemStack getIcon() {
        return new ItemStack(ModBlocks.CRYSTALLIZER.get());
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeTypes.CRYSTALLIZER_SERIALIZER.get();
    }

    public static class CrystallizerRecipeType implements IRecipeType<CrystallizerRecipe> {
        @Override
        public String toString() {
            return CrystallizerRecipe.TYPE_ID.toString();
        }
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>>
            implements IRecipeSerializer<CrystallizerRecipe> {

        @Override
        public CrystallizerRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            ItemStack output = ShapedRecipe.itemFromJson(JSONUtils.convertToJsonObject(json, "output"));

            int productTime = JSONUtils.convertToJsonObject(json,"producttime").getAsInt();

            JsonArray ingredients = JSONUtils.convertToJsonArray(json, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(2, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new CrystallizerRecipe(recipeId, output, inputs,productTime);
        }

        @Nullable
        @Override
        public CrystallizerRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(2, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buffer));
            }

            ItemStack output = buffer.readItem();
            int productTime = buffer.readInt();
            return new CrystallizerRecipe(recipeId, output, inputs,productTime);
        }

        @Override
        public void toNetwork(PacketBuffer buffer, CrystallizerRecipe recipe) {
            buffer.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buffer);
            }
            buffer.writeItemStack(recipe.getResultItem(), false);
            buffer.writeInt(recipe.productTime);
        }
    }
}
