package phlaxmod.common.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import phlaxmod.common.capability.PhlaxModCapabilities;
import phlaxmod.common.capability.phlaxplayerdataholder.IPhlaxPlayerDataHolderCapability;

public class orbSpellSelector extends Item {

    public orbSpellSelector(Properties pProperties) {
        super(pProperties);
    }
    @Override
    public ActionResult<ItemStack> use(World pLevel, PlayerEntity pPlayer, Hand pHand)
    {
        IPhlaxPlayerDataHolderCapability playerData = pPlayer.getCapability(PhlaxModCapabilities.PLAYER_DATA_HOLDER_CAPABILITY).orElse(null);
        playerData.getCurrentSpell()playerData.getCurrentSpell().next();
        return ActionResult.pass(pPlayer.getItemInHand(pHand));
    }
}
