package dev.luminaeclipse.rl.init;

import dev.luminaeclipse.rl.RL;
import dev.luminaeclipse.rl.block.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.Instrument;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

public class RLBlocks {
    protected static final Map<Block, Identifier> BLOCKS = new LinkedHashMap();
    public static final Block GLASS_PANEL;
    public static final Block COLLECTOR_MARK;
    public static final Block MOSS_LAYER;
    public static final Block SNOW_LAYER;
    public static final Block LARGE_ITEM_FRAME;

    public static final Block OAK_HOLLOW_LOG;
    public static final Block STRIPPED_OAK_HOLLOW_LOG;
    public static final Block SPRUCE_HOLLOW_LOG;
    public static final Block STRIPPED_SPRUCE_HOLLOW_LOG;
    public static final Block BIRCH_HOLLOW_LOG;
    public static final Block STRIPPED_BIRCH_HOLLOW_LOG;
    public static final Block JUNGLE_HOLLOW_LOG;
    public static final Block STRIPPED_JUNGLE_HOLLOW_LOG;
    public static final Block ACACIA_HOLLOW_LOG;
    public static final Block STRIPPED_ACACIA_HOLLOW_LOG;
    public static final Block DARK_OAK_HOLLOW_LOG;
    public static final Block STRIPPED_DARK_OAK_HOLLOW_LOG;
    public static final Block CRIMSON_HOLLOW_STEM;
    public static final Block STRIPPED_CRIMSON_HOLLOW_STEM;
    public static final Block WARPED_HOLLOW_STEM;
    public static final Block STRIPPED_WARPED_HOLLOW_STEM;
    public static final Block MANGROVE_HOLLOW_LOG;
    public static final Block STRIPPED_MANGROVE_HOLLOW_LOG;
    public static final Block CHERRY_HOLLOW_LOG;
    public static final Block STRIPPED_CHERRY_HOLLOW_LOG;

    public static void init() {
        BLOCKS.forEach((block, id) -> {
            Registry.register(Registries.BLOCK, id, block);
        });
    }

    protected static <T extends Block> T register(String name, T block) {
        BLOCKS.put(block, RL.id(name));
        return block;
    }

    protected static <T extends Block> T registerWithItem(String name, T block) {
        return registerWithItem(name, block, new FabricItemSettings());
    }

    protected static <T extends Block> T registerWithItem(String name, T block, FabricItemSettings settings) {
        return registerWithItem(name, block, (b) -> {
            return new BlockItem(b, settings);
        });
    }

    protected static <T extends Block> T registerWithItem(String name, T block, Function<T, BlockItem> itemGenerator) {
        RLItems.register(name, (BlockItem)itemGenerator.apply(block));
        return register(name, block);
    }

    public RLBlocks() {
    }

    static {
        GLASS_PANEL = registerWithItem("glass_panel", new GlassPanelBlock(FabricBlockSettings.create().instrument(Instrument.HAT).strength(0.3F).sounds(BlockSoundGroup.GLASS).nonOpaque().allowsSpawning(Blocks::never)));
        COLLECTOR_MARK = registerWithItem("collector_mark", new CollectorMarkBlock(FabricBlockSettings.create().breakInstantly().nonOpaque().allowsSpawning(Blocks::never).noBlockBreakParticles()));
        MOSS_LAYER = registerWithItem("moss_layer", new LayerBlock(AbstractBlock.Settings.create().mapColor(MapColor.GREEN).sounds(BlockSoundGroup.MOSS_CARPET).noCollision().breakInstantly()));
        SNOW_LAYER = registerWithItem("snow_layer", new LayerBlock(AbstractBlock.Settings.create().mapColor(MapColor.WHITE).sounds(BlockSoundGroup.POWDER_SNOW).noCollision().breakInstantly()));
        LARGE_ITEM_FRAME = registerWithItem("large_item_frame", new LargeItemFrameBlock(AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN).instrument(Instrument.BASS).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)));

        OAK_HOLLOW_LOG = registerWithItem("oak_hollow_log", HollowLogBlock.of(Blocks.OAK_LOG));
        STRIPPED_OAK_HOLLOW_LOG = registerWithItem("stripped_oak_hollow_log", HollowLogBlock.of(Blocks.STRIPPED_OAK_LOG));
        SPRUCE_HOLLOW_LOG = registerWithItem("spruce_hollow_log", HollowLogBlock.of(Blocks.SPRUCE_LOG));
        STRIPPED_SPRUCE_HOLLOW_LOG = registerWithItem("stripped_spruce_hollow_log", HollowLogBlock.of(Blocks.STRIPPED_SPRUCE_LOG));
        BIRCH_HOLLOW_LOG = registerWithItem("birch_hollow_log", HollowLogBlock.of(Blocks.BIRCH_LOG));
        STRIPPED_BIRCH_HOLLOW_LOG = registerWithItem("stripped_birch_hollow_log", HollowLogBlock.of(Blocks.STRIPPED_BIRCH_LOG));
        JUNGLE_HOLLOW_LOG = registerWithItem("jungle_hollow_log", HollowLogBlock.of(Blocks.JUNGLE_LOG));
        STRIPPED_JUNGLE_HOLLOW_LOG = registerWithItem("stripped_jungle_hollow_log", HollowLogBlock.of(Blocks.STRIPPED_JUNGLE_LOG));
        ACACIA_HOLLOW_LOG = registerWithItem("acacia_hollow_log", HollowLogBlock.of(Blocks.ACACIA_LOG));
        STRIPPED_ACACIA_HOLLOW_LOG = registerWithItem("stripped_acacia_hollow_log", HollowLogBlock.of(Blocks.STRIPPED_ACACIA_LOG));
        DARK_OAK_HOLLOW_LOG = registerWithItem("dark_oak_hollow_log", HollowLogBlock.of(Blocks.DARK_OAK_LOG));
        STRIPPED_DARK_OAK_HOLLOW_LOG = registerWithItem("stripped_dark_oak_hollow_log", HollowLogBlock.of(Blocks.STRIPPED_DARK_OAK_LOG));
        CRIMSON_HOLLOW_STEM = registerWithItem("crimson_hollow_stem", HollowLogBlock.of(Blocks.CRIMSON_STEM));
        STRIPPED_CRIMSON_HOLLOW_STEM = registerWithItem("stripped_crimson_hollow_stem", HollowLogBlock.of(Blocks.STRIPPED_CRIMSON_STEM));
        WARPED_HOLLOW_STEM = registerWithItem("warped_hollow_stem", HollowLogBlock.of(Blocks.WARPED_STEM));
        STRIPPED_WARPED_HOLLOW_STEM = registerWithItem("stripped_warped_hollow_stem", HollowLogBlock.of(Blocks.STRIPPED_WARPED_STEM));
        MANGROVE_HOLLOW_LOG = registerWithItem("mangrove_hollow_log", HollowLogBlock.of(Blocks.MANGROVE_LOG));
        STRIPPED_MANGROVE_HOLLOW_LOG = registerWithItem("stripped_mangrove_hollow_log", HollowLogBlock.of(Blocks.STRIPPED_MANGROVE_LOG));
        CHERRY_HOLLOW_LOG = registerWithItem("cherry_hollow_log", HollowLogBlock.of(Blocks.CHERRY_LOG));
        STRIPPED_CHERRY_HOLLOW_LOG = registerWithItem("stripped_cherry_hollow_log", HollowLogBlock.of(Blocks.STRIPPED_CHERRY_LOG));
    }
}