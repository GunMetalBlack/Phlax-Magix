package phlaxmod.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import phlaxmod.container.ContainerArcaneCondenser;
import phlaxmod.container.ContainerCrystalizer;
import phlaxmod.container.ModContainers;
import phlaxmod.tileentity.ModTileEntities;
import phlaxmod.tileentity.TileMachine;

import javax.annotation.Nullable;

public class BlockArcaneCondenser extends BlockMachine{

    public BlockArcaneCondenser() {
        super(TileMachine.class);
    }

    public BlockArcaneCondenser(Properties properties, Class<?> tileEntityClass) {
        super(properties, tileEntityClass);
    }

    @Override
    public String getDisplayNameTranslationKey() {
        return "screen.tutorialmod.arcane_condenser";
    }

    @Override
    public ModContainers.IPhlaxModContainerConstructor getContainerConstructor() {
        return ContainerArcaneCondenser::new;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntities.ARCANE_CONDENSER_TILE.get().create();
    }

}
