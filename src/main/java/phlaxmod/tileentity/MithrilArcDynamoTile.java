package phlaxmod.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import phlaxmod.DifReg;
import phlaxmod.PhlaxMod;
import phlaxmod.common.block.util.CustomEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MithrilArcDynamoTile extends TileEntity implements ITickableTileEntity {

    private ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    public final CustomEnergyStorage energyStorage;

    private int capacity = 2000, maxExtract = 100;
    private int progress, maxProgress = 0;
    private LazyOptional<CustomEnergyStorage> energy;
    public MithrilArcDynamoTile(TileEntityType<?> p_i48289_1_) {
        super(p_i48289_1_);
        this.energyStorage = createEnergyStorage();
        this.energy = LazyOptional.of(() -> this.energyStorage);
    }

    public MithrilArcDynamoTile(){
        this(ModTileEntities.MITHRIL_ARC_DYNAMO_TILE.get());
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.energy.invalidate();
    }
    public int getEnergyForStack(ItemStack stack) {
        return ForgeHooks.getBurnTime(stack, IRecipeType.SMELTING);
    }
    public int getMaxProgress() {
        return this.maxProgress;
    }

    public int getProgress() {
        return this.progress;
    }
    private CustomEnergyStorage createEnergyStorage(){
        return new CustomEnergyStorage(this.capacity,0,this.maxExtract,this);
    }
    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        itemHandler.deserializeNBT(nbt.getCompound("inv"));
        this.energyStorage.setEnergy(nbt.getInt("Energy"));
        super.load(state, nbt);
    }

    public void outputEnergy() {
        if (this.energyStorage.getEnergyStored() >= this.maxExtract && this.energyStorage.canExtract()) {
            for (final Direction direction : Direction.values()) {
                final TileEntity be = this.level.getBlockEntity(this.worldPosition.relative(direction));
                if (be == null) {
                    continue;
                }

                be.getCapability(CapabilityEnergy.ENERGY, direction.getOpposite()).ifPresent(storage -> {
                    if (be != this && storage.getEnergyStored() < storage.getMaxEnergyStored()) {
                        final int toSend = MithrilArcDynamoTile.this.energyStorage.extractEnergy(this.maxExtract,
                                false);
                        PhlaxMod.logger.info("Send: {}", toSend);
                        final int received = storage.receiveEnergy(toSend, false);
                        PhlaxMod.logger.info("Final Received: {}", received);

                        MithrilArcDynamoTile.this.energyStorage.setEnergy(
                                MithrilArcDynamoTile.this.energyStorage.getEnergyStored() + toSend - received);
                    }
                });
            }
        }
    }

    @Override
    public void tick() {
        if(this.level.isClientSide()){return;}


        if (this.energyStorage.getEnergyStored() <= this.energyStorage.getMaxEnergyStored() - 100) {
            if (!this.itemHandler.getStackInSlot(0).isEmpty() && (this.progress <= this.maxProgress )) {
                this.maxProgress = getEnergyForStack(this.itemHandler.getStackInSlot(0));
                this.progress++;
            }if(this.maxProgress <= this.progress ){
                this.itemHandler.getStackInSlot(0).shrink(1);
                this.progress = 0;
                this.energyStorage.setEnergy(this.energyStorage.getEnergyStored() + this.maxProgress);
            }

        }
        PhlaxMod.logger.info("TickProgress{}{}", this.progress,this.level.isClientSide);
        outputEnergy();
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        compound.put("inv", itemHandler.serializeNBT());
        compound.putInt("Energy",this.energyStorage.getEnergyStored());
        return super.save(compound);
    }


    private ItemStackHandler createHandler(){
        return new ItemStackHandler(1){
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }
            //What items can you put in the machine
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                switch (slot){
                    case 0: return stack.getItem() == DifReg.OIL_BUCKET.get() || stack.getItem() == DifReg.OIL_DROP.get() || stack.getItem() == Items.COAL;
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
            return handler.cast();
        }
        return cap == CapabilityEnergy.ENERGY ? this.energy.cast() : super.getCapability(cap, side);
    }

    //Checks If an Item is in the first slot and that item is a mana crystal
    public void createOutputFromInput() {
        boolean hasFuel = this.itemHandler.getStackInSlot(0).getCount() > 0
                && this.itemHandler.getStackInSlot(0).getItem() == DifReg.OIL_BUCKET.get() || this.itemHandler.getStackInSlot(0).getItem() == DifReg.OIL_DROP.get() || this.itemHandler.getStackInSlot(0).getItem() == Items.COAL;
        if(hasFuel){
            this.itemHandler.getStackInSlot(0).shrink(1);
        }
    }

}
