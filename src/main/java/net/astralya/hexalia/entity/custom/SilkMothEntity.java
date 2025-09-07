package net.astralya.hexalia.entity.custom;

import net.astralya.hexalia.component.ModComponents;
import net.astralya.hexalia.component.item.MothData;
import net.astralya.hexalia.entity.ModEntities;
import net.astralya.hexalia.entity.ai.silkmoth.AttractedToLightGoal;
import net.astralya.hexalia.entity.custom.variant.SilkMothVariant;
import net.astralya.hexalia.item.ModItems;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.*;

public class SilkMothEntity extends Animal implements GeoEntity {

    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    private static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT =
            SynchedEntityData.defineId(SilkMothEntity.class, EntityDataSerializers.INT);

    public SilkMothEntity(EntityType<? extends Animal> type, Level level) {
        super(type, level);
        this.moveControl = new FlyingMoveControl(this, 20, true);
        this.setPathfindingMalus(PathType.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(PathType.WATER, -1.0F);
        this.setPathfindingMalus(PathType.WATER_BORDER, 16.0F);
        this.setPathfindingMalus(PathType.COCOA, -1.0F);
        this.setPathfindingMalus(PathType.FENCE, -1.0F);
    }

    public static AttributeSupplier setAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 2.0D)
                .add(Attributes.FLYING_SPEED, 0.3F)
                .add(Attributes.MOVEMENT_SPEED, 0.3F)
                .build();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FleeSunGoal(this, 1.25D));
        this.goalSelector.addGoal(1, new AttractedToLightGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new FloatGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D));
    }

    @Override
    public Vec3 getLeashOffset() {
        return new Vec3(0.0, 0.5F * this.getEyeHeight(), this.getBbWidth() * 0.2F);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation navigation = new FlyingPathNavigation(this, level) {
            @Override
            public boolean isStableDestination(BlockPos pos) {
                return !this.level.getBlockState(pos.below()).isAir();
            }
        };
        navigation.setCanOpenDoors(false);
        navigation.setCanFloat(false);
        navigation.setCanPassDoors(true);
        return navigation;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected boolean isFlapping() {
        return !this.onGround();
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {

    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return false;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.onGround() && this.getDeltaMovement().y < 0) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(1, 0.6, 1));
        }
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob other) {
        return ModEntities.SILK_MOTH_ENTITY.get().create(serverLevel);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> state) {
        if (!this.onGround()) {
            state.getController().setAnimation(RawAnimation.begin()
                    .then("animation.silkmoth.flying", Animation.LoopType.LOOP));
        } else {
            state.getController().setAnimation(RawAnimation.begin()
                    .then("animation.silkmoth.idle", Animation.LoopType.LOOP));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack heldItem = player.getItemInHand(hand);

        if (heldItem.is(ModItems.RUSTIC_BOTTLE.get()) && !this.level().isClientSide) {
            ItemStack bottledMoth = new ItemStack(ModItems.BOTTLED_MOTH.get());
            String name = this.hasCustomName() ? this.getCustomName().getString() : "";
            int variantId = this.getVariant().getId();

            bottledMoth.set(ModComponents.MOTH.get(), new MothData(name, variantId));

            this.remove(RemovalReason.DISCARDED);

            if (!player.getInventory().add(bottledMoth)) {
                this.spawnAtLocation(bottledMoth);
            }

            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.PLAYERS, 1.0F, 1.0F);
            heldItem.shrink(1);
            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(player, hand);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_ID_TYPE_VARIANT, 0);
    }

    public void setVariant(SilkMothVariant variant) {
        this.entityData.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }

    public SilkMothVariant getVariant() {
        return SilkMothVariant.byId(this.entityData.get(DATA_ID_TYPE_VARIANT) & 255);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                        MobSpawnType reason, @Nullable SpawnGroupData spawnGroupData) {
        this.setVariant(Util.getRandom(SilkMothVariant.values(), this.random));
        return super.finalizeSpawn(level, difficulty, reason, spawnGroupData);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("SilkMothVariant", this.getVariant().getId());
        if (this.hasCustomName()) {
            tag.putString("MothName", this.getCustomName().getString());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("SilkMothVariant")) {
            this.setVariant(SilkMothVariant.byId(tag.getInt("SilkMothVariant")));
        }
        if (tag.contains("MothName")) {
            this.setCustomName(Component.literal(tag.getString("MothName")));
        }
    }

}