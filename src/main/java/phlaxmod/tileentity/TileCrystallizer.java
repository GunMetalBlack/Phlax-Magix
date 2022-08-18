package phlaxmod.tileentity;

import phlaxmod.data.recipes.ModRecipeTypes;

public class TileCrystallizer extends TileMachine{
    public TileCrystallizer(){
        super(
                ModTileEntities.CRYSTALIZER_TILE.get(),
                70,
                500,
                0,
                500/2,
                2,
                64,
                ModRecipeTypes.CRYSTALLIZER_RECIPE_TYPE
        );
        guiBarLookUpTable.put("productProgress",this::getProductProgress);
    }

    @Override
    public void tick(){
        tickEnergyReceive();
        tickProduce();
        super.tick();
    }

    public double getProductProgress(){
        // ticksRemaining == 0 is ambiguous between "idle" and "done" - assuming "idle".
        if(ticksRemaining == 0) return 0;
        // prevent dividing by zero
        if(peakTicksRemaining == 0) return 0;
        // complement of the ratio of ticksRemaining to peakTicksRemaining
        return 1 - (ticksRemaining/(double)peakTicksRemaining);
    }

}
