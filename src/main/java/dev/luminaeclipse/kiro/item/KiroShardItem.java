package dev.luminaeclipse.kiro.item;

import dev.luminaeclipse.kiro.init.KiroItems;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class KiroShardItem extends Item {
    public KiroShardItem(Settings settings) {
        super(settings);
    }

    public static boolean isFractured(ItemStack stack) {
        if (!stack.hasNbt()) {
            return false;
        } else {
            return stack.getNbt().contains("fractured") && stack.getNbt().getInt("fractured") != 0;
        }
    }

    @Override
    public Text getName(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        if (nbt != null && nbt.contains("fractured")) {
            return Text.translatable("item.kiro.fractured_kiro_shard");
        } else {
            return Text.translatable("item.kiro.kiro_shard");
        }
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return !isFractured(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World World, List<Text> tooltip, TooltipContext context) {
        if (stack.isOf(KiroItems.KIRO_SHARD_K)) {
            tooltip.add(Text.translatable("item.kiro.kiro_shard.desc_k"));
        } else if (stack.isOf(KiroItems.KIRO_SHARD_RS)) {
            tooltip.add(Text.translatable("item.kiro.kiro_shard.desc_rs"));
        } else if (stack.isOf(KiroItems.KIRO_SHARD_V)) {
            tooltip.add(Text.translatable("item.kiro.kiro_shard.desc_v"));
        } else if (stack.isOf(KiroItems.KIRO_SHARD_RL)) {
            tooltip.add(Text.translatable("item.kiro.kiro_shard.desc_kiro"));
        } else if (stack.isOf(KiroItems.KIRO_SHARD_V0)) {
            tooltip.add(Text.translatable("item.kiro.kiro_shard.desc_v0"));
        } else if (stack.isOf(KiroItems.KIRO_SHARD_A)) {
            tooltip.add(Text.translatable("item.kiro.kiro_shard.desc_a"));
        }
    }
}
