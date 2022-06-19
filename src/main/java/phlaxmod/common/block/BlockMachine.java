package phlaxmod.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;
import phlaxmod.PhlaxMod;

public class BlockMachine extends Block {
    public BlockMachine(){
        this(AbstractBlock.Properties
                .create(Material.IRON, MaterialColor.GRAY).hardnessAndResistance(1).sound(SoundType.STONE)
                .setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(2).notSolid().setBlocksVision((BlockState state, IBlockReader level, BlockPos pos)->false));
    }
    public BlockMachine(Properties properties) {
        super(properties);
    }

}
