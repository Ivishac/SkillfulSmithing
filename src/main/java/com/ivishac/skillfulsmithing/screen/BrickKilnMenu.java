package com.ivishac.skillfulsmithing.screen;

import com.ivishac.skillfulsmithing.block.ModBlocks;
import com.ivishac.skillfulsmithing.block.entity.BrickKilnBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class BrickKilnMenu extends AbstractContainerMenu {

    private final BrickKilnBlockEntity kiln;
    private final ContainerLevelAccess access;
    private final BlockPos kilnPos;
    private final ContainerData data;

    // Server-side constructor
    public BrickKilnMenu(int id, Inventory playerInv, BrickKilnBlockEntity kiln,
                         ContainerLevelAccess access, ContainerData data) {
        super(ModMenuTypes.KILN_MENU.get(), id);
        this.kiln = kiln;
        this.access = access;
        this.kilnPos = kiln.getBlockPos();
        this.data = data;

        // sync burnTime / burnTimeTotal to client
        this.addDataSlots(data);

        // Kiln inventory: fuel (slot 0) only exposed here
        kiln.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(handler -> {
            // fuel slot aligned with GUI (80, 40)
            this.addSlot(new SlotItemHandler(handler, 0, 80, 40));
            // slot 1 (crucible) is controlled via click-on-top logic, no GUI slot
        });

        // Player inventory
        int startX = 8;
        int startY = 84;

        // main inventory
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInv, col + row * 9 + 9,
                        startX + col * 18, startY + row * 18));
            }
        }

        // hotbar
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInv, col, startX + col * 18, startY + 58));
        }
    }

    // Client-side constructor (Forge sends BlockPos in extra data)
    public BrickKilnMenu(int id, Inventory playerInv, FriendlyByteBuf buf) {
        this(id, playerInv, getBlockEntity(playerInv, buf),
                ContainerLevelAccess.NULL,
                new SimpleContainerData(2));
    }

    private static BrickKilnBlockEntity getBlockEntity(Inventory inv, FriendlyByteBuf buf) {
        BlockPos pos = buf.readBlockPos();
        BlockEntity be = inv.player.level().getBlockEntity(pos);
        if (be instanceof BrickKilnBlockEntity k) {
            return k;
        }
        throw new IllegalStateException("KilnBlockEntity not found at " + pos);
    }

    // --- Helpers for the screen ---

    /** True if the fuel slot currently contains an item (client-side view). */
    public boolean hasFuel() {
        Slot fuelSlot = this.slots.get(0); // first slot is fuel
        return fuelSlot != null && fuelSlot.hasItem();
    }

    /** Block position of this kiln, used by the screen to read blockstate. */
    public BlockPos getKilnPos() {
        return kilnPos;
    }

    /** 0..pixels scaled burn amount (like vanilla furnace flame). */
    public int getBurnProgressScaled(int pixels) {
        int burnTime = data.get(0);
        int burnTimeTotal = data.get(1);

        if (burnTimeTotal <= 0) {
            burnTimeTotal = 200; // fallback to avoid div by zero
        }

        if (burnTime <= 0) {
            return 0;
        }

        return Mth.clamp(burnTime * pixels / burnTimeTotal, 0, pixels);
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(access, player, ModBlocks.BRICK_KILN.get());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        // basic shift-click behavior
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack stackInSlot = slot.getItem();
            itemstack = stackInSlot.copy();

            int kilnSlots = 1; // only fuel slot exposed
            if (index < kilnSlots) {
                // from kiln -> player
                if (!this.moveItemStackTo(stackInSlot, kilnSlots, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                // from player -> kiln
                if (!this.moveItemStackTo(stackInSlot, 0, kilnSlots, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stackInSlot.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }
}