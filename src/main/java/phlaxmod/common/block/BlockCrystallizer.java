package phlaxmod.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import phlaxmod.container.ContainerCrystalizer;
import phlaxmod.container.ModContainers;
import phlaxmod.tileentity.ModTileEntities;
import phlaxmod.tileentity.TileCrystalizer;

import javax.annotation.Nullable;

public class BlockCrystallizer extends BlockMachine {

    public BlockCrystallizer() {
        super(TileCrystalizer.class);
    }

    public BlockCrystallizer(Properties properties, Class<?> tileEntityClass) {
        super(properties, tileEntityClass);
    }

    @Override
    public String getDisplayNameTranslationKey() {
        return "screen.tutorialmod.crystallizer";
    }

    @Override
    public ModContainers.IPhlaxModContainerConstructor getContainerConstructor() {
        return ContainerCrystalizer::new;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntities.CRYSTALIZER_TILE.get().create();
    }

}
