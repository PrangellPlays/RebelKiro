package dev.luminaeclipse.kiro.mixin.client;

import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawText(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;IIIZ)I"), method = "renderExperienceBar", index = 4)
    private int kiro$experienceBarTextOutlineColour(int oldColour) {
        //Outline Colour
        return 0x000000;
    }

    @ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawText(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;IIIZ)I", ordinal = 4), method = "renderExperienceBar", index = 4)
    private int kiro$experienceBarTextColour(int oldColour) {
        //Text Colour
        return 0xc94558;
    }
}