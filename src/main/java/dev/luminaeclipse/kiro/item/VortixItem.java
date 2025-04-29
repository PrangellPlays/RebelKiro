package dev.luminaeclipse.kiro.item;

import dev.luminaeclipse.kiro.init.KiroComponents;
import dev.luminaeclipse.kiro.currency.Currency;
import dev.luminaeclipse.kiro.item.util.ColouredTooltipItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class VortixItem extends Item implements CurrencyItem, ColouredTooltipItem {
    public final Currency currency;

    public VortixItem(Currency currency) {
        super(new FabricItemSettings());
        this.currency = currency;
    }

    @Override
    public boolean onClicked(ItemStack clickedStack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (clickType != ClickType.LEFT) return false;

        if ((otherStack.getItem() == this && otherStack.getCount() + clickedStack.getCount() <= otherStack.getMaxCount()) || !(otherStack.getItem() instanceof CurrencyItem currencyItem))
            return false;

        long[] values = currencyItem.getCombinedValue(otherStack);
        values[this.currency.ordinal()] += clickedStack.getCount();

        cursorStackReference.set(ItemStack.EMPTY);

        return true;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.getStackInHand(hand) != null && !world.isClient()) {
            ItemStack clickedStack = user.getStackInHand(hand);
            long rawValue = ((VortixItem) clickedStack.getItem()).currency.getRawValue(clickedStack.getCount());

            KiroComponents.CURRENCY.get(user).modify(rawValue);

            if (!user.isCreative()) {
                user.getStackInHand(hand).decrement(clickedStack.getCount());
                //user.setStackInHand(hand, ItemStack.EMPTY);
                return TypedActionResult.success(clickedStack);
            }
        }
        return TypedActionResult.success(ItemStack.EMPTY);
    }

    @Override
    public boolean wasAdjusted(ItemStack other) {
        return other.getItem() != this;
    }

    @Override
    public long getValue(ItemStack stack) {
        return this.currency.getRawValue(stack.getCount());
    }

    @Override
    public long[] getCombinedValue(ItemStack stack) {
        final long[] values = new long[3];
        values[this.currency.ordinal()] = stack.getCount();
        return values;
    }


    //Android (android.graphics.Color)
    @Override
    public int startColor() {
        //#2ea2fa
        return 0xFF2EA2FA;
    }

    @Override
    public int endColor() {
        //#3b2d73
        return 0xFF3B2D73;
    }

    @Override
    public int backgroundColor() {
        //#110A31
        return 0xF0110A31;
    }
}
