package phlaxmod.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext.Builder;
import phlaxmod.DifReg;

import java.util.ArrayList;
import java.util.List;

public class CrimsonStone extends Block {
	@Override
	public List<ItemStack> getDrops(BlockState state, Builder builder) {
		
		ArrayList<ItemStack>list = new ArrayList<ItemStack>();
		
		list.add(new ItemStack(DifReg.CRIMSON_STONE_ITEM.get(),1));
	
		return list;
	}

	public CrimsonStone(Properties properties) {
		super(properties);
	
		}

}
