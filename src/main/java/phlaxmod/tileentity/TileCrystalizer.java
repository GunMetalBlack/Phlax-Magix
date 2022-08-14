package phlaxmod.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.inventory.Inventory;
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
import org.apache.commons.lang3.ObjectUtils;
import phlaxmod.PhlaxMod;
import phlaxmod.common.item.ModItems;
import phlaxmod.common.util.CustomEnergyStorage;
import phlaxmod.data.recipes.CrystallizerRecipe;
import phlaxmod.data.recipes.ModRecipeTypes;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Optional;

public class TileCrystalizer extends TileEntity implements ITickableTileEntity {

    private ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    public final CustomEnergyStorage energyStorage;

    private final LazyOptional<CustomEnergyStorage> optionalEnergyStorage;

    private int capacity = 500, maxExtract = capacity, maxRecive = capacity/2;

    private int ticksRemaining = 0, peakTicksRemaining = 0;

    public TileCrystalizer(TileEntityType<?> p_i48289_1_) {
        super(p_i48289_1_);
        this.energyStorage = createEnergyStorage();
        this.optionalEnergyStorage = LazyOptional.of(() -> this.energyStorage);
    }
    public double getProductProgress(){
        if(peakTicksRemaining == 0)return 0;
        return ticksRemaining/(double)peakTicksRemaining;
    }
    private CustomEnergyStorage createEnergyStorage(){
        return new CustomEnergyStorage(this.capacity,this.maxRecive,this.maxExtract,this);
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
    public void outputEnergy() {
        if (this.energyStorage.canExtract()) {
            for (final Direction direction : Direction.values()) {
                final TileEntity be = this.level.getBlockEntity(this.worldPosition.relative(direction));
                if (be == null) {
                    continue;
                }

                be.getCapability(CapabilityEnergy.ENERGY, direction.getOpposite()).ifPresent(storage -> {
                    if (be != this && storage.getEnergyStored() < storage.getMaxEnergyStored()) {
                        final int toSend = TileCrystalizer.this.energyStorage.extractEnergy(this.maxExtract,
                                false);
                        PhlaxMod.logger.info("Send: {}", toSend);
                        final int received = storage.receiveEnergy(toSend,true);
                        PhlaxMod.logger.info("Final Received: {}", received);

                        TileCrystalizer.this.energyStorage.setEnergy(
                                TileCrystalizer.this.energyStorage.getEnergyStored() + toSend - received);
                    }
                });
            }
        }
    }
    @Override
    public void tick() {
        if(this.level.isClientSide()) return;

        if(this.ticksRemaining > 0) {
            if(this.itemHandler.getStackInSlot(0).isEmpty()){
                ticksRemaining = 0;
                return;
            }
            this.ticksRemaining--;
        } else {
            if (!this.itemHandler.getStackInSlot(0).isEmpty() &&  0 < energyStorage.getEnergyStored()) {
                this.energyStorage.setEnergy(this.energyStorage.getEnergyStored() - 100);
                this.ticksRemaining = getMaxProgressForFuelGigachadVersion();
                createOutputFromInput();
                this.peakTicksRemaining = ticksRemaining;
            }
        }
        outputEnergy();
       // PhlaxMod.logger.info("TickProgress:{} IsClient:{} TickMax:{} CurrentEnergy{} MaxMachineEnergy {}", this.ticksRemaining, this.level.isClientSide, getMaxProgressForFuelGigachadVersion(),this.energyStorage.getEnergyStored(),this.energyStorage.getMaxEnergyStored());
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
                switch (slot){
                    case 0: return stack.getItem().equals(ModItems.MANA_CRYSTAL.get()) || stack.getItem().equals(ModItems.CITRINE_CRYSTAL.get());
                    case 1: return false;
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
        return cap.equals(CapabilityEnergy.ENERGY) ? this.optionalEnergyStorage.cast() : super.getCapability(cap, side);
    }

    //Checks If an Item is in the first slot and that item is a mana crystal
    public void createOutputFromInput() {
        Inventory inv = new Inventory(itemHandler.getSlots());
            inv.setItem(0, itemHandler.getStackInSlot(0));

        Optional<CrystallizerRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(ModRecipeTypes.CRYSTALLIZER_RECIPE, inv, level);

        recipe.ifPresent(iRecipe -> {
            ItemStack output = iRecipe.getResultItem();

            if(ticksRemaining <= 0) {
                craftTheItem(output);
            }
            setChanged();
        });
    }
    private void craftTheItem(ItemStack output) {
        itemHandler.extractItem(0, 1, false);
        itemHandler.insertItem(1, output, false);
    }
    //Checks If an Item is in the first slot and that item is a mana crystal
    public int getMaxProgressForFuelGigachadVersion() {
        Inventory inv = new Inventory(itemHandler.getSlots());
        inv.setItem(0, itemHandler.getStackInSlot(0));
        CrystallizerRecipe recipe = level.getRecipeManager()
                .getRecipeFor(ModRecipeTypes.CRYSTALLIZER_RECIPE, inv, level).orElse(null);
        if(recipe == null){
          //  PhlaxMod.logger.debug("Ballz No Recipes Here");
            return 0;
        }else{
           // PhlaxMod.logger.debug("Display Product Time",recipe.productTime);
            return recipe.productTime;
        }

   }
}
