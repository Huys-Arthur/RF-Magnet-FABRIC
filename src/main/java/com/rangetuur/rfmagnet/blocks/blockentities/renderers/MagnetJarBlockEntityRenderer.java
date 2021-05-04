package com.rangetuur.rfmagnet.blocks.blockentities.renderers;

import com.rangetuur.rfmagnet.blocks.blockentities.MagnetJarBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.item.ItemStack;

public class MagnetJarBlockEntityRenderer extends BlockEntityRenderer<MagnetJarBlockEntity> {

    public MagnetJarBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(MagnetJarBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        System.out.println("Render: " + entity.getItems().get(0));
        if (entity.getStack(0)!=ItemStack.EMPTY){
            matrices.push();

            double offset = Math.sin((entity.getWorld().getTime() + tickDelta) / 8.0) / 4.0;
            matrices.translate(0.5, 0.25 + offset, 0.5);
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((entity.getWorld().getTime() + tickDelta) * 4));
            int lightAbove = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos().up());
            MinecraftClient.getInstance().getItemRenderer().renderItem(entity.getStack(0), ModelTransformation.Mode.GROUND, lightAbove, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers);

            matrices.pop();
        }
    }
}
