package phlaxmod.tileentity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import phlaxmod.DifReg;
import phlaxmod.PhlaxMod;

public class ModTileEntities {
    //May God Have Mercy On my Soul

    public static DeferredRegister<TileEntityType<?>> TILE_ENTITES =
            DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, PhlaxMod.MODID);

    public static RegistryObject<TileEntityType<CrystalizerTile>> CRYSTALIZER_TILE =
            TILE_ENTITES.register("crystallizer_tile", () -> TileEntityType.Builder.of( CrystalizerTile::new, DifReg.CRYSTALLIZER.get()).build(null));

    public static void register(IEventBus ebus){
        TILE_ENTITES.register(ebus);
    }

}
