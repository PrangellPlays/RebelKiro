package dev.luminaeclipse.kiro.item;

import dev.luminaeclipse.kiro.item.util.ColouredTooltipItem;
import dev.luminaeclipse.kiro.util.PlayerURLData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class RankDebugItem extends Item implements ColouredTooltipItem {
    private boolean discard = false;
    public RankDebugItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!discard) {
            if (PlayerURLData.isPlayerTeam(user)) {
                user.sendMessage(Text.literal("You are part of the Kiro Team"), true);
            } else if (PlayerURLData.isPlayerPartner(user)) {
                user.sendMessage(Text.literal("You are a partner of Kiro"), true);
            } else {
                user.sendMessage(Text.literal("You do not have special data"), true);
            }
        }
        discard = !discard;
        return super.use(world, user, hand);
    }

    @Override
    public int startColor() {
        //#C74EBD
        return 0xFFC74EBD;
    }

    @Override
    public int endColor() {
        //#2D2D2D
        return 0xFF2D2D2D;
    }

    @Override
    public int backgroundColor() {
        //#000000
        return 0xF0000000;
    }
}
