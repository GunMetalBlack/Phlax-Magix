package phlaxmod.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import phlaxmod.PhlaxMod;
import phlaxmod.common.item.ModItems;
import phlaxmod.common.util.CustomEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;

public class TileMithrilArcDynamo extends TileEntity implements ITickableTileEntity {

    private final ItemStackHandler itemStackHandler = createItemStackHandler();
    private final LazyOptional<IItemHandler> optionalItemStackHandler = LazyOptional.of(() -> itemStackHandler);
    public final CustomEnergyStorage energyStorage;
    private final LazyOptional<CustomEnergyStorage> optionalEnergyStorage;

    private int capacity = 500000, maxExtract = capacity;
    private int ticksRemaining = 0, peakTicksRemaining = 0;
    public TileMithrilArcDynamo(TileEntityType<?> p_i48289_1_) {
        super(p_i48289_1_);
        this.energyStorage = createEnergyStorage();
        this.optionalEnergyStorage = LazyOptional.of(() -> this.energyStorage);
    }

    public TileMithrilArcDynamo(){
        this(ModTileEntities.MITHRIL_ARC_DYNAMO_TILE.get());
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.optionalEnergyStorage.invalidate();
    }
    public double getBurnProgress(){
        if(peakTicksRemaining == 0)return 0;
        return ticksRemaining/(double)peakTicksRemaining;
    }
    public double getEnergyBarProgress(){
        return energyStorage.getEnergyStored()/(double)energyStorage.getMaxEnergyStored();
    }
    private CustomEnergyStorage createEnergyStorage(){
        return new CustomEnergyStorage(this.capacity,0,this.maxExtract,this);
    }
    @Override
    public CompoundNBT save(CompoundNBT compound) {
        super.save(compound);
        compound.put("inv", itemStackHandler.serializeNBT());
        compound.putInt("energy",this.energyStorage.getEnergyStored());
        compound.putInt("ticks_remaining",this.ticksRemaining);
        compound.putInt("peak_ticks_remaining",this.peakTicksRemaining);
        return compound;
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        itemStackHandler.deserializeNBT(nbt.getCompound("inv"));
        this.energyStorage.setEnergy(nbt.getInt("energy"));
        ticksRemaining = nbt.getInt("ticks_remaining");
        peakTicksRemaining = nbt.getInt("peak_ticks_remaining");
    }

    public void outputEnergy() {
        if (this.energyStorage.canExtract()) {
            for (final Direction direction : Direction.values()) {
                final TileEntity be = this.level.getBlockEntity(this.worldPosition.relative(direction));
                if (be == null) {
                    continue;
                }

                be.getCapability(CapabilityEnergy.ENERGY, direction.getOpposite()).ifPresent(storage -> {
                    if (be != this && storage.getEnergyStored() < storage.getMaxEnergyStored()) {
                        final int toSend = TileMithrilArcDynamo.this.energyStorage.extractEnergy(this.maxExtract,
                                false);
                        PhlaxMod.logger.info("Send: {}", toSend);
                        final int received = storage.receiveEnergy(toSend, false);
                        PhlaxMod.logger.info("Final Received: {}", received);

                        TileMithrilArcDynamo.this.energyStorage.setEnergy(
                                TileMithrilArcDynamo.this.energyStorage.getEnergyStored() + toSend - received);
                    }
                });
            }
        }
    }

    @Override
    public void tick() {
        if(this.level.isClientSide()) return;

        if(this.ticksRemaining > 0) {
            this.ticksRemaining--;
            this.energyStorage.setEnergy(this.energyStorage.getEnergyStored() + 2000);
        } else {
            if (!this.itemStackHandler.getStackInSlot(0).isEmpty() && energyStorage.getEnergyStored() < energyStorage.getMaxEnergyStored()) {
                this.ticksRemaining = getMaxProgressForFuelGigachadVersion();
                this.peakTicksRemaining = ticksRemaining;
                this.itemStackHandler.getStackInSlot(0).shrink(1);
            }
        }
        outputEnergy();
        //PhlaxMod.logger.info("TickProgress:{} IsClient:{} TickMax:{} CurrentEnergy{} MaxMachineEnergy {}", this.ticksRemaining, this.level.isClientSide, getMaxProgressForFuelGigachadVersion(),this.energyStorage.getEnergyStored(),this.energyStorage.getMaxEnergyStored());
        SUpdateTileEntityPacket updatePacket = this.getUpdatePacket();
        if(updatePacket != null && level != null && level.getServer() != null) {
            level.getServer().getPlayerList().broadcast(null, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(),64, level.dimension(), updatePacket);
        }
    }
    @Override
    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(worldPosition, 1, getUpdateTag());
    }
    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT tag = super.getUpdateTag();
        return save(tag);
    }
    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        if(level != null && level.isClientSide()) {
            handleUpdateTag(getBlockState(), pkt.getTag());
        }
    }

    private ItemStackHandler createItemStackHandler(){
        return new ItemStackHandler(1){
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }
            //What items can you put in the machine
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                switch (slot){
                    case 0: return stack.getItem().equals(ModItems.OIL_BUCKET.get()) || stack.getItem().equals(ModItems.OIL_ORE.get()) || stack.getItem().equals(Items.COAL);
                    default:
                        return false;
                }
            }
            //How many Items of that type can be placed in container
            @Override
            public int getSlotLimit(int slot) {
                return 64;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if(!isItemValid(slot, stack)){
                    return stack;
                }
                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {

        if(cap.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)) {
            return optionalItemStackHandler.cast();
        }
        return cap.equals(CapabilityEnergy.ENERGY) ? this.optionalEnergyStorage.cast() : super.getCapability(cap, side);
    }

    //Checks If an Item is in the first slot and that item is a mana crystal
    private static HashMap<Item, Integer> maxProgressForFuels;
    public int getMaxProgressForFuelGigachadVersion() {
        if (this.itemStackHandler.getStackInSlot(0).isEmpty()) return -1;
        if (maxProgressForFuels == null) {
            maxProgressForFuels = new HashMap<>();
            maxProgressForFuels.put(ModItems.OIL_BUCKET.get(), 5000);
            maxProgressForFuels.put(ModItems.OIL_ORE.get(), 1000);
            maxProgressForFuels.put(Items.COAL, 500);
        }
        return maxProgressForFuels.get(this.itemStackHandler.getStackInSlot(0).getItem());
    }
}


