package com.rangetuur.rfmagnet.blocks;

import com.rangetuur.rfmagnet.ImplementedInventory;
import com.rangetuur.rfmagnet.blocks.blockentities.MagnetJarBlockEntity;
import com.rangetuur.rfmagnet.items.MagnetItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

import static net.minecraft.util.function.BooleanBiFunction.OR;

public class MagnetJarBlock extends Block implements BlockEntityProvider {

    private static final VoxelShape SHAPE = Stream.of(
            Block.createCuboidShape(2, 0, 2, 14, 2, 14),
            Block.createCuboidShape(2, 2, 2, 14, 12, 14),
            Block.createCuboidShape(3, 12, 3, 13, 14, 13),
            Block.createCuboidShape(5, 14, 5, 11, 16, 11)).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, OR);}).get();

    public MagnetJarBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new MagnetJarBlockEntity();
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        Inventory blockEntity = (Inventory) world.getBlockEntity(pos);

        if (!player.getStackInHand(hand).isEmpty() && player.getStackInHand(hand).getItem() instanceof MagnetItem) {
            if (blockEntity.getStack(0).isEmpty()) {
                blockEntity.setStack(0, player.getStackInHand(hand).copy());
                player.getStackInHand(hand).setCount(0);
                blockEntity.markDirty();
            }
        } else {
            if (!blockEntity.getStack(0).isEmpty()) {
                player.inventory.offerOrDrop(world, blockEntity.getStack(0));
                blockEntity.removeStack(0);
                blockEntity.markDirty();
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof Inventory) {
                ItemScatterer.spawn(world, pos, (Inventory)blockEntity);
                world.updateComparators(pos, this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }
}
