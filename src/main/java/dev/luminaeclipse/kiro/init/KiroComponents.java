package dev.luminaeclipse.kiro.init;

import dev.luminaeclipse.kiro.Kiro;
import dev.luminaeclipse.kiro.component.CurrencyComponent;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;

public class KiroComponents implements EntityComponentInitializer {
    public static final ComponentKey<CurrencyComponent> CURRENCY = ComponentRegistry.getOrCreate(Kiro.id("currency"), CurrencyComponent.class);

    public KiroComponents() {
    }

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry entityComponentFactoryRegistry) {
        entityComponentFactoryRegistry.registerForPlayers(CURRENCY, CurrencyComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
    }
}
