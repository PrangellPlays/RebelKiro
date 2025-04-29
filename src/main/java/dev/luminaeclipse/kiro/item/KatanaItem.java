package dev.luminaeclipse.kiro.item;

import dev.luminaeclipse.kiro.init.KiroToolMaterials;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class KatanaItem extends SwordItem {
    public KatanaItem(Settings settings) {
        super(KiroToolMaterials.SHIRASTEEL, 9, -2.7F, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return super.use(world, user, hand);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (Math.abs(MathHelper.subtractAngles(attacker.getHeadYaw(), target.getHeadYaw())) <= 75.0F) {
            if (target.isUndead()) {
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 10, 0, false, false, false), attacker);
            } else {
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE, 10, 0, false, false, false), attacker);
            }
        }
        return super.postHit(stack, target, attacker);
    }
}
