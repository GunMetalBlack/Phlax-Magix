package firstmod.tools;

import firstmod.FirstMod;
import firstmod.common.entities.PhlaxWandEntitie;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ItemPhlaxWand extends Item {
	
	public ItemPhlaxWand(Properties properties) {
		
		super(properties);
		
		
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack stack = PhlaxWandEntitie.nstack;
		if(!worldIn.isRemote) 
		{
			PhlaxWandEntitie phlaxen = new PhlaxWandEntitie(worldIn);
			
			phlaxen.setLocationAndAngles(playerIn.getPosX(), playerIn.getPosY() + playerIn.getEyeHeight() * 0.8, playerIn.getPosZ(), playerIn.rotationYaw, playerIn.rotationPitch);
			phlaxen.func_234612_a_(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0, 1, 1);
			worldIn.addEntity(phlaxen);
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
		
	}
	
}
