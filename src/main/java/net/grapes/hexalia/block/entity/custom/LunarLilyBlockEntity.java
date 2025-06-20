package net.grapes.hexalia.block.entity.custom;

import net.grapes.hexalia.block.entity.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class LunarLilyBlockEntity extends BlockEntity {
    private static final int DEFAULT_DURATION = 1200;
    private static final int BONEMEAL_INTERVAL = 240;

    private long activationTime = -1;
    private int duration = DEFAULT_DURATION;
    private long lastBonemealTime = -1;

    public LunarLilyBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntityTypes.LUNAR_LILY.get(), pos, blockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, LunarLilyBlockEntity entity) {
        if (level instanceof ServerLevel serverLevel && entity.isActive()) {
            long currentTime = level.getGameTime();
            long elapsedTime = currentTime - entity.activationTime;

            if (!isNight(level)) {
                entity.deactivate();
                return;
            }

            if (elapsedTime >= entity.duration) {
                entity.deactivate();
                return;
            }

            long expectedBonemealApplications = elapsedTime / BONEMEAL_INTERVAL;
            long actualBonemealApplications = entity.lastBonemealTime == -1 ? 0 :
                    (entity.lastBonemealTime - entity.activationTime) / BONEMEAL_INTERVAL + 1;

            if (expectedBonemealApplications > actualBonemealApplications) {
                long missedApplications = Math.min(expectedBonemealApplications - actualBonemealApplications, 5);
                for (long i = 0; i < missedApplications; i++) {
                    applyBonemealToCropsAndSaplings(serverLevel, pos);
                }
                entity.lastBonemealTime = currentTime;
            } else if (elapsedTime % BONEMEAL_INTERVAL == 0 && elapsedTime > 0) {
                applyBonemealToCropsAndSaplings(serverLevel, pos);
                entity.lastBonemealTime = currentTime;
            }

            entity.setChanged();
        }
    }

    private static void applyBonemealToCropsAndSaplings(ServerLevel level, BlockPos centerPos) {
        BlockPos.betweenClosedStream(centerPos.offset(-4, -2, -4), centerPos.offset(4, 2, 4)).forEach(pos -> {
            BlockState state = level.getBlockState(pos);
            if (state.getBlock() instanceof BonemealableBlock bonemealableBlock &&
                    (state.is(BlockTags.CROPS) || state.is(BlockTags.SAPLINGS))) {
                if (bonemealableBlock.isValidBonemealTarget(level, pos, state)) {
                    bonemealableBlock.performBonemeal(level, level.random, pos, state);
                    level.sendParticles(ParticleTypes.HAPPY_VILLAGER, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                            1, 0.2, 0.2, 0.2, 0.0);
                }
            }
        });
    }

    public boolean isActive() {
        return activationTime > 0 && level != null && level.getGameTime() >= activationTime;
    }

    public void activate(long gameTime) {
        activate(gameTime, DEFAULT_DURATION);
    }

    public void activate(long gameTime, int customDuration) {
        this.activationTime = gameTime;
        this.duration = customDuration;
        this.lastBonemealTime = -1;
        this.setChanged();
    }

    public void deactivate() {
        this.activationTime = -1;
        this.lastBonemealTime = -1;
        this.setChanged();
    }

    public int getDuration() {
        return duration;
    }

    public float getProgress() {
        if (!isActive() || level == null) {
            return 0.0f;
        }
        long elapsed = level.getGameTime() - activationTime;
        return Math.min(1.0f, (float) elapsed / duration);
    }

    private static boolean isNight(Level level) {
        long time = level.getDayTime() % 24000;
        return time >= 13000 && time <= 23000;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putLong("activationTime", activationTime);
        tag.putInt("duration", duration);
        tag.putLong("lastBonemealTime", lastBonemealTime);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.activationTime = tag.getLong("activationTime");
        this.duration = tag.contains("duration") ? tag.getInt("duration") : DEFAULT_DURATION;
        this.lastBonemealTime = tag.getLong("lastBonemealTime");
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        tag.putLong("activationTime", activationTime);
        tag.putInt("duration", duration);
        tag.putLong("lastBonemealTime", lastBonemealTime);
        return tag;
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}