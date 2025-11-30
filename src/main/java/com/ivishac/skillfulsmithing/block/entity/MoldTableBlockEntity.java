package com.ivishac.skillfulsmithing.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import net.minecraftforge.items.ItemStackHandler;

public class MoldTableBlockEntity extends BlockEntity {

    private final ItemStackHandler itemHandler = new ItemStackHandler(1);

    private static final int SLOT_COUNT = 1;

    public MoldTableBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.MOLD_TABLE_BE.get(), pPos, pBlockState);
    }

    public boolean insertOneSand(ItemStack playerStack) {
        if(playerStack.isEmpty() || !playerStack.is(Tags.Items.SAND)) {
            return false;
        }

        ItemStack slotStack = itemHandler.getStackInSlot(0);
        if(slotStack.isEmpty()) {
            itemHandler.setStackInSlot(0, new ItemStack(Items.SAND, 1));
            playerStack.shrink(1);
            setChanged();
            return true;
        } else if(slotStack.is(Items.SAND) && slotStack.getCount() < slotStack.getMaxStackSize()) {
            slotStack.grow(1);
            playerStack.shrink(1);
            setChanged();
            return true;
        }
        return false;
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i)); // FIX: use i, not 1
        }
        if (this.level != null) {
            Containers.dropContents(this.level, this.worldPosition, inventory);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("inventory", itemHandler.serializeNBT());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if(tag.contains("inventory")) {
            itemHandler.deserializeNBT(tag.getCompound("inventory"));
        }
    }
}

