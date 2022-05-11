package firstmod;

import firstmod.common.entities.spells.PhlaxWandEntitie;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.function.Supplier;

@Mod(value = "firstmod_123")
@EventBusSubscriber(modid = "firstmod_123", bus = Bus.MOD)
public class FirstMod {

    public static final ItemGroup PhlaxFixins_Group = new PhlaxFixinsGroup("firstmod_123");

    public FirstMod() {

        IEventBus Ebus = FMLJavaModLoadingContext.get().getModEventBus();
        DifReg.FLUIDS.register(Ebus);
        DifReg.ITEMS.register(Ebus);
        DifReg.BLOCKS.register(Ebus);
        DifReg.ENTITIES.register(Ebus);
        DifReg.TILE_ENTITIES.register(Ebus);

        MinecraftForge.EVENT_BUS.addListener(FirstMod::onBiomeLoadingEvent);

    }

    public static class PhlaxFixinsGroup extends ItemGroup {

        public PhlaxFixinsGroup(String label) {
            super(label);
        }

        @Override
        public ItemStack createIcon() {
            return DifReg.itemcherry.getDefaultInstance();
        }
    }

    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event) {

        RenderTypeLookup.setRenderLayer(DifReg.phlax_fluxcrop, RenderType.getCutout());
        registerEntityModels(event.getMinecraftSupplier());

    }


    private static void registerEntityModels(Supplier<Minecraft> minecraft) {
        net.minecraft.client.renderer.ItemRenderer renderer = minecraft.get().getItemRenderer();

        RenderingRegistry.registerEntityRenderingHandler(DifReg.Phlax_Projectile.get(), (rendermanager) -> new SpriteRenderer<PhlaxWandEntitie>(rendermanager, renderer));
    }

    public static void registerOreGenFeature(BiomeGenerationSettingsBuilder builder, BlockState oreBlockState, int size, int range, int count) {
        builder.getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(
                () -> Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, oreBlockState, size)).range(range).square().func_242731_b(count)
        );
    }

    public static void onBiomeLoadingEvent(final BiomeLoadingEvent event) {
        registerOreGenFeature(event.getGeneration(), DifReg.PHLAX_ORE.get().getDefaultState(), 4, 44, 22);
        registerOreGenFeature(event.getGeneration(), DifReg.NICKEL_ORE.get().getDefaultState(), 8, 34, 20);
        registerOreGenFeature(event.getGeneration(), DifReg.OIL_ORE.get().getDefaultState(), 12, 25, 12);
        registerOreGenFeature(event.getGeneration(), DifReg.MITHRAL_ORE.get().getDefaultState(), 2, 15, 3);
    }

}
