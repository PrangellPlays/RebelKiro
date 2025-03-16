package dev.luminaeclipse.rl;

import dev.luminaeclipse.rl.util.RLRegistries;
import net.fabricmc.api.ClientModInitializer;

public class RLClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        RLRegistries.initClient();
    }
}
