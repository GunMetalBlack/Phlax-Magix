package phlaxmod.common.capability.phlaxplayerdataholder;

import net.minecraftforge.common.capabilities.CapabilityManager;
import phlaxmod.common.networking.SPacketPhlaxPlayerDataUpdate;
import phlaxmod.common.spells.Spell;

import java.util.ArrayList;
import java.util.function.Consumer;

public class PhlaxPlayerDataHolderCapability implements IPhlaxPlayerDataHolderCapability {

    public ArrayList<Spell> learnedSpells = new ArrayList<>();
    public float mana = 100f;
    public float maxMana = 100f;
    public float manaRegenRate = 5;
    public boolean shouldRegenMana = false;

    private boolean isDirty = false;

    @Override
    public void tick(Consumer<SPacketPhlaxPlayerDataUpdate> packetSendFunction) {
        if(shouldRegenMana()) {
            addMana(getManaRegenRate());
            setShouldRegenMana(false);
        }
        if(isDirty) {
            isDirty = false;
            packetSendFunction.accept(new SPacketPhlaxPlayerDataUpdate(this));
        }
    }

    @Override
    public void learnSpell(Spell spell) {
        learnedSpells.add(spell);
        this.isDirty = true;
    }

    @Override
    public void unlearnSpell(Spell spell) {
        learnedSpells.remove(spell);
    }

    @Override
    public ArrayList<Spell> getLearnedSpells() {
        return learnedSpells;
    }

    @Override
    public void setMana(float mana) {
        this.mana = Math.max(Math.min(mana, maxMana), 0);
        this.isDirty = true;
    }

    @Override
    public float getMana() {
        return mana;
    }

    @Override
    public void removeMana(float mana) {
        setMana(getMana() - mana);
    }

    @Override
    public void addMana(float mana) {
        setMana(getMana() + mana);
    }

    @Override
    public void setManaRegenRate(float manaRegenRate) {
        this.manaRegenRate = manaRegenRate;
        this.isDirty = true;
    }

    @Override
    public float getManaRegenRate() {
        return manaRegenRate;
    }

    @Override
    public void setShouldRegenMana(boolean shouldRegenMana) {
        this.shouldRegenMana = shouldRegenMana;
        this.isDirty = true;
    }

    @Override
    public boolean shouldRegenMana() {
        return shouldRegenMana;
    }

    @Override
    public void setMaxMana(float maxMana) {
        this.maxMana = maxMana;
        this.isDirty = true;
    }

    @Override
    public float getMaxMana() {
        return maxMana;
    }

    public static void register() {
        CapabilityManager.INSTANCE.register(IPhlaxPlayerDataHolderCapability.class, new PhlaxPlayerDataHolderCapabilityStorage(), PhlaxPlayerDataHolderCapability::new);
    }

}
