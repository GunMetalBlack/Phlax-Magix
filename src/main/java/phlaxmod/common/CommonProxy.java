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
import phlaxmod.PhlaxMod;
import phlaxmod.common.block.ModBlocks;
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
                () -> Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, oreBlockState, size)).range(range).squared().count(count)
        );
    }

    public void onBiomeLoadingEvent(final BiomeLoadingEvent event) {
        registerOreGenFeature(event.getGeneration(), ModBlocks.PHLAX_ORE.get().defaultBlockState(), 4, 44, 22);
        registerOreGenFeature(event.getGeneration(), ModBlocks.NICKEL_ORE.get().defaultBlockState(), 8, 50, 20);
        registerOreGenFeature(event.getGeneration(), ModBlocks.OIL_ORE.get().defaultBlockState(), 12, 25, 12);
        registerOreGenFeature(event.getGeneration(), ModBlocks.MITHRIL_ORE.get().defaultBlockState(), 2, 15, 3);
        registerOreGenFeature(event.getGeneration(), ModBlocks.CITRINE_CRYSTAL.get().defaultBlockState(), 3, 10, 3);
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
        if(!event.player.level.isClientSide()) {
            IPhlaxPlayerDataHolderCapability playerData = event.player.getCapability(PhlaxModCapabilities.PLAYER_DATA_HOLDER_CAPABILITY).orElse(null);
            if(playerData != null) {
                playerData.tick((packet) -> {
                    if (event.player instanceof ServerPlayerEntity) {
                        PhlaxModNetworking.INSTANCE.sendTo(packet, ((ServerPlayerEntity) event.player).connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
                    }
                });
            }
            else {
                PhlaxMod.logger.warn("Ticking Player with null Phlax Player Data!");
            }
        }else{
            IPhlaxPlayerDataHolderCapability playerData = event.player.getCapability(PhlaxModCapabilities.PLAYER_DATA_HOLDER_CAPABILITY).orElse(null);
            if(playerData != null) {
                if(playerData.getMana() > playerData.getManaClient()){
                    playerData.setManaClient(playerData.getManaClient() + 1);
                }else if(playerData.getMana() < playerData.getManaClient()){
                    playerData.setManaClient(playerData.getManaClient() - 1);
                }
            }
        }
    }

}
