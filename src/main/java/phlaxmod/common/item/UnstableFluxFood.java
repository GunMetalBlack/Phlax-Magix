package phlaxmod.common.item;


import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

public class UnstableFluxFood extends BlockItem {

	public UnstableFluxFood(Block block, Properties properties) {
		super(block, properties);
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
