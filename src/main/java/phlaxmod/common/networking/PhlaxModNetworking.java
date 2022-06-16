package phlaxmod.common.networking;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import phlaxmod.PhlaxMod;

/**
 * https://mcforge.readthedocs.io/en/1.16.x/networking/simpleimpl/
 */
public class PhlaxModNetworking {

    public static final String PROTOCOL_VERSION = "1";
    public static SimpleChannel INSTANCE;

    public static void init() {

        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(PhlaxMod.MODID, "main"),
                () -> PROTOCOL_VERSION,
                PROTOCOL_VERSION::equals,
                PROTOCOL_VERSION::equals
        );

        int packetID = 0;
        INSTANCE.messageBuilder(
                SPacketPhlaxPlayerDataUpdate.class, packetID++)
                .encoder(SPacketPhlaxPlayerDataUpdate::write)
                .decoder(SPacketPhlaxPlayerDataUpdate::read)
                .consumer(SPacketPhlaxPlayerDataUpdate::handle)
                .add();

    }

}
