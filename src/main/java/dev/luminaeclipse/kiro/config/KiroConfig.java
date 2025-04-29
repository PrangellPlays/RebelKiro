package dev.luminaeclipse.kiro.config;

import eu.midnightdust.lib.config.MidnightConfig;

public class KiroConfig extends MidnightConfig {
    public static final String kiro = "kiro";
    @Entry(category = kiro)
    public static boolean enableMod = true;
}
