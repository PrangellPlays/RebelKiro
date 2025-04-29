package dev.luminaeclipse.kiro.client.entity;

import dev.luminaeclipse.kiro.Kiro;
import dev.luminaeclipse.kiro.entity.ThrowingDaggerEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class ThrowingDaggerEntityRenderer extends EntityRenderer<ThrowingDaggerEntity> {
    private static final Identifier TEXTURE = Kiro.id("textures/item/throwing_dagger.png");
    public ThrowingDaggerEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(ThrowingDaggerEntity dagger, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        float yawAngle = MathHelper.lerp(tickDelta, dagger.prevYaw, dagger.getYaw());
        float pitchAngle = MathHelper.lerp(tickDelta, dagger.prevPitch, dagger.getPitch());
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(yawAngle - 90.0F));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(pitchAngle * 24));
        ItemStack stack = dagger.getStack().copy();
        MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformationMode.FIXED, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, dagger.getWorld(), 0);
        matrices.pop();
        super.render(dagger, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(ThrowingDaggerEntity entity) {
        return TEXTURE;
    }

    @Override
    public boolean shouldRender(ThrowingDaggerEntity entity, Frustum frustum, double x, double y, double z) {
        return true;
    }
}