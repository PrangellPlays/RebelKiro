package dev.luminaeclipse.rl.config;

import eu.midnightdust.lib.config.MidnightConfig;

public class RLConfig extends MidnightConfig {
    public static final String rl = "rl";
    @Entry(category = rl)
    public static boolean enableMod = true;
}
