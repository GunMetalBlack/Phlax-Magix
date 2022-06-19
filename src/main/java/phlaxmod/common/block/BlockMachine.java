package phlaxmod.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

public class BlockMachine extends Block {
    public BlockMachine(){
        this(AbstractBlock.Properties
                .of(Material.METAL, MaterialColor.COLOR_GRAY).strength(1).sound(SoundType.STONE)
                .requiresCorrectToolForDrops().harvestTool(ToolType.PICKAXE).harvestLevel(2).noOcclusion().isViewBlocking((BlockState state, IBlockReader level, BlockPos pos)->false));
    }
    public BlockMachine(Properties properties) {
        super(properties);
    }

}
