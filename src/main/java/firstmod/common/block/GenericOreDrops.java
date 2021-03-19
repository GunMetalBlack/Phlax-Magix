package firstmod.common.block;

import java.util.ArrayList;
import java.util.List;

import firstmod.DifReg;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext.Builder;
public class GenericOreDrops extends Block{


	public GenericOreDrops(Properties properties) {
		super(properties);
		
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, Builder builder) {
		
		ArrayList<ItemStack>list = new ArrayList<ItemStack>();
		if (state.getBlock() == DifReg.Oreblock[0]) {
			list.add(new ItemStack(DifReg.Oreblockitems[0], 1));
		}
		if (state.getBlock() == DifReg.Oreblock[1]) {
			list.add(new ItemStack(DifReg.Oreblockitems[1], 1));
		}
		if (state.getBlock() == DifReg.Oreblock[2]) {
			list.add(new ItemStack(DifReg.Oreitems[2], 1));
		}
		return list;
	}
}
	




