package phlaxmod.common.networking;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketDirection;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.Level;
import phlaxmod.PhlaxMod;
import phlaxmod.common.capability.PhlaxModCapabilities;
import phlaxmod.common.capability.phlaxplayerdataholder.IPhlaxPlayerDataHolderCapability;
import phlaxmod.common.capability.phlaxplayerdataholder.PhlaxPlayerDataHolderCapability;
import phlaxmod.common.spells.Spell;

import java.util.ArrayList;
import java.util.function.Supplier;

public class SPacketPhlaxPlayerDataUpdate {

    public ArrayList<Spell> learnedSpells = new ArrayList<>();
    public float mana = 100f;
    public float maxMana = 100f;
    public float manaRegenRate = 5;
    public boolean shouldRegenMana = false;

    public SPacketPhlaxPlayerDataUpdate(ArrayList<Spell> learnedSpells, float mana, float maxMana, float manaRegenRate, boolean shouldRegenMana) {
        this.learnedSpells = learnedSpells;
        this.mana = mana;
        this.maxMana = maxMana;
        this.manaRegenRate = manaRegenRate;
        this.shouldRegenMana = shouldRegenMana;
    }

    public SPacketPhlaxPlayerDataUpdate(PhlaxPlayerDataHolderCapability capability) {
        this(
                capability.learnedSpells,
                capability.mana,
                capability.maxMana,
                capability.manaRegenRate,
                capability.shouldRegenMana
        );
    }

    public static void write(SPacketPhlaxPlayerDataUpdate packet, PacketBuffer buffer) {
        buffer.writeInt(packet.learnedSpells.size());
        for(Spell spell : packet.learnedSpells) {
            buffer.writeString(spell.name);
        }
        buffer.writeFloat(packet.mana);
        buffer.writeFloat(packet.maxMana);
        buffer.writeFloat(packet.manaRegenRate);
        buffer.writeBoolean(packet.shouldRegenMana);
    }

    public static SPacketPhlaxPlayerDataUpdate read(PacketBuffer buffer) {
        int spellCount = buffer.readInt();
        ArrayList<Spell> spells = new ArrayList<>(spellCount);
        for(int i = 0; i < spellCount; ++i) {
            spells.add(Spell.spells.get(buffer.readString()).get());
        }
        return new SPacketPhlaxPlayerDataUpdate(
                spells,
                buffer.readFloat(),
                buffer.readFloat(),
                buffer.readFloat(),
                buffer.readBoolean()
        );
    }

    public static void handle(SPacketPhlaxPlayerDataUpdate packet, Supplier<NetworkEvent.Context> contextSupplier) {

        contextSupplier.get().enqueueWork(() -> {

            if(contextSupplier.get().getDirection() != NetworkDirection.PLAY_TO_CLIENT || contextSupplier.get().getNetworkManager().getDirection() != PacketDirection.CLIENTBOUND) {
                try {
                    PhlaxMod.logger.log(Level.WARN, "Received packet intended for client on the server - this is most likely the result of a player using a modified client to attempt to exploit the game! Source IP Address:" + contextSupplier.get().getSender().getPlayerIP());
                } catch (Throwable ignored) {}
                return;
            }

            try {
                IPhlaxPlayerDataHolderCapability playerData = Minecraft.getInstance().player.getCapability(PhlaxModCapabilities.PLAYER_DATA_HOLDER_CAPABILITY, null).orElse(null);
                // TODO: Make this less brute-forcey and only add/remove the spells that differ. This way the learnSpell() method could be used to trigger GUI events, etc.
                playerData.getLearnedSpells().clear();
                for(Spell spell : packet.learnedSpells) {
                    playerData.learnSpell(spell);
                }
                playerData.setMana(packet.mana);
                playerData.setMaxMana(packet.maxMana);
                playerData.setManaRegenRate(packet.manaRegenRate);
                playerData.setShouldRegenMana(packet.shouldRegenMana);
            } catch (NullPointerException npe) {
                npe.printStackTrace();
            }

        });

        contextSupplier.get().setPacketHandled(true);

    }

}
