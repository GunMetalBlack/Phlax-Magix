package phlaxmod.common.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import phlaxmod.common.capability.PhlaxModCapabilities;
import phlaxmod.common.capability.phlaxplayerdataholder.IPhlaxPlayerDataHolderCapability;

public class ItemOrbSpellSelector extends Item {

    public ItemOrbSpellSelector(Properties pProperties) {
        super(pProperties);
    }
    @Override
    public ActionResult<ItemStack> use(World pLevel, PlayerEntity pPlayer, Hand pHand)
    {
        if(!pLevel.isClientSide()) {
            IPhlaxPlayerDataHolderCapability playerData = pPlayer.getCapability(PhlaxModCapabilities.PLAYER_DATA_HOLDER_CAPABILITY).orElse(null);
            playerData.setCurrentSpell(playerData.getCurrentSpell().next());
            pPlayer.sendMessage(new StringTextComponent(playerData.getCurrentSpell().name), Util.NIL_UUID);
        }
        return ActionResult.pass(pPlayer.getItemInHand(pHand));
    }
}
