package dev.luminaeclipse.rl.init;

import dev.luminaeclipse.rl.RL;
import dev.luminaeclipse.rl.block.entity.LargeItemFrameBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public class RLBlockEntities {
    protected static final Map<BlockEntityType<?>, Identifier> BLOCK_ENTITY_TYPES = new LinkedHashMap();
    public static final BlockEntityType<LargeItemFrameBlockEntity> LARGE_ITEM_FRAME;

    public RLBlockEntities() {
    }

    protected static <T extends BlockEntity> BlockEntityType<T> create(String name, BlockEntityType<T> blockEntityType) {
        BLOCK_ENTITY_TYPES.put(blockEntityType, RL.id(name));
        return blockEntityType;
    }

    public static void init() {
        BLOCK_ENTITY_TYPES.forEach((blockEntityType, id) -> {
            Registry.register(Registries.BLOCK_ENTITY_TYPE, id, blockEntityType);
        });
    }

    static {
        LARGE_ITEM_FRAME = create("large_item_frame", FabricBlockEntityTypeBuilder.create(LargeItemFrameBlockEntity::new, RLBlocks.LARGE_ITEM_FRAME).build());
    }
}