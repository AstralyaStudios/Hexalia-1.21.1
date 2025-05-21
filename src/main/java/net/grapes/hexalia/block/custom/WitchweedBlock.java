package net.grapes.hexalia.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WitchweedBlock extends FlowerBlock {

    protected static final VoxelShape SHAPE = Shapes.or(Block.box(2, 0, 1, 15, 7, 15));

    public WitchweedBlock(Holder<MobEffect> effect, float seconds, Properties properties) {
        super(effect, seconds, properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!level.isClientSide && level.getDifficulty() != net.minecraft.world.Difficulty.PEACEFUL) {
            if (entity instanceof LivingEntity livingEntity) {
                if (!livingEntity.isSteppingCarefully() && !(livingEntity instanceof Frog)
                        && !(livingEntity instanceof Bee)) {
                    if (livingEntity instanceof Player player && player.isCreative()) {
                        return;
                    }
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 100));
                }
            }
        }
    }
}
