package phlaxmod.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;

public class CitrineOre extends Block {

	public CitrineOre() {
		super(AbstractBlock.Properties.of(Material.METAL,MaterialColor.COLOR_GRAY)
				.strength(5)
				.noOcclusion()
				.isViewBlocking((BlockState state, IBlockReader level, BlockPos pos)->false)
				.sound(SoundType.GLASS)
				.requiresCorrectToolForDrops()
				.harvestTool(ToolType.PICKAXE)
				.harvestLevel(2));
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public float getShadeBrightness(BlockState state, IBlockReader level, BlockPos blockPos) {
		return 1.0F;
	}

	/**
	 * Prevents the rendering of internal faces when multiple blocks of the same type are placed next to each other
	 */
	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {
		return state.getBlock() == adjacentBlockState.getBlock() || super.skipRendering(state, adjacentBlockState, side);
	}

	/**
	 * Copied from {@link net.minecraft.block.AbstractGlassBlock}
	 */
	@Override
	public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
		return true;
	}

}
