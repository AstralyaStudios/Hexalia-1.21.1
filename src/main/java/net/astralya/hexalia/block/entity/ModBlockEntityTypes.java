package net.astralya.hexalia.block.entity;

import net.astralya.hexalia.HexaliaMod;
import net.astralya.hexalia.block.ModBlocks;
import net.astralya.hexalia.block.entity.custom.AstrylisBlockEntity;
import net.astralya.hexalia.block.entity.custom.NautiliteBlockEntity;
import net.astralya.hexalia.block.entity.custom.WindsongBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntityTypes {


    // Enchanted Plants
    public static final BlockEntityType<NautiliteBlockEntity> NAUTILITE = Registry.register(Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(HexaliaMod.MODID, "nautilite"), BlockEntityType.Builder.create(NautiliteBlockEntity::new, ModBlocks.NAUTILITE).build(null));
    public static final BlockEntityType<AstrylisBlockEntity> ASTRYLIS = Registry.register(Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(HexaliaMod.MODID, "astrylis"), BlockEntityType.Builder.create(AstrylisBlockEntity::new, ModBlocks.ASTRYLIS).build(null));
    public static final BlockEntityType<WindsongBlockEntity> WINDSONG = Registry.register(Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(HexaliaMod.MODID, "windsong"), BlockEntityType.Builder.create(WindsongBlockEntity::new, ModBlocks.WINDSONG).build(null));

    public static void registerBlockEntities() {
        HexaliaMod.LOGGER.info("Registering Block Entities for " + HexaliaMod.MODID);
    }
}
