package dev.luminaeclipse.rl.init;

import dev.luminaeclipse.rl.RL;
import dev.luminaeclipse.rl.item.KiroShardItem;
import dev.luminaeclipse.rl.item.PotatoHoeItem;
import dev.luminaeclipse.rl.item.RLToolMaterials;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.LinkedHashMap;
import java.util.Map;

public class RLItems {
    protected static final Map<Item, Identifier> ITEMS = new LinkedHashMap();
    public static final Item KIRO_SHARD_K;
    public static final Item KIRO_SHARD_RS;
    public static final Item KIRO_SHARD_V;
    public static final Item KIRO_SHARD_RL;
    public static final Item KIRO_SHARD_V0;
    public static final Item KIRO_SHARD_A;

    public static final Item POTATO_HOE;

    public static void init() {
        ITEMS.forEach((item, id) -> {
            Registry.register(Registries.ITEM, id, item);
        });
    }

    protected static <T extends Item> T register(String name, T item) {
        ITEMS.put(item, RL.id(name));
        return item;
    }

    public RLItems() {
    }

    static {
        KIRO_SHARD_K = register((String) "kiro_shard_k", (Item) (new KiroShardItem(new FabricItemSettings().maxCount(1).fireproof().rarity(Rarity.COMMON))));
        KIRO_SHARD_RS = register((String) "kiro_shard_rs", (Item) (new KiroShardItem(new FabricItemSettings().maxCount(1).fireproof().rarity(Rarity.COMMON))));
        KIRO_SHARD_V = register((String) "kiro_shard_v", (Item) (new KiroShardItem(new FabricItemSettings().maxCount(1).fireproof().rarity(Rarity.COMMON))));
        KIRO_SHARD_RL = register((String) "kiro_shard_rl", (Item) (new KiroShardItem(new FabricItemSettings().maxCount(1).fireproof().rarity(Rarity.COMMON))));
        KIRO_SHARD_V0 = register((String) "kiro_shard_v0", (Item) (new KiroShardItem(new FabricItemSettings().maxCount(1).fireproof().rarity(Rarity.COMMON))));
        KIRO_SHARD_A = register((String) "kiro_shard_a", (Item) (new KiroShardItem(new FabricItemSettings().maxCount(1).fireproof().rarity(Rarity.COMMON))));

        POTATO_HOE = register((String) "potato_hoe", (Item) (new PotatoHoeItem(RLToolMaterials.POTATO, new FabricItemSettings().fireproof().food((new FoodComponent.Builder()).meat().hunger(5).saturationModifier(0.7F).build()))));
    }
}
