package phlaxmod.tools;


import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import phlaxmod.PhlaxMod;
import phlaxmod.common.capability.PhlaxModCapabilities;
import phlaxmod.common.capability.phlaxplayerdataholder.IPhlaxPlayerDataHolderCapability;
import phlaxmod.common.entities.EntityMagicOrb;

public class ItemPhlaxWand extends Item {

	public ItemPhlaxWand(Properties properties) {
		super(properties);
	}


	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		IPhlaxPlayerDataHolderCapability playerData = playerIn.getCapability(PhlaxModCapabilities.PLAYER_DATA_HOLDER_CAPABILITY).orElse(null);
		if (!worldIn.isClientSide )
		{
			if(playerData.getMana() > playerData.getCurrentSpell().manaCost)
			{
				if (playerData.getCurrentSpell().isProjectile) {
					EntityMagicOrb entityMagicOrb = new EntityMagicOrb(worldIn, playerIn);
					entityMagicOrb.moveTo(playerIn.getX(), playerIn.getY() + playerIn.getEyeHeight() * 0.8,
							playerIn.getZ(), playerIn.yRot, playerIn.xRot);
					entityMagicOrb.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, 0, 1, 1);
					worldIn.addFreshEntity(entityMagicOrb);
				} else {
					playerData.getCurrentSpell().startSpell();
				}
				playerData.removeMana(playerData.getCurrentSpell().manaCost);
			}
			else if(playerIn instanceof ServerPlayerEntity)
			{
				ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) playerIn;
				serverPlayerEntity.sendMessage(new TranslationTextComponent(PhlaxMod.MODID+".message.wand.mana_warn"), Util.NIL_UUID);
			}
		}
		return super.use(worldIn, playerIn, handIn);

	}

}
