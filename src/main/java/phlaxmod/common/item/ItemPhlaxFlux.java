package phlaxmod.common.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import phlaxmod.common.capability.PhlaxModCapabilities;
import phlaxmod.common.capability.phlaxplayerdataholder.IPhlaxPlayerDataHolderCapability;

public class ItemPhlaxFlux extends Item {

	public ItemPhlaxFlux(Properties properties) {
		super(properties);
	}
	

	@Override
		public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
			IPhlaxPlayerDataHolderCapability playerData = entityLiving.getCapability(PhlaxModCapabilities.PLAYER_DATA_HOLDER_CAPABILITY).orElse(null);
			if(playerData != null){
				playerData.setShouldRegenMana(true);
				playerData.setManaRegenRate(10);
			}
			return super.onItemUseFinish(stack, worldIn, entityLiving);
		}

}
