package phlaxmod.common.spells;

public class SpellBarrier extends Spell {

    public SpellBarrier(String name, int manaCost, SpellTypes spellType) {
        super(name, manaCost, spellType);
    }

    /*
     * When is this called?
     * Where does it get its data?
     * Cleanup logic somewhere else?
     */
    @Override
    public void startSpell() {
        //level.setBlockState(BlockPos.ZERO, Blocks.DIRT.getDefaultState());
    }

}
