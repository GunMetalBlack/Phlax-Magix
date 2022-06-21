package phlaxmod.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import phlaxmod.container.CrystalizerContainer;
import phlaxmod.container.ModContainers;
import phlaxmod.tileentity.CrystalizerTile;
import phlaxmod.tileentity.ModTileEntities;

import javax.annotation.Nullable;

public class CrystallizerBlock extends BlockMachine {

    public CrystallizerBlock() {
        super(CrystalizerTile.class);
    }

    public CrystallizerBlock(Properties properties, Class<?> tileEntityClass) {
        super(properties, tileEntityClass);
    }

    @Override
    public String getDisplayNameTranslationKey() {
        return "screen.tutorialmod.crystallizer";
    }

    @Override
    public ModContainers.IPhlaxModContainerConstructor getContainerConstructor() {
        return CrystalizerContainer::new;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntities.CRYSTALIZER_TILE.get().create();
    }

}
