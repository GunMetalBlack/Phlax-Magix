package phlaxmod.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;

public class RubyOre extends Block{

	public RubyOre() {
		super(AbstractBlock.Properties.create(Material.IRON,MaterialColor.GRAY).hardnessAndResistance(5).sound(SoundType.NETHER_GOLD).setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(2));
		
	}


}
