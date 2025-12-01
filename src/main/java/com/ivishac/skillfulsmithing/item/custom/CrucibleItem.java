package com.ivishac.skillfulsmithing.item.custom;

import com.ivishac.skillfulsmithing.screen.CrucibleMenu;
import com.ivishac.skillfulsmithing.world.HeatableItem;
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
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CrucibleItem extends Item {

    private static final String TAG_ITEMS = "Items";

    public CrucibleItem(Properties properties) {
        super(properties);
    }

    // ---------------- NBT inventory helpers ----------------

    public static List<ItemStack> getContents(ItemStack crucibleStack) {
        List<ItemStack> result = new ArrayList<>();
        CompoundTag tag = crucibleStack.getOrCreateTag();
        ListTag itemsTag = tag.getList(TAG_ITEMS, Tag.TAG_COMPOUND);

        for (int i = 0; i < itemsTag.size(); i++) {
            CompoundTag stackTag = itemsTag.getCompound(i);
            ItemStack stack = ItemStack.of(stackTag);
            if (!stack.isEmpty()) {
                result.add(stack);
            }
        }
        return result;
    }

    public static void setContents(ItemStack crucibleStack, List<ItemStack> contents) {
        ListTag list = new ListTag();
        for (ItemStack stack : contents) {
            if (!stack.isEmpty()) {
                CompoundTag stackTag = new CompoundTag();
                stack.save(stackTag);
                list.add(stackTag);
            }
        }
        crucibleStack.getOrCreateTag().put(TAG_ITEMS, list);
    }

    /** Called by the kiln to heat everything inside this crucible. */
    public static void heatContents(ItemStack crucibleStack, int heatPerTick) {
        List<ItemStack> contents = getContents(crucibleStack);
        boolean changed = false;

        for (ItemStack stack : contents) {
            if (!stack.isEmpty() && stack.getItem() instanceof HeatableItem) {
                HeatableItem.addHeat(stack, heatPerTick);
                changed = true;
            }
        }

        if (changed) {
            setContents(crucibleStack, contents);
        }
    }

    // ---------------- Tooltip ----------------

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
                int count = s.getCount();
                tooltip.add(Component.literal(
                        "- x" + count + " " + s.getHoverName().getString() + " (" + temp + "°)"
                ));
            }
        }
    }

    // ---------------- Right-click: open GUI ----------------

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            ItemStack stack = player.getItemInHand(hand);

            int slotIndex = hand == InteractionHand.MAIN_HAND
                    ? player.getInventory().selected
                    : 40; // offhand index in 1.20

            NetworkHooks.openScreen(serverPlayer, new SimpleMenuProvider(
                    (id, inv, p) -> new CrucibleMenu(id, inv, slotIndex),
                    stack.getHoverName()
            ), buf -> buf.writeInt(slotIndex));
        }

        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
    }
}