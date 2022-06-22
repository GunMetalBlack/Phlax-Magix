package phlaxmod.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import phlaxmod.container.MithrilArcDynamoContainer;
import phlaxmod.container.ModContainers;
import phlaxmod.tileentity.MithrilArcDynamoTile;
import phlaxmod.tileentity.ModTileEntities;

import javax.annotation.Nullable;

public class MithrilArcDynamo extends BlockMachine{

    public MithrilArcDynamo() {
        super(MithrilArcDynamoTile.class);
    }

    public MithrilArcDynamo(Properties properties, Class<?> tileEntityClass) {
        super(properties, tileEntityClass);
    }

    @Override
    public String getDisplayNameTranslationKey() {
        return "screen.tutorialmod.mithril_arc_dynamo";
    }

    @Override
    public ModContainers.IPhlaxModContainerConstructor getContainerConstructor() {
        return MithrilArcDynamoContainer::new;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntities.MITHRIL_ARC_DYNAMO_TILE.get().create();
    }

}
