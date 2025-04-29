package dev.luminaeclipse.kiro.init;

import dev.luminaeclipse.kiro.Kiro;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class KiroItemGroups {
    public static final ItemGroup KIRO_ITEM_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(Kiro.MOD_ID, "items"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.kiro.items")).icon(() -> new ItemStack(KiroItems.KIRO_SHARD_K)).entries((displayContext, entries) -> {
                ItemStack kiroStack = new ItemStack(KiroItems.KIRO_SHARD_K);
                kiroStack.setNbt(new NbtCompound());
                kiroStack.getNbt().putInt("fractured", 1);
                ItemStack rsStack = new ItemStack(KiroItems.KIRO_SHARD_RS);
                rsStack.setNbt(new NbtCompound());
                rsStack.getNbt().putInt("fractured", 1);
                ItemStack vStack = new ItemStack(KiroItems.KIRO_SHARD_V);
                vStack.setNbt(new NbtCompound());
                vStack.getNbt().putInt("fractured", 1);
                ItemStack rlStack = new ItemStack(KiroItems.KIRO_SHARD_RL);
                rlStack.setNbt(new NbtCompound());
                rlStack.getNbt().putInt("fractured", 1);
                ItemStack v0Stack = new ItemStack(KiroItems.KIRO_SHARD_V0);
                v0Stack.setNbt(new NbtCompound());
                v0Stack.getNbt().putInt("fractured", 1);
                ItemStack aStack = new ItemStack(KiroItems.KIRO_SHARD_A);
                aStack.setNbt(new NbtCompound());
                aStack.getNbt().putInt("fractured", 1);

                entries.add(KiroItems.KIRO_SHARD_K);
                entries.add(kiroStack);
                entries.add(KiroItems.KIRO_SHARD_RS);
                entries.add(rsStack);
                entries.add(KiroItems.KIRO_SHARD_V);
                entries.add(vStack);
                entries.add(KiroItems.KIRO_SHARD_RL);
                entries.add(rlStack);
                entries.add(KiroItems.KIRO_SHARD_V0);
                entries.add(v0Stack);
                entries.add(KiroItems.KIRO_SHARD_A);
                entries.add(aStack);

                entries.add(KiroBlocks.COLLECTOR_MARK);
                entries.add(KiroBlocks.GLASS_PANEL);
                entries.add(KiroItems.POTATO_HOE);
                entries.add(KiroBlocks.MOSS_LAYER);
                entries.add(KiroBlocks.SNOW_LAYER);

                entries.add(KiroBlocks.OAK_HOLLOW_LOG);
                entries.add(KiroBlocks.STRIPPED_OAK_HOLLOW_LOG);
                entries.add(KiroBlocks.SPRUCE_HOLLOW_LOG);
                entries.add(KiroBlocks.STRIPPED_SPRUCE_HOLLOW_LOG);
                entries.add(KiroBlocks.BIRCH_HOLLOW_LOG);
                entries.add(KiroBlocks.STRIPPED_BIRCH_HOLLOW_LOG);
                entries.add(KiroBlocks.JUNGLE_HOLLOW_LOG);
                entries.add(KiroBlocks.STRIPPED_JUNGLE_HOLLOW_LOG);
                entries.add(KiroBlocks.ACACIA_HOLLOW_LOG);
                entries.add(KiroBlocks.STRIPPED_ACACIA_HOLLOW_LOG);
                entries.add(KiroBlocks.DARK_OAK_HOLLOW_LOG);
                entries.add(KiroBlocks.STRIPPED_DARK_OAK_HOLLOW_LOG);
                entries.add(KiroBlocks.CRIMSON_HOLLOW_STEM);
                entries.add(KiroBlocks.STRIPPED_CRIMSON_HOLLOW_STEM);
                entries.add(KiroBlocks.WARPED_HOLLOW_STEM);
                entries.add(KiroBlocks.STRIPPED_WARPED_HOLLOW_STEM);
                entries.add(KiroBlocks.MANGROVE_HOLLOW_LOG);
                entries.add(KiroBlocks.STRIPPED_MANGROVE_HOLLOW_LOG);
                entries.add(KiroBlocks.CHERRY_HOLLOW_LOG);
                entries.add(KiroBlocks.STRIPPED_CHERRY_HOLLOW_LOG);

                entries.add(KiroBlocks.OAK_BRANCH);
                entries.add(KiroBlocks.STRIPPED_OAK_BRANCH);
                entries.add(KiroBlocks.SPRUCE_BRANCH);
                entries.add(KiroBlocks.STRIPPED_SPRUCE_BRANCH);
                entries.add(KiroBlocks.BIRCH_BRANCH);
                entries.add(KiroBlocks.STRIPPED_BIRCH_BRANCH);
                entries.add(KiroBlocks.JUNGLE_BRANCH);
                entries.add(KiroBlocks.STRIPPED_JUNGLE_BRANCH);
                entries.add(KiroBlocks.ACACIA_BRANCH);
                entries.add(KiroBlocks.STRIPPED_ACACIA_BRANCH);
                entries.add(KiroBlocks.DARK_OAK_BRANCH);
                entries.add(KiroBlocks.STRIPPED_DARK_OAK_BRANCH);
                entries.add(KiroBlocks.CRIMSON_BRANCH);
                entries.add(KiroBlocks.STRIPPED_CRIMSON_BRANCH);
                entries.add(KiroBlocks.WARPED_BRANCH);
                entries.add(KiroBlocks.STRIPPED_WARPED_BRANCH);
                entries.add(KiroBlocks.MANGROVE_BRANCH);
                entries.add(KiroBlocks.STRIPPED_MANGROVE_BRANCH);
                entries.add(KiroBlocks.CHERRY_BRANCH);
                entries.add(KiroBlocks.STRIPPED_CHERRY_BRANCH);

                entries.add(KiroBlocks.LARGE_ITEM_FRAME);
                entries.add(KiroItems.VORTIX);
                entries.add(KiroItems.KATANA);
                entries.add(KiroItems.SILENT_BOOTS);

                entries.add(KiroBlocks.BRAZIER);

                entries.add(KiroItems.SHIRASTEEL_PLATE);
                entries.add(KiroItems.SHIRASTEEL_KEYCARD_1);
                entries.add(KiroItems.SHIRASTEEL_KEYCARD_2);
                entries.add(KiroItems.SHIRASTEEL_KEYCARD_3);
                entries.add(KiroItems.SHIRASTEEL_KEYCARD_MASTER);

                entries.add(KiroItems.VOID_INGOT);
                entries.add(KiroItems.VOID_CRYSTAL);

                entries.add(KiroItems.THROWING_DAGGER);

                entries.add(KiroItems.DEBUG_RANK_ITEM);
            }).build());


    public static void init() {

    }
}
