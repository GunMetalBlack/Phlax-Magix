package phlaxmod;

import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeMaker;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import phlaxmod.common.block.ModBlocks;
import phlaxmod.common.item.ModItems;

public class ModMiscellaneousReg {
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES,PhlaxMod.MODID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, "phlaxmod");

    //BIOMES-------------------------------------------------------------------------------------------------------------------------------------
    public static final RegistryObject<Biome> PHLAX_BIOME = BIOMES.register("phlax_biome", BiomeMaker::theVoidBiome);
    public static final RegistryKey<Biome> PHLAX_BIOME_REGISTRY_KEY = RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(PhlaxMod.MODID, "phlax_biome"));
    public static void addBiomesToBiomeManager(){
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(PHLAX_BIOME_REGISTRY_KEY,10));
    }

    //FLUIDS-------------------------------------------------------------------------------------------------------------------------------------
    public static final ResourceLocation WATER_STILL_RL = new ResourceLocation("block/water_still");
    public static final ResourceLocation WATER_FLOWING_RL = new ResourceLocation("block/water_flow");
    public static final ResourceLocation WATER_OVERLAY_RL = new ResourceLocation("block/water_overlay");
    public static final RegistryObject<FlowingFluid> FLUID_OIL = FLUIDS.register("oil", () -> new ForgeFlowingFluid.Source(ModMiscellaneousReg.OIL_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLUID_OIL_FLOWING = FLUIDS.register("flowing_oil", () -> new ForgeFlowingFluid.Flowing(ModMiscellaneousReg.OIL_PROPERTIES));
    public static final ForgeFlowingFluid.Properties OIL_PROPERTIES = new ForgeFlowingFluid.Properties(
            () -> FLUID_OIL.get(), () -> FLUID_OIL_FLOWING.get(), FluidAttributes.builder(WATER_STILL_RL, WATER_FLOWING_RL)
            .density(3000).viscosity(6000).overlay(WATER_OVERLAY_RL)
            .color(0xFF111111)).slopeFindDistance(2).levelDecreasePerBlock(2)
            .block(() -> ModBlocks.OIL.get()).bucket(() -> ModItems.OIL_BUCKET.get());

}
