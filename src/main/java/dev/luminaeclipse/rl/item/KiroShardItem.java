package dev.luminaeclipse.rl.item;

import dev.luminaeclipse.rl.init.RLItems;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
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
            return Text.translatable("item.rl.fractured_kiro_shard");
        } else {
            return Text.translatable("item.rl.kiro_shard");
        }
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return !isFractured(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (stack.isOf(RLItems.KIRO_SHARD_K)) {
            tooltip.add(Text.translatable("item.rl.kiro_shard.desc_k"));
        } else if (stack.isOf(RLItems.KIRO_SHARD_RS)) {
            tooltip.add(Text.translatable("item.rl.kiro_shard.desc_rs"));
        } else if (stack.isOf(RLItems.KIRO_SHARD_V)) {
            tooltip.add(Text.translatable("item.rl.kiro_shard.desc_v"));
        } else if (stack.isOf(RLItems.KIRO_SHARD_RL)) {
            tooltip.add(Text.translatable("item.rl.kiro_shard.desc_rl"));
        } else if (stack.isOf(RLItems.KIRO_SHARD_V0)) {
            tooltip.add(Text.translatable("item.rl.kiro_shard.desc_v0"));
        } else if (stack.isOf(RLItems.KIRO_SHARD_A)) {
            tooltip.add(Text.translatable("item.rl.kiro_shard.desc_a"));
        }
    }
}
