package net.astralya.hexalia.block.custom;

import net.astralya.hexalia.block.ModBlocks;
import net.astralya.hexalia.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SkullBlock;
import net.minecraft.block.WallSkullBlock;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

public class GrimshadeBlock extends EnchantedPlantBlock {

    public GrimshadeBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        for (int i = 0; i < 3; i++) {
            double x = pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 0.5;
            double y = pos.getY() + 0.5 + (random.nextDouble() - 0.5) * 0.5;
            double z = pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 0.5;
            world.addParticle(ParticleTypes.SMOKE, x, y, z, 0, 0, 0);
        }
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, net.minecraft.world.BlockView world, BlockPos pos) {
        return super.canPlantOnTop(floor, world, pos)
                || floor.isOf(Blocks.NETHERRACK)
                || floor.isOf(Blocks.SOUL_SAND)
                || floor.isOf(Blocks.SOUL_SOIL);
    }

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!world.isClient && world.getDifficulty() != Difficulty.PEACEFUL) {
            if (entity instanceof LivingEntity living) {
                if (!living.isInvulnerableTo(world.getDamageSources().wither())) {
                    living.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 40));
                }
            }
        }
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos,
                                             PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.getStackInHand(hand).isOf(ModItems.HEX_FOCUS)) {
            if (!world.isClient) {
                spawnActivationEffects(world, pos);
                transformSkulls((ServerWorld) world, pos);
                transformSkeletons((ServerWorld) world, pos);
            }
            return ItemActionResult.SUCCESS;
        }
        return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    private void spawnActivationEffects(World world, BlockPos pos) {
        spawnParticles((ServerWorld) world, pos);
        playDecayingSounds(world, pos);
    }

    private void spawnParticles(ServerWorld world, BlockPos pos) {
        Box area = new Box(pos).expand(2.5);
        Random r = world.getRandom();

        for (int i = 0; i < 50; i++) {
            double x = area.minX + r.nextDouble() * (area.maxX - area.minX);
            double y = area.minY + r.nextDouble() * (area.maxY - area.minY);
            double z = area.minZ + r.nextDouble() * (area.maxZ - area.minZ);
            world.spawnParticles(ParticleTypes.SMOKE, x, y, z, 2, 0, 0, 0, 0);
        }
    }

    private void playDecayingSounds(World world, BlockPos pos) {
        world.playSound(null, pos, SoundEvents.ENTITY_WITHER_SKELETON_AMBIENT, SoundCategory.BLOCKS, 1.0f, 1.0f);
        world.playSound(null, pos, SoundEvents.ENTITY_GHAST_AMBIENT, SoundCategory.BLOCKS, 1.0f, 1.0f);
    }

    private void transformSkeletons(ServerWorld world, BlockPos pos) {
        Box area = new Box(pos).expand(2.5);
        boolean anyTransformed = false;

        for (SkeletonEntity sk : world.getEntitiesByClass(SkeletonEntity.class, area, e -> true)) {
            WitherSkeletonEntity wither = EntityType.WITHER_SKELETON.create(world);
            if (wither != null) {
                wither.refreshPositionAndAngles(sk.getX(), sk.getY(), sk.getZ(), sk.getYaw(), sk.getPitch());
                sk.discard();
                world.spawnEntity(wither);
                anyTransformed = true;
            }
        }

        if (anyTransformed) {
            Random random = world.getRandom();
            double chance = random.nextDouble();
            if (chance < 0.75) {
                spawnWitherRose(world, pos, random);
                if (chance < 0.50) {
                    spawnWitherRose(world, pos, random);
                }
            }
            world.breakBlock(pos, false);
        }
    }

    private void transformSkulls(ServerWorld world, BlockPos pos) {
        Box area = new Box(pos).expand(2.5);
        int transformed = 0;

        for (ItemEntity item : world.getEntitiesByClass(ItemEntity.class, area, e -> true)) {
            if (transformed >= 3) break;
            ItemStack stack = item.getStack();
            if (stack.isOf(Items.SKELETON_SKULL)) {
                item.setStack(new ItemStack(Items.WITHER_SKELETON_SKULL, stack.getCount()));
                transformed++;
            }
        }

        if (transformed < 3) {
            BlockPos min = new BlockPos((int) Math.floor(area.minX), (int) Math.floor(area.minY), (int) Math.floor(area.minZ));
            BlockPos max = new BlockPos((int) Math.floor(area.maxX), (int) Math.floor(area.maxY), (int) Math.floor(area.maxZ));

            for (BlockPos bp : BlockPos.iterate(min, max)) {
                if (transformed >= 3) break;

                BlockState state = world.getBlockState(bp);

                if (state.isOf(Blocks.SKELETON_SKULL)) {
                    int rot = state.get(SkullBlock.ROTATION);
                    world.setBlockState(bp, Blocks.WITHER_SKELETON_SKULL.getDefaultState().with(SkullBlock.ROTATION, rot), Block.NOTIFY_ALL);
                    transformed++;
                    continue;
                }

                if (state.isOf(Blocks.SKELETON_WALL_SKULL)) {
                    Direction facing = state.get(WallSkullBlock.FACING);
                    world.setBlockState(bp, Blocks.WITHER_SKELETON_WALL_SKULL.getDefaultState().with(WallSkullBlock.FACING, facing), Block.NOTIFY_ALL);
                    transformed++;
                    continue;
                }

                if (state.isOf(ModBlocks.CANDLE_SKULL)) {
                    world.setBlockState(bp, ModBlocks.WITHER_CANDLE_SKULL.getDefaultState(), Block.NOTIFY_ALL);
                    transformed++;
                }
            }
        }

        if (transformed > 0) {
            world.breakBlock(pos, false);
        }
    }

    private void spawnWitherRose(ServerWorld world, BlockPos pos, Random random) {
        int x = pos.getX() + random.nextInt(5) - 2;
        int z = pos.getZ() + random.nextInt(5) - 2;
        BlockPos rosePos = new BlockPos(x, pos.getY(), z);

        if (world.isAir(rosePos) && world.getBlockState(rosePos.down()).isOpaqueFullCube(world, rosePos.down())) {
            world.setBlockState(rosePos, Blocks.WITHER_ROSE.getDefaultState(), Block.NOTIFY_ALL);
        }
    }
}