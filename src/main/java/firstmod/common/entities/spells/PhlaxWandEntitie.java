package firstmod.common.entities.spells;

import firstmod.DifReg;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion.Mode;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class PhlaxWandEntitie extends ThrowableEntity implements IRendersAsItem{
	
	@Override
		public void tick() {
			world.addParticle(ParticleTypes.DRIPPING_OBSIDIAN_TEAR	, getPosX(), getPosY(), getPosZ(), 0, 0, 0);
			super.tick();
		}
	
	public static ItemStack nstack;
	public PhlaxWandEntitie(EntityType<? extends ThrowableEntity> p_i231584_1_, World p_i231584_2_) {
		super(p_i231584_1_, p_i231584_2_);
	
	}
	public PhlaxWandEntitie(World world) {
		this(DifReg.Phlax_Projectile.get(), world);
	
	}

	@Override
	protected void registerData() {
		
		
	}

	@Override
	public IPacket<?> createSpawnPacket() {
	
		return NetworkHooks.getEntitySpawningPacket(this);
	
	}
	@Override
	public void onImpact(RayTraceResult result) 
	{
	if(!world.isRemote) {
		world.createExplosion(this, getPosX(), getPosY(), getPosZ(), 10, Mode.DESTROY);
	}
		if(ticksExisted>20) {
		this.remove();	
	}
	
	}
	@Override
	public ItemStack getItem() {
		
		return nstack = new ItemStack(DifReg.wand_projectile);
	}
	
}
