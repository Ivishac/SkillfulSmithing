package com.ivishac.skillfulsmithing.screen;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class CrucibleMenu extends AbstractContainerMenu {
    private final ItemStack crucibleStack;

    protected CrucibleMenu(int pContainerId, Inventory pPlayerInventory, FriendlyByteBuf pExtraData) {
        this(pContainerId, pPlayerInventory, pPlayerInventory.getItem(pExtraData.readInt()));
    }

    public CrucibleMenu(int pContainerId, Inventory pPlayerInventory, ItemStack crucibleStack) {
        super(ModMenuTypes.CRUCIBLE_MENU.get(), pContainerId);
        this.crucibleStack = crucibleStack;

        this.crucibleStack.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new SlotItemHandler(handler, 0, 80, 17));
            this.addSlot(new SlotItemHandler(handler, 1, 80, 35));
        });

        addPlayerInventory(pPlayerInventory);
        addPlayerHotbar(pPlayerInventory);
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);

        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            // Indices 0 and 1 are the Crucible Inventory (Size 2)
            if (pIndex < 2) {
                // Move FROM Crucible TO Player Inventory (indices 2 to 38)
                if (!this.moveItemStackTo(itemstack1, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
            }
            // Indices 2 to 38 are Player Inventory + Hotbar
            else {
                // Move FROM Player TO Crucible (indices 0 to 2)
                if (!this.moveItemStackTo(itemstack1, 0, 2, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(pPlayer, itemstack1);
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return !crucibleStack.isEmpty();
    }

    private void addPlayerInventory(Inventory pPlayerInventory) {
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(pPlayerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory pPlayerInventory) {
        for(int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(pPlayerInventory, i, 8 + i * 18, 142));
        }
    }
}
