package dev.luminaeclipse.kiro.mixin.server;

import dev.luminaeclipse.kiro.init.KiroSoundEvents;
import dev.luminaeclipse.kiro.util.KiroChecks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Block.class})
public class EntityStepsSound {
    private Entity entity1 = null;
    private BlockSoundGroup originalSoundGroup = null;

    public EntityStepsSound() {
    }

    @Inject(at = {@At("HEAD")}, method = {"onSteppedOn"})
    protected void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity, CallbackInfo cir) {
        this.entity1 = entity;
        this.originalSoundGroup = state.getSoundGroup();
    }

    @Inject(at = {@At("HEAD")}, method = {"getSoundGroup"}, cancellable = true)
    protected void getSoundGroup(BlockState state, CallbackInfoReturnable<BlockSoundGroup> cir) {
        BlockSoundGroup defaultSoundGroup = this.originalSoundGroup;
        if (this.entity1 != null && this.entity1 instanceof LivingEntity) {
            LivingEntity liEntity = (LivingEntity)this.entity1;
            if (defaultSoundGroup != null) {
                BlockSoundGroup woolBlockSoundGroup = new BlockSoundGroup(1.0F, 1.0F, defaultSoundGroup.getBreakSound(), KiroSoundEvents.SILENT_STEP, defaultSoundGroup.getPlaceSound(), defaultSoundGroup.getHitSound(), KiroSoundEvents.SILENT_STEP);
                if (KiroChecks.isWearingSilentBoots(liEntity)) {
                    cir.setReturnValue(woolBlockSoundGroup);
                }
            }
        }
    }
}