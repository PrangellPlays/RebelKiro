package dev.luminaeclipse.kiro.init;

import dev.luminaeclipse.kiro.Kiro;
import dev.luminaeclipse.kiro.entity.ThrowingDaggerEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public interface KiroEntities {
    Map<EntityType<? extends Entity>, Identifier> ENTITIES = new LinkedHashMap();
    EntityType<ThrowingDaggerEntity> THROWING_DAGGER = Registry.register(Registries.ENTITY_TYPE, Kiro.id("throwing_dagger"), FabricEntityTypeBuilder.<ThrowingDaggerEntity>create(SpawnGroup.MISC, ThrowingDaggerEntity::new).dimensions(new EntityDimensions(0.25F, 0.25F, true)).trackRangeChunks(4).trackedUpdateRate(10).build());

    private static <T extends EntityType<? extends Entity>> T createEntity(String name, T entity) {
        ENTITIES.put(entity, new Identifier(Kiro.MOD_ID, name));
        return entity;
    }

    static void init() {
        ENTITIES.keySet().forEach((entityType) -> {
            Registry.register(Registries.ENTITY_TYPE, (Identifier) ENTITIES.get(entityType), entityType);
        });
    }
}