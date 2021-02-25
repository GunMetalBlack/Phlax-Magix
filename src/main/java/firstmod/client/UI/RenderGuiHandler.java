package firstmod.client.UI;

import com.mojang.blaze3d.matrix.MatrixStack;

import firstmod.common.MagicManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
@EventBusSubscriber(modid = "firstmod_123",bus = Bus.FORGE)
public class RenderGuiHandler {
		
	  
		@SubscribeEvent
		public static void textRenderGui(RenderGameOverlayEvent.Post event) 
		{
			 int Colors;
			if(MagicManager.Mana <= 10)
			{
				Colors = 16321548;
			}else {
				Colors = 799993;
			}
			
			 if(event.getType() == RenderGameOverlayEvent.ElementType.TEXT)
		     {
				 
				    MatrixStack stack = event.getMatrixStack();
				    FontRenderer font = Minecraft.getInstance().fontRenderer;
				    font.func_243248_b(stack, new StringTextComponent("Mana: "+MagicManager.Mana), 12, 12,Colors);
				 
		     }
		}
	
}
