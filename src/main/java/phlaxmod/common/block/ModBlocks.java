package phlaxmod.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import phlaxmod.ModMiscellaneousReg;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "phlaxmod");

    public static final RegistryObject<BlockCitrineOre> CITRINE_CRYSTAL = BLOCKS.register("citrine_ore", BlockCitrineOre::new);

    public static final RegistryObject<Block> CRIMSON_ROCK = BLOCKS.register("crimson_rock",
            () -> new Block(AbstractBlock.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK)
                    .strength(5.0f, 3.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()
                    .harvestTool(ToolType.PICKAXE).harvestLevel(0)));

    public static final RegistryObject<Block> CRIMSON_STONE = BLOCKS.register("crimson_stone",
            () -> new Block(AbstractBlock.Properties
                    .of(Material.CLAY, MaterialColor.COLOR_ORANGE).strength(5.0f, 3.0f)
                    .sound(SoundType.STONE).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).harvestLevel(0)));
    public static final RegistryObject<Block> CRYSTALLIZER = BLOCKS.register("crystallizer", BlockCrystallizer::new);

    public static final RegistryObject<Block> MITHRIL_ARC_DYNAMO = BLOCKS.register("mithril_arc_dynamo", BlockMithrilArcDynamo::new);
    public static final RegistryObject<Block> ARCANE_CONDENSER = BLOCKS.register("arcane_condenser", BlockArcaneCondenser::new);

    public static final RegistryObject<Block> MITHRIL_ORE = BLOCKS.register("mithril_ore", () -> new Block(AbstractBlock.Properties.of(Material.METAL, MaterialColor.COLOR_BLACK).strength(12).sound(SoundType.ANCIENT_DEBRIS).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).harvestLevel(3)));

    public static final RegistryObject<Block> NICKEL_ORE = BLOCKS.register("nickel_ore", () -> new Block(AbstractBlock.Properties.of(Material.METAL, MaterialColor.COLOR_GRAY).strength(5).sound(SoundType.NETHER_BRICKS).requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).harvestLevel(2)));

    public static final RegistryObject<FlowingFluidBlock> OIL = BLOCKS.register(ModMiscellaneousReg.FLUID_OIL.getId().getPath(), () -> new FlowingFluidBlock(() -> ModMiscellaneousReg.FLUID_OIL.get(), AbstractBlock.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops()));

    public static final RegistryObject<Block> OIL_ORE = BLOCKS.register("oil_ore",
            () -> new Block(AbstractBlock.Properties
                    .of(Material.METAL, MaterialColor.COLOR_GRAY).strength(1).sound(SoundType.SLIME_BLOCK)
                    .requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).harvestLevel(2)));

    public static final RegistryObject<BlockPhlaxCrop> PHLAX_FLUXCROP = BLOCKS.register("raw_phlax_flux",
            () -> new BlockPhlaxCrop(Block.Properties.of(Material.PLANT)
                    .strength(0f).noCollission().randomTicks().sound(SoundType.GRASS)));

    public static final RegistryObject<Block> PHLAX_ORE = BLOCKS.register("phlax_ore",
            () -> new Block(AbstractBlock.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK)
                    .strength(5.0f, 3.0f).sound(SoundType.STONE).requiresCorrectToolForDrops()
                    .harvestTool(ToolType.PICKAXE).harvestLevel(3)));

}
