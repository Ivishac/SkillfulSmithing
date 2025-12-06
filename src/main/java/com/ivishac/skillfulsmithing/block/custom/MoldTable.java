package com.ivishac.skillfulsmithing.block.custom;

import com.ivishac.skillfulsmithing.block.ModBlocks;
import com.ivishac.skillfulsmithing.block.entity.MoldTableBlockEntity;
import com.ivishac.skillfulsmithing.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class MoldTable extends BaseEntityBlock {
    public static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14, 11, 14);

    public static final BooleanProperty FILLED = BooleanProperty.create("filled");

    public MoldTable(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FILLED, Boolean.FALSE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(FILLED);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new MoldTableBlockEntity(pPos, pState);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer,
                                 InteractionHand pHand, BlockHitResult pHit) {

        BlockEntity be = pLevel.getBlockEntity(pPos);

        if (be instanceof MoldTableBlockEntity moldTable) {

            ItemStack held = pPlayer.getItemInHand(pHand);

            // Sneak-right-click: remove clay (if present)
            if (pPlayer.isShiftKeyDown()) {
                if (!pLevel.isClientSide) {
                    boolean removed = moldTable.removeFireClay(pPlayer);
                    if (removed) {
                        return InteractionResult.sidedSuccess(false);
                    }
                }
                return InteractionResult.sidedSuccess(true);
            }

            // If holding fire clay, insert it
            if (!held.isEmpty() && held.is(ModBlocks.FIRE_CLAY.get().asItem())) {
                if (!pLevel.isClientSide) {
                    boolean inserted = moldTable.insertFireClay(held);
                    if (inserted) {
                        return InteractionResult.sidedSuccess(false);
                    }
                    return InteractionResult.PASS;
                }
                return InteractionResult.sidedSuccess(true);
            }

            // If the table already has clay, allow selecting a head pattern
            if (moldTable.hasFireClay() && !held.isEmpty()) {
                // Only allow known head items here, adjust as needed
                Item headItem = held.getItem();
                boolean isHead =
                        headItem == ModItems.FLINT_SWORD_HEAD.get() ||
                                headItem == ModItems.FLINT_PICKAXE_HEAD.get() ||
                                headItem == ModItems.FLINT_SHOVEL_HEAD.get() ||
                                headItem == ModItems.FLINT_AXE_HEAD.get() ||
                                headItem == ModItems.FLINT_HOE_HEAD.get();

                if (isHead) {
                    if (!pLevel.isClientSide) {
                        boolean set = moldTable.setSelectedHeadFromItem(held);
                        if (set) {
                            // Decide if you want to consume the head pattern or not
                            // For a reusable pattern, comment out the shrink:
                            held.shrink(1);
                            return InteractionResult.sidedSuccess(false);
                        }
                    }
                    return InteractionResult.sidedSuccess(true);
                }
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public void onRemove(BlockState pstate, Level pLevel, BlockPos pPos, BlockState newState, boolean isMoving) {
        if (!pstate.is(newState.getBlock())) {
            BlockEntity be = pLevel.getBlockEntity(pPos);
            if (be instanceof MoldTableBlockEntity moldTable) {
                moldTable.drops();
            }
        }
        super.onRemove(pstate, pLevel, pPos, newState, isMoving);
    }

    @Override
    public <T extends BlockEntity> @Nullable BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pType) {
        return null;
    }
}