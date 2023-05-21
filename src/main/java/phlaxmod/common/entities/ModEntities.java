package phlaxmod.common.entities;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, "phlaxmod");

    public static final RegistryObject<EntityType<EntityMagicOrb>> PHLAX_PROJECTILE = ENTITIES.register(
            "phlax_projectile",
            () -> EntityType.Builder.<EntityMagicOrb>of(EntityMagicOrb::new, EntityClassification.MISC)
                    .sized(0.25f, 0.25f).build("phlax_projectile"));

}
