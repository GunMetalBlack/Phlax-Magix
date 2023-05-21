package phlaxmod.common.capability.phlaxplayerdataholder;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import phlaxmod.common.spells.Spell;

import javax.annotation.Nullable;

public class PhlaxPlayerDataHolderCapabilityStorage implements Capability.IStorage<IPhlaxPlayerDataHolderCapability> {

    /*
    public ArrayList<Spell> learnedSpells = new ArrayList<>();
    public float mana = 100f;
    public float maxMana = 100f;
    public float manaRegenRate = 5;
    public boolean shouldRegenMana = false;
     */

    @Nullable
    @Override
    public INBT writeNBT(Capability<IPhlaxPlayerDataHolderCapability> capability, IPhlaxPlayerDataHolderCapability instance, Direction side) {

        CompoundNBT nbt = new CompoundNBT();

        ListNBT spells = new ListNBT();
        for(Spell spell : instance.getLearnedSpells()) {
            StringNBT spellName = StringNBT.valueOf(spell.name);
            spells.add(spellName);
        }

        nbt.put("learnedSpells", spells);
        nbt.putFloat("mana", instance.getMana());
        nbt.putFloat("maxMana", instance.getMaxMana());
        nbt.putFloat("manaRegenRate", instance.getManaRegenRate());
        nbt.putBoolean("shouldRegenMana", instance.shouldRegenMana());

        return nbt;

    }

    @Override
    public void readNBT(Capability<IPhlaxPlayerDataHolderCapability> capability, IPhlaxPlayerDataHolderCapability instance, Direction side, INBT nbtIn) {

        CompoundNBT nbt = (CompoundNBT) nbtIn;

        ListNBT learnedSpells = nbt.getList("learnedSpells", 8);
        for(int i = 0; i < learnedSpells.size(); ++i) {
            instance.learnSpell(Spell.getSpellByName(((StringNBT)learnedSpells.get(i)).getAsString()));
        }

        instance.setMana(nbt.getFloat("mana"));
        instance.setMaxMana(nbt.getFloat("maxMana"));
        instance.setManaRegenRate(nbt.getFloat("manaRegenRate"));
        instance.setShouldRegenMana(nbt.getBoolean("shouldRegenMana"));

    }

}
