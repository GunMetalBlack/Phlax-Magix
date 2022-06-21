package phlaxmod;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.*;
import net.minecraft.item.Item.Properties;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeMaker;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import phlaxmod.common.block.*;
import phlaxmod.common.entities.PhlaxWandEntity;
import phlaxmod.common.item.ItemPhlaxFlux;
import phlaxmod.common.item.UnstableFluxFood;
import phlaxmod.tools.ItemPhlaxWand;
import phlaxmod.tools.ModItemTeir;

import java.util.function.Supplier;

public class DifReg {
    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "phlaxmod");
    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "phlaxmod");
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES,PhlaxMod.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES,
            "phlaxmod");
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister
            .create(ForgeRegistries.TILE_ENTITIES, "phlaxmod");
    public static final RegistryObject<EntityType<PhlaxWandEntity>> Phlax_Projectile = ENTITIES.register(
            "phlax_projectile",
            () -> EntityType.Builder.<PhlaxWandEntity>of(PhlaxWandEntity::new, EntityClassification.MISC)
                    .sized(0.25f, 0.25f).build("phlax_projectile"));
    public static DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, "phlaxmod");

    //FLUIDS-------------------------------------------------------------------------------------------------------------------------------------
    public static final ResourceLocation WATER_STILL_RL = new ResourceLocation("block/water_still");
    public static final ResourceLocation WATER_FLOWING_RL = new ResourceLocation("block/water_flow");
    public static final ResourceLocation WATER_OVERLAY_RL = new ResourceLocation("block/water_overlay");
    public static final RegistryObject<FlowingFluid> OIL = FLUIDS.register("oil", () -> new ForgeFlowingFluid.Source(DifReg.OIL_PROPERTIES));
    public static final RegistryObject<FlowingFluid> OIL_FLOWING = FLUIDS.register("flowing_oil", () -> new ForgeFlowingFluid.Flowing(DifReg.OIL_PROPERTIES));
    public static final ForgeFlowingFluid.Properties OIL_PROPERTIES = new ForgeFlowingFluid.Properties(
            () -> OIL.get(), () -> OIL_FLOWING.get(), FluidAttributes.builder(WATER_STILL_RL, WATER_FLOWING_RL)
            .density(3000).viscosity(6000).overlay(WATER_OVERLAY_RL)
            .color(0xFF111111)).slopeFindDistance(2).levelDecreasePerBlock(2)
            .block(() -> DifReg.OIL_BLOCK.get()).bucket(() -> DifReg.OIL_BUCKET.get());
    public static final RegistryObject<FlowingFluidBlock> OIL_BLOCK = BLOCKS.register(OIL.getId().getPath(), () -> new FlowingFluidBlock(() -> DifReg.OIL.get(), AbstractBlock.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops()));
    public static final RegistryObject<BucketItem> OIL_BUCKET = ITEMS.register("oil_bucket", () -> new BucketItem(() -> DifReg.OIL.get(), new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP).stacksTo(1)));
    //BIOMES----------------------------------------------------------------------------------------------------

    static {
        createBiome("phlax_biome",BiomeMaker::theVoidBiome);
    }
    public static RegistryKey<Biome> PHLAX_BIOME = registerBiome("phlax_biome");

    public static RegistryKey<Biome> registerBiome(String biomeName){
        return RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(PhlaxMod.MODID,biomeName));
    }
    public static RegistryObject<Biome> createBiome(String biomeName, Supplier<Biome> biome){
        return BIOMES.register(biomeName,biome);
    }
    public static void registeredBiomes(){
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(PHLAX_BIOME,10));
    }
    //BLOCKS--------------------------------------------------------------------------------------------------------
    public static final RegistryObject<PhlaxCropBlock> PHLAX_FLUXCROP = BLOCKS.register("raw_phlax_flux",
            () -> new PhlaxCropBlock(Block.Properties.of(Material.PLANT)
                    .strength(0f).noCollission().randomTicks().sound(SoundType.GRASS)));

    public static final RegistryObject<CrimsonStone> CRIMSON_STONE = BLOCKS.register("crimson_stone",
            () -> new CrimsonStone(AbstractBlock.Properties
                    .of(Material.CLAY, MaterialColor.COLOR_ORANGE).strength(5.0f, 3.0f)
                    .sound(SoundType.STONE).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).harvestLevel(0)));

    public static final RegistryObject<CrimsonRock> CRIMSON_ROCK = BLOCKS.register("crimson_rock",
            () -> new CrimsonRock(AbstractBlock.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK)
                    .strength(5.0f, 3.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()
                    .harvestTool(ToolType.PICKAXE).harvestLevel(0)));
    //Machine BLOCKS-----------------------------------------------------------------------------------------
    public static final RegistryObject<Block> CRYSTALLIZER = BLOCKS.register("crystallizer", CrystallizerBlock::new);
    // BLOCK ITEMS------------------------------------------------------------------------------------------------

    public static final RegistryObject<Item> CRIMSON_STONE_ITEM = ITEMS.register("crimson_stone",
            () -> new BlockItem(CRIMSON_STONE.get(), new Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> CRIMSON_ROCK_ITEM = ITEMS.register("crimson_rock",
            () -> new BlockItem(CRIMSON_ROCK.get(), new Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));
    public static final RegistryObject<Item>  CRYSTALIZER_ITEM = ITEMS.register("crystallizer",
            () -> new BlockItem(CRYSTALLIZER.get(), new Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));


    //ORES BLOCKS---------------------------------------------------------------------------------------------------------------------------------------------
    public static final RegistryObject<PhlaxOre> PHLAX_ORE = BLOCKS.register("phlax_ore",
            () -> new PhlaxOre(AbstractBlock.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK)
                    .strength(5.0f, 3.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()
                    .harvestTool(ToolType.PICKAXE).harvestLevel(3)));
    public static final RegistryObject<CitrineOre> CITRINE_CRYSTAL = BLOCKS.register("citrine_ore", CitrineOre::new);
    public static final RegistryObject<Block> NICKEL_ORE = BLOCKS.register("nickel_ore", () -> new Block(AbstractBlock.Properties.of(Material.METAL, MaterialColor.COLOR_GRAY).strength(5).sound(SoundType.NETHER_BRICKS).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).harvestLevel(2)));

    public static final RegistryObject<Block> OIL_ORE = BLOCKS.register("oil_ore",
            () -> new Block(AbstractBlock.Properties
                    .of(Material.METAL, MaterialColor.COLOR_GRAY).strength(1).sound(SoundType.SLIME_BLOCK)
                    .requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).harvestLevel(2)));
    public static final RegistryObject<Block> MITHRIL_ORE = BLOCKS.register("mithril_ore", () -> new Block(AbstractBlock.Properties.of(Material.METAL, MaterialColor.COLOR_BLACK).strength(12).sound(SoundType.ANCIENT_DEBRIS).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).harvestLevel(3)));
    //ORE BLOCKS ITEMS------------------------------------------------------------------------------------------------------------------------------------------
    public static RegistryObject<BlockItem> PHLAX_ORE_ITEM = ITEMS.register("phlax_ore", () -> new BlockItem(PHLAX_ORE.get(), new Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));
    public static RegistryObject<BlockItem> CITRINE_CRYSTAL_ITEM = ITEMS.register("citrine_ore", () -> new BlockItem(CITRINE_CRYSTAL.get(), new Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));
    public static RegistryObject<BlockItem> NICKEL_ORE_ITEM = ITEMS.register("nickel_ore", () -> new BlockItem(NICKEL_ORE.get(), new Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));
    public static RegistryObject<BlockItem> MITHRIL_ORE_ITEM = ITEMS.register("mithril_ore", () -> new BlockItem(MITHRIL_ORE.get(), new Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));
    public static RegistryObject<BlockItem> OIL_ORE_ITEM = ITEMS.register("oil_ore", () -> new BlockItem(OIL_ORE.get(), new Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    //ITEMS-------------------------------------------------------------------------------------------------------------------------------------------------
    public static final RegistryObject<Item> OIL_DROP = ITEMS.register("oil_drop",
            () -> new Item(new Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> MITHRAL_INGOT = ITEMS.register("mithril_ingot",
            () ->new Item(new Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> NICKLE_INGOT = ITEMS.register("nickel_ingot",
            () ->new Item(new Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> PHLAX = ITEMS.register("phlax",
            () -> new Item(new Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> PHLAX_CONDENSED = ITEMS.register("condensed_phlax",
            () -> new Item(new Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> CITRINE_ITEM = ITEMS.register("citrine_crystal",
            () -> new Item(new Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));
    public static final RegistryObject<UnstableFluxFood> RAW_PHLAX_FLUX = ITEMS.register("raw_phlax_flux", () -> new UnstableFluxFood(PHLAX_FLUXCROP.get(), new Properties()
            .tab(PhlaxMod.PHLAX_ITEM_GROUP).food(new Food.Builder().alwaysEat().nutrition(1).build())));

    public static final RegistryObject<ItemPhlaxFlux> PHLAX_FLUXCROP_ITEM = ITEMS.register("refined_phlax_flux",
            () -> new ItemPhlaxFlux(new Properties()
                    .tab(PhlaxMod.PHLAX_ITEM_GROUP).food(new Food.Builder().alwaysEat().nutrition(5).build())));
    //Components_items--------------------------------------------------------------------------------------------
    public static final RegistryObject<Item> HELIOTROPE_STEEL = ITEMS.register("heliotrope_steel",
            () -> new Item(new Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> VACUUM_TUBE = ITEMS.register("vacuum_tube",
            () -> new Item(new Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> HELIOTROPE_LOGIC_CHIP = ITEMS.register("heliotrope_logic_chip",
            () -> new Item(new Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> IRON_CONDENSED = ITEMS.register("iron_condensed",
            () -> new Item(new Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> GOLD_CONDENSED = ITEMS.register("gold_condensed",
            () -> new Item(new Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> NICKEL_CONDENSED = ITEMS.register("nickel_condensed",
            () -> new Item(new Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> REDSTONE_CONDENSED = ITEMS.register("redstone_condensed",
            () -> new Item(new Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> MITHRIL_CONDENSED = ITEMS.register("mithril_condensed",
            () -> new Item(new Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> CITRINE_CONDENSED = ITEMS.register("citrine_condensed",
            () -> new Item(new Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> INFUSED_IRON_CONDENSED = ITEMS.register("infused_iron_condensed",
            () -> new Item(new Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> INFUSED_GOLD_CONDENSED = ITEMS.register("infused_gold_condensed",
            () -> new Item(new Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> INFUSED_NICKEL_CONDENSED = ITEMS.register("infused_nickel_condensed",
            () -> new Item(new Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> INFUSED_REDSTONE_CONDENSED = ITEMS.register("infused_redstone_condensed",
            () -> new Item(new Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> INFUSED_MITHRIL_CONDENSED = ITEMS.register("infused_mithril_condensed",
            () -> new Item(new Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> INFUSED_CITRINE_CONDENSED = ITEMS.register("infused_citrine_condensed",
            () -> new Item(new Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));


    public static final RegistryObject<Item> RUBBER = ITEMS.register("rubber",
            () -> new Item(new Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> MANA_CRYSTAL = ITEMS.register("mana_crystal",
            () -> new Item(new Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> MYSTICAL_MECHANISM = ITEMS.register("mystical_mechanism",
            () -> new Item(new Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> INFUSED_MYSTICAL_MECHANISM = ITEMS.register("infused_mystical_mechanism",
            () -> new Item(new Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> COMPRESSED_PHLAX_FLUX = ITEMS.register("compressed_phlax_flux",
            () -> new Item(new Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> MANA_INJECTOR = ITEMS.register("mana_injector",
            () -> new Item(new Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));


    public static final RegistryObject<Item> CHARGED_CITRINE = ITEMS.register("charged_citrine",
            () -> new Item(new Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    /*
    SUB: ITEMS***********************************************
    Components(Vacuum Tube, Heliotrope logic chip,Heliotrope Steel, Material Plates(Like ingots getting turned into a plate)
    ,Arcane Machine Chassis
    ,Machine Chassis
    , rubber(Made from oil drop and charcoal),
    Mana Crystals,Mystical Mechanism, Infused Mystical Mechanism, Compressed Phlax Flux,
     (Phlax Bulb aka Mana Injector), Compressed Mithril, Infused Mithril,Charged Citrine);
    CAN BE INFUSED(All Material Plates, Mithril)

    */
   //Tools--------------------------------------------------------------------------------------------------

    public static final RegistryObject<ItemPhlaxWand> PHLAX_WAND = ITEMS.register("phlax_wand",
           () -> new ItemPhlaxWand(new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<AxeItem> PHLAX_AXE = ITEMS.register("phlax_axe",
            () -> new AxeItem(ModItemTeir.PHLAX, 1, 1f,
                    new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<ShovelItem> PHLAX_SHOVEL = ITEMS.register("phlax_shovel",
            () -> new ShovelItem(ModItemTeir.PHLAX, -3, 1f,
                    new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<SwordItem> PHLAX_SWORD = ITEMS.register("phlax_sword",
            () -> new SwordItem(ModItemTeir.PHLAX, 8, 5f,
                    new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<PickaxeItem> PHLAX_PICKAXE = ITEMS.register("phlax_pickaxe",
            () -> new PickaxeItem(ModItemTeir.PHLAX, -1, 3f,
                    new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    //Spells
    public static final RegistryObject<Item> WAND_PROJECTILE = ITEMS.register("wand_projectile",
           () -> new Item(new Properties()));

}
