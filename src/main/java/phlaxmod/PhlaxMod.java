package phlaxmod;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
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
import phlaxmod.common.block.ModBlocks;
import phlaxmod.common.entities.ModEntities;
import phlaxmod.common.item.ModItems;
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
            return ModItems.COMPRESSED_PHLAX_FLUX.get().getDefaultInstance();
        }
    };

    public static CommonProxy proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    public PhlaxMod() {
        // Add Deferred Registers to Mod Event Bus
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModMiscellaneousReg.BIOMES.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModContainers.CONTAINERS.register(modEventBus);
        ModEntities.ENTITIES.register(modEventBus);
        ModMiscellaneousReg.FLUIDS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModTileEntities.TILE_ENTITES.register(modEventBus);
        // Post-Registration
        ModMiscellaneousReg.addBiomesToBiomeManager();
        // Add Listeners to Forge Event Bus
        MinecraftForge.EVENT_BUS.addListener(PhlaxMod::onBiomeLoadingEvent);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, ()-> ()-> MinecraftForge.EVENT_BUS.addListener(PhlaxMod::onRenderGameOverlayEvent));
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
