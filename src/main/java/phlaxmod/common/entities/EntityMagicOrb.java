package phlaxmod.common.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion.Mode;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;
import phlaxmod.common.capability.PhlaxModCapabilities;
import phlaxmod.common.capability.phlaxplayerdataholder.IPhlaxPlayerDataHolderCapability;
import phlaxmod.common.item.ModItems;
import phlaxmod.common.spells.Spell;

@OnlyIn(
        value = Dist.CLIENT,
        _interface = IRendersAsItem.class
)
public class EntityMagicOrb extends ThrowableEntity implements IRendersAsItem {

    public PlayerEntity player;

    public EntityMagicOrb(EntityType<? extends ThrowableEntity> entityType, World world) {
        super(entityType, world);
    }

    public EntityMagicOrb(World world, PlayerEntity player) {
        this(ModEntities.PHLAX_PROJECTILE.get(), world);
        this.player = player;
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
            IPhlaxPlayerDataHolderCapability playerData = this.player.getCapability(PhlaxModCapabilities.PLAYER_DATA_HOLDER_CAPABILITY).orElse(null);
            if(playerData.getCurrentSpell() == Spell.SMALL_EXPLOSIVE)
            {
                level.explode(player, getX(), getY(), getZ(), 20, Mode.DESTROY);
            }
            else if(playerData.getCurrentSpell() == Spell.STRIKE)
            {
                if(result instanceof EntityRayTraceResult)
                {
                    EntityRayTraceResult entityRayTraceResult = (EntityRayTraceResult) result;
                    if(entityRayTraceResult.getEntity() instanceof LivingEntity)
                    {
                        LivingEntity livingEntity = (LivingEntity) entityRayTraceResult.getEntity();
                        //No worries my Friend Hurt Funny man
                        livingEntity.hurt(DamageSource.GENERIC, Spell.STRIKE.damage);
                    }
                }
            }

        }
        this.remove();
    }

    @Override
    public ItemStack getItem() {
        return ModItems.WAND_PROJECTILE.get().getDefaultInstance();
    }

}
