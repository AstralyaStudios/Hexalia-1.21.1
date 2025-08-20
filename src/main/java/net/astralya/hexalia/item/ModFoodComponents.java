package net.astralya.hexalia.item;

import net.astralya.hexalia.effect.ModEffects;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class ModFoodComponents {
    public static final FoodComponent SIREN_KELP = new FoodComponent.Builder().nutrition(1).saturationModifier(0.1f)
            .statusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 100), 1f).build();

    public static final FoodComponent CHILLBERRIES = new FoodComponent.Builder().nutrition(2).saturationModifier(0.3f).build();
    public static final FoodComponent CHILLBERRY_PIE = new FoodComponent.Builder().nutrition(6).saturationModifier(0.8f)
            .statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 900, 1), 1f).build();

    // NEEDS OVERFED
    public static final FoodComponent MANDRAKE_STEW = new FoodComponent.Builder().nutrition(6).saturationModifier(0.7f)
            .statusEffect(new StatusEffectInstance(ModEffects.OVERFED, 900, 0), 1f).build();

    public static final FoodComponent SUNFIRE_TOMATO = new FoodComponent.Builder().nutrition(1).saturationModifier(0.3f).build();
    public static final FoodComponent SPICY_SANDWICH = new FoodComponent.Builder().nutrition(6).saturationModifier(0.6f)
            .statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 900, 1), 1f).build();

    public static final FoodComponent SALTSPROUT = new FoodComponent.Builder().nutrition(2).saturationModifier(0.1f)
            .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600), 1f).build();

    public static final FoodComponent GALEBERRIES = new FoodComponent.Builder().nutrition(2).saturationModifier(0.3f).build();
    public static final FoodComponent GALEBERRIES_COOKIE = new FoodComponent.Builder().nutrition(6).saturationModifier(0.8f)
            .statusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 900, 1), 1f).build();

    // NEEDS OVERFED
    public static final FoodComponent WITCH_SALAD = new FoodComponent.Builder().nutrition(6).saturationModifier(0.6f)
            .statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 600), 1f)
            .statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 600), 1f)
            .statusEffect(new StatusEffectInstance(ModEffects.OVERFED, 600, 0), 1f)
            .build();
}
