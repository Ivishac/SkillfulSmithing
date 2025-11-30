package com.ivishac.skillfulsmithing.item.custom;

import com.ivishac.skillfulsmithing.item.ModItems;
import com.ivishac.skillfulsmithing.screen.CrucibleMenu;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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

public class CrucibleItem extends Item {
    public CrucibleItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        if (!pLevel.isClientSide && pPlayer instanceof ServerPlayer serverPlayer) {
            ItemStack stack = pPlayer.getItemInHand(pHand);

            int slotIndex = pHand == InteractionHand.MAIN_HAND ? pPlayer.getInventory().selected : 40;

            NetworkHooks.openScreen(serverPlayer, new SimpleMenuProvider(
                    (id, inv, player) -> new CrucibleMenu(id, inv, stack),
                    stack.getHoverName()
            ), buf -> buf.writeInt(slotIndex));
        }
        return InteractionResultHolder.sidedSuccess(pPlayer.getItemInHand(pHand), pLevel.isClientSide());
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new ICapabilitySerializable<CompoundTag>() {
            private final ItemStackHandler itemHandler = new ItemStackHandler(2) {
                @Override
                protected void onContentsChanged(int slot) {
                    super.onContentsChanged(slot);
                }

                @Override
                public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                    if(stack.getItem() instanceof CrucibleItem) {
                        return false;
                    }
                    return super.isItemValid(slot, stack);
                }
            };

            private final LazyOptional<IItemHandler> lazyItemhandler = LazyOptional.of(() -> itemHandler);

            @Override
            public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
                return cap == ForgeCapabilities.ITEM_HANDLER ? lazyItemhandler.cast() : LazyOptional.empty();
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
