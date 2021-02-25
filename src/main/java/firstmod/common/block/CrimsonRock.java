package firstmod.common.block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import firstmod.DifReg;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext.Builder;

public class CrimsonRock extends Block {
	@Override
	public List<ItemStack> getDrops(BlockState state, Builder builder) {
		return Arrays.<ItemStack>asList(new ItemStack(DifReg.crimson_rock, 1));
	}

	public CrimsonRock(Properties properties) {
		super(properties);
	
		}

}
