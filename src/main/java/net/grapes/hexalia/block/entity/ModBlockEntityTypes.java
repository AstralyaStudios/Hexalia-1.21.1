package net.grapes.hexalia.block.entity;

import net.grapes.hexalia.HexaliaMod;
import net.grapes.hexalia.block.ModBlocks;
import net.grapes.hexalia.block.entity.custom.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntityTypes {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, HexaliaMod.MODID);

    public static final Supplier<BlockEntityType<SmallCauldronBlockEntity>> SMALL_CAULDRON = BLOCK_ENTITY_TYPE.register("small_cauldron",
            () -> BlockEntityType.Builder.of(SmallCauldronBlockEntity::new, ModBlocks.SMALL_CAULDRON.get()).build(null));
    public static final Supplier<BlockEntityType<RitualTableBlockEntity>> RITUAL_TABLE = BLOCK_ENTITY_TYPE.register("ritual_table",
            () -> BlockEntityType.Builder.of(RitualTableBlockEntity::new, ModBlocks.RITUAL_TABLE.get()).build(null));
    public static final Supplier<BlockEntityType<RitualBrazierBlockEntity>> RITUAL_BRAZIER = BLOCK_ENTITY_TYPE.register("ritual_brazier",
            () -> BlockEntityType.Builder.of(RitualBrazierBlockEntity::new, ModBlocks.RITUAL_BRAZIER.get()).build(null));
    public static final Supplier<BlockEntityType<ShelfBlockEntity>> SHELF = BLOCK_ENTITY_TYPE.register("shelf",
            () -> BlockEntityType.Builder.of(ShelfBlockEntity::new, ModBlocks.SHELF.get()).build(null));

    // Enchanted Plants
    public static final Supplier<BlockEntityType<NautiliteBlockEntity>> NAUTILITE = BLOCK_ENTITY_TYPE.register("nautilite",
            () -> BlockEntityType.Builder.of(NautiliteBlockEntity::new, ModBlocks.NAUTILITE.get()).build(null));
    public static final Supplier<BlockEntityType<AstrylisBlockEntity>> ASTRYLIS = BLOCK_ENTITY_TYPE.register("astrylis",
            () -> BlockEntityType.Builder.of(AstrylisBlockEntity::new, ModBlocks.ASTRYLIS.get()).build(null));
    public static final Supplier<BlockEntityType<WindsongBlockEntity>> WINDSONG = BLOCK_ENTITY_TYPE.register("windsong",
            () -> BlockEntityType.Builder.of(WindsongBlockEntity::new, ModBlocks.WINDSONG.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY_TYPE.register(eventBus);
    }
}
