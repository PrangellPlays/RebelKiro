package dev.luminaeclipse.kiro;

import dev.luminaeclipse.kiro.init.KiroParticles;
import dev.luminaeclipse.kiro.util.KiroRegistries;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

import java.util.HashMap;
import java.util.Map;

public class KiroClient implements ClientModInitializer {
    private final Map<Integer, Float> entityHealthMap = new HashMap();
    private Integer bleedCooldown = 100;

    @Override
    public void onInitializeClient() {
        KiroRegistries.initClient();
        ClientTickEvents.END_CLIENT_TICK.register(this::onClientTick);
        ClientPlayConnectionEvents.JOIN.register((ClientPlayConnectionEvents.Join)(handler, sender, client) -> entityHealthMap.clear());
    }

    private void onClientTick(MinecraftClient client) {
        if (client.world != null) {
            client.world.getEntities().forEach(this::checkEntityHealth);
            bleedCooldown = bleedCooldown - 1;
        }
    }

    private void checkEntityHealth(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            float currentHealth = livingEntity.getHealth();
            float maxHealth = livingEntity.getMaxHealth();
            int entityId = livingEntity.getId();
            if (this.entityHealthMap.containsKey(entityId)) {
                float previousHealth = (Float)this.entityHealthMap.get(entityId);
                if (currentHealth < previousHealth) {
                    this.spawnBloodParticles(livingEntity, previousHealth - currentHealth);
                }

                if (currentHealth < maxHealth) {
                    this.spawnPassiveBloodParticles(livingEntity);
                }
            }

            this.entityHealthMap.put(entityId, currentHealth);
        }

    }

    private void spawnBloodParticles(Entity entity, float damageTaken) {
        ClientWorld world = (ClientWorld) entity.getWorld();
        Vec3d pos = entity.getPos();
        int particleCount = Math.max(20, (int)(damageTaken * 100.0F));

        for(int i = 0; i < particleCount; ++i) {
            double offsetX = (world.random.nextDouble() - (double)0.5F) * (double)entity.getWidth();
            double offsetY = world.random.nextDouble() * (double)entity.getHeight();
            double offsetZ = (world.random.nextDouble() - (double)0.5F) * (double)entity.getWidth();
            world.addParticle(KiroParticles.BLOOD, pos.x + offsetX, pos.y + offsetY, pos.z + offsetZ, (double)0.0F, (double)0.0F, (double)0.0F);
        }
    }

    private void spawnPassiveBloodParticles(Entity entity) {
        ClientWorld world = (ClientWorld) entity.getWorld();
        Vec3d pos = entity.getPos();
        int particleCount = 10;

        if (bleedCooldown == 0) {
            bleedCooldown = 100;
            for (int i = 0; i < particleCount; ++i) {
                double offsetX = (world.random.nextDouble() - (double) 0.5F) * (double) entity.getWidth();
                double offsetY = world.random.nextDouble() * (double) entity.getHeight();
                double offsetZ = (world.random.nextDouble() - (double) 0.5F) * (double) entity.getWidth();
                world.addParticle(KiroParticles.BLOOD, pos.x + offsetX, pos.y + offsetY, pos.z + offsetZ, (double) 0.0F, (double) 0.0F, (double) 0.0F);
            }
        }
    }
}
