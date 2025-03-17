package dev.luminaeclipse.rl.util;

import dev.luminaeclipse.rl.RL;
import dev.luminaeclipse.rl.init.RLBlockEntities;
import dev.luminaeclipse.rl.init.RLBlocks;
import dev.luminaeclipse.rl.init.RLItemGroups;
import dev.luminaeclipse.rl.init.RLItems;
import dev.luminaeclipse.rl.item.KiroShardItem;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;

public class RLRegistries {
    public static void init() {
        RLBlocks.init();
        RLBlockEntities.init();
        RLItems.init();
        RLItemGroups.init();
        RLTags.init();
        registerStrippableBlocks();
    }

    public static void initClient() {
        registerModelPredicates();
        registerBlockRenderLayerMaps();
    }

    //Server
    private static void registerStrippableBlocks() {
        StrippableBlockRegistry.register(RLBlocks.OAK_HOLLOW_LOG, RLBlocks.STRIPPED_OAK_HOLLOW_LOG);
        StrippableBlockRegistry.register(RLBlocks.SPRUCE_HOLLOW_LOG, RLBlocks.STRIPPED_SPRUCE_HOLLOW_LOG);
        StrippableBlockRegistry.register(RLBlocks.BIRCH_HOLLOW_LOG, RLBlocks.STRIPPED_BIRCH_HOLLOW_LOG);
        StrippableBlockRegistry.register(RLBlocks.JUNGLE_HOLLOW_LOG, RLBlocks.STRIPPED_JUNGLE_HOLLOW_LOG);
        StrippableBlockRegistry.register(RLBlocks.ACACIA_HOLLOW_LOG, RLBlocks.STRIPPED_ACACIA_HOLLOW_LOG);
        StrippableBlockRegistry.register(RLBlocks.DARK_OAK_HOLLOW_LOG, RLBlocks.STRIPPED_DARK_OAK_HOLLOW_LOG);
        StrippableBlockRegistry.register(RLBlocks.CRIMSON_HOLLOW_STEM, RLBlocks.STRIPPED_CRIMSON_HOLLOW_STEM);
        StrippableBlockRegistry.register(RLBlocks.WARPED_HOLLOW_STEM, RLBlocks.STRIPPED_WARPED_HOLLOW_STEM);
        StrippableBlockRegistry.register(RLBlocks.MANGROVE_HOLLOW_LOG, RLBlocks.STRIPPED_MANGROVE_HOLLOW_LOG);
        StrippableBlockRegistry.register(RLBlocks.CHERRY_HOLLOW_LOG, RLBlocks.STRIPPED_CHERRY_HOLLOW_LOG);
    }

    //Client
    private static void registerModelPredicates() {
        ModelPredicateProviderRegistry.register(RLItems.KIRO_SHARD_K, RL.id("fractured_kiro_shard_k"), (stack, world, entity, i) -> {
            return KiroShardItem.isFractured(stack) ? 1.0F : 0.0F;
        });
        ModelPredicateProviderRegistry.register(RLItems.KIRO_SHARD_RS, RL.id("fractured_kiro_shard_rs"), (stack, world, entity, i) -> {
            return KiroShardItem.isFractured(stack) ? 1.0F : 0.0F;
        });
        ModelPredicateProviderRegistry.register(RLItems.KIRO_SHARD_V, RL.id("fractured_kiro_shard_v"), (stack, world, entity, i) -> {
            return KiroShardItem.isFractured(stack) ? 1.0F : 0.0F;
        });
        ModelPredicateProviderRegistry.register(RLItems.KIRO_SHARD_RL, RL.id("fractured_kiro_shard_rl"), (stack, world, entity, i) -> {
            return KiroShardItem.isFractured(stack) ? 1.0F : 0.0F;
        });
        ModelPredicateProviderRegistry.register(RLItems.KIRO_SHARD_V0, RL.id("fractured_kiro_shard_v0"), (stack, world, entity, i) -> {
            return KiroShardItem.isFractured(stack) ? 1.0F : 0.0F;
        });
        ModelPredicateProviderRegistry.register(RLItems.KIRO_SHARD_A, RL.id("fractured_kiro_shard_a"), (stack, world, entity, i) -> {
            return KiroShardItem.isFractured(stack) ? 1.0F : 0.0F;
        });
    }

    private static void registerBlockRenderLayerMaps() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(), new Block[]{RLBlocks.GLASS_PANEL});
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), new Block[]{RLBlocks.COLLECTOR_MARK, RLBlocks.MOSS_LAYER, RLBlocks.SNOW_LAYER, RLBlocks.LARGE_ITEM_FRAME});
    }
}