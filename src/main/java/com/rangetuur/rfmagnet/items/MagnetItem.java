package com.rangetuur.rfmagnet.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import team.reborn.energy.Energy;
import team.reborn.energy.EnergyHolder;
import team.reborn.energy.EnergyTier;

import java.util.List;

public class MagnetItem extends Item implements EnergyHolder {

    private final int range;
    private final int maxEnergy;
    private final EnergyTier tier;

    public MagnetItem(Settings settings, int range, int maxEnergy, EnergyTier tier) {
        super(settings);
        this.range = range;
        this.maxEnergy = maxEnergy;
        this.tier = tier;
    }

    @Override
    public double getMaxStoredPower() {
        return maxEnergy;
    }

    @Override
    public EnergyTier getTier() {
        return tier;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        boolean magnetActive = false;
        for (ItemStack s: entity.getItemsHand()){
            if (s.getItem() instanceof MagnetItem) {
                magnetActive = true;
            }
        }

        if(magnetActive){
            attractItemsToPlayer(entity, stack);
        }
    }

    private void attractItemsToPlayer(Entity entity, ItemStack stack) {
        float itemMotion = 0.2F;
        double x = entity.getX();
        double y = entity.getY() + 0.75;
        double z = entity.getZ();

        List<ItemEntity> items = entity.getEntityWorld().getEntitiesByType(EntityType.ITEM, new Box(x-range,y-range,z-range,x+range,y+range,z+range), EntityPredicates.VALID_ENTITY);

        Vec3d playerVec = new Vec3d(x, y, z);

        for (ItemEntity item : items) {
            int energyForItem = item.getStack().getCount();
            if(Energy.of(stack).getEnergy()>energyForItem) {
                Vec3d itemEntityVec = new Vec3d(item.getX(), item.getY(), item.getZ());
                Vec3d finalVec = playerVec.subtract(itemEntityVec).multiply(itemMotion);
                item.move(MovementType.PLAYER, finalVec);
                Energy.of(stack).extract(energyForItem);
            }
        }
    }

    public int getRange() {
        return range;
    }
}
