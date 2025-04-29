package dev.luminaeclipse.kiro.item;

import dev.luminaeclipse.kiro.entity.ThrowingDaggerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ThrowingDaggerItem extends Item {
    public ThrowingDaggerItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (!world.isClient()) {
            ThrowingDaggerEntity dagger = new ThrowingDaggerEntity(world, user);
            dagger.setVelocity(Vec3d.fromPolar(user.getPitch(), user.getYaw()));
            world.spawnEntity(dagger);
            user.getItemCooldownManager().set(stack.getItem(), 20);
            user.incrementStat(Stats.USED.getOrCreateStat(this));
        }
        return TypedActionResult.success(stack);
    }
}
