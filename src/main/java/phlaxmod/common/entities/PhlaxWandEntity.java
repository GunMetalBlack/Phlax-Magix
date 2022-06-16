package phlaxmod.common.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion.Mode;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import phlaxmod.DifReg;

public class PhlaxWandEntity extends ThrowableEntity implements IRendersAsItem {

    public PlayerEntity player;

    public PhlaxWandEntity(EntityType<? extends ThrowableEntity> entityType, World world) {
        super(entityType, world);
    }

    public PhlaxWandEntity(World world) {
        this(DifReg.Phlax_Projectile.get(), world);
    }

    @Override
    public void tick() {
        world.addParticle(ParticleTypes.DRIPPING_OBSIDIAN_TEAR, getPosX(), getPosY(), getPosZ(), 0, 0, 0);
        super.tick();
    }

    @Override
    protected void registerData() {}

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void onImpact(RayTraceResult result) {
        if (!world.isRemote) {
            world.createExplosion(player, getPosX(), getPosY(), getPosZ(), 5, Mode.DESTROY);
        }
        this.remove();
    }

    @Override
    public ItemStack getItem() {
        return DifReg.WAND_PROJECTILE.get().getDefaultInstance();
    }

}
