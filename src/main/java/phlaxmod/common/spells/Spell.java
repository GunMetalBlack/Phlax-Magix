package phlaxmod.common.spells;

import java.util.HashMap;
import java.util.function.Supplier;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.Explosion;
import net.minecraft.world.ExplosionContext;
import net.minecraft.world.World;
import phlaxmod.PhlaxMod;

import javax.annotation.Nullable;

public enum Spell {
    SMALL_EXPLOSIVE("small_explosive", 20, 0,true){
        @Override
        public void startSpell(World level, PlayerEntity player, final Double x, final Double y, final Double z) {
            //Do explosion stuff
            level.explode(player, x, y, z, 20, Explosion.Mode.DESTROY);
        }
    },
    STRIKE("strike", 5, 10,true),
    BARRIER("wall",420,0,false) {
        @Override
        public void startSpell() {
            // anonymous subclasses go brrrrrrrrrrr
            PhlaxMod.logger.info("Holy Moly");
        }
    };


    public String name;
    public int manaCost;
    public boolean isProjectile;
    public int damage;

    public static Spell getSpellByName(String name) {
        for(Spell spell : Spell.values()) {
            if(spell.name.equals(name)) return spell;
        }
        return null;
    }

     Spell(String name, int manaCost, int damage, boolean isProjectile) {
        this.name = name;
        this.manaCost = manaCost;
        this.isProjectile = isProjectile;
        this.damage = damage;
    }

    public Spell next() {
        int newOrdinal = ordinal() + 1;
        if(values().length <= newOrdinal) {
            newOrdinal = 0;
        }
        return values()[newOrdinal];
    }

    public void startSpell() {}
    public void startSpell(World level, PlayerEntity player, final Double x, final Double y, final Double z) {}
}
