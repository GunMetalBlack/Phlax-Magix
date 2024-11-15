package phlaxmod.common.capability.phlaxplayerdataholder;

import net.minecraftforge.common.capabilities.CapabilityManager;
import phlaxmod.common.networking.SPacketPhlaxPlayerDataUpdate;
import phlaxmod.common.spells.Spell;

import java.util.ArrayList;
import java.util.function.Consumer;

public class PhlaxPlayerDataHolderCapability implements IPhlaxPlayerDataHolderCapability {

    public ArrayList<Spell> learnedSpells = new ArrayList<>();
    public float mana = 100f;
    public float manaClient = 0f;
    public float maxMana = 100f;
    public float manaRegenRate = 5;
    public boolean shouldRegenMana = false;
    private boolean isDirty = false;
    private Spell CurrentSpell;
    public PhlaxPlayerDataHolderCapability()
    {
        for(Spell spell : Spell.values())
        {
            learnedSpells.add(spell);
        }
        this.CurrentSpell = learnedSpells.get(0);
    }
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
    public float getManaClient(){
        return manaClient;
    }
    @Override
    public void setManaClient(float manaClient){
        //No networking IF CLIENT WASN'T CLEAR ENOUGH
        //Used to create count effect on Mana UI Text
        this.manaClient = manaClient;
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
    public void setCurrentSpell(Spell CurrentSpell)
    {
        this.CurrentSpell = CurrentSpell;
    }

    @Override
    public Spell getCurrentSpell() {
        return this.CurrentSpell;
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
