package phlaxmod.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import phlaxmod.DifReg;
import phlaxmod.common.CommonProxy;
import phlaxmod.common.capability.PhlaxModCapabilities;
import phlaxmod.common.capability.phlaxplayerdataholder.IPhlaxPlayerDataHolderCapability;
import phlaxmod.common.entities.PhlaxWandEntity;

import java.util.function.Supplier;

public class ClientProxy extends CommonProxy {

    @Override
    public void onClientSetupEvent(final FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(DifReg.PHLAX_FLUXCROP.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DifReg.CITRINE_CRYSTAL.get(), RenderType.getTranslucent());
        RenderingRegistry.registerEntityRenderingHandler(DifReg.Phlax_Projectile.get(), (rendererManager) -> new SpriteRenderer<PhlaxWandEntity>(rendererManager, event.getMinecraftSupplier().get().getItemRenderer()));
    }

    @Override
    public void onRenderGameOverlayEvent(RenderGameOverlayEvent.Post event) {
        if(event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            IPhlaxPlayerDataHolderCapability playerData = Minecraft.getInstance().player.getCapability(PhlaxModCapabilities.PLAYER_DATA_HOLDER_CAPABILITY).orElse(null);
            Minecraft.getInstance().fontRenderer.func_243248_b(event.getMatrixStack(), new StringTextComponent("Mana: " + playerData.getMana()), 12, 12, playerData.getMana() <= 10 ? 16321548 : 14467071);
        }
    }

}
