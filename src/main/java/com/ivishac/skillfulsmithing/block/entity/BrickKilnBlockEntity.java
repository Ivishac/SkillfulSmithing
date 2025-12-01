package com.ivishac.skillfulsmithing.block.entity;

import com.ivishac.skillfulsmithing.item.custom.CrucibleItem;
import com.ivishac.skillfulsmithing.screen.BrickKilnMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BrickKilnBlockEntity extends BlockEntity implements MenuProvider {

    // slot 0 = fuel, slot 1 = crucible
    private final ItemStackHandler items = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private int burnTime;
    private int burnTimeTotal;

    private final LazyOptional<ItemStackHandler> itemCap = LazyOptional.of(() -> items);

    public BrickKilnBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BRICK_KILN_BE.get(), pos, state);
    }

    // --- Inventory helpers ---
    public ItemStack getFuel() {
        return items.getStackInSlot(0);
    }

    public ItemStack getCrucible() {
        return items.getStackInSlot(1);
    }

    public void setCrucible(ItemStack stack) {
        items.setStackInSlot(1, stack);
    }

    public ItemStack removeCrucible() {
        ItemStack stack = items.getStackInSlot(1);
        items.setStackInSlot(1, ItemStack.EMPTY);
        return stack;
    }

    // --- Ticking ---
    public static void serverTick(Level level, BlockPos pos, BlockState state, BrickKilnBlockEntity be) {
        boolean dirty = false;

        if (be.burnTime > 0) {
            be.burnTime--;
        }

        // try to consume fuel if empty
        if (be.burnTime == 0) {
            ItemStack fuel = be.getFuel();
            if (!fuel.isEmpty()) {
                int newBurn = ForgeHooks.getBurnTime(fuel, RecipeType.SMELTING);
                if (newBurn > 0) {
                    be.burnTime = be.burnTimeTotal = newBurn;
                    fuel.shrink(1);
                    dirty = true;
                }
            }
        }

        // heat crucible contents if burning
        if (be.burnTime > 0) {
            ItemStack crucibleStack = be.getCrucible();
            if (!crucibleStack.isEmpty() && crucibleStack.getItem() instanceof CrucibleItem) {
                // uses the static helper we made on CrucibleItem
                CrucibleItem.heatContents(crucibleStack, 5);
                dirty = true;
            }
        }

        if (dirty) {
            setChanged(level, pos, state);
        }
    }

    // --- MenuProvider ---
    @Override
    public Component getDisplayName() {
        return Component.translatable("block.skillfulsmithing.brick_kiln");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInv, Player player) {
        // use your actual menu class here – NOT ModMenuTypes
        return new BrickKilnMenu(id, playerInv, this, ContainerLevelAccess.create(level, worldPosition));
    }

    // --- Capabilities ---
    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable net.minecraft.core.Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return itemCap.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        itemCap.invalidate();
    }

    // --- NBT save/load ---
    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("Items", items.serializeNBT());
        tag.putInt("BurnTime", burnTime);
        tag.putInt("BurnTimeTotal", burnTimeTotal);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        items.deserializeNBT(tag.getCompound("Items"));
        burnTime = tag.getInt("BurnTime");
        burnTimeTotal = tag.getInt("BurnTimeTotal");
    }
}