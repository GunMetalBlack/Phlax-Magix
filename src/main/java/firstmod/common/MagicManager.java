package firstmod.common;

import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
@EventBusSubscriber(modid = "firstmod_123",bus = Bus.FORGE)
public class MagicManager {
	
	public static int count = 20;
	public static float Mana = 100f;
	public static boolean RegenMana = false;
	@SubscribeEvent
	public static void playerTick(PlayerTickEvent event) {
   MagicManager.MagicController();
	if(RegenMana == true &&  count > 0 )
	{
		count--;
		Mana+=1;
		if(RegenMana == true && count <= 0 ) {
			count += 20;
			RegenMana = false;
		}
	}
   
}
	
	
	
	public static void MagicController() 
	{
		Mana = Mana > 100 ? 100 : Mana;
	}
	
}
