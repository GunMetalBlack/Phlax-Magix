package phlaxmod.common;

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import phlaxmod.DifReg;

public class CommonProxy {

    public void onClientSetupEvent(final FMLClientSetupEvent event) {}

    public void onRenderGameOverlayEvent(RenderGameOverlayEvent.Post event) {}

    public static void registerOreGenFeature(BiomeGenerationSettingsBuilder builder, BlockState oreBlockState, int size, int range, int count) {
        builder.getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(
                () -> Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, oreBlockState, size)).range(range).square().func_242731_b(count)
        );
    }

    public void onBiomeLoadingEvent(final BiomeLoadingEvent event) {
        registerOreGenFeature(event.getGeneration(), DifReg.PHLAX_ORE.get().getDefaultState(), 4, 44, 22);
        registerOreGenFeature(event.getGeneration(), DifReg.NICKEL_ORE.get().getDefaultState(), 8, 34, 20);
        registerOreGenFeature(event.getGeneration(), DifReg.OIL_ORE.get().getDefaultState(), 12, 25, 12);
        registerOreGenFeature(event.getGeneration(), DifReg.MITHRAL_ORE.get().getDefaultState(), 2, 15, 3);
    }

}
