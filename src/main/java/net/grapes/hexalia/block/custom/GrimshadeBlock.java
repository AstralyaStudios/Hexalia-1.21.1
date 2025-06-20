package net.grapes.hexalia.block.custom;

import net.grapes.hexalia.block.ModBlocks;
import net.grapes.hexalia.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;

public class GrimshadeBlock extends EnchantedPlantBlock{

    public GrimshadeBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (level.isClientSide) {
            for (int i = 0; i < 3; i++) {
                double x = pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 0.5;
                double y = pos.getY() + 0.5 + (random.nextDouble() - 0.5) * 0.5;
                double z = pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 0.5;
                level.addParticle(ParticleTypes.SMOKE, x, y, z, 0, 0, 0);
            }
        }
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return super.mayPlaceOn(state, level, pos) || state.is(Blocks.NETHERRACK) || state.is(Blocks.SOUL_SAND) || state.is(Blocks.SOUL_SOIL);
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!level.isClientSide && level.getDifficulty() != Difficulty.PEACEFUL) {
            if (entity instanceof LivingEntity livingentity) {
                if (!livingentity.isInvulnerableTo(level.damageSources().wither())) {
                    livingentity.addEffect(new MobEffectInstance(MobEffects.WITHER, 40));
                }
            }
        }
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (player.getItemInHand(hand).getItem() == ModItems.HEX_FOCUS.get()) {
            if (!level.isClientSide) {
                spawnActivationEffects(level, pos);
                transformSkulls((ServerLevel) level, pos);
                transformSkeletons((ServerLevel) level, pos);
            }
            return ItemInteractionResult.SUCCESS;
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    private void spawnActivationEffects(Level level, BlockPos pos) {
        spawnParticles((ServerLevel) level, pos);
        playDecayingSounds(level, pos);
    }

    private void spawnParticles(ServerLevel level, BlockPos pos) {
        AABB area = new AABB(pos).inflate(2.5);
        for (int i = 0; i < 50; i++) {
            double x = area.minX + level.random.nextDouble() * (area.maxX - area.minX);
            double y = area.minY + level.random.nextDouble() * (area.maxY - area.minY);
            double z = area.minZ + level.random.nextDouble() * (area.maxZ - area.minZ);
            level.sendParticles(ParticleTypes.SMOKE, x, y, z, 2, 0, 0, 0, 0);
        }
    }

    private void playDecayingSounds(Level level, BlockPos pos) {
        level.playSound(null, pos, SoundEvents.WITHER_SKELETON_AMBIENT, SoundSource.BLOCKS, 1.0f, 1.0f);
        level.playSound(null, pos, SoundEvents.GHAST_AMBIENT, SoundSource.BLOCKS, 1.0f, 1.0f);
    }

    private void transformSkeletons(ServerLevel pLevel, BlockPos pPos) {
        AABB area = new AABB(pPos).inflate(2.5);
        boolean skeletonTransformed = false;
        for (Entity entity : pLevel.getEntities(null, area)) {
            if (entity instanceof Skeleton skeleton) {
                WitherSkeleton witherSkeleton = EntityType.WITHER_SKELETON.create(pLevel);
                if (witherSkeleton != null) {
                    witherSkeleton.moveTo(skeleton.getX(), skeleton.getY(), skeleton.getZ(), skeleton.getYRot(), skeleton.getXRot());
                    skeleton.discard();
                    pLevel.addFreshEntity(witherSkeleton);
                    skeletonTransformed = true;
                }
            }
        }
        if (skeletonTransformed) {
            RandomSource random = pLevel.getRandom();
            double chance = random.nextDouble();
            if (chance < 0.75) {
                spawnWitherRose(pLevel, pPos, random);
                if (chance < 0.50) {
                    spawnWitherRose(pLevel, pPos, random);
                }
            }
            pLevel.destroyBlock(pPos, false);
        }
    }

    private void transformSkulls(ServerLevel pLevel, BlockPos pPos) {
        AABB area = new AABB(pPos).inflate(2.5);
        int skullsTransformed = 0;

        for (Entity entity : pLevel.getEntities(null, area)) {
            if (entity instanceof net.minecraft.world.entity.item.ItemEntity itemEntity) {
                if (itemEntity.getItem().getItem() == Items.SKELETON_SKULL && skullsTransformed < 3) {
                    itemEntity.setItem(new net.minecraft.world.item.ItemStack(Items.WITHER_SKELETON_SKULL, itemEntity.getItem().getCount()));
                    skullsTransformed++;
                }
            }
        }

        for (BlockPos blockPos : BlockPos.betweenClosed(
                (int) area.minX, (int) area.minY, (int) area.minZ,
                (int) area.maxX, (int) area.maxY, (int) area.maxZ)) {
            BlockState blockState = pLevel.getBlockState(blockPos);
            if (blockState.is(Blocks.SKELETON_SKULL) && skullsTransformed < 3) {
                pLevel.setBlock(blockPos, Blocks.WITHER_SKELETON_SKULL.defaultBlockState(), 3);
                skullsTransformed++;
            } else if (blockState.is(Blocks.SKELETON_WALL_SKULL) && skullsTransformed < 3) {
                pLevel.setBlock(blockPos, Blocks.WITHER_SKELETON_WALL_SKULL.defaultBlockState()
                        .setValue(SkullBlock.ROTATION, blockState.getValue(SkullBlock.ROTATION)), 3);
                skullsTransformed++;
            } else if (blockState.is(ModBlocks.CANDLE_SKULL) && skullsTransformed < 3) {
                pLevel.setBlock(blockPos, ModBlocks.WITHER_CANDLE_SKULL.get().defaultBlockState(), 3);
                skullsTransformed++;
            }
        }

        if (skullsTransformed > 0) {
            pLevel.destroyBlock(pPos, false);
        }
    }

    private void spawnWitherRose(ServerLevel pLevel, BlockPos pPos, RandomSource random) {
        int x = pPos.getX() + random.nextInt(5) - 2;
        int z = pPos.getZ() + random.nextInt(5) - 2;
        BlockPos rosePos = new BlockPos(x, pPos.getY(), z);
        if (pLevel.isEmptyBlock(rosePos) && pLevel.getBlockState(rosePos.below()).isSolidRender(pLevel, rosePos.below())) {
            pLevel.setBlock(rosePos, Blocks.WITHER_ROSE.defaultBlockState(), 3);
        }
    }
}
