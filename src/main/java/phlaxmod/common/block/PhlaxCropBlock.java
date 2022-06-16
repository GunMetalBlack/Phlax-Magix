package phlaxmod.common.block;

import java.util.ArrayList;
import java.util.List;

import phlaxmod.DifReg;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext.Builder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class PhlaxCropBlock extends CropsBlock {

	public static VoxelShape[] SHAPES = new VoxelShape[] { Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
			Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D),
			Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
			Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D),
			Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
			Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D),
			Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
			Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D) };

	public PhlaxCropBlock(Properties builder) {
		super(builder);

	}

	@Override
	public List<ItemStack> getDrops(BlockState state, Builder builder) {

		ArrayList<ItemStack> list = new ArrayList<ItemStack>();

		if (state.getBlock() == DifReg.PHLAX_FLUXCROP.get()) {
			if (this.isMaxAge(state)) {
				list.add(new ItemStack(DifReg.RAW_PHLAX_FLUX.get(), 2));
			}

		}
		return list;

	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {

		return SHAPES[state.get(this.getAgeProperty())];
	}

}
