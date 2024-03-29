package phlaxmod.tileentity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import phlaxmod.PhlaxMod;
import phlaxmod.common.block.ModBlocks;

public class ModTileEntities {
    //May God Have Mercy On my Soul

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITES =
            DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, PhlaxMod.MODID);

    public static final RegistryObject<TileEntityType<? extends TileMachine>> CRYSTALIZER_TILE =
            TILE_ENTITES.register("crystallizer_tile", () -> TileEntityType.Builder.of(TileCrystallizer::new , ModBlocks.CRYSTALLIZER.get()).build(null));
    public static final RegistryObject<TileEntityType<? extends TileMachine>> ARCANE_CONDENSER_TILE =
            TILE_ENTITES.register("arcane_condenser_tile", () -> TileEntityType.Builder.of(TileArcaneCondenser::new , ModBlocks.ARCANE_CONDENSER.get()).build(null));
    public static final RegistryObject<TileEntityType<TileMithrilArcDynamo>> MITHRIL_ARC_DYNAMO_TILE =
            TILE_ENTITES.register("mithril_arc_dynamo", () -> TileEntityType.Builder.of( TileMithrilArcDynamo::new, ModBlocks.MITHRIL_ARC_DYNAMO.get()).build(null));

}
