package firstmod;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(value = "firstmod_123")
@EventBusSubscriber(modid = "firstmod_123",bus = Bus.MOD)
public class FirstMod {

	public FirstMod() {
		IEventBus Ebus = FMLJavaModLoadingContext.get().getModEventBus();
		DifReg.ITEMS.register(Ebus);
	}

}
