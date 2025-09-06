package net.astralya.hexalia.entity;

import net.astralya.hexalia.HexaliaMod;
import net.astralya.hexalia.entity.custom.RabbageProjectile;
import net.astralya.hexalia.entity.custom.SilkMothEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {

    public static final EntityType<SilkMothEntity> SILK_MOTH_ENTITY = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(HexaliaMod.MODID, "silk_moth"),
            EntityType.Builder.create(SilkMothEntity::new, SpawnGroup.CREATURE).dimensions(0.6f, 0.06f).build());

    public static final EntityType<RabbageProjectile> RABBAGE = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(HexaliaMod.MODID, "tomahawk"),
            EntityType.Builder.<RabbageProjectile>create(RabbageProjectile::new, SpawnGroup.MISC)
                    .dimensions(1.375f, 0.5625f).build());

    public static void registerModEntities() {
        HexaliaMod.LOGGER.info("Registering Mod Entities for " + HexaliaMod.MODID);
    }
}
