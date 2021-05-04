package com.rangetuur.rfmagnet.registry;

import com.rangetuur.rfmagnet.RFMagnet;
import com.rangetuur.rfmagnet.blocks.blockentities.MagnetJarBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlockEntityTypes {

    public static BlockEntityType<MagnetJarBlockEntity> MAGNET_JAR = BlockEntityType.Builder.create(MagnetJarBlockEntity::new, ModBlocks.MAGNET_JAR).build(null);

    public static void registerBlockEntityTypes() {
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(RFMagnet.MOD_ID, "magnet_jar"), MAGNET_JAR);
    }
}
