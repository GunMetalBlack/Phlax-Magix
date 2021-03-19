package firstmod.common.block;

import java.util.ArrayList;
import java.util.List;

import firstmod.DifReg;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext.Builder;
import net.minecraftforge.common.ToolType;

public class RubyOre extends Block{

	@Override 
public List<ItemStack> getDrops(BlockState state, Builder builder) {
		
		ArrayList<ItemStack>list = new ArrayList<ItemStack>();
		
		list.add(new ItemStack(DifReg.ruby_item,4));
	
		return list;
	}
	public RubyOre() {
		super(AbstractBlock.Properties.create(Material.IRON,MaterialColor.GRAY).hardnessAndResistance(5).sound(SoundType.NETHER_GOLD).setRequiresTool().harvestTool(ToolType.PICKAXE).harvestLevel(2));
		
	}


}
