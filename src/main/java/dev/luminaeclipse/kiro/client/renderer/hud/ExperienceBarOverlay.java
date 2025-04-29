package dev.luminaeclipse.kiro.client.renderer.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.luminaeclipse.kiro.Kiro;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.util.Identifier;

public class ExperienceBarOverlay implements HudRenderCallback {
    private static final Identifier EXPERIENCE_BAR_OVERLAY = new Identifier(Kiro.MOD_ID, "textures/gui/experience_bar_overlay.png");

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        int x = 0;
        int y = 0;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null) {
            int width = client.getWindow().getScaledWidth();
            int height = client.getWindow().getScaledHeight();

            x = width / 2;
            y = height;
        }

        if (client.player.isSpectator()) {
            return;
        } else if (client.player.isCreative()) {
            return;
        }
        else {
            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, EXPERIENCE_BAR_OVERLAY);
            for (int i = 0; i < 1; i++) {
                if (client.options.getGuiScale().getValue() == 4 || client.options.getGuiScale().getValue() == 0) {
                    drawContext.drawTexture(EXPERIENCE_BAR_OVERLAY, (client.getWindow().getScaledWidth()) - 337, client.getWindow().getScaledHeight() - 34, 0, 0, 194, 15, 194, 15);
                } else if (client.options.getGuiScale().getValue() == 3) {
                    drawContext.drawTexture(EXPERIENCE_BAR_OVERLAY, (client.getWindow().getScaledWidth()) - 417, client.getWindow().getScaledHeight() - 34, 0, 0, 194, 15, 194, 15);
                } else if (client.options.getGuiScale().getValue() == 2) {
                    drawContext.drawTexture(EXPERIENCE_BAR_OVERLAY, (client.getWindow().getScaledWidth()) - 577, client.getWindow().getScaledHeight() - 34, 0, 0, 194, 15, 194, 15);
                } else if (client.options.getGuiScale().getValue() == 1) {
                    drawContext.drawTexture(EXPERIENCE_BAR_OVERLAY, (client.getWindow().getScaledWidth()) - 1057, client.getWindow().getScaledHeight() - 34, 0, 0, 194, 15, 194, 15);
                }
            }
        }
    }
}