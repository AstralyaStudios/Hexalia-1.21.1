package net.grapes.hexalia.datagen.loot;

import net.grapes.hexalia.HexaliaMod;
import net.grapes.hexalia.item.ModItems;
import net.grapes.hexalia.loot.modifier.AddItemModifier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.concurrent.CompletableFuture;

public class GlobalLootModifier extends GlobalLootModifierProvider {
    public GlobalLootModifier(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, HexaliaMod.MODID);
    }

    @Override
    protected void start() {
        add("ancient_seed_from_sniffer_dig", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(BuiltInLootTables.SNIFFER_DIGGING.location()).build()}, ModItems.ANCIENT_SEED.get()));

        add("ancient_seed_from_jungle_temple", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(BuiltInLootTables.JUNGLE_TEMPLE.location()).build()}, ModItems.ANCIENT_SEED.get()));
    }
}