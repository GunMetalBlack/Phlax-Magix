package phlaxmod.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
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
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import phlaxmod.common.util.CustomEnergyStorage;
import phlaxmod.container.data.recipes.PhlaxModBaseRecipe;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Supplier;

public class TileMachine extends TileEntity implements ITickableTileEntity {

    public final int ENERGY_CONSUMED_PER_ACTIVE_TICK;
    public final int ENERGY_CAPACITY;
    public final int MAX_ENERGY_EXTRACT_PER_TICK;
    public final int MAX_ENERGY_RECEIVE_PER_TICK;
    private final ItemStackHandler itemHandler;
    private final LazyOptional<IItemHandler> handler;
    public final CustomEnergyStorage energyStorage;
    private final LazyOptional<CustomEnergyStorage> optionalEnergyStorage;
    // Machine State Parameters
    public int ticksRemaining = 0, peakTicksRemaining = 0;

    public HashMap<String, Supplier<Double>> guiBarLookUpTable = new HashMap<>();
    public IRecipeType<PhlaxModBaseRecipe> recipeType;

    public TileMachine(TileEntityType<?> tileEntityType, int energyConsumedPerActiveTick, int energyCapacity, int maxEnergyExtractPerTick, int maxEnergyReceivePerTick, int itemSlotCount, int itemSlotLimit, IRecipeType<PhlaxModBaseRecipe> recipeType) {
        super(tileEntityType);
        this.ENERGY_CONSUMED_PER_ACTIVE_TICK = energyConsumedPerActiveTick;
        this.ENERGY_CAPACITY = energyCapacity;
        this.MAX_ENERGY_EXTRACT_PER_TICK = maxEnergyExtractPerTick;
        this.MAX_ENERGY_RECEIVE_PER_TICK = maxEnergyReceivePerTick;
        this.energyStorage = createEnergyStorage();
        this.optionalEnergyStorage = LazyOptional.of(() -> this.energyStorage);
        this.itemHandler = createHandler(itemSlotCount,itemSlotLimit);
        this.handler = LazyOptional.of(() -> itemHandler);
        this.recipeType = recipeType;
    }


    private CustomEnergyStorage createEnergyStorage(){
        return new CustomEnergyStorage(this.ENERGY_CAPACITY,this.MAX_ENERGY_RECEIVE_PER_TICK,this.MAX_ENERGY_EXTRACT_PER_TICK,this);
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

    public void tickEnergyReceive() {
        // Null check
        if(this.level == null) return;
        // Calculate goal for how much energy to retrieve this tick
        int amountToTryToReceive = Math.min(Math.max(this.ENERGY_CAPACITY - this.energyStorage.getEnergyStored(), 0), MAX_ENERGY_RECEIVE_PER_TICK);
        // Iterate through directions to attempt to receive energy from
        for (final Direction direction : Direction.values()) {
            // Check if done - can return early
            if(amountToTryToReceive <= 0) return;
            // Try retrieve blockentity in current direction
            final TileEntity blockEntity = this.level.getBlockEntity(this.worldPosition.relative(direction));
            if (blockEntity == null) continue;
            // Try retrieve IEnergyStorage from found blockentity
            IEnergyStorage externalEnergyStorage = blockEntity.getCapability(CapabilityEnergy.ENERGY, direction.getOpposite()).orElse(null);
            if (externalEnergyStorage == null) continue;
            // Extract and receive as much energy as possible up to amountToTryToReceive
            if(blockEntity != this && externalEnergyStorage.canExtract()) {
                // Extract and receive
                int amountReceived = Math.max(externalEnergyStorage.extractEnergy(amountToTryToReceive, false), 0);
                TileMachine.this.energyStorage.setEnergy(TileMachine.this.energyStorage.getEnergyStored() + amountReceived);
                // Update Goal
                amountToTryToReceive -= amountReceived;
            }
        }
    }

    @Override
    public void tick() {
        // TODO: Only send this if something has actually changed.
        SUpdateTileEntityPacket updatePacket = this.getUpdatePacket();
        if(updatePacket != null && level != null && level.getServer() != null) {
            level.getServer().getPlayerList().broadcast(null, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(),64, level.dimension(), updatePacket);
        }
    }

    public void tickProduce() {
        // Only run on logical server
        if(this.level.isClientSide()) return;
        // If machine is waiting...
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
        // PhlaxMod.logger.info("TickProgress:{} IsClient:{} TickMax:{} CurrentEnergy{} MaxMachineEnergy {}", this.ticksRemaining, this.level.isClientSide, getMaxProgressForFuelGigachadVersion(),this.energyStorage.getEnergyStored(),this.energyStorage.getMaxEnergyStored());

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

    private ItemStackHandler createHandler(int slotCount,int slotLimit){
        return new ItemStackHandler(slotCount){
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
                return slotLimit;
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

    public Optional<PhlaxModBaseRecipe> getCurrentRecipe() {
        // Construct an "Inventory" that contains only the input ItemStack
        Inventory inv = new Inventory(itemHandler.getSlots());
        inv.setItem(0, itemHandler.getStackInSlot(0).copy());
        // Lookup recipe matching inventory
        Optional<PhlaxModBaseRecipe> recipe = level.getRecipeManager().getRecipeFor(recipeType, inv, level);
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
        PhlaxModBaseRecipe recipe = getCurrentRecipe().orElse(null);
        return recipe == null ? 0 : recipe.productTime;
    }

}
