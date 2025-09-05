package net.astralya.hexalia.block.entity.renderer;

import net.astralya.hexalia.block.entity.custom.RitualBrazierBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.client.render.OverlayTexture;

public class RitualBrazierBlockEntityRenderer implements BlockEntityRenderer<RitualBrazierBlockEntity> {

    public RitualBrazierBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
    }

    @Override
    public void render(RitualBrazierBlockEntity be,
                       float tickDelta,
                       MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers,
                       int light,
                       int overlay) {

        ItemStack stack = be.getStoredItem();
        if (stack.isEmpty()) return;

        World world = be.getWorld();
        if (world == null) return;

        var itemRenderer = MinecraftClient.getInstance().getItemRenderer();

        matrices.push();
        matrices.translate(0.5f, 0.4f, 0.5f);
        matrices.scale(1f, 1f, 1f);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(be.getRenderingRotation()));

        int packedLight = LightmapTextureManager.pack(
                world.getLightLevel(LightType.BLOCK, be.getPos().up()),
                world.getLightLevel(LightType.SKY,   be.getPos().up())
        );

        itemRenderer.renderItem(
                stack,
                ModelTransformationMode.GROUND,
                packedLight,
                OverlayTexture.DEFAULT_UV,
                matrices,
                vertexConsumers,
                world,
                0
        );

        matrices.pop();
    }
}
