package phlaxmod.common.item;


import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class UnstableFluxFood extends Item{

	public UnstableFluxFood(Properties properties) {
		super(properties);
	}
//TODO REMOVE AT SOME POINT
	//@Override
	//public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
	//	MagicManager.CanUseUnstable = true;
	//	MagicManager.MagicController();
	//	MagicManager.Mana += 5;
	//	return super.onItemUseFinish(stack, worldIn, entityLiving);
	//}

}
