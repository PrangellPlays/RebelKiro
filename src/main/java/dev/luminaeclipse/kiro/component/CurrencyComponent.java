package dev.luminaeclipse.kiro.component;

import dev.luminaeclipse.kiro.init.KiroComponents;
import dev.luminaeclipse.kiro.currency.CurrencyConverter;
import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

import java.util.ArrayList;
import java.util.List;

public class CurrencyComponent implements Component, AutoSyncedComponent {
    private long value;
    private final PlayerEntity provider;
    private final List<Long> transactions;
    public CurrencyComponent(PlayerEntity provider) {
        this.provider = provider;
        this.transactions = new ArrayList<Long>();
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        value = tag.getLong("Value");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putLong("Value", value);
    }

    public long getValue() {
        return value;
    }

    @Deprecated
    public void setValue(long value) {
        this.value = value;

        if (!provider.getWorld().isClient) {
            KiroComponents.CURRENCY.sync(this.provider);
        }
    }

    public void modify(long value) {
        setValue(this.value + value);

        long tempValue = value < 0 ? -value : value;

        List<ItemStack> transactionStacks = CurrencyConverter.getAsItemStackList(tempValue);
        if (transactionStacks.isEmpty()) {
            return;
        }

        if (provider.getWorld().isClient()) {
            return;
        }
    }

    public void silentModify(long value) {
        setValue(this.value + value);
    }

    public void pushTransaction(long value) {
        this.transactions.add(value);
    }

    public Long popTransaction() {
        return this.transactions.remove(this.transactions.size() - 1);
    }

    public void commitTransactions() {
        this.modify(this.transactions.stream().mapToLong(Long::longValue).sum());
        this.transactions.clear();
    }
}