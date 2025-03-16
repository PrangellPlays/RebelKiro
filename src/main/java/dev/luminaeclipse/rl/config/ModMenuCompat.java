package dev.luminaeclipse.rl.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.luminaeclipse.rl.RL;
import eu.midnightdust.lib.config.MidnightConfig;

public class ModMenuCompat implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> MidnightConfig.getScreen(parent, RL.MOD_ID);
    }
}