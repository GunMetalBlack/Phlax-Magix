package phlaxmod.common.capability.phlaxplayerdataholder;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import phlaxmod.common.capability.PhlaxModCapabilities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PhlaxPlayerDataHolderCapabilityProvider implements ICapabilitySerializable<INBT> {

    private IPhlaxPlayerDataHolderCapability capabilityInstance = new PhlaxPlayerDataHolderCapability();

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction side) {
        return PhlaxModCapabilities.PLAYER_DATA_HOLDER_CAPABILITY.orEmpty(capability, LazyOptional.of(()-> this.capabilityInstance));
    }

    @Override
    public net.minecraft.nbt.INBT serializeNBT() {
        return PhlaxModCapabilities.PLAYER_DATA_HOLDER_CAPABILITY.getStorage().writeNBT(PhlaxModCapabilities.PLAYER_DATA_HOLDER_CAPABILITY, capabilityInstance, null);
    }

    @Override
    public void deserializeNBT(net.minecraft.nbt.INBT nbt) {
        PhlaxModCapabilities.PLAYER_DATA_HOLDER_CAPABILITY.getStorage().readNBT(PhlaxModCapabilities.PLAYER_DATA_HOLDER_CAPABILITY, capabilityInstance, null, nbt);
    }

}
