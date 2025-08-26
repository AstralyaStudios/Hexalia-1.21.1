package net.astralya.hexalia.block.custom;

import net.astralya.hexalia.entity.custom.SilkMothEntity;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.FrogEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

public class WitchweedBlock extends FlowerBlock {

    protected static final VoxelShape SHAPE = Block.createCuboidShape(2, 0, 1, 15, 7, 15);

    public WitchweedBlock(RegistryEntry<StatusEffect> stewEffect, float effectLengthInSeconds, Settings settings) {
        super(stewEffect, effectLengthInSeconds, settings);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!world.isClient && (world.getDifficulty() != Difficulty.PEACEFUL)) {
            if (entity instanceof LivingEntity livingEntity) {
                if (!livingEntity.bypassesSteppingEffects() && !(livingEntity instanceof FrogEntity)
                        && !(livingEntity instanceof SilkMothEntity) && !(livingEntity instanceof BeeEntity)) {
                    if (livingEntity instanceof PlayerEntity player && player.isCreative()) {
                        return;
                    }
                    livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100));
                }
            }
        }
    }
}
