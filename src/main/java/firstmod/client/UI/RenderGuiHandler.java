package firstmod.client.UI;

import firstmod.common.MagicManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = "firstmod_123",bus = Bus.FORGE)
public class RenderGuiHandler {

		@SubscribeEvent
		public static void textRenderGui(RenderGameOverlayEvent.Post event) {
			if(event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
		        Minecraft.getInstance().fontRenderer.func_243248_b(event.getMatrixStack(), new StringTextComponent("Mana: "+ MagicManager.Mana ), 12, 12, MagicManager.Mana <=10 ? 16321548 : 799993);
		        }
		}
	
}
