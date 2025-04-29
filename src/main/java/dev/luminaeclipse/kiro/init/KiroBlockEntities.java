package dev.luminaeclipse.kiro.init;

import dev.luminaeclipse.kiro.Kiro;
import dev.luminaeclipse.kiro.block.entity.LargeItemFrameBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public class KiroBlockEntities {
    protected static final Map<BlockEntityType<?>, Identifier> BLOCK_ENTITY_TYPES = new LinkedHashMap();
    public static final BlockEntityType<LargeItemFrameBlockEntity> LARGE_ITEM_FRAME;

    public KiroBlockEntities() {
    }

    protected static <T extends BlockEntity> BlockEntityType<T> create(String name, BlockEntityType<T> blockEntityType) {
        BLOCK_ENTITY_TYPES.put(blockEntityType, Kiro.id(name));
        return blockEntityType;
    }

    public static void init() {
        BLOCK_ENTITY_TYPES.forEach((blockEntityType, id) -> {
            Registry.register(Registries.BLOCK_ENTITY_TYPE, id, blockEntityType);
        });
    }

    static {
        LARGE_ITEM_FRAME = create("large_item_frame", FabricBlockEntityTypeBuilder.create(LargeItemFrameBlockEntity::new, KiroBlocks.LARGE_ITEM_FRAME).build());
    }
}