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
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class NautiliteBlockEntity extends BlockEntity {
    private int activeTicks = 0;
    private long activationTime = -1;
    private static final int DURATION = 2400;
    private static final int EFFECT_RADIUS = 16;

    public NautiliteBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntityTypes.NAUTILITE.get(), pos, blockState);
    }

    public void activate() {
        this.activeTicks = DURATION;
        if (this.level != null) {
            this.activationTime = this.level.getGameTime();
        }
        setChanged();
    }

    public boolean isActive() {
        return this.activeTicks > 0;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, NautiliteBlockEntity blockEntity) {
        if (blockEntity.activationTime != -1 && level != null) {
            long currentTime = level.getGameTime();
            long elapsed = currentTime - blockEntity.activationTime;
            int expectedTicks = DURATION - (int)elapsed;

            if (Math.abs(blockEntity.activeTicks - expectedTicks) > 5) {
                blockEntity.activeTicks = Math.max(0, expectedTicks);
            }
        }

        if (blockEntity.isActive()) {
            blockEntity.activeTicks--;

            if (level instanceof ServerLevel serverLevel) {
                AABB area = new AABB(pos).inflate(EFFECT_RADIUS);
                List<Player> players = serverLevel.getEntitiesOfClass(Player.class, area);
                List<LivingEntity> mobs = serverLevel.getEntitiesOfClass(LivingEntity.class, area);

                for (Player player : players) {
                    if (player.isInWaterOrRain()) {
                        player.addEffect(new MobEffectInstance(MobEffects.CONDUIT_POWER, 40, 0, true, false));
                    }
                    if (player.hasEffect(MobEffects.DIG_SLOWDOWN)) {
                        player.removeEffect(MobEffects.DIG_SLOWDOWN);
                    }
                }

                for (LivingEntity mob : mobs) {
                    if ((mob instanceof Drowned || mob instanceof Guardian) && mob.isInWaterOrRain() && pos.closerThan(mob.blockPosition(), EFFECT_RADIUS)) {
                        mob.hurt(level.damageSources().magic(), 2.0F);
                        serverLevel.sendParticles(ParticleTypes.BUBBLE, mob.getX(), mob.getY(), mob.getZ(), 10, 0.5, 0.5, 0.5, 0.1);
                    }
                }

                emitParticles(serverLevel, pos);
            }

            if (blockEntity.activeTicks <= 0) {
                level.playSound(null, pos, SoundEvents.CONDUIT_DEACTIVATE, SoundSource.BLOCKS, 1.0f, 1.0f);
                level.destroyBlock(pos, false);
            } else {
                blockEntity.setChanged();
            }
        }
    }

    private static void emitParticles(ServerLevel level, BlockPos pos) {
        Vec3 center = new Vec3(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
        RandomSource random = level.random;
        for (int i = 0; i < 5; i++) {
            double x = center.x + (random.nextDouble() - 0.5) * 2;
            double y = center.y + (random.nextDouble() - 0.5) * 2;
            double z = center.z + (random.nextDouble() - 0.5) * 2;
            level.sendParticles(ParticleTypes.BUBBLE, x, y, z, 1, 0, 0, 0, 0);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("ActiveTicks", activeTicks);
        tag.putLong("ActivationTime", activationTime);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.activeTicks = tag.getInt("ActiveTicks");
        this.activationTime = tag.getLong("ActivationTime");

        if (this.activationTime != -1 && this.level != null && this.activeTicks > 0) {
            long currentTime = this.level.getGameTime();
            long elapsed = currentTime - this.activationTime;
            this.activeTicks = Math.max(0, DURATION - (int)elapsed);
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        tag.putInt("ActiveTicks", activeTicks);
        tag.putLong("ActivationTime", activationTime);
        return tag;
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}