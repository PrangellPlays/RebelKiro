package dev.luminaeclipse.kiro.init;

import dev.luminaeclipse.kiro.Kiro;
import dev.luminaeclipse.kiro.item.*;
import dev.luminaeclipse.kiro.currency.Currency;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.LinkedHashMap;
import java.util.Map;

public class KiroItems {
    protected static final Map<Item, Identifier> ITEMS = new LinkedHashMap();
    public static final Item KIRO_SHARD_K;
    public static final Item KIRO_SHARD_RS;
    public static final Item KIRO_SHARD_V;
    public static final Item KIRO_SHARD_RL;
    public static final Item KIRO_SHARD_V0;
    public static final Item KIRO_SHARD_A;

    public static final Item POTATO_HOE;
    public static final VortixItem VORTIX;
    public static final Item KATANA;
    public static final Item SILENT_BOOTS;

    public static final Item SHIRASTEEL_PLATE;
    public static final Item SHIRASTEEL_KEYCARD_1;
    public static final Item SHIRASTEEL_KEYCARD_2;
    public static final Item SHIRASTEEL_KEYCARD_3;
    public static final Item SHIRASTEEL_KEYCARD_MASTER;

    public static final Item VOID_INGOT;
    public static final Item VOID_CRYSTAL;

    public static final Item THROWING_DAGGER;

    public static final Item DEBUG_RANK_ITEM;

    public static void init() {
        ITEMS.forEach((item, id) -> {
            Registry.register(Registries.ITEM, id, item);
        });
    }

    protected static <T extends Item> T register(String name, T item) {
        ITEMS.put(item, Kiro.id(name));
        return item;
    }

    public KiroItems() {
    }

    static {
        KIRO_SHARD_K = register((String) "kiro_shard_k", (Item) (new KiroShardItem(new FabricItemSettings().maxCount(1).fireproof().rarity(Rarity.COMMON))));
        KIRO_SHARD_RS = register((String) "kiro_shard_rs", (Item) (new KiroShardItem(new FabricItemSettings().maxCount(1).fireproof().rarity(Rarity.COMMON))));
        KIRO_SHARD_V = register((String) "kiro_shard_v", (Item) (new KiroShardItem(new FabricItemSettings().maxCount(1).fireproof().rarity(Rarity.COMMON))));
        KIRO_SHARD_RL = register((String) "kiro_shard_rl", (Item) (new KiroShardItem(new FabricItemSettings().maxCount(1).fireproof().rarity(Rarity.COMMON))));
        KIRO_SHARD_V0 = register((String) "kiro_shard_v0", (Item) (new KiroShardItem(new FabricItemSettings().maxCount(1).fireproof().rarity(Rarity.COMMON))));
        KIRO_SHARD_A = register((String) "kiro_shard_a", (Item) (new KiroShardItem(new FabricItemSettings().maxCount(1).fireproof().rarity(Rarity.COMMON))));

        POTATO_HOE = register((String) "potato_hoe", (Item) (new PotatoHoeItem(RLToolMaterials.POTATO, new FabricItemSettings().fireproof().food((new FoodComponent.Builder()).meat().hunger(5).saturationModifier(0.7F).build()))));
        VORTIX = (VortixItem) register((String) "vortix", (Item) (new VortixItem(Currency.VORTIX)));
        KATANA = register((String) "katana", (Item) (new KatanaItem(new FabricItemSettings())));
        SILENT_BOOTS = register((String) "silent_boots", (Item) (new ArmorItem(KiroArmorMaterials.SILENT, ArmorItem.Type.BOOTS, new FabricItemSettings().rarity(Rarity.RARE))));

        SHIRASTEEL_PLATE = register((String) "shirasteel_plate", (Item) (new Item(new FabricItemSettings())));
        SHIRASTEEL_KEYCARD_1 = register((String) "shirasteel_keycard_1", (Item) (new Item(new FabricItemSettings())));
        SHIRASTEEL_KEYCARD_2 = register((String) "shirasteel_keycard_2", (Item) (new Item(new FabricItemSettings())));
        SHIRASTEEL_KEYCARD_3 = register((String) "shirasteel_keycard_3", (Item) (new Item(new FabricItemSettings())));
        SHIRASTEEL_KEYCARD_MASTER = register((String) "shirasteel_keycard_master", (Item) (new Item(new FabricItemSettings())));

        VOID_INGOT = register((String) "void_ingot", (Item) (new Item(new FabricItemSettings())));
        VOID_CRYSTAL = register((String) "void_crystal", (Item) (new Item(new FabricItemSettings())));

        THROWING_DAGGER = register((String) "throwing_dagger", (Item) (new ThrowingDaggerItem(new FabricItemSettings())));

        DEBUG_RANK_ITEM = register((String) "debug_rank_item", (Item) (new RankDebugItem(new FabricItemSettings().maxCount(1))));
    }
}
