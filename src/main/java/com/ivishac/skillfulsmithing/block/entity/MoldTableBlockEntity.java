package com.ivishac.skillfulsmithing.block.entity;

import com.ivishac.skillfulsmithing.block.ModBlocks;
import com.ivishac.skillfulsmithing.block.custom.MoldTable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

public class MoldTableBlockEntity extends BlockEntity {

    private final ItemStackHandler itemHandler = new ItemStackHandler(1);

    // Which tool-head pattern is currently inscribed in the clay (optional)
    private Item selectedHeadItem = null;

    private static final String TAG_INVENTORY = "inventory";
    private static final String TAG_SELECTED_HEAD = "SelectedHead";

    public MoldTableBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.MOLD_TABLE_BE.get(), pPos, pBlockState);
    }

    public boolean hasFireClay() {
        return !itemHandler.getStackInSlot(0).isEmpty();
    }

    public boolean hasSelectedHead() {
        return selectedHeadItem != null;
    }

    public Item getSelectedHeadItem() {
        return selectedHeadItem;
    }

    public void clearSelectedHead() {
        this.selectedHeadItem = null;
        setChanged();
    }

    /**
     * Called when player right-clicks with a tool head to "imprint" this mold.
     * Requires fire clay to be present.
     */
    public boolean setSelectedHeadFromItem(ItemStack headStack) {
        if (headStack.isEmpty() || !hasFireClay()) {
            return false;
        }

        this.selectedHeadItem = headStack.getItem();
        setChanged();
        return true;
    }

    public boolean insertFireClay(ItemStack playerStack) {
        if (playerStack.isEmpty() || !playerStack.is(ModBlocks.FIRE_CLAY.get().asItem())) {
            return false;
        }

        ItemStack slotStack = itemHandler.getStackInSlot(0);
        if (slotStack.isEmpty()) {
            ItemStack oneFireClay = playerStack.copy();
            oneFireClay.setCount(1);

            itemHandler.setStackInSlot(0, oneFireClay);
            playerStack.shrink(1);
            setChanged();

            //Mark Block as filled
            if (this.level != null) {
                BlockState state = this.level.getBlockState(this.worldPosition);
                if (state.getBlock() instanceof MoldTable) {
                    this.level.setBlock(this.worldPosition,
                            state.setValue(MoldTable.FILLED, Boolean.TRUE), 3);
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

        // When clay is removed, clear the selected head pattern as well
        this.selectedHeadItem = null;

        if (!pPlayer.addItem(toGive) && this.level != null) {
            pPlayer.drop(toGive, false);
        }

        //Mark Block as empty
        if (this.level != null) {
            BlockState state = this.level.getBlockState(this.worldPosition);
            if (state.getBlock() instanceof MoldTable) {
                this.level.setBlock(this.worldPosition,
                        state.setValue(MoldTable.FILLED, Boolean.FALSE), 3);
            }
        }

        return true;
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        if (this.level != null) {
            Containers.dropContents(this.level, this.worldPosition, inventory);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put(TAG_INVENTORY, itemHandler.serializeNBT());

        if (selectedHeadItem != null) {
            // Save selected head as its registry name
            ResourceLocation key = net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(selectedHeadItem);
            if (key != null) {
                tag.putString(TAG_SELECTED_HEAD, key.toString());
            }
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains(TAG_INVENTORY)) {
            itemHandler.deserializeNBT(tag.getCompound(TAG_INVENTORY));
        }

        if (tag.contains(TAG_SELECTED_HEAD)) {
            String id = tag.getString(TAG_SELECTED_HEAD);
            ResourceLocation rl = new ResourceLocation(id);
            Item item = net.minecraft.core.registries.BuiltInRegistries.ITEM.get(rl);
            this.selectedHeadItem = (item != null && item != net.minecraft.world.item.Items.AIR) ? item : null;
        } else {
            this.selectedHeadItem = null;
        }
    }
}