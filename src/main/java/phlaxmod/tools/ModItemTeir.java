package phlaxmod.tools;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import phlaxmod.common.item.ModItems;

import java.util.function.Supplier;

public enum ModItemTeir implements IItemTier{
	PHLAX(3, 450, 7.0F, 3.0f, 12, () -> {
		
		return Ingredient.of(ModItems.PHLAX_SWORD.get());
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
	public int getUses() {
		// TODO Auto-generated method stub
		return maxUses;
	}

	@Override
	public float getSpeed() {
		// TODO Auto-generated method stub
		return efficency;
	}

	@Override
	public float getAttackDamageBonus() {
		// TODO Auto-generated method stub
		return attackDammage;
	}

	@Override
	public int getLevel() {
		// TODO Auto-generated method stub
		return	harvestLevel;
	}

	@Override
	public int getEnchantmentValue() {
		// TODO Auto-generated method stub
		return enchantability;
	}

	@Override
	public Ingredient getRepairIngredient() {
		// TODO Auto-generated method stub
		return  reapairMat.get();
	}

}
