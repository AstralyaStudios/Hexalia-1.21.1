package net.grapes.hexalia.util;

import net.grapes.hexalia.HexaliaMod;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;

public class ModArmorMaterials {

    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS =
            DeferredRegister.create(Registries.ARMOR_MATERIAL, HexaliaMod.MODID);

    public static final Holder<ArmorMaterial> BOGGED =
            ARMOR_MATERIALS.register("bogged", () -> new ArmorMaterial(
                    Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                        map.put(ArmorItem.Type.BOOTS,       3);
                        map.put(ArmorItem.Type.LEGGINGS,    5);
                        map.put(ArmorItem.Type.CHESTPLATE,  7);
                        map.put(ArmorItem.Type.HELMET,      2);
                        map.put(ArmorItem.Type.BODY,        0);
                    }),

                    15,

                    SoundEvents.ARMOR_EQUIP_LEATHER,

                    () -> Ingredient.of(Items.DRIED_KELP),

                    List.of(new ArmorMaterial.Layer(
                            ResourceLocation.fromNamespaceAndPath(HexaliaMod.MODID, "bogged"))),

                    1.0f,

                    0.0f
            ));


    public static void register(IEventBus eventBus) {
        ARMOR_MATERIALS.register(eventBus);
    }
}
