package dev.luminaeclipse.kiro.mixin.server;

import dev.luminaeclipse.kiro.util.KiroChecks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.listener.Vibration;
import net.minecraft.world.event.listener.VibrationSelector;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({VibrationSelector.class})
public class VibrationAcceptorEdits {
    public VibrationAcceptorEdits() {
    }

    @Inject(at = {@At("HEAD")}, method = {"shouldSelect"}, cancellable = true)
    void canAccept(Vibration vibration, long tick, CallbackInfoReturnable<Boolean> cir) {
        Entity entity = vibration.entity();
        GameEvent event = vibration.gameEvent();
        if (entity instanceof LivingEntity livingEntity) {
            if (KiroChecks.isWearingSilentBoots(livingEntity)) {
                if (event.equals(GameEvent.STEP)) {
                    cir.setReturnValue(false);
                }

                if (event.equals(GameEvent.HIT_GROUND)) {
                    cir.setReturnValue(false);
                }

                if (event.equals(GameEvent.PROJECTILE_SHOOT)) {
                    cir.setReturnValue(false);
                }
            }
        }
    }
}