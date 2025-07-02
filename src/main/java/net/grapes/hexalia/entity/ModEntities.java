package net.grapes.hexalia.entity;

import net.grapes.hexalia.HexaliaMod;
import net.grapes.hexalia.entity.boat.ModBoatEntity;
import net.grapes.hexalia.entity.boat.ModChestBoatEntity;
import net.grapes.hexalia.entity.custom.RabbageProjectile;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, HexaliaMod.MODID);

    public static final Supplier<EntityType<RabbageProjectile>> RABBAGE  =
            ENTITY_TYPE.register("rabbage", () -> EntityType.Builder.<RabbageProjectile>of(RabbageProjectile::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("rabbage"));

    public static final Supplier<EntityType<ModBoatEntity>> MOD_BOAT =
            ENTITY_TYPE.register("mod_boat", () -> EntityType.Builder.<ModBoatEntity>of(ModBoatEntity::new, MobCategory.MISC)
                    .sized(1.375f, 0.5625f).build("mod_boat"));

    public static final Supplier<EntityType<ModChestBoatEntity>> MOD_CHEST_BOAT =
            ENTITY_TYPE.register("mod_chest_boat", () -> EntityType.Builder.<ModChestBoatEntity>of(ModChestBoatEntity::new, MobCategory.MISC)
                    .sized(1.375f, 0.5625f).build("mod_chest_boat"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPE.register(eventBus);
    }
}
