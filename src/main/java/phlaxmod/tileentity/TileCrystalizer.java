package phlaxmod.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import phlaxmod.common.item.ModItems;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileCrystalizer extends TileEntity {

    private ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    public TileCrystalizer(TileEntityType<?> p_i48289_1_) {
        super(p_i48289_1_);
    }

    public TileCrystalizer(){
        this(ModTileEntities.CRYSTALIZER_TILE.get());
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        itemHandler.deserializeNBT(nbt.getCompound("inv"));
        super.load(state, nbt);
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        compound.put("inv", itemHandler.serializeNBT());
        return super.save(compound);
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
                    case 1: return  stack.getItem().equals(ModItems.CHARGED_CITRINE.get());
                    default:
                        return false;
                }
            }
            //How many Items of that type can be placed in container
            @Override
            public int getSlotLimit(int slot) {
                return 1;
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
        return super.getCapability(cap, side);
    }

    //Checks If an Item is in the first slot and that item is a mana crystal
    public void createOutputFromInput() {
        boolean hasManaCrystal = this.itemHandler.getStackInSlot(0).getCount() > 0
                && this.itemHandler.getStackInSlot(0).getItem().equals(ModItems.MANA_CRYSTAL.get());
        boolean hasCitrineCrystal = this.itemHandler.getStackInSlot(0).getCount() > 0
                && this.itemHandler.getStackInSlot(0).getItem().equals(ModItems.CITRINE_ORE.get());
        //TODO ADD CHARGED MANA CRYSTAL HERE
        if(hasCitrineCrystal){
            this.itemHandler.getStackInSlot(0).shrink(1);
            this.itemHandler.insertItem(1, new ItemStack(ModItems.CHARGED_CITRINE.get()),false);
        }
    }

}