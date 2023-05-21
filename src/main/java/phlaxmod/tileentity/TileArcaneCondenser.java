package phlaxmod.tileentity;

import phlaxmod.container.data.recipes.ModRecipeTypes;

public class TileArcaneCondenser extends TileMachine{
    public TileArcaneCondenser(){
        super(
                ModTileEntities.ARCANE_CONDENSER_TILE.get(),
                70,
                500,
                0,
                500/2,
                2,
                64,
                ModRecipeTypes.ARCANE_CONDENSER_RECIPE_TYPE
        );
        //TODO Add gui to look up table
        guiBarLookUpTable.put("product_Progress_arcane_condenser",this::getProductProgress);
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
