package phlaxmod.common.util;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.energy.EnergyStorage;

public class CustomEnergyStorage extends EnergyStorage {
    private final TileEntity tileEntity;
    public CustomEnergyStorage(TileEntity tileEntity, int capacity) {
        super(capacity);
        this.tileEntity = tileEntity;
    }

    public CustomEnergyStorage(int capacity, int maxTransfer, TileEntity tileEntity) {
        super(capacity, maxTransfer);
        this.tileEntity = tileEntity;
    }

    public CustomEnergyStorage(int capacity, int maxReceive, int maxExtract, TileEntity tileEntity) {
        super(capacity, maxReceive, maxExtract);
        this.tileEntity = tileEntity;
    }

    public CustomEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy, TileEntity tileEntity) {
        super(capacity, maxReceive, maxExtract, energy);
        this.tileEntity = tileEntity;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        this.tileEntity.setChanged();
        return super.extractEnergy(maxExtract, simulate);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        this.tileEntity.setChanged();
        return super.receiveEnergy(maxReceive, simulate);
    }

    public void setEnergy(int energy){
    this.energy = Math.max(0,Math.min(energy,this.capacity));
    }
}
