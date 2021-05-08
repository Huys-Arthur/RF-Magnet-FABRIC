package com.rangetuur.rfmagnet.blocks.blockentities;

import com.rangetuur.rfmagnet.ImplementedInventory;
import com.rangetuur.rfmagnet.RFMagnet;
import com.rangetuur.rfmagnet.RFMagnetConfig;
import com.rangetuur.rfmagnet.items.MagnetItem;
import com.rangetuur.rfmagnet.registry.ModBlockEntityTypes;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.*;

import java.util.List;

public class MagnetJarBlockEntity extends BlockEntity implements ImplementedInventory, SidedInventory, Tickable, BlockEntityClientSerializable, EnergyStorage {

    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(2, ItemStack.EMPTY);

    public MagnetJarBlockEntity() {
        super(ModBlockEntityTypes.MAGNET_JAR);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        Inventories.fromTag(tag,items);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        Inventories.toTag(tag,items);
        return super.toTag(tag);
    }

    @Override
    public void fromClientTag(CompoundTag tag) {
        Inventories.fromTag(tag,items);
    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag) {
        Inventories.toTag(tag,items);
        return tag;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        int[] result = new int[getItems().size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = i;
        }

        return result;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return stack.getItem() instanceof MagnetItem;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return slot==1;
    }

    @Override
    public void tick() {
        if(getStack(0)!=ItemStack.EMPTY){
            BlockEntity entityUp = world.getBlockEntity(getPos().up());
            if (RFMagnetConfig.generate_energy_magnet_jar){
                if (world.getFluidState(getPos().up()).getFluid() instanceof LavaFluid){
                    Energy.of(getStack(0)).insert(RFMagnetConfig.generate_amount_magnet_jar);
                }
            }
            else if (entityUp!=null){
                Energy.of(entityUp).side(EnergySide.DOWN).into(Energy.of(getStack(0))).move();
            }
            attractItemsAroundBlock(pos, getStack(0));
        }
        if (getStack(1).isEmpty()) {
            putItemAroundBlockInInventory();
        }
    }

    private void attractItemsAroundBlock(BlockPos pos, ItemStack stack) {
        float itemMotion = 0.2F;
        int range = ((MagnetItem) stack.getItem()).getRange();
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();

        List<ItemEntity> items = world.getEntitiesByType(EntityType.ITEM, new Box(x-range,y-range,z-range,x+1+range,y+1+range,z+1+range), EntityPredicates.VALID_ENTITY);
        List<ItemEntity> itemsInBlock = world.getEntitiesByType(EntityType.ITEM, new Box(pos.getX(),pos.getY(),pos.getZ(),pos.getX()+1,pos.getY()+1,pos.getZ()+1), EntityPredicates.VALID_ENTITY);

        Vec3d blockVec = new Vec3d(x + 0.5F, y, z + 0.5F);

        for (ItemEntity item : items) {
            if (!itemsInBlock.contains(item)){
                int energyForItem = item.getStack().getCount();
                if(Energy.of(stack).getEnergy()>energyForItem) {
                    Vec3d itemEntityVec = new Vec3d(item.getX(), item.getY(), item.getZ());
                    Vec3d finalVec = blockVec.subtract(itemEntityVec).multiply(itemMotion);
                    item.move(MovementType.PLAYER, finalVec);
                    Energy.of(stack).extract(energyForItem);
                }
            }
        }
    }

    private void putItemAroundBlockInInventory() {
        List<ItemEntity> items = world.getEntitiesByType(EntityType.ITEM, new Box(pos.getX(),pos.getY(),pos.getZ(),pos.getX()+1,pos.getY()+1,pos.getZ()+1), EntityPredicates.VALID_ENTITY);
        if (!items.isEmpty()){
            ItemEntity item = items.get(0);

            setStack(1, item.getStack().copy());
            item.remove();
            markDirty();
        }
    }

    @Override
    public double getStored(EnergySide face) {
        return 0;
    }

    @Override
    public void setStored(double amount) {

    }

    @Override
    public double getMaxStoredPower() {
        return 0;
    }


    @Override
    public EnergyTier getTier() {
        return EnergyTier.MICRO;
    }

    @Override
    public double getMaxInput(EnergySide side) {
        return 0;
    }

    @Override
    public double getMaxOutput(EnergySide side) {
        return 0;
    }
}
