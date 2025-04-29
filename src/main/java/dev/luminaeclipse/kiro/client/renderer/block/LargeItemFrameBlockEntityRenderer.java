package dev.luminaeclipse.kiro.client.renderer.block;

import dev.luminaeclipse.kiro.block.entity.LargeItemFrameBlockEntity;
import net.minecraft.block.WallMountedBlock;
import net.minecraft.block.enums.WallMountLocation;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;

public class LargeItemFrameBlockEntityRenderer  implements BlockEntityRenderer<LargeItemFrameBlockEntity> {
    private final ItemRenderer itemRenderer;
    private final TextRenderer textRenderer;

    public LargeItemFrameBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        this.itemRenderer = ctx.getItemRenderer();
        this.textRenderer = ctx.getTextRenderer();
    }

    public int getRenderDistance() {
        return 32;
    }

    public void render(LargeItemFrameBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        World world = entity.getWorld();
        Direction d = ((Direction)entity.getCachedState().get(WallMountedBlock.FACING)).getOpposite();
        WallMountLocation face = (WallMountLocation)entity.getCachedState().get(WallMountedBlock.FACE);
        float angle = d.asRotation() + (float)(d.getAxis().equals(Direction.Axis.Z) ? 180 : 0);
        double offset = 0.425;
        matrices.translate((double)0.5F, (double)0.5F, (double)0.5F);
        if (face.equals(WallMountLocation.WALL)) {
            matrices.translate((double)d.getOffsetX() * offset, (double)d.getOffsetY() * offset, (double)d.getOffsetZ() * offset);
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(angle));
        } else if (face.equals(WallMountLocation.FLOOR)) {
            matrices.translate((double)0.0F, -offset, (double)0.0F);
            matrices.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(90.0F));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(angle + 180.0F));
        } else if (face.equals(WallMountLocation.CEILING)) {
            matrices.translate((double)0.0F, offset, (double)0.0F);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0F));
            matrices.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees(angle + 180.0F));
        }

        matrices.scale(0.5F, 0.5F, 0.01F);
        ItemStack stack = entity.getStack();
        if (!stack.isEmpty()) {
            this.itemRenderer.renderItem(stack, ModelTransformationMode.GUI, light, overlay, matrices, vertexConsumers, world, (int) world.getTime());
            if (stack.getCount() != 1) {
                String string = String.valueOf(stack.getCount());
                float scale = 0.0625F;
                matrices.scale(scale, scale, scale);
                matrices.translate((double)(9 - this.textRenderer.getWidth(string)), (double)0.0F, (double)8.0F);
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F));
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180.0F));
                this.textRenderer.draw(Text.of(string), 1.0F, 2.0F, 4473924, false, matrices.peek().getPositionMatrix(), vertexConsumers, TextRenderer.TextLayerType.NORMAL, 0, light);
                matrices.translate((double)0.0F, (double)0.0F, (double)-1.0F);
                this.textRenderer.draw(Text.of(string), 0.0F, 1.0F, 16777215, false, matrices.peek().getPositionMatrix(), vertexConsumers, TextRenderer.TextLayerType.NORMAL, 0, light);
            }
        }

        matrices.pop();
    }
}