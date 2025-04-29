package dev.luminaeclipse.kiro.currency;

import dev.luminaeclipse.kiro.item.CurrencyItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;

import java.util.ArrayList;
import java.util.List;

public class CurrencyHelper {
    public static long getMoneyInInventory(PlayerEntity player, boolean remove) {

        long value = 0;

        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (isCombined(stack)) continue;
            if (!(stack.getItem() instanceof CurrencyItem currencyItem)) continue;

            value += currencyItem.getValue(stack);

            if (remove) player.getInventory().removeOne(stack);
        }

        return value;
    }

    public static int getValue(List<ItemStack> stacks) {
        return stacks.stream().mapToInt(stack -> {
            if (stack == null) return 0;

            if (isCombined(stack)) return 0;
            if (!(stack.getItem() instanceof CurrencyItem currencyItem)) return 0;
            return (int) currencyItem.getValue(stack);
        }).sum();
    }

    public static void offerAsCoins(PlayerEntity player, long value) {
        for (ItemStack itemStack : CurrencyConverter.getAsValidStacks(value)) {
            player.getInventory().offerOrDrop(itemStack);
        }
    }

    public static boolean deductFromInventory(PlayerEntity player, long value) {
        long presentInInventory = getMoneyInInventory(player, false);
        if (presentInInventory < value) return false;

        getMoneyInInventory(player, true);

        offerAsCoins(player, presentInInventory - value);
        return true;
    }

    public static List<ItemStack> getAsStacks(long value, int maxStacks) {

        List<ItemStack> stacks = new ArrayList<>();
        List<ItemStack> rawStacks = CurrencyConverter.getAsValidStacks(value);

        if (rawStacks.size() <= maxStacks) {
            stacks.addAll(rawStacks);
        }

        return stacks;
    }

    public static ItemStack getClosest(long value) {
        long[] values = CurrencyResolver.splitValues(value);

        for (int i = 0; i < 2; i++) {
            if (values[i + 1] == 0) break;
            values[i + 1] += Math.round(values[i] / 100f);
            values[i] = 0;
        }

        return CurrencyConverter.getAsItemStackList(CurrencyResolver.combineValues(values)).get(0);
    }

    public static long[] getFromNbt(NbtCompound nbt, String key) {
        if (nbt.contains(key, NbtElement.LONG_ARRAY_TYPE)) return nbt.getLongArray(key).clone();
        if (!nbt.contains(key, NbtElement.INT_ARRAY_TYPE)) return new long[0];

        var intArray = nbt.getIntArray(key);
        var longArray = new long[intArray.length];
        for (int i = 0; i < intArray.length; i++) {
            longArray[i] = intArray[i];
        }

        return longArray;
    }

    private static boolean isCombined(ItemStack stack) {
        return stack.hasNbt() && stack.getNbt().contains("Combined", NbtElement.BYTE_TYPE);
    }
}