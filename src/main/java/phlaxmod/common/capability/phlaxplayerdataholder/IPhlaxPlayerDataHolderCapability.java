package phlaxmod.common.capability.phlaxplayerdataholder;

import phlaxmod.common.networking.SPacketPhlaxPlayerDataUpdate;
import phlaxmod.common.spells.Spell;

import java.util.ArrayList;
import java.util.function.Consumer;

public interface IPhlaxPlayerDataHolderCapability {
    // TODO: Call tick from server tick!
    void tick(Consumer<SPacketPhlaxPlayerDataUpdate> packetSendFunction);
    void learnSpell(Spell spell);
    void unlearnSpell(Spell spell);
    ArrayList<Spell> getLearnedSpells();
    void setMana(float Mana);
    float getMana();
    float getManaClient();
    void setManaClient(float manaClient);
    void removeMana(float mana);
    void addMana(float mana);
    void setManaRegenRate(float manaRegenRate);
    float getManaRegenRate();
    void setShouldRegenMana(boolean shouldRegenMana);
    boolean shouldRegenMana();
    void setMaxMana(float maxMana);
    float getMaxMana();
    Spell getCurrentSpell();
}
