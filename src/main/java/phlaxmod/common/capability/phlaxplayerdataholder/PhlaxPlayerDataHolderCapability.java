package phlaxmod.common.capability.phlaxplayerdataholder;

import phlaxmod.common.spells.Spell;

import java.util.ArrayList;

public class PhlaxPlayerDataHolderCapability implements IPhlaxPlayerDataHolderCapability {

    public ArrayList<Spell> learnedSpells = new ArrayList<>();
    public float mana = 100f;
    public float maxMana = 100f;
    public float manaRegenRate = 5;
    public boolean shouldRegenMana = false;

    @Override
    public void tick() {
        addMana(getManaRegenRate());
    }

    @Override
    public void learnSpell(Spell spell) {
        learnedSpells.add(spell);
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
    }

    @Override
    public float getManaRegenRate() {
        return manaRegenRate;
    }

    @Override
    public void setShouldRegenMana(boolean shouldRegenMana) {
        this.shouldRegenMana = shouldRegenMana;
    }

    @Override
    public boolean shouldRegenMana() {
        return shouldRegenMana;
    }

    @Override
    public void setMaxMana(float maxMana) {
        this.maxMana = maxMana;
    }

    @Override
    public float getMaxMana() {
        return maxMana;
    }

}
