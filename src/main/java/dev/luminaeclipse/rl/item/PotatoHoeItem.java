package dev.luminaeclipse.rl.item;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PotatoHoeItem extends HoeItem {
    public PotatoHoeItem(ToolMaterial material, Settings settings) {
        super(material, -4, 0.0F, settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof ServerPlayerEntity serverPlayerEntity) {
            Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
            serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
        }
        if (!world.isClient && user instanceof PlayerEntity) {
            user.eatFood(world, stack.copy());
            stack.damage(75, user, (p) -> p.sendToolBreakStatus(user.getActiveHand()));
        }
        return stack;
    }
}
