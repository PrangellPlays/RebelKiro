package dev.luminaeclipse.kiro.currency;

import dev.luminaeclipse.kiro.init.KiroItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;

public enum Currency implements ItemConvertible {
    VORTIX {
        @Override
        public long getRawValue(long amount) {
            return amount;
        }

        @Override
        public Item asItem() {
            return KiroItems.VORTIX;
        }
    };

    public abstract long getRawValue(long amount);
}