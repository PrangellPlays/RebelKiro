package dev.luminaeclipse.rl;

import dev.luminaeclipse.rl.config.RLConfig;
import dev.luminaeclipse.rl.init.RLBlocks;
import dev.luminaeclipse.rl.init.RLItemGroups;
import dev.luminaeclipse.rl.init.RLItems;
import dev.luminaeclipse.rl.util.RLRegistries;
import dev.luminaeclipse.rl.util.RLTags;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RL implements ModInitializer {
	public static final String MOD_ID = "rl";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		MidnightConfig.init(MOD_ID, RLConfig.class);
		RLRegistries.init();
	}

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}
}