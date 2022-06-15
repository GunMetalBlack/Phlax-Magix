package phlaxmod.common.spells;

import java.util.HashMap;
import java.util.function.Supplier;

public class Spell {

    public static HashMap<String, Supplier<Spell>> spells = new HashMap<>();

    static {
        spells.put("explosive_small", () -> new Spell("Small Explosive", 69, SpellTypes.EXPLOSIVE));
        spells.put("strike", () -> new Spell("Strike", 69, SpellTypes.ATTACK));
    }

    String name;
    int manaCost;
    SpellTypes spellType;

    public Spell(String name, int manaCost, SpellTypes spellType) {
        this.name = name;
        this.manaCost = manaCost;
        this.spellType = spellType;
    }

    public void startSpell() {}

}
