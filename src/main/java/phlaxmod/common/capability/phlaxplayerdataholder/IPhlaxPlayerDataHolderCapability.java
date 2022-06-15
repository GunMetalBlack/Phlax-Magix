package phlaxmod.common.capability.phlaxplayerdataholder;

import phlaxmod.common.spells.Spell;

import java.util.ArrayList;

interface IPhlaxPlayerDataHolderCapability {
    void tick();
    void learnSpell(Spell spell);
    void unlearnSpell(Spell spell);
    ArrayList<Spell> getLearnedSpells();
    void setMana(float Mana);
    float getMana();
    void removeMana(float mana);
    void addMana(float mana);
    void setManaRegenRate(float manaRegenRate);
    float getManaRegenRate();
    void setShouldRegenMana(boolean shouldRegenMana);
    boolean shouldRegenMana();
    void setMaxMana(float maxMana);
    float getMaxMana();
}
