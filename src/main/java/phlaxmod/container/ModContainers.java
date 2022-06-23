package phlaxmod.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import phlaxmod.PhlaxMod;

public class ModContainers {

    @FunctionalInterface
    public interface IPhlaxModContainerConstructor {
        Container construct(int windowID, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player);
    }

    public static final DeferredRegister<ContainerType<?>> CONTAINERS =
            DeferredRegister.create(ForgeRegistries.CONTAINERS, PhlaxMod.MODID);
    //pain o_o
    public static final RegistryObject<ContainerType<ContainerCrystalizer>> CRYSTALIZER_CONTAINER
            = CONTAINERS.register("crystallizer_container",
            () -> IForgeContainerType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                World world = inv.player.getCommandSenderWorld();
                return new ContainerCrystalizer(windowId, world, pos, inv, inv.player);
            })));
    public static final RegistryObject<ContainerType<ContainerMithrilArcDynamo>> MITHRIL_ARC_DYNAMO_CONTAINER
            = CONTAINERS.register("mithril_arc_dynamo_container",
            () -> IForgeContainerType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                World world = inv.player.getCommandSenderWorld();
                return new ContainerMithrilArcDynamo(windowId, world, pos, inv, inv.player);
            })));

}
