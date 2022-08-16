package phlaxmod.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
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
import phlaxmod.common.util.CustomEnergyStorage;
import phlaxmod.data.recipes.CrystallizerRecipe;
import phlaxmod.data.recipes.ModRecipeTypes;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class TileCrystalizer extends TileEntity implements ITickableTileEntity {

    public static final int ENERGY_CONSUMED_PER_ACTIVE_TICK = 70;

    private ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    public final CustomEnergyStorage energyStorage;

    private final LazyOptional<CustomEnergyStorage> optionalEnergyStorage;

    private int capacity = 500, maxExtract = 0, maxRecive = capacity/2;

    private int ticksRemaining = 0, peakTicksRemaining = 0;

    public TileCrystalizer(TileEntityType<?> p_i48289_1_) {
        super(p_i48289_1_);
        this.energyStorage = createEnergyStorage();
        this.optionalEnergyStorage = LazyOptional.of(() -> this.energyStorage);
    }
    public double getProductProgress(){
        // ticksRemaining == 0 is ambiguous between "idle" and "done" - assuming "idle".
        if(ticksRemaining == 0) return 0;
        // prevent dividing by zero
        if(peakTicksRemaining == 0) return 0;
        // complement of the ratio of ticksRemaining to peakTicksRemaining
        return 1 - (ticksRemaining/(double)peakTicksRemaining);
    }
    private CustomEnergyStorage createEnergyStorage(){
        return new CustomEnergyStorage(this.capacity,this.maxReceive,this.maxExtract,this);
    }
    public TileCrystalizer(){
        this(ModTileEntities.CRYSTALIZER_TILE.get());
    }


    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.optionalEnergyStorage.invalidate();
    }
    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inv"));
        this.energyStorage.setEnergy(nbt.getInt("energy"));
        ticksRemaining = nbt.getInt("ticks_remaining");
        peakTicksRemaining = nbt.getInt("peak_ticks_remaining");
    }
    @Override
    public CompoundNBT save(CompoundNBT compound) {
        super.save(compound);
        compound.put("inv", itemHandler.serializeNBT());
        compound.putInt("energy",this.energyStorage.getEnergyStored());
        compound.putInt("ticks_remaining",this.ticksRemaining);
        compound.putInt("peak_ticks_remaining",this.peakTicksRemaining);
        return compound;
    }
    //Handles the input and output of power in the machine
    public void tickEnergy() {
        int amountToReceive = Math.min(Math.max(this.capacity - this.energyStorage.getEnergyStored(),0), maxReceive);
        TileCrystalizer.this.energyStorage.setEnergy(
                TileCrystalizer.this.energyStorage.getEnergyStored() + this.energyStorage.receiveEnergy(amountToReceive,false));
        }
    @Override
    public void tick() {
        // Only run on logical server
        if(this.level.isClientSide()) return;
        // If machine is waiting... DEAR LORD WHAT HAVE YOU DONE
        if(this.ticksRemaining > 0) {
            // Confirm that input and energy are still present
            if(this.itemHandler.getStackInSlot(0).isEmpty() || energyStorage.getEnergyStored() <= 0){
                ticksRemaining = 0;
                return;
            }
            // Decrement timer
            this.ticksRemaining--;
            // Consume energy
            this.energyStorage.setEnergy(this.energyStorage.getEnergyStored() - ENERGY_CONSUMED_PER_ACTIVE_TICK);
            // If production is done... (This check needs to be nested to make sure that it only fires when the machine is "done" and not just "idle", as ticksRemaining == 0 is ambiguous. No U)
            if(this.ticksRemaining == 0) {
                // Produce output and consume input
                produceUsingCurrentRecipe();
            }
        }
        // If machine is idle...
        else {
            // Lookup recipe for current input. If a recipe is found, start countdown based on "producttime" variable within recipe.
            int productionTimeForCurrentRecipe = getProductionTimeForCurrentRecipe();
            // Assign current and peak ticks remaining to production time of recipe
            this.ticksRemaining = productionTimeForCurrentRecipe;
            this.peakTicksRemaining = productionTimeForCurrentRecipe;
        }
        // TODO: Why does this thing output energy? Seems like it should just be a receiver...
        tickEnergy();
        // PhlaxMod.logger.info("TickProgress:{} IsClient:{} TickMax:{} CurrentEnergy{} MaxMachineEnergy {}", this.ticksRemaining, this.level.isClientSide, getMaxProgressForFuelGigachadVersion(),this.energyStorage.getEnergyStored(),this.energyStorage.getMaxEnergyStored());

        // TODO: Only send this if something has actually changed.
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

    private ItemStackHandler createHandler(){
        return new ItemStackHandler(2){
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }
            //What items can you put in the machine
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return true;
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
        return cap.equals(CapabilityEnergy.ENERGY) ? this.optionalEnergyStorage.cast() : super.getCapability(cap, side);
    }

    public Optional<CrystallizerRecipe> getCurrentRecipe() {
        // Construct an "Inventory" that contains only the input ItemStack
        Inventory inv = new Inventory(itemHandler.getSlots());
        inv.setItem(0, itemHandler.getStackInSlot(0));
        // Lookup recipe matching inventory
        Optional<CrystallizerRecipe> recipe = level.getRecipeManager().getRecipeFor(ModRecipeTypes.CRYSTALLIZER_RECIPE, inv, level);
        // Return result
        return recipe;
    }

    // Executes current recipe, if one is present.
    public void produceUsingCurrentRecipe() {
        getCurrentRecipe().ifPresent(iRecipe -> {
            ItemStack output = iRecipe.getResultItem();
            produce(output);
        });
    }

    private void produce(ItemStack output) {
        itemHandler.extractItem(0, 1, false);
        itemHandler.insertItem(1, output, false);
        setChanged();
    }

    public int getProductionTimeForCurrentRecipe() {
        CrystallizerRecipe recipe = getCurrentRecipe().orElse(null);
        return recipe == null ? 0 : recipe.productTime;
   }

}
