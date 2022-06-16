package phlaxmod.tools;

import java.util.function.Supplier;

import phlaxmod.DifReg;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;

public enum ModItemTeir implements IItemTier{
	PHLAX(3, 450, 7.0F, 3.0f, 12, () -> {
		
		return Ingredient.fromItems(DifReg.PHLAX_SWORD.get());
	});

	private final int harvestLevel;
	private final int maxUses;
	private final float efficency;
	private final float attackDammage;
	private final int enchantability;
	private final Supplier<Ingredient>reapairMat;
	
	ModItemTeir(int harvestLevel, int maxUses, float efficency, float attackDammage, int enchantability, Supplier<Ingredient>reapairMat){
		this.harvestLevel = harvestLevel;
		this.maxUses = maxUses;
		this.efficency = efficency;
		this.attackDammage = attackDammage;
		this.enchantability = enchantability;
		this.reapairMat = reapairMat;
	}
	
	@Override
	public int getMaxUses() {
		// TODO Auto-generated method stub
		return maxUses;
	}

	@Override
	public float getEfficiency() {
		// TODO Auto-generated method stub
		return efficency;
	}

	@Override
	public float getAttackDamage() {
		// TODO Auto-generated method stub
		return attackDammage;
	}

	@Override
	public int getHarvestLevel() {
		// TODO Auto-generated method stub
		return	harvestLevel;
	}

	@Override
	public int getEnchantability() {
		// TODO Auto-generated method stub
		return enchantability;
	}

	@Override
	public Ingredient getRepairMaterial() {
		// TODO Auto-generated method stub
		return  reapairMat.get();
	}

}
