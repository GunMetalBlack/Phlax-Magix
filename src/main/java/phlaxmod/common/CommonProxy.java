package phlaxmod.common;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.INBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.network.NetworkDirection;
import org.apache.logging.log4j.Level;
import phlaxmod.DifReg;
import phlaxmod.PhlaxMod;
import phlaxmod.common.capability.PhlaxModCapabilities;
import phlaxmod.common.capability.phlaxplayerdataholder.IPhlaxPlayerDataHolderCapability;
import phlaxmod.common.capability.phlaxplayerdataholder.PhlaxPlayerDataHolderCapabilityProvider;
import phlaxmod.common.networking.PhlaxModNetworking;

public class CommonProxy {

    public void onClientSetupEvent(final FMLClientSetupEvent event) {}

    public void onRenderGameOverlayEvent(RenderGameOverlayEvent.Post event) {}

    public void onAttachCapabilitiesEventEntity(final AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof PlayerEntity) {
            event.addCapability(new ResourceLocation(PhlaxMod.MODID, "phlax_player_data_holder"), new PhlaxPlayerDataHolderCapabilityProvider());
        }
    }

    public void onCommonSetupEvent(final FMLCommonSetupEvent event) {
        PhlaxModCapabilities.register();
        PhlaxModNetworking.init();
    }

    public static void registerOreGenFeature(BiomeGenerationSettingsBuilder builder, BlockState oreBlockState, int size, int range, int count) {
        builder.getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(
                () -> Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, oreBlockState, size)).range(range).square().func_242731_b(count)
        );
    }

    public void onBiomeLoadingEvent(final BiomeLoadingEvent event) {
        registerOreGenFeature(event.getGeneration(), DifReg.PHLAX_ORE.get().getDefaultState(), 4, 44, 22);
        registerOreGenFeature(event.getGeneration(), DifReg.NICKEL_ORE.get().getDefaultState(), 8, 50, 20);
        registerOreGenFeature(event.getGeneration(), DifReg.OIL_ORE.get().getDefaultState(), 12, 25, 12);
        registerOreGenFeature(event.getGeneration(), DifReg.MITHRIL_ORE.get().getDefaultState(), 2, 15, 3);
        registerOreGenFeature(event.getGeneration(), DifReg.CITRINE_CRYSTAL.get().getDefaultState(), 3, 10, 3);
    }

    public void onPlayerEventClone(final PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            IPhlaxPlayerDataHolderCapability original = event.getOriginal().getCapability(PhlaxModCapabilities.PLAYER_DATA_HOLDER_CAPABILITY).orElse(null);
            IPhlaxPlayerDataHolderCapability target = event.getPlayer().getCapability(PhlaxModCapabilities.PLAYER_DATA_HOLDER_CAPABILITY).orElse(null);
            if(original == null || target == null) {
                PhlaxMod.logger.log(Level.ERROR, "Oopsie doopsie, I made a fuckie wucky! Got null capability when attempting to persist Phlax player data across death.");
                return;
            }
            INBT originalNBT = PhlaxModCapabilities.PLAYER_DATA_HOLDER_CAPABILITY.getStorage().writeNBT(PhlaxModCapabilities.PLAYER_DATA_HOLDER_CAPABILITY, original, null);
            PhlaxModCapabilities.PLAYER_DATA_HOLDER_CAPABILITY.getStorage().readNBT(PhlaxModCapabilities.PLAYER_DATA_HOLDER_CAPABILITY, target, null, originalNBT);
        }
    }

    public void onPlayerTickEvent(final TickEvent.PlayerTickEvent event) {
        if(!event.player.world.isRemote()) {
            IPhlaxPlayerDataHolderCapability playerData = event.player.getCapability(PhlaxModCapabilities.PLAYER_DATA_HOLDER_CAPABILITY).orElse(null);
            if(playerData != null) {
                playerData.tick((packet) -> {
                    if (event.player instanceof ServerPlayerEntity) {
                        PhlaxModNetworking.INSTANCE.sendTo(packet, ((ServerPlayerEntity) event.player).connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
                    }
                });
            }
            else {
                PhlaxMod.logger.warn("Ticking Player with null Phlax Player Data!");
            }
        }
    }

}
