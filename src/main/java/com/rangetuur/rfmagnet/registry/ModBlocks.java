package com.rangetuur.rfmagnet.registry;

import com.google.common.base.Predicates;
import com.rangetuur.rfmagnet.RFMagnet;
import com.rangetuur.rfmagnet.blocks.MagnetJarBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import javax.naming.Context;
import javax.sql.rowset.Predicate;


public class ModBlocks {

    public static final Block MAGNET_JAR = new MagnetJarBlock(FabricBlockSettings.of(Material.GLASS).breakByTool(FabricToolTags.PICKAXES, 3).requiresTool().strength(50.0f, 500.0f).sounds(BlockSoundGroup.GLASS).luminance(10).nonOpaque());

    public static void registerBlocks() {
        Registry.register(Registry.BLOCK, new Identifier(RFMagnet.MOD_ID, "magnet_jar"), MAGNET_JAR);
    }
}
