package firstmod.common;

import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MagicManager{

	public static int count;
	public static float Mana = 100f;
	@SubscribeEvent
	public static void MagicController() 
	{
		if(Mana > 100f)
		{
			
			Mana = 100f;
		}
	}
	
}
