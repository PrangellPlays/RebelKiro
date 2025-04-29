package dev.luminaeclipse.kiro.init;

import dev.luminaeclipse.kiro.Kiro;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public interface KiroSoundEvents {
    Map<SoundEvent, Identifier> SOUND_EVENTS = new LinkedHashMap();
    SoundEvent SILENT_STEP = createSoundEvent("silent_step");

    static void init() {
        SOUND_EVENTS.keySet().forEach((soundEvent) -> {
            Registry.register(Registries.SOUND_EVENT, (Identifier)SOUND_EVENTS.get(soundEvent), soundEvent);
        });
    }

    private static SoundEvent createSoundEvent(String path) {
        SoundEvent soundEvent = SoundEvent.of(new Identifier(Kiro.MOD_ID, path));
        SOUND_EVENTS.put(soundEvent, new Identifier(Kiro.MOD_ID, path));
        return soundEvent;
    }
}