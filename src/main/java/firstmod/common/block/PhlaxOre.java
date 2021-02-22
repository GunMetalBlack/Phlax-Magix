package firstmod.common.block;

import java.util.ArrayList;
import java.util.List;

import firstmod.DifReg;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext.Builder;

public class PhlaxOre extends Block {
	@Override
	public List<ItemStack> getDrops(BlockState state, Builder builder) {
		
		ArrayList<ItemStack>list = new ArrayList<ItemStack>();
		list.add(new ItemStack(DifReg.phlaxItem,2));
		return list;
	}

	public PhlaxOre(Properties properties) {
		super(properties);
	
	}

}
