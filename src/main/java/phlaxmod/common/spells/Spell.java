package phlaxmod.common.spells;

import java.util.HashMap;
import java.util.function.Supplier;
import phlaxmod.PhlaxMod;

public enum Spell {
    SMALL_EXPLOSIVE("small_explosive", 20, 0,true),
    STRIKE("strike", 5, 10,true),
    BARRIER("wall",420,0,false) {
        @Override
        public void startSpell() {
            // anonymous subclasses go brrrrrrrrrrr
            PhlaxMod.logger.info("Hi Mom!");
        }
    };

    public String name;
    public int manaCost;
    public boolean isProjectile;
    public int damage;

    public static Spell getSpellByName(String name) {
        for(Spell spell : Spell.values()) {
            if(spell.name == name) return spell;
        }
        return null;
    }

    private Spell(String name, int manaCost, int damage, boolean isProjectile) {
        this.name = name;
        this.manaCost = manaCost;
        this.isProjectile = isProjectile;
        this.damage = damage;
    }

    public void startSpell() {}

}
