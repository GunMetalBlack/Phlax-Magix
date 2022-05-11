package firstmod;

import firstmod.common.block.*;
import firstmod.common.entities.spells.PhlaxWandEntitie;
import firstmod.common.item.ItemCherry;
import firstmod.common.item.OilDrop;
import firstmod.common.item.UnstableFluxFood;
import firstmod.tools.ItemPhlaxWand;
import firstmod.tools.ModItemTeir;
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
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DifReg {
    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "firstmod_123");
    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "firstmod_123");
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES,
            "firstmod_123");
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister
            .create(ForgeRegistries.TILE_ENTITIES, "firstmod_123");
    public static final RegistryObject<EntityType<PhlaxWandEntitie>> Phlax_Projectile = ENTITIES.register(
            "phlax_projectile",
            () -> EntityType.Builder.<PhlaxWandEntitie>create(PhlaxWandEntitie::new, EntityClassification.MISC)
                    .size(0.25f, 0.25f).build("phlax_projectile"));
    public static DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, "firstmod_123");

    public static Item phlaxItem;
    public static Item condensedphlaxItem;
    public static Item phlaxsword;
    public static Item ruby_item;
    public static Item phlaxpickaxe;
    public static Item phlaxaxe;
    public static Item phlaxshovel;
    public static Item phlaxwand;
    public static Item wand_projectile;
    public static Item itemcherry;
    public static FlaxCropBlock phlax_fluxcrop;
    public static CrimsonStone crimson_stone;
    public static CrimsonRock crimson_rock;
    public static UnstableFluxFood unstablefluxfood;
    public static Item[] Oreitems;
    public static BlockItem[] Oreblockitems;

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
    public static final RegistryObject<FlowingFluidBlock> OIL_BLOCK = BLOCKS.register(OIL.getId().getPath(), () -> new FlowingFluidBlock(() -> DifReg.OIL.get(), AbstractBlock.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()));
    public static final RegistryObject<BucketItem> OIL_BUCKET = ITEMS.register("oil_bucket", () -> new BucketItem(() -> DifReg.OIL.get(), new Item.Properties().group(FirstMod.PhlaxFixins_Group).maxStackSize(1)));

    public static final RegistryObject<PhlaxOre> PHLAX_ORE = BLOCKS.register("phlaxore",
            () -> new PhlaxOre(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.BLACK)
                    .hardnessAndResistance(5.0f, 3.0f).sound(SoundType.STONE).setRequiresTool()
                    .harvestTool(ToolType.PICKAXE).harvestLevel(3)));
    public static final RegistryObject<RubyOre> RUBY_ORE = BLOCKS.register("ruby_ore", RubyOre::new);
    public static final RegistryObject<Block> NICKEL_ORE = BLOCKS.register("nickel_ore", () -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.GRAY).hardnessAndResistance(5).sound(SoundType.NETHER_BRICK).setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(2)));

    public static final RegistryObject<Block> OIL_ORE = BLOCKS.register("oil_ore",
            () -> new Block(AbstractBlock.Properties
                    .create(Material.IRON, MaterialColor.GRAY).hardnessAndResistance(1).sound(SoundType.SLIME)
                    .setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(2)));
    public static final RegistryObject<Block> MITHRAL_ORE = BLOCKS.register("mithral_ore", () -> new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.BLACK).hardnessAndResistance(12).sound(SoundType.ANCIENT_DEBRIS).setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(3)));

    static {

        Oreblockitems = new BlockItem[3];
        Oreitems = new Item[3];
        // [0] is nickel [1] is mithral
        // Items
        ITEMS.register("phlax", () -> phlaxItem = new Item(new Properties().group(FirstMod.PhlaxFixins_Group)));
        ITEMS.register("condensedphlax",
                () -> condensedphlaxItem = new Item(new Properties().group(FirstMod.PhlaxFixins_Group)));
        ITEMS.register("test_textures", () -> new Item(new Properties().group(FirstMod.PhlaxFixins_Group)));
        ITEMS.register("ruby_item", () -> ruby_item = new Item(new Properties().group(FirstMod.PhlaxFixins_Group)));
        ITEMS.register("oil_drop",
                () -> Oreitems[2] = new OilDrop(new Properties().group(FirstMod.PhlaxFixins_Group)));
        ITEMS.register("mithral_ingot",
                () -> Oreitems[1] = new Item(new Properties().group(FirstMod.PhlaxFixins_Group)));
        ITEMS.register("nickle_ingot",
                () -> Oreitems[0] = new Item(new Properties().group(FirstMod.PhlaxFixins_Group)));

        // Spells
        ITEMS.register("wand_projectile", () -> wand_projectile = new Item(new Properties()));

        // Item Tools
        ITEMS.register("phlax_axe", () -> phlaxaxe = new AxeItem(ModItemTeir.PHLAX, 1, 1f,
                new Item.Properties().group(FirstMod.PhlaxFixins_Group)));
        ITEMS.register("phlax_shovel", () -> phlaxshovel = new ShovelItem(ModItemTeir.PHLAX, -3, 1f,
                new Item.Properties().group(FirstMod.PhlaxFixins_Group)));
        ITEMS.register("phlax_sword", () -> phlaxsword = new SwordItem(ModItemTeir.PHLAX, 3, 5f,
                new Item.Properties().group(FirstMod.PhlaxFixins_Group)));
        ITEMS.register("phlax_pickaxe", () -> phlaxpickaxe = new PickaxeItem(ModItemTeir.PHLAX, -1, 3f,
                new Item.Properties().group(FirstMod.PhlaxFixins_Group)));
        ITEMS.register("phlax_wand",
                () -> phlaxwand = new ItemPhlaxWand(new Item.Properties().group(FirstMod.PhlaxFixins_Group)));
        // Blocks

        BLOCKS.register("phlax_fluxcrop",
                () -> phlax_fluxcrop = new FlaxCropBlock(Block.Properties.create(Material.PLANTS)
                        .hardnessAndResistance(0f).doesNotBlockMovement().tickRandomly().sound(SoundType.PLANT)));
        BLOCKS.register("crimson_stone",
                () -> crimson_stone = new CrimsonStone(AbstractBlock.Properties
                        .create(Material.CLAY, MaterialColor.ADOBE).hardnessAndResistance(5.0f, 3.0f)
                        .sound(SoundType.STONE).setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(0)));
        BLOCKS.register("crimson_rock",
                () -> crimson_rock = new CrimsonRock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.BLACK)
                        .hardnessAndResistance(5.0f, 3.0f).sound(SoundType.STONE).setRequiresTool()
                        .harvestTool(ToolType.PICKAXE).harvestLevel(0)));

        // Entities

        // FoodItems
        ITEMS.register("unstable_fluxfood", () -> unstablefluxfood = new UnstableFluxFood(new Properties()
                .group(FirstMod.PhlaxFixins_Group).food(new Food.Builder().setAlwaysEdible().hunger(1).build())));
        // Block Items
        ITEMS.register("phlax_fluxcrop", () -> itemcherry = new ItemCherry(phlax_fluxcrop, new Properties()
                .group(FirstMod.PhlaxFixins_Group).food(new Food.Builder().setAlwaysEdible().hunger(5).build())));
        ITEMS.register("crimson_stone",
                () -> new BlockItem(crimson_stone, new Properties().group(FirstMod.PhlaxFixins_Group)));
        ITEMS.register("crimson_rock",
                () -> new BlockItem(crimson_rock, new Properties().group(FirstMod.PhlaxFixins_Group)));
        // Ore BlockItems
        ITEMS.register("phlaxore", () -> new BlockItem(PHLAX_ORE.get(), new Properties().group(FirstMod.PhlaxFixins_Group)));
        ITEMS.register("ruby_ore", () -> new BlockItem(RUBY_ORE.get(), new Properties().group(FirstMod.PhlaxFixins_Group)));
        ITEMS.register("nickel_ore", () -> Oreblockitems[0] = new BlockItem(NICKEL_ORE.get(),
                new Properties().group(FirstMod.PhlaxFixins_Group)));
        ITEMS.register("mithral_ore", () -> Oreblockitems[1] = new BlockItem(MITHRAL_ORE.get(),
                new Properties().group(FirstMod.PhlaxFixins_Group)));
        ITEMS.register("oil_ore", () -> Oreblockitems[2] = new BlockItem(OIL_ORE.get(),
                new Properties().group(FirstMod.PhlaxFixins_Group)));

    }

}
