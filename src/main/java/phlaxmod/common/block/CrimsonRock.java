package phlaxmod.common.block;

import java.util.Arrays;
import java.util.List;

import phlaxmod.DifReg;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext.Builder;

public class CrimsonRock extends Block {
	@Override
	public List<ItemStack> getDrops(BlockState state, Builder builder) {
		return Arrays.<ItemStack>asList(new ItemStack(DifReg.CRIMSON_ROCK_ITEM.get(), 1));
	}

	public CrimsonRock(Properties properties) {
		super(properties);
	
		}

}
