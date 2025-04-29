package dev.luminaeclipse.kiro.init;

import dev.luminaeclipse.kiro.Kiro;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public interface KiroParticles {
    Map<ParticleType<?>, Identifier> PARTICLES = new LinkedHashMap();
    DefaultParticleType BLOOD = (DefaultParticleType)create("blood", FabricParticleTypes.simple(true));

    static void init() {
        PARTICLES.keySet().forEach((particle) -> {
            Registry.register(Registries.PARTICLE_TYPE, (Identifier)PARTICLES.get(particle), particle);
        });
    }

    private static <T extends ParticleType<?>> T create(String name, T particle) {
        PARTICLES.put(particle, Kiro.id(name));
        return particle;
    }
}