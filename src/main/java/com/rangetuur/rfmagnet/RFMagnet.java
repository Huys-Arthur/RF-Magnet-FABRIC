package com.rangetuur.rfmagnet;

import com.rangetuur.rfmagnet.blocks.blockentities.renderers.MagnetJarBlockEntityRenderer;
import com.rangetuur.rfmagnet.registry.ModBlockEntityTypes;
import com.rangetuur.rfmagnet.registry.ModBlocks;
import com.rangetuur.rfmagnet.registry.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import reborncore.common.config.Configuration;

public class RFMagnet implements ModInitializer {

    public static final String MOD_ID ="rfmagnet";

    @Override
    public void onInitialize() {
        new Configuration(RFMagnetConfig.class, "rfmagnet");

        ModItems.registerItems();
        ModBlocks.registerBlocks();
        ModBlockEntityTypes.registerBlockEntityTypes();
    }
}
