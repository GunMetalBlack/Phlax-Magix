package phlaxmod.data.recipes;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import phlaxmod.PhlaxMod;

public class ModRecipeTypes {
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZER =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, PhlaxMod.MODID);

    public static final RegistryObject<CrystallizerRecipe.Serializer> CRYSTALLIZER_SERIALIZER
            = RECIPE_SERIALIZER.register("crystallizer", CrystallizerRecipe.Serializer::new);

    public static IRecipeType<CrystallizerRecipe> CRYSTALLIZER_RECIPE
            = new CrystallizerRecipe.CrystallizerRecipeType();


    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZER.register(eventBus);
        Registry.register(Registry.RECIPE_TYPE, CrystallizerRecipe.TYPE_ID, CRYSTALLIZER_RECIPE);
    }
}
