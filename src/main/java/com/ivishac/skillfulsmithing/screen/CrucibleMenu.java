package com.ivishac.skillfulsmithing.screen;

import com.ivishac.skillfulsmithing.item.custom.CrucibleItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.ArrayList;
import java.util.List;

public class CrucibleMenu extends AbstractContainerMenu {

    private final Inventory playerInventory;
    private final int crucibleSlotIndex;
    private final ItemStack crucibleStack;
    private final ItemStackHandler handler;

    // Client ctor
    public CrucibleMenu(int containerId, Inventory playerInv, FriendlyByteBuf extraData) {
        this(containerId, playerInv, extraData.readInt());
    }

    // Shared ctor
    public CrucibleMenu(int containerId, Inventory playerInv, int crucibleSlotIndex) {
        super(ModMenuTypes.CRUCIBLE_MENU.get(), containerId);
        this.playerInventory = playerInv;
        this.crucibleSlotIndex = crucibleSlotIndex;
        this.crucibleStack = playerInv.getItem(crucibleSlotIndex);

        // --- handler now writes back to NBT on EVERY change ---
        this.handler = new ItemStackHandler(2) {
            @Override
            public int getSlotLimit(int slot) {
                return 64; // or 1 if you want single-item slots
            }

            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);

                // Only do NBT updates on the server side
                if (!playerInventory.player.level().isClientSide) {
                    List<ItemStack> contents = new ArrayList<>();
                    for (int i = 0; i < getSlots(); i++) {
                        contents.add(getStackInSlot(i));
                    }

                    // Write current handler contents into the crucible's NBT
                    CrucibleItem.setContents(crucibleStack, contents);

                    // Make sure the player inventory slot gets updated & marked dirty
                    playerInventory.setItem(crucibleSlotIndex, crucibleStack);
                    playerInventory.setChanged();
                }
            }
        };

        // Load NBT contents into handler once on open
        List<ItemStack> contents = CrucibleItem.getContents(crucibleStack);
        for (int i = 0; i < handler.getSlots(); i++) {
            if (i < contents.size()) {
                handler.setStackInSlot(i, contents.get(i));
            } else {
                handler.setStackInSlot(i, ItemStack.EMPTY);
            }
        }

        // Crucible slots
        this.addSlot(new SlotItemHandler(handler, 0, 80, 17));
        this.addSlot(new SlotItemHandler(handler, 1, 80, 35));

        addPlayerInventory(playerInv);
        addPlayerHotbar(playerInv);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack stackInSlot = slot.getItem();
            itemstack = stackInSlot.copy();

            int crucibleSlots = 2; // 0–1
            int invStart = crucibleSlots;
            int invEnd = this.slots.size();

            if (index < crucibleSlots) {
                // From crucible → player
                if (!this.moveItemStackTo(stackInSlot, invStart, invEnd, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                // From player → crucible
                if (!this.moveItemStackTo(stackInSlot, 0, crucibleSlots, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stackInSlot.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stackInSlot.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, stackInSlot);
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(Player player) {
        ItemStack current = playerInventory.getItem(crucibleSlotIndex);
        return !current.isEmpty() && current.getItem() instanceof CrucibleItem;
    }

    private void addPlayerInventory(Inventory inv) {
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(inv, col + row * 9 + 9,
                        8 + col * 18, 84 + row * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory inv) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(inv, i,
                    8 + i * 18, 142));
        }
    }
}