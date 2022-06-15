package phlaxmod.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class OilDrop extends Item{

	public OilDrop(Properties properties) {
		super(properties);
		
	}
	@Override
	public int getBurnTime(ItemStack itemstack) {
		return 1300;
		
	}
}
