package com.ivishac.skillfulsmithing.block.entity;

import com.ivishac.skillfulsmithing.block.ModBlocks;
import com.ivishac.skillfulsmithing.block.custom.MoldTable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

public class MoldTableBlockEntity extends BlockEntity {

    private final ItemStackHandler itemHandler = new ItemStackHandler(1);

    private static final int SLOT_COUNT = 1;

    public MoldTableBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.MOLD_TABLE_BE.get(), pPos, pBlockState);
    }

    public boolean insertFireClay(ItemStack playerStack) {
        if (playerStack.isEmpty() || !playerStack.is(ModBlocks.FIRE_CLAY.get().asItem())) {
            return false;
        }

        ItemStack slotStack = itemHandler.getStackInSlot(0);
        if (slotStack.isEmpty()) {
            // Store the *same* sand type that the player used (sand OR red sand),
            // but only 1 item.
            ItemStack oneFireClay = playerStack.copy();
            oneFireClay.setCount(1);

            itemHandler.setStackInSlot(0, oneFireClay);
            playerStack.shrink(1);
            setChanged();

            //Mark Block as filled
            if(this.level != null) {
                BlockState state = this.level.getBlockState(this.worldPosition);
                if(state.getBlock() instanceof MoldTable) {
                    this.level.setBlock(this.worldPosition, state.setValue(MoldTable.FILLED, Boolean.TRUE), 3);
                }
            }
            return true;
        }
        return false;
    }

    public boolean removeFireClay(Player pPlayer) {
        ItemStack slotStack = itemHandler.getStackInSlot(0);

        if (slotStack.isEmpty()) {
            return false;
        }

        // Give back the exact stack from the table
        ItemStack toGive = slotStack.copy();
        itemHandler.setStackInSlot(0, ItemStack.EMPTY);
        setChanged();

        if (!pPlayer.addItem(toGive) && this.level != null) {
            pPlayer.drop(toGive, false);
        }

        //Mark Block as empty
        if(this.level != null) {
            BlockState state = this.level.getBlockState(this.worldPosition);
            if(state.getBlock() instanceof MoldTable) {
                this.level.setBlock(this.worldPosition, state.setValue(MoldTable.FILLED, Boolean.FALSE), 3);
            }
        }

        return true;
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

