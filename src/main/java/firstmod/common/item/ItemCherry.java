package firstmod.common.item;

import firstmod.common.MagicManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemCherry extends Item{
@Override
public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
	
	return super.canApplyAtEnchantingTable(stack, enchantment);
}
	public ItemCherry(Properties properties) {
		super(properties);
		
	}
	
	
	@Override
		public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
		
			MagicManager.Mana += 5f;
			return super.onItemUseFinish(stack, worldIn, entityLiving);
		}

}
