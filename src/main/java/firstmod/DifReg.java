package firstmod;

import firstmod.common.block.PhlaxOre;
import firstmod.common.entities.spells.PhlaxWandEntitie;
import firstmod.common.item.ItemCherry;
import firstmod.tools.ItemPhlaxWand;
import firstmod.tools.ModItemTeir;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DifReg {
	public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "firstmod_123");
	public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "firstmod_123");
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, "firstmod_123");
	public static final RegistryObject<EntityType<PhlaxWandEntitie>> Phlax_Projectile = ENTITIES.register("phlax_projectile", () -> EntityType.Builder.<PhlaxWandEntitie>create(PhlaxWandEntitie::new, EntityClassification.MISC).size(0.25f,0.25f).build("phlax_projectile"));
	
	public static Item phlaxItem; 
	public static Item condensedphlaxItem;
	public static PhlaxOre phlaxOre; 
	public static Item phlaxsword; 
	public static Item phlaxpickaxe; 
	public static Item phlaxaxe; 
	public static Item phlaxshovel; 
	public static Item phlaxwand;
	public static Item wand_projectile;
	public static Item itemcherry;
	static {
		
		//Items
		ITEMS.register("cherry",() -> new ItemCherry(new Properties().group(FirstMod.PhlaxFixins_Group).food(new Food.Builder().setAlwaysEdible().hunger(5).build())));
		ITEMS.register("phlax",() -> phlaxItem = new Item(new Properties().group(FirstMod.PhlaxFixins_Group)));
		ITEMS.register("condensedphlax",() -> condensedphlaxItem = new Item(new Properties().group(FirstMod.PhlaxFixins_Group)));
		
		//Spells
		ITEMS.register("wand_projectile",() ->wand_projectile = new Item(new Properties()));
		
		//Item Tools
		ITEMS.register("phlax_axe",() -> phlaxaxe = new AxeItem(ModItemTeir.PHLAX, 1, 1f, new Item.Properties().group(FirstMod.PhlaxFixins_Group)));
		ITEMS.register("phlax_shovel",() -> phlaxshovel = new ShovelItem(ModItemTeir.PHLAX, -3, 1f, new Item.Properties().group(FirstMod.PhlaxFixins_Group)));
		ITEMS.register("phlax_sword",() -> phlaxsword = new SwordItem(ModItemTeir.PHLAX, 3, 5f, new Item.Properties().group(FirstMod.PhlaxFixins_Group)));
		ITEMS.register("phlax_pickaxe",() -> phlaxpickaxe = new PickaxeItem(ModItemTeir.PHLAX, -1, 3f, new Item.Properties().group(FirstMod.PhlaxFixins_Group)));
		ITEMS.register("phlax_wand",() -> phlaxwand = new ItemPhlaxWand( new Item.Properties().group(FirstMod.PhlaxFixins_Group)));
		//Blocks
		BLOCKS.register("phlaxore",() -> phlaxOre = new PhlaxOre(AbstractBlock.Properties.create(Material.ROCK,MaterialColor.BLACK).hardnessAndResistance(5.0f,3.0f).sound(SoundType.STONE).setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(3)));
		
		//Entities
	
		
		//Block Items
		ITEMS.register("phlaxore",() -> new BlockItem(phlaxOre, new Properties().group(FirstMod.PhlaxFixins_Group)));
	}
}
