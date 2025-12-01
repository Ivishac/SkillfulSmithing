package com.ivishac.skillfulsmithing.item.custom;

import com.ivishac.skillfulsmithing.screen.CrucibleMenu;
import com.ivishac.skillfulsmithing.world.HeatableItem;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CrucibleItem extends Item {

    public CrucibleItem(Properties pProperties) {
        super(pProperties);
    }

    // ----------------------------------------------------------------
    // STATIC HELPERS: getContents & heatContents
    // ----------------------------------------------------------------

    /**
     * Get all non-empty item stacks inside this crucible's capability inventory.
     */
    public static List<ItemStack> getContents(ItemStack crucibleStack) {
        List<ItemStack> result = new ArrayList<>();

        crucibleStack.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(handler -> {
            for (int i = 0; i < handler.getSlots(); i++) {
                ItemStack stackInSlot = handler.getStackInSlot(i);
                if (!stackInSlot.isEmpty()) {
                    result.add(stackInSlot);
                }
            }
        });

        return result;
    }

    /**
     * Called by the kiln to heat everything inside this crucible.
     * Modifies the ItemStacks in-place via their NBT.
     */
    public static void heatContents(ItemStack crucibleStack, int heatPerTick) {
        crucibleStack.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(handler -> {
            for (int i = 0; i < handler.getSlots(); i++) {
                ItemStack stackInSlot = handler.getStackInSlot(i);
                if (!stackInSlot.isEmpty() && stackInSlot.getItem() instanceof HeatableItem) {
                    HeatableItem.addHeat(stackInSlot, heatPerTick);
                    // No need to reinsert: we mutated the same ItemStack instance.
                }
            }
        });
    }

    // ----------------------------------------------------------------
    // RIGHT-CLICK: open crucible menu
    // ----------------------------------------------------------------

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        if (!pLevel.isClientSide && pPlayer instanceof ServerPlayer serverPlayer) {
            ItemStack stack = pPlayer.getItemInHand(pHand);

            // 36–44 hotbar/inv indexing: 0–8 = hotbar, 9–35 = main, 36–44 = armor/offhand
            int slotIndex = pHand == InteractionHand.MAIN_HAND
                    ? pPlayer.getInventory().selected
                    : 40; // offhand index

            NetworkHooks.openScreen(serverPlayer, new SimpleMenuProvider(
                    (id, inv, player) -> new CrucibleMenu(id, inv, stack),
                    stack.getHoverName()
            ), buf -> buf.writeInt(slotIndex));
        }
        return InteractionResultHolder.sidedSuccess(pPlayer.getItemInHand(pHand), pLevel.isClientSide());
    }

    // ----------------------------------------------------------------
    // TOOLTIP: show contents & their temperatures
    // ----------------------------------------------------------------

    @Override
    public void appendHoverText(ItemStack stack,
                                @Nullable Level level,
                                List<Component> tooltip,
                                TooltipFlag flag) {

        List<ItemStack> contents = CrucibleItem.getContents(stack);

        if (contents.isEmpty()) {
            tooltip.add(Component.literal("Empty"));
        } else {
            tooltip.add(Component.literal("Contents:"));
            for (ItemStack s : contents) {
                int temp = (s.getItem() instanceof HeatableItem)
                        ? HeatableItem.getTemperature(s)
                        : 0;
                tooltip.add(Component.literal(
                        "- " + s.getHoverName().getString() + " (" + temp + "°)"
                ));
            }
        }
    }

    // ----------------------------------------------------------------
    // CAPABILITY: item handler inventory attached to this item
    // ----------------------------------------------------------------

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new ICapabilitySerializable<CompoundTag>() {

            // 2-slot crucible inventory – tweak size as you like
            private final ItemStackHandler itemHandler = new ItemStackHandler(2) {
                @Override
                protected void onContentsChanged(int slot) {
                    super.onContentsChanged(slot);
                    // If you want, you can do extra stuff here when contents change
                }

                @Override
                public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                    // Prevent crucibles inside crucibles
                    if (stack.getItem() instanceof CrucibleItem) {
                        return false;
                    }
                    return super.isItemValid(slot, stack);
                }
            };

            private final LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.of(() -> itemHandler);

            @NotNull
            @Override
            public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
                return cap == ForgeCapabilities.ITEM_HANDLER ? lazyItemHandler.cast() : LazyOptional.empty();
            }

            @Override
            public CompoundTag serializeNBT() {
                return itemHandler.serializeNBT();
            }

            @Override
            public void deserializeNBT(CompoundTag nbt) {
                itemHandler.deserializeNBT(nbt);
            }
        };
    }
}