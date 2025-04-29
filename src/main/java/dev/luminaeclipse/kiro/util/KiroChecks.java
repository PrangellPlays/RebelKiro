package dev.luminaeclipse.kiro.util;

import dev.luminaeclipse.kiro.init.KiroItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Arrays;

public class KiroChecks {
    public static ArrayList<Identifier> silentBootsList;

    public KiroChecks() {
    }

    public static boolean isWearingSilentBoots(LivingEntity livingEntity) {
        return isItemStackSilentBoots(livingEntity.getEquippedStack(EquipmentSlot.FEET));
    }

    public static boolean isItemStackSilentBoots(ItemStack stack) {
        return silentBootsList.contains(Registries.ITEM.getId(stack.getItem()));
    }

    static {
        silentBootsList = new ArrayList(Arrays.asList(Registries.ITEM.getId(KiroItems.SILENT_BOOTS)));
    }
}
