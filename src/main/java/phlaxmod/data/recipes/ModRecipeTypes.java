package phlaxmod.data.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import phlaxmod.PhlaxMod;
import phlaxmod.common.block.ModBlocks;

public class ModRecipeTypes {
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, PhlaxMod.MODID);

    // IDs (Used in multiple places)
    public static final ResourceLocation CRYSTALLIZER_RECIPE_ID = new ResourceLocation(PhlaxMod.MODID, "crystallizer");

    // Serializer Registry Objects (Registered through Forge)
    public static final RegistryObject<PhlaxModBaseRecipe.Serializer> CRYSTALLIZER_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register(CRYSTALLIZER_RECIPE_ID.getPath(), () -> new PhlaxModBaseRecipe.Serializer(() -> new ItemStack(ModBlocks.CRYSTALLIZER.get()), CRYSTALLIZER_RECIPE_ID));

    // Recipe Types (Registered through Vanilla)
    public static IRecipeType<PhlaxModBaseRecipe> CRYSTALLIZER_RECIPE_TYPE = new PhlaxModBaseRecipe.RecipeType(CRYSTALLIZER_RECIPE_ID);

    // Called from Mod Constructor
    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
        Registry.register(Registry.RECIPE_TYPE, CRYSTALLIZER_RECIPE_ID, CRYSTALLIZER_RECIPE_TYPE);
    }
}
