package net.grapes.hexalia.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class DreamcatcherBlock extends WallMountedBlock{

    private static final int CHECK_RADIUS = 16;

    public DreamcatcherBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        AABB checkArea = new AABB(
                Vec3.atCenterOf(pos.offset(-CHECK_RADIUS, -CHECK_RADIUS, -CHECK_RADIUS)),
                Vec3.atCenterOf(pos.offset(CHECK_RADIUS, CHECK_RADIUS, CHECK_RADIUS))
        );

        List<Phantom> phantoms = level.getEntitiesOfClass(Phantom.class, checkArea);

        for (Phantom phantom : phantoms) {
            phantom.igniteForSeconds(5);
        }

        level.scheduleTick(pos, this, 20);
    }


    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);

        if (!level.isClientSide) {
            level.scheduleTick(pos, this, 20);
        }
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        super.onRemove(state, level, pos, newState, movedByPiston);
    }
}
