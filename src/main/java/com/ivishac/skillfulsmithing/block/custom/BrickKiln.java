package com.ivishac.skillfulsmithing.block.custom;

import com.ivishac.skillfulsmithing.block.entity.BrickKilnBlockEntity;
import com.ivishac.skillfulsmithing.block.entity.ModBlockEntities;
import com.ivishac.skillfulsmithing.item.custom.CrucibleItem;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class BrickKiln extends BaseEntityBlock {

    public static final BooleanProperty HAS_CRUCIBLE = BooleanProperty.create("has_crucible");

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Block.box(1.0D, 0.0D, 1.0D, 14.0D, 18.0D, 14.0D);
    }

    public BrickKiln(Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition.any().setValue(HAS_CRUCIBLE, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HAS_CRUCIBLE);
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

        // bottom half → open fuel GUI
        if (localY < 0.5) {
            if (player instanceof ServerPlayer serverPlayer) {
                NetworkHooks.openScreen(serverPlayer, kiln, pos);
            }
            return InteractionResult.CONSUME;
        }

        // top half → crucible insert/remove
        ItemStack held = player.getItemInHand(hand);

        // put crucible in
        if (!held.isEmpty() && held.getItem() instanceof CrucibleItem && kiln.getCrucible().isEmpty()) {
            ItemStack toInsert = held.copyWithCount(1);
            kiln.setCrucible(toInsert);
            held.shrink(1);

            if (!state.getValue(HAS_CRUCIBLE)) {
                level.setBlock(pos, state.setValue(HAS_CRUCIBLE, true), Block.UPDATE_ALL);
            }

            return InteractionResult.CONSUME;
        }

        // take crucible out
        if (held.isEmpty() && !kiln.getCrucible().isEmpty()) {
            ItemStack crucible = kiln.removeCrucible();
            player.setItemInHand(hand, crucible);

            if (state.getValue(HAS_CRUCIBLE)) {
                level.setBlock(pos, state.setValue(HAS_CRUCIBLE, false), Block.UPDATE_ALL);
            }

            return InteractionResult.CONSUME;
        }

        return InteractionResult.PASS;
    }
}