package com.ivishac.skillfulsmithing.block.custom;

import com.ivishac.skillfulsmithing.block.entity.BrickKilnBlockEntity;
import com.ivishac.skillfulsmithing.block.entity.ModBlockEntities;
import com.ivishac.skillfulsmithing.item.custom.CrucibleItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class BrickKiln extends BaseEntityBlock {

    public BrickKiln(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BrickKilnBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            Level level, BlockState state, BlockEntityType<T> type
    ) {
        return level.isClientSide ? null :
                createTickerHelper(type, ModBlockEntities.BRICK_KILN_BE.get(), BrickKilnBlockEntity::serverTick);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos,
                                 Player player, InteractionHand hand, BlockHitResult hit) {

        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof BrickKilnBlockEntity kiln)) {
            return InteractionResult.PASS;
        }

        double localY = hit.getLocation().y - pos.getY();

        if (localY < 0.5) {
            // bottom half → open fuel GUI
            player.openMenu(kiln);
            return InteractionResult.CONSUME;
        } else {
            // top half → crucible insert/remove
            ItemStack held = player.getItemInHand(hand);

            // put crucible in
            if (!held.isEmpty() && held.getItem() instanceof CrucibleItem && kiln.getCrucible().isEmpty()) {
                ItemStack toInsert = held.copyWithCount(1);
                kiln.setCrucible(toInsert);
                held.shrink(1);
                return InteractionResult.CONSUME;
            }

            // take crucible out
            if (held.isEmpty() && !kiln.getCrucible().isEmpty()) {
                ItemStack crucible = kiln.removeCrucible();
                player.setItemInHand(hand, crucible);
                return InteractionResult.CONSUME;
            }

            return InteractionResult.PASS;
        }
    }
}