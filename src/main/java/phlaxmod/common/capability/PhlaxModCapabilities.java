package phlaxmod.common.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import phlaxmod.common.capability.phlaxplayerdataholder.IPhlaxPlayerDataHolderCapability;
import phlaxmod.common.capability.phlaxplayerdataholder.PhlaxPlayerDataHolderCapability;

public class PhlaxModCapabilities {

    private PhlaxModCapabilities() {}

    @CapabilityInject(IPhlaxPlayerDataHolderCapability.class)
    public static Capability<IPhlaxPlayerDataHolderCapability> PLAYER_DATA_HOLDER_CAPABILITY;

    public static void register() {
        PhlaxPlayerDataHolderCapability.register();
    }

}
