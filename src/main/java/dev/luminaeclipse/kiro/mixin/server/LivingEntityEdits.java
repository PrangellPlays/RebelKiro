package dev.luminaeclipse.kiro.mixin.server;

import dev.luminaeclipse.kiro.init.KiroSoundEvents;
import dev.luminaeclipse.kiro.util.KiroChecks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({LivingEntity.class})
public class LivingEntityEdits {
    @Mutable @Shadow @Final
    private DefaultedList<ItemStack> syncedArmorStacks;

    public LivingEntityEdits() {
        this.syncedArmorStacks = DefaultedList.ofSize(4, ItemStack.EMPTY);
    }

    @Inject(at = {@At("HEAD")}, method = {"getFallSound"}, cancellable = true)
    protected void getFallSound(int distance, CallbackInfoReturnable<SoundEvent> cir) {
        if (KiroChecks.isItemStackSilentBoots((ItemStack)this.syncedArmorStacks.get(0))) {
            cir.setReturnValue(KiroSoundEvents.SILENT_STEP);
        }
    }
}