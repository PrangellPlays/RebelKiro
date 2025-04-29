package dev.luminaeclipse.kiro.block.entity;

import dev.luminaeclipse.kiro.init.KiroBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class LargeItemFrameBlockEntity extends BlockEntity {
    private @Nullable ItemStack stack;

    public LargeItemFrameBlockEntity(BlockPos pos, BlockState state) {
        super(KiroBlockEntities.LARGE_ITEM_FRAME, pos, state);
        this.setStack(ItemStack.EMPTY);
    }

    public void setStack(@Nullable ItemStack stack) {
        this.stack = stack != null ? stack : ItemStack.EMPTY;
        this.markDirty();
        if (this.world != null) {
            this.world.updateListeners(this.pos, this.getCachedState(), this.getCachedState(), 3);
        }
    }

    public ItemStack getStack() {
        return this.stack != null ? this.stack : ItemStack.EMPTY;
    }

    protected void writeNbt(NbtCompound nbt) {
        nbt.put("Item", this.getStack().writeNbt(new NbtCompound()));
    }

    public void readNbt(NbtCompound nbt) {
        this.setStack(ItemStack.fromNbt(nbt.getCompound("Item")));
    }

    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }

    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }
}
