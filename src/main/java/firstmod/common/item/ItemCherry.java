package firstmod.common.item;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemCherry extends Item{
@Override
public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
	
	return super.canApplyAtEnchantingTable(stack, enchantment);
}
	public ItemCherry(Properties properties) {
		super(properties);
		
	}

}
