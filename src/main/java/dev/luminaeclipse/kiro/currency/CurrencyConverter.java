package dev.luminaeclipse.kiro.currency;

import dev.luminaeclipse.kiro.init.KiroItems;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CurrencyConverter {
    public static ItemStack[] getAsItemStackArray(long value) {
        ItemStack[] output = new ItemStack[]{null, null, null};

        long[] values = CurrencyResolver.splitValues(value);

        output[0] = new ItemStack(KiroItems.VORTIX, asInt(values[0]));

        return output;
    }

    public static List<ItemStack> getAsItemStackList(long value) {
        List<ItemStack> list = new ArrayList<>();

        Arrays.stream(getAsItemStackArray(value)).forEach(itemStack -> {
            assert itemStack != null;
            if (itemStack.getCount() != 0) list.add(0, itemStack);
        });

        return list;
    }

    public static List<ItemStack> getAsItemStackList(long[] values) {
        List<ItemStack> list = new ArrayList<>();

        for (int i = 0; i < values.length; i++) {
            if (values[i] <= 0) continue;
            list.add(0, new ItemStack(Currency.values()[i], asInt(values[i])));
        }

        return list;
    }

    public static int getRequiredCurrencyTypes(long value) {
        return splitAtMaxCount(getAsItemStackList(value)).size();
    }

    public static List<ItemStack> splitAtMaxCount(List<ItemStack> input) {
        List<ItemStack> output = new ArrayList<>();

        for (ItemStack stack : input) {
            if (stack.getCount() <= stack.getMaxCount()) {
                output.add(stack);
            } else {
                for (int i = 0; i < stack.getCount() / stack.getMaxCount(); i++) {
                    ItemStack copy = stack.copy();
                    copy.setCount(stack.getMaxCount());
                    output.add(copy);
                }

                ItemStack copy = stack.copy();
                copy.setCount(stack.getCount() % stack.getMaxCount());
                output.add(copy);
            }
        }

        return output;
    }

    public static List<ItemStack> getAsValidStacks(long value) {
        return splitAtMaxCount(getAsItemStackList(value));
    }

    public static List<ItemStack> getAsValidStacks(long[] values) {
        return splitAtMaxCount(getAsItemStackList(values));
    }

    public static int asInt(long value) {
        return value > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) value;
    }
}