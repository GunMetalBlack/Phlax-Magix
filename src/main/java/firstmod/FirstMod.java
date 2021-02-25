package firstmod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Supplier;

import com.google.common.collect.Lists;

import firstmod.common.entities.spells.PhlaxWandEntitie;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(value = "firstmod_123")
@EventBusSubscriber(modid = "firstmod_123",bus = Bus.MOD)
public class FirstMod {
	
	public static final ItemGroup PhlaxFixins_Group  = new PhlaxFixinsGroup("firstmod_123");

	public FirstMod() {
		
		IEventBus Ebus = FMLJavaModLoadingContext.get().getModEventBus();
		DifReg.ITEMS.register(Ebus);
		DifReg.BLOCKS.register(Ebus);
		DifReg.ENTITIES.register(Ebus);
		 
	}
	
	public static class PhlaxFixinsGroup extends ItemGroup {

		public PhlaxFixinsGroup(String label) {
			super(label);
			
		}

		@Override
		public ItemStack createIcon() {
			
			return DifReg.itemcherry.getDefaultInstance();
		}}
	@SubscribeEvent
	public static void clientSetup(final FMLClientSetupEvent event) {
		
		RenderTypeLookup.setRenderLayer(DifReg.phlax_fluxcrop, RenderType.getCutout());
		registerEntityModels(event.getMinecraftSupplier());
		
	}
	
	
	
	private static void registerEntityModels(Supplier<Minecraft> minecraft) {
		net.minecraft.client.renderer.ItemRenderer renderer = minecraft.get().getItemRenderer();
		
		RenderingRegistry.registerEntityRenderingHandler(DifReg.Phlax_Projectile.get(), (rendermanager) -> new SpriteRenderer<PhlaxWandEntitie>(rendermanager,renderer));
	}
	
	@SubscribeEvent
    public static void onLoadEvent(FMLLoadCompleteEvent event) {
            OreFeatureConfig feature = new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, DifReg.phlaxOre.getDefaultState(), 11);
            Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, DifReg.phlaxOre.getRegistryName(),
                    Feature.ORE.withConfiguration(feature).range(33).func_242731_b(4).square());
        setupGen();
    }

    @Deprecated
    public static void setupGen() {
        ArrayList<Block> blocks = new ArrayList<Block>();
        // add blocks to list here. SHOULD CONTAIN ALL BLOCKS REGISTERED BEFORE
       blocks.add(DifReg.phlaxOre);
        for (Block block : blocks) {
            for (Entry<RegistryKey<Biome>, Biome> biome : WorldGenRegistries.BIOME.getEntries()) {
                if (!biome.getValue().getCategory().equals(Biome.Category.NETHER) && !biome.getValue().getCategory().equals(Biome.Category.THEEND)) {
                    addFeatureToBiome(biome.getValue(), GenerationStage.Decoration.UNDERGROUND_ORES, WorldGenRegistries.CONFIGURED_FEATURE.getOrDefault(block.getRegistryName()));
                }
            }
        }
    }

    public static void addFeatureToBiome(Biome biome, GenerationStage.Decoration decoration, ConfiguredFeature<?, ?> configuredFeature) {
        List<List<Supplier<ConfiguredFeature<?, ?>>>> biomeFeatures = new ArrayList<>(biome.getGenerationSettings().getFeatures());

        while (biomeFeatures.size() <= decoration.ordinal()) {
            biomeFeatures.add(Lists.newArrayList());
        }

        List<Supplier<ConfiguredFeature<?, ?>>> features = new ArrayList<>(biomeFeatures.get(decoration.ordinal()));
        features.add(() -> configuredFeature);
        biomeFeatures.set(decoration.ordinal(), features);

        ObfuscationReflectionHelper.setPrivateValue(BiomeGenerationSettings.class, biome.getGenerationSettings(), biomeFeatures, "field_242484_f");
    }


}
