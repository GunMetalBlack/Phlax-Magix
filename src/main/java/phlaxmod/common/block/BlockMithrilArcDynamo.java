package phlaxmod.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import phlaxmod.container.ContainerMithrilArcDynamo;
import phlaxmod.container.ModContainers;
import phlaxmod.tileentity.ModTileEntities;
import phlaxmod.tileentity.TileMithrilArcDynamo;

import javax.annotation.Nullable;

public class BlockMithrilArcDynamo extends BlockMachine{

    public BlockMithrilArcDynamo() {
        super(TileMithrilArcDynamo.class);
    }

    public BlockMithrilArcDynamo(Properties properties, Class<?> tileEntityClass) {
        super(properties, tileEntityClass);
    }

    @Override
    public String getDisplayNameTranslationKey() {
        return "screen.tutorialmod.mithril_arc_dynamo";
    }

    @Override
    public ModContainers.IPhlaxModContainerConstructor getContainerConstructor() {
        return ContainerMithrilArcDynamo::new;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntities.MITHRIL_ARC_DYNAMO_TILE.get().create();
    }

}
