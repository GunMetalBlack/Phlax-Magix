package firstmod;

import firstmod.common.block.PhlaxOre;
import firstmod.common.item.ItemCherry;
import firstmod.tools.ModItemTeir;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DifReg {
	public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "firstmod_123");
	public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "firstmod_123");
	public static Item phlaxItem; 
	public static Item condensedphlaxItem;
	public static PhlaxOre phlaxOre; 
	public static Item phlaxsword; 
	public static Item phlaxpickaxe; 
	public static Item phlaxaxe; 
	public static Item phlaxshovel; 
	static {
		
		//Items
		ITEMS.register("cherry",() -> new ItemCherry(new Properties().group(FirstMod.PhlaxFixins_Group).food(new Food.Builder().hunger(5).build())));
		ITEMS.register("phlax",() -> phlaxItem = new Item(new Properties().group(FirstMod.PhlaxFixins_Group)));
		ITEMS.register("condensedphlax",() -> condensedphlaxItem = new Item(new Properties().group(FirstMod.PhlaxFixins_Group)));
		
		//Item Tools
		ITEMS.register("phlax_axe",() -> phlaxaxe = new AxeItem(ModItemTeir.PHLAX, 1, 1f, new Item.Properties().group(FirstMod.PhlaxFixins_Group)));
		ITEMS.register("phlax_shovel",() -> phlaxshovel = new ShovelItem(ModItemTeir.PHLAX, -3, -3f, new Item.Properties().group(FirstMod.PhlaxFixins_Group)));
		ITEMS.register("phlax_sword",() -> phlaxsword = new SwordItem(ModItemTeir.PHLAX, 3, -3f, new Item.Properties().group(FirstMod.PhlaxFixins_Group)));
		ITEMS.register("phlax_pickaxe",() -> phlaxpickaxe = new PickaxeItem(ModItemTeir.PHLAX, -1, 3f, new Item.Properties().group(FirstMod.PhlaxFixins_Group)));
		//Blocks
		BLOCKS.register("phlaxore",() -> phlaxOre = new PhlaxOre(AbstractBlock.Properties.create(Material.ROCK,MaterialColor.BLACK).hardnessAndResistance(5.0f,3.0f).sound(SoundType.STONE).setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(3)));
		
		
		//Block Items
		ITEMS.register("phlaxore",() -> new BlockItem(phlaxOre, new Properties().group(FirstMod.PhlaxFixins_Group)));
	}
}
