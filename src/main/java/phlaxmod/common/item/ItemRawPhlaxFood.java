package phlaxmod.common.item;


import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import phlaxmod.common.capability.PhlaxModCapabilities;
import phlaxmod.common.capability.phlaxplayerdataholder.IPhlaxPlayerDataHolderCapability;

public class ItemRawPhlaxFood extends BlockItem {

	public ItemRawPhlaxFood(Block block, Properties properties) {
		super(block, properties);
	}
	@Override
	public ItemStack finishUsingItem(ItemStack stack, World worldIn, LivingEntity entityLiving) {
		IPhlaxPlayerDataHolderCapability playerData = entityLiving.getCapability(PhlaxModCapabilities.PLAYER_DATA_HOLDER_CAPABILITY).orElse(null);
		if(playerData != null){
			playerData.setShouldRegenMana(true);
			playerData.setManaRegenRate(15);
		}
		return super.finishUsingItem(stack, worldIn, entityLiving);
	}
}

