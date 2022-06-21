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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;
import phlaxmod.DifReg;

@OnlyIn(
        value = Dist.CLIENT,
        _interface = IRendersAsItem.class
)
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
        level.addParticle(ParticleTypes.DRIPPING_OBSIDIAN_TEAR, getX(), getY(), getZ(), 0, 0, 0);
        super.tick();
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void onHit(RayTraceResult result) {
        if (!level.isClientSide) {
            level.explode(player, getX(), getY(), getZ(), 20, Mode.DESTROY);
        }
        this.remove();
    }

    @Override
    public ItemStack getItem() {
        return DifReg.WAND_PROJECTILE.get().getDefaultInstance();
    }

}
