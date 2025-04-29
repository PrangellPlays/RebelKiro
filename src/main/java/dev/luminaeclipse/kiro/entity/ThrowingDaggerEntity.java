package dev.luminaeclipse.kiro.entity;

import dev.luminaeclipse.kiro.init.KiroEntities;
import dev.luminaeclipse.kiro.init.KiroItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class ThrowingDaggerEntity extends ThrownItemEntity {
    public ThrowingDaggerEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public ThrowingDaggerEntity(World world, LivingEntity owner) {
        super(KiroEntities.THROWING_DAGGER, owner, world);
    }

    public ThrowingDaggerEntity(World world, double x, double y, double z) {
        super(KiroEntities.THROWING_DAGGER, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return KiroItems.THROWING_DAGGER;
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        this.discard();
        super.onBlockHit(blockHitResult);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        LivingEntity target = (LivingEntity) entityHitResult.getEntity();
        target.damage(getWorld().getDamageSources().mobProjectile(getOwner(), this.getControllingPassenger()), 5);
        super.onEntityHit(entityHitResult);
    }
}
