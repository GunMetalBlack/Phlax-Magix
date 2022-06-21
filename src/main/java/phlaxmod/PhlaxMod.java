package phlaxmod;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import phlaxmod.client.ClientProxy;
import phlaxmod.common.CommonProxy;
import phlaxmod.container.ModContainers;
import phlaxmod.tileentity.ModTileEntities;

@Mod(value = "phlaxmod")
@EventBusSubscriber(modid = "phlaxmod", bus = Bus.MOD)
public class PhlaxMod {

    public static final String MODID = "phlaxmod";
    public static Logger logger = LogManager.getLogger(MODID);

    public static final ItemGroup PHLAX_ITEM_GROUP = new ItemGroup(MODID) {
        @Override
        public ItemStack makeIcon() {
            return DifReg.PHLAX_FLUXCROP_ITEM.get().getDefaultInstance();
        }
    };

    public static CommonProxy proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    public PhlaxMod() {

        IEventBus Ebus = FMLJavaModLoadingContext.get().getModEventBus();
        DifReg.FLUIDS.register(Ebus);
        DifReg.ITEMS.register(Ebus);
        DifReg.BLOCKS.register(Ebus);
        ModTileEntities.register(Ebus);
        ModContainers.register(Ebus);
        DifReg.ENTITIES.register(Ebus);
        DifReg.TILE_ENTITIES.register(Ebus);
        DifReg.BIOMES.register(Ebus);
        DifReg.registeredBiomes();
        MinecraftForge.EVENT_BUS.addListener(PhlaxMod::onBiomeLoadingEvent);
        MinecraftForge.EVENT_BUS.addListener(PhlaxMod::onRenderGameOverlayEvent);
        MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, PhlaxMod::onAttachCapabilitiesEventEntity);
        MinecraftForge.EVENT_BUS.addListener(PhlaxMod::onPlayerEventClone);
        MinecraftForge.EVENT_BUS.addListener(PhlaxMod::onPlayerTickEvent);

    }

    @SubscribeEvent
    public static void onClientSetupEvent(final FMLClientSetupEvent event) {
        proxy.onClientSetupEvent(event);
    }

    public static void onRenderGameOverlayEvent(RenderGameOverlayEvent.Post event) {
        proxy.onRenderGameOverlayEvent(event);
    }

    public static void onAttachCapabilitiesEventEntity(final AttachCapabilitiesEvent<Entity> event) {
        proxy.onAttachCapabilitiesEventEntity(event);
    }

    @SubscribeEvent
    public static void onCommonSetupEvent(final FMLCommonSetupEvent event) {
        proxy.onCommonSetupEvent(event);
    }

    public static void onBiomeLoadingEvent(final BiomeLoadingEvent event) {
        proxy.onBiomeLoadingEvent(event);
    }

    public static void onPlayerEventClone(final PlayerEvent.Clone event) {
        proxy.onPlayerEventClone(event);
    }

    public static void onPlayerTickEvent(final TickEvent.PlayerTickEvent event) {
        proxy.onPlayerTickEvent(event);
    }

}
