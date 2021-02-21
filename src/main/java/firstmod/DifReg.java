package firstmod;

import firstmod.common.item.ItemCherry;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DifReg {

	public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "firstmod_123");
	static {
		ITEMS.register("cherry",() -> new ItemCherry(new Properties().group(ItemGroup.FOOD).food(new Food.Builder().hunger(5).build())));
	}
}
