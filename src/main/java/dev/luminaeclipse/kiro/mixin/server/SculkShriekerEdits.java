package dev.luminaeclipse.kiro.mixin.server;

import dev.luminaeclipse.kiro.util.KiroChecks;
import net.minecraft.block.BlockState;
import net.minecraft.block.SculkShriekerBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({SculkShriekerBlock.class})
public class SculkShriekerEdits {
    public SculkShriekerEdits() {
    }

    @Inject(at = {@At("HEAD")}, method = {"onSteppedOn"}, cancellable = true)
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity, CallbackInfo cir) {
        if (entity instanceof LivingEntity livingEntity) {
            if (KiroChecks.isWearingSilentBoots(livingEntity)) {
                cir.cancel();
            }
        }
    }
}