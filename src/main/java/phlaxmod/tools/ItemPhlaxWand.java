package phlaxmod.tools;


import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import phlaxmod.common.capability.PhlaxModCapabilities;
import phlaxmod.common.capability.phlaxplayerdataholder.IPhlaxPlayerDataHolderCapability;
import phlaxmod.common.entities.PhlaxWandEntity;

public class ItemPhlaxWand extends Item {

	public ItemPhlaxWand(Properties properties) {
		super(properties);
	}


	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		IPhlaxPlayerDataHolderCapability playerData = playerIn.getCapability(PhlaxModCapabilities.PLAYER_DATA_HOLDER_CAPABILITY).orElse(null);
		if (!worldIn.isRemote && playerData.getMana() > 0) {
			PhlaxWandEntity phlaxen = new PhlaxWandEntity(worldIn);
			phlaxen.setLocationAndAngles(playerIn.getPosX(), playerIn.getPosY() + playerIn.getEyeHeight() * 0.8,
					playerIn.getPosZ(), playerIn.rotationYaw, playerIn.rotationPitch);
			phlaxen.func_234612_a_(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0, 1, 1);
			worldIn.addEntity(phlaxen);
			phlaxen.player = playerIn;
			playerData.removeMana(10f);
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);

	}

}
