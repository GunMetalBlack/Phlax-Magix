package phlaxmod;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.DistExecutor;
import phlaxmod.client.ClientProxy;
import phlaxmod.common.CommonProxy;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(value = "phlaxmod")
@EventBusSubscriber(modid = "phlaxmod", bus = Bus.MOD)
public class PhlaxMod {

    public static final ItemGroup PHLAX_ITEM_GROUP = new ItemGroup("phlaxmod") {
        @Override
        public ItemStack createIcon() {
            return DifReg.itemcherry.getDefaultInstance();
        }
    };

    public static CommonProxy proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    public PhlaxMod() {

        IEventBus Ebus = FMLJavaModLoadingContext.get().getModEventBus();
        DifReg.FLUIDS.register(Ebus);
        DifReg.ITEMS.register(Ebus);
        DifReg.BLOCKS.register(Ebus);
        DifReg.ENTITIES.register(Ebus);
        DifReg.TILE_ENTITIES.register(Ebus);

        MinecraftForge.EVENT_BUS.addListener(PhlaxMod::onBiomeLoadingEvent);
        MinecraftForge.EVENT_BUS.addListener(PhlaxMod::onRenderGameOverlayEvent);

    }

    @SubscribeEvent
    public static void onClientSetupEvent(final FMLClientSetupEvent event) {
        proxy.onClientSetupEvent(event);
    }

    public static void onRenderGameOverlayEvent(RenderGameOverlayEvent.Post event) {
        proxy.onRenderGameOverlayEvent(event);
    }

    public static void onBiomeLoadingEvent(final BiomeLoadingEvent event) {
        proxy.onBiomeLoadingEvent(event);
    }

}
