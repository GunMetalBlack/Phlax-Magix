package firstmod.common.block;

import java.util.ArrayList;
import java.util.List;

import firstmod.DifReg;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext.Builder;
import net.minecraftforge.common.ToolType;

public class CrimsonStone extends Block {
	@Override
	public List<ItemStack> getDrops(BlockState state, Builder builder) {
		
		ArrayList<ItemStack>list = new ArrayList<ItemStack>();
		
		list.add(new ItemStack(DifReg.crimson_stone,1));
	
		return list;
	}

	public CrimsonStone(Properties properties) {
		super(properties);
	
		}

}
