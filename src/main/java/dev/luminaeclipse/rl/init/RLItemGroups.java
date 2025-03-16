package dev.luminaeclipse.rl.init;

import dev.luminaeclipse.rl.RL;
import dev.luminaeclipse.rl.item.KiroShardItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class RLItemGroups {
    public static final ItemGroup RL_ITEM_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(RL.MOD_ID, "items"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.rl.items")).icon(() -> new ItemStack(RLItems.KIRO_SHARD_K)).entries((displayContext, entries) -> {
                ItemStack kiroStack = new ItemStack(RLItems.KIRO_SHARD_K);
                kiroStack.setNbt(new NbtCompound());
                kiroStack.getNbt().putInt("fractured", 1);
                ItemStack rsStack = new ItemStack(RLItems.KIRO_SHARD_RS);
                rsStack.setNbt(new NbtCompound());
                rsStack.getNbt().putInt("fractured", 1);
                ItemStack vStack = new ItemStack(RLItems.KIRO_SHARD_V);
                vStack.setNbt(new NbtCompound());
                vStack.getNbt().putInt("fractured", 1);
                ItemStack rlStack = new ItemStack(RLItems.KIRO_SHARD_RL);
                rlStack.setNbt(new NbtCompound());
                rlStack.getNbt().putInt("fractured", 1);
                ItemStack v0Stack = new ItemStack(RLItems.KIRO_SHARD_V0);
                v0Stack.setNbt(new NbtCompound());
                v0Stack.getNbt().putInt("fractured", 1);
                ItemStack aStack = new ItemStack(RLItems.KIRO_SHARD_A);
                aStack.setNbt(new NbtCompound());
                aStack.getNbt().putInt("fractured", 1);

                entries.add(RLItems.KIRO_SHARD_K);
                entries.add(kiroStack);
                entries.add(RLItems.KIRO_SHARD_RS);
                entries.add(rsStack);
                entries.add(RLItems.KIRO_SHARD_V);
                entries.add(vStack);
                entries.add(RLItems.KIRO_SHARD_RL);
                entries.add(rlStack);
                entries.add(RLItems.KIRO_SHARD_V0);
                entries.add(v0Stack);
                entries.add(RLItems.KIRO_SHARD_A);
                entries.add(aStack);

                entries.add(RLBlocks.COLLECTOR_MARK);
                entries.add(RLBlocks.GLASS_PANEL);
                entries.add(RLItems.POTATO_HOE);
                entries.add(RLBlocks.MOSS_LAYER);
                entries.add(RLBlocks.SNOW_LAYER);

                entries.add(RLBlocks.OAK_HOLLOW_LOG);
                entries.add(RLBlocks.STRIPPED_OAK_HOLLOW_LOG);
                entries.add(RLBlocks.SPRUCE_HOLLOW_LOG);
                entries.add(RLBlocks.STRIPPED_SPRUCE_HOLLOW_LOG);
                entries.add(RLBlocks.BIRCH_HOLLOW_LOG);
                entries.add(RLBlocks.STRIPPED_BIRCH_HOLLOW_LOG);
                entries.add(RLBlocks.JUNGLE_HOLLOW_LOG);
                entries.add(RLBlocks.STRIPPED_JUNGLE_HOLLOW_LOG);
                entries.add(RLBlocks.ACACIA_HOLLOW_LOG);
                entries.add(RLBlocks.STRIPPED_ACACIA_HOLLOW_LOG);
                entries.add(RLBlocks.DARK_OAK_HOLLOW_LOG);
                entries.add(RLBlocks.STRIPPED_DARK_OAK_HOLLOW_LOG);
                entries.add(RLBlocks.CRIMSON_HOLLOW_STEM);
                entries.add(RLBlocks.STRIPPED_CRIMSON_HOLLOW_STEM);
                entries.add(RLBlocks.WARPED_HOLLOW_STEM);
                entries.add(RLBlocks.STRIPPED_WARPED_HOLLOW_STEM);
                entries.add(RLBlocks.MANGROVE_HOLLOW_LOG);
                entries.add(RLBlocks.STRIPPED_MANGROVE_HOLLOW_LOG);
                entries.add(RLBlocks.CHERRY_HOLLOW_LOG);
                entries.add(RLBlocks.STRIPPED_CHERRY_HOLLOW_LOG);
            }).build());


    public static void init() {

    }
}
