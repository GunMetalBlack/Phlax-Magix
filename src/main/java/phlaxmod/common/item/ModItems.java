package phlaxmod.common.item;

import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import phlaxmod.ModMiscellaneousReg;
import phlaxmod.PhlaxMod;
import phlaxmod.common.block.ModBlocks;
import phlaxmod.tools.ItemPhlaxWand;
import phlaxmod.tools.ModItemTeir;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "phlaxmod");

    public static final RegistryObject<Item> CRIMSON_STONE = ITEMS.register("crimson_stone",
            () -> new BlockItem(ModBlocks.CRIMSON_STONE.get(), new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> CRIMSON_ROCK = ITEMS.register("crimson_rock",
            () -> new BlockItem(ModBlocks.CRIMSON_ROCK.get(), new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> CRYSTALIZER = ITEMS.register("crystallizer",
            () -> new BlockItem(ModBlocks.CRYSTALLIZER.get(), new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<BlockItem> CITRINE_ORE = ITEMS.register("citrine_ore", () -> new BlockItem(ModBlocks.CITRINE_CRYSTAL.get(), new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> CITRINE_CRYSTAL = ITEMS.register("citrine_crystal",
            () -> new Item(new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> HELIOTROPE_STEEL = ITEMS.register("heliotrope_steel",
            () -> new Item(new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> HELIOTROPE_LOGIC_CHIP = ITEMS.register("heliotrope_logic_chip",
            () -> new Item(new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> IRON_CONDENSED = ITEMS.register("iron_condensed",
            () -> new Item(new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> GOLD_CONDENSED = ITEMS.register("gold_condensed",
            () -> new Item(new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> CITRINE_CONDENSED = ITEMS.register("citrine_condensed",
            () -> new Item(new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    //A Mix Of Iron And Gold Condensed
    public static final RegistryObject<Item> ELECTRUM_ALLOY = ITEMS.register("electrum_alloy",
            () -> new Item(new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    //Blood Nickel Alloy a mix of nickel and redstone
    public static final RegistryObject<Item> BLOODNICKEL_ALLOY = ITEMS.register("bloodnickel_alloy",
            () -> new Item(new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    //Phlaxium alloy a mix of phlax and cithril
    public static final RegistryObject<Item> PHLAXIUM_ALLOY = ITEMS.register("phlaxium_alloy",
            () -> new Item(new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

   //Cithril alloy a mix of citrine and redstone
    public static final RegistryObject<Item> CITHRIL_ALLOY = ITEMS.register("cithril_alloy",
            () -> new Item(new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> INFUSED_MYSTICAL_MECHANISM = ITEMS.register("infused_mystical_mechanism",
            () -> new Item(new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));
    public static final RegistryObject<Item> COMPRESSED_PHLAX_FLUX = ITEMS.register("compressed_phlax_flux",
            () -> new Item(new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));
    public static final RegistryObject<Item> CHARGED_CITRINE = ITEMS.register("charged_citrine",
            () -> new Item(new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));
    public static final RegistryObject<Item> MANA_CRYSTAL = ITEMS.register("mana_crystal",
            () -> new Item(new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> MANA_INJECTOR = ITEMS.register("mana_injector",
            () -> new Item(new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> MITHRIL_ARC_DYNAMO = ITEMS.register("mithril_arc_dynamo",
            () -> new BlockItem(ModBlocks.MITHRIL_ARC_DYNAMO.get(), new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> ARCANE_CONDENSER = ITEMS.register("arcane_condenser",
            () -> new BlockItem(ModBlocks.ARCANE_CONDENSER.get(), new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));


    public static final RegistryObject<Item> MITHRIL_CONDENSED = ITEMS.register("mithril_condensed",
            () -> new Item(new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> MITHRIL_INGOT = ITEMS.register("mithril_ingot",
            () -> new Item(new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<BlockItem> MITHRIL_ORE = ITEMS.register("mithril_ore", () -> new BlockItem(ModBlocks.MITHRIL_ORE.get(), new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> MYSTICAL_MECHANISM = ITEMS.register("mystical_mechanism",
            () -> new Item(new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> NICKEL_CONDENSED = ITEMS.register("nickel_condensed",
            () -> new Item(new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> NICKEL_SHARD = ITEMS.register("nickel_shard",
            () -> new Item(new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> GOLD_SHARD = ITEMS.register("gold_shard",
            () -> new Item(new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> IRON_SHARD = ITEMS.register("iron_shard",
            () -> new Item(new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> MITHRIL_SHARD = ITEMS.register("mithril_shard",
            () -> new Item(new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> REDSTONE_SHARD = ITEMS.register("redstone_shard",
            () -> new Item(new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> PHLAX_SHARD = ITEMS.register("phlax_shard",
            () -> new Item(new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> NICKEL_INGOT = ITEMS.register("nickel_ingot",
            () -> new Item(new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<BlockItem> NICKEL_ORE = ITEMS.register("nickel_ore", () -> new BlockItem(ModBlocks.NICKEL_ORE.get(), new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<BucketItem> OIL_BUCKET = ITEMS.register("oil_bucket", () -> new BucketItem(() -> ModMiscellaneousReg.FLUID_OIL.get(), new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP).stacksTo(1)));

    public static final RegistryObject<Item> OIL_DROP = ITEMS.register("oil_drop",
            () -> new Item(new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<BlockItem> OIL_ORE = ITEMS.register("oil_ore", () -> new BlockItem(ModBlocks.OIL_ORE.get(), new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> PHLAX = ITEMS.register("phlax",
            () -> new Item(new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<AxeItem> PHLAX_AXE = ITEMS.register("phlax_axe",
            () -> new AxeItem(ModItemTeir.PHLAX, 1, 1f,
                    new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> PHLAX_CONDENSED = ITEMS.register("phlax_condensed",
            () -> new Item(new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<BlockItem> PHLAX_ORE = ITEMS.register("phlax_ore", () -> new BlockItem(ModBlocks.PHLAX_ORE.get(), new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<PickaxeItem> PHLAX_PICKAXE = ITEMS.register("phlax_pickaxe",
            () -> new PickaxeItem(ModItemTeir.PHLAX, -1, 3f,
                    new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<ShovelItem> PHLAX_SHOVEL = ITEMS.register("phlax_shovel",
            () -> new ShovelItem(ModItemTeir.PHLAX, -3, 1f,
                    new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<SwordItem> PHLAX_SWORD = ITEMS.register("phlax_sword",
            () -> new SwordItem(ModItemTeir.PHLAX, 8, 5f,
                    new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<ItemPhlaxWand> PHLAX_WAND = ITEMS.register("phlax_wand",
            () -> new ItemPhlaxWand(new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<ItemRawPhlaxFood> RAW_PHLAX_FLUX = ITEMS.register("raw_phlax_flux", () -> new ItemRawPhlaxFood(ModBlocks.PHLAX_FLUXCROP.get(), new Item.Properties()
            .tab(PhlaxMod.PHLAX_ITEM_GROUP).food(new Food.Builder().alwaysEat().nutrition(1).build())));

    public static final RegistryObject<Item> REDSTONE_CONDENSED = ITEMS.register("redstone_condensed",
            () -> new Item(new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<ItemOrbSpellSelector> ORB_SPELL_SELECTOR = ITEMS.register("orb_spell_selector",
            () -> new ItemOrbSpellSelector(new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<ItemPhlaxFlux> REFINED_PHLAX_FLUX = ITEMS.register("refined_phlax_flux",
            () -> new ItemPhlaxFlux(new Item.Properties()
                    .tab(PhlaxMod.PHLAX_ITEM_GROUP).food(new Food.Builder().alwaysEat().nutrition(5).build())));

    public static final RegistryObject<Item> RUBBER = ITEMS.register("rubber",
            () -> new Item(new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    public static final RegistryObject<Item> VACUUM_TUBE = ITEMS.register("vacuum_tube",
            () -> new Item(new Item.Properties().tab(PhlaxMod.PHLAX_ITEM_GROUP)));

    /**
     * This isn't used as an item - it is only registered to be able to easily use its model. May be removed at some point.
     */
    public static final RegistryObject<Item> WAND_PROJECTILE = ITEMS.register("wand_projectile",
            () -> new Item(new Item.Properties()));

}
