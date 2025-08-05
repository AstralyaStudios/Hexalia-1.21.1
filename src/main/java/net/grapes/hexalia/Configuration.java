package net.grapes.hexalia;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Configuration {

    public static ModConfigSpec COMMON_CONFIG;
    public static ModConfigSpec CLIENT_CONFIG;

    public static final String FUNCTIONAL_BLOCKS = "functional_blocks";
    public static ModConfigSpec.IntValue DREAMCATCHER_RADIUS;
    public static ModConfigSpec.IntValue PHANTOM_IGNITE_DURATION;

    public static final ModConfigSpec.IntValue CENSER_EFFECT_RADIUS;
    public static final ModConfigSpec.IntValue CENSER_EFFECT_DURATION;

    public static final String CATEGORY_BREWS = "brews";
    public static ModConfigSpec.IntValue BREW_EFFECT_DURATION;
    public static ModConfigSpec.IntValue BREW_AMPLIFIER_BONUS;

    public static final String CATEGORY_MANDRAKE = "mandrake";
    public static ModConfigSpec.DoubleValue MANDRAKE_SCREAM_RADIUS;
    public static ModConfigSpec.IntValue MANDRAKE_STUN_DURATION;

    public static final String CATEGORY_ENCHANTED_PLANTS = "enchanted_plants";

    public static ModConfigSpec.IntValue NAUTILITE_DURATION;
    public static ModConfigSpec.IntValue NAUTILITE_EFFECT_RADIUS;

    public static ModConfigSpec.IntValue WINDSONG_DURATION;
    public static ModConfigSpec.IntValue WINDSONG_EFFECT_RADIUS;

    public static ModConfigSpec.IntValue ASTRYLIS_DURATION;
    public static ModConfigSpec.IntValue ASTRYLIS_BONEMEAL_INTERVAL;

    static {
        ModConfigSpec.Builder COMMON_BUILDER = new ModConfigSpec.Builder();

        // --- CATEGORY: Dreamcatcher
        COMMON_BUILDER.comment("Functional Blocks Settings").push(FUNCTIONAL_BLOCKS);
        CENSER_EFFECT_RADIUS = COMMON_BUILDER
                .comment("Radius of the censer's area of effect")
                .defineInRange("censerEffectRadius", 16, 1, 64);
        CENSER_EFFECT_DURATION = COMMON_BUILDER
                .comment("Burn duration (in ticks) of the censer effect")
                .defineInRange("censerEffectDuration", 7200, 20, 24000);

        DREAMCATCHER_RADIUS = COMMON_BUILDER
                .comment("How far (in blocks) the Dreamcatcher detects and affects phantoms.")
                .defineInRange("radius", 16, 1, 64);
        PHANTOM_IGNITE_DURATION = COMMON_BUILDER
                .comment("Duration (in ticks) phantoms are set on fire. 20 ticks = 1 second.")
                .defineInRange("phantomIgniteDuration", 100, 0, 600);
        COMMON_BUILDER.pop();

        // --- CATEGORY: Brews
        COMMON_BUILDER.comment("Brew Settings").push(CATEGORY_BREWS);
        BREW_EFFECT_DURATION = COMMON_BUILDER
                .comment("Fixed duration in ticks for all brew effects. 20 ticks = 1 second. Default: 4800 ticks = 4 minutes")
                .defineInRange("brewEffectDuration", 4800, 20, 24000);
        BREW_AMPLIFIER_BONUS = COMMON_BUILDER
                .comment("Flat amplifier bonus applied to all brews. Default: 0")
                .defineInRange("brewAmplifierBonus", 0, -2, 5);
        COMMON_BUILDER.pop();

        // --- CATEGORY: Mandrake
        COMMON_BUILDER.comment("Mandrake Scream Settings").push(CATEGORY_MANDRAKE);
        MANDRAKE_SCREAM_RADIUS = COMMON_BUILDER
                .comment("Radius in blocks around the player affected by the Mandrake's scream. Default: 5.0")
                .defineInRange("mandrakeScreamRadius", 5.0, 1.0, 32.0);
        MANDRAKE_STUN_DURATION = COMMON_BUILDER
                .comment("Stun duration in ticks applied by the Mandrake's scream. Default: 60 ticks = 3 seconds")
                .defineInRange("mandrakeStunDuration", 60, 1, 600);
        COMMON_BUILDER.pop();

        // --- CATEGORY: Enchanted Plants
        COMMON_BUILDER.comment("Enchanted Plant Settings").push(CATEGORY_ENCHANTED_PLANTS);
        NAUTILITE_DURATION = COMMON_BUILDER
                .comment("How long (in ticks) the Nautilite stays active before disappearing. 20 ticks = 1 second. Default: 2400 (2 minutes)")
                .defineInRange("nautiliteDuration", 2400, 100, 24000);
        NAUTILITE_EFFECT_RADIUS = COMMON_BUILDER
                .comment("Radius in blocks around the Nautilite in which players and mobs are affected.")
                .defineInRange("nautiliteEffectRadius", 16, 1, 64);

        WINDSONG_DURATION = COMMON_BUILDER
                .comment("How long (in ticks) the Windsong stays active before disappearing. Default: 600 ticks = 30 seconds")
                .defineInRange("windsongDuration", 600, 100, 24000);
        WINDSONG_EFFECT_RADIUS = COMMON_BUILDER
                .comment("Radius in blocks around the Windsong that deflects projectiles. Default: 6")
                .defineInRange("windsongEffectRadius", 6, 1, 32);

        ASTRYLIS_DURATION = COMMON_BUILDER
                .comment("How long (in ticks) the Astrylis stays active before deactivating. Default: 1200 = 60 seconds")
                .defineInRange("astrylisDuration", 1200, 100, 24000);
        ASTRYLIS_BONEMEAL_INTERVAL = COMMON_BUILDER
                .comment("Interval in ticks between bonemeal pulses while Astrylis is active. Default: 240 ticks = 12 seconds")
                .defineInRange("astrylisBonemealInterval", 240, 20, 1200);
        COMMON_BUILDER.pop();


        COMMON_CONFIG = COMMON_BUILDER.build();
        ModConfigSpec.Builder CLIENT_BUILDER = new ModConfigSpec.Builder();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }
}