package net.astralya.hexalia;

import net.astralya.hexalia.block.ModBlocks;
import net.astralya.hexalia.effect.ModEffects;
import net.astralya.hexalia.item.ModItemGroups;
import net.astralya.hexalia.item.ModItems;
import net.astralya.hexalia.particle.ModParticleType;
import net.astralya.hexalia.sound.ModSoundEvents;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HexaliaMod implements ModInitializer {

	public static final String MODID = "hexalia";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModItemGroups.registerItemGroups();
		ModEffects.registerEffects();
		ModBlocks.registerModBlocks();
		ModParticleType.registerParticles();
		ModSoundEvents.registerSounds();
	}
}