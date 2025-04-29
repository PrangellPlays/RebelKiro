package dev.luminaeclipse.kiro;

import dev.luminaeclipse.kiro.config.KiroConfig;
import dev.luminaeclipse.kiro.util.KiroRegistries;
import dev.luminaeclipse.kiro.util.PlayerURLData;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Kiro implements ModInitializer {
	public static final String MOD_ID = "kiro";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		MidnightConfig.init(MOD_ID, KiroConfig.class);
		KiroRegistries.init();
		PlayerURLData.init();
	}

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}
}