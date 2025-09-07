package net.astralya.hexalia.block.custom;

import net.astralya.hexalia.entity.ModEntities;
import net.astralya.hexalia.entity.custom.SilkMothEntity;
import net.astralya.hexalia.entity.custom.variant.SilkMothVariant;
import net.astralya.hexalia.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class CocoonBlock extends Block {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final IntegerProperty HATCH = IntegerProperty.create("hatch", 0, 2);

    private static final VoxelShape NORTH_SHAPE = Shapes.create(new AABB(5.0 / 16.0, 5.0 / 16.0, 11.0 / 16.0, 11.0 / 16.0, 12.0 / 16.0, 1.0));
    private static final VoxelShape SOUTH_SHAPE = Shapes.create(new AABB(5.0 / 16.0, 5.0 / 16.0, 0.0, 11.0 / 16.0, 12.0 / 16.0, 5.0 / 16.0));
    private static final VoxelShape WEST_SHAPE = Shapes.create(new AABB(11.0 / 16.0, 5.0 / 16.0, 5.0 / 16.0, 1.0, 12.0 / 16.0, 11.0 / 16.0));
    private static final VoxelShape EAST_SHAPE = Shapes.create(new AABB(0.0, 5.0 / 16.0, 5.0 / 16.0, 5.0 / 16.0, 12.0 / 16.0, 11.0 / 16.0));

    public CocoonBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(HATCH, 0));
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide()) {
            return ItemInteractionResult.SUCCESS;
        }

        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.getItem() instanceof BlockItem blockItem) {
            Block block = blockItem.getBlock();
            if (block.defaultBlockState().getLightEmission() > 8) {
                level.scheduleTick(pos, this, 200);
                return ItemInteractionResult.SUCCESS;
            }
        }
        return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        level.removeBlock(pos, false);
        SilkMothEntity silkMoth = ModEntities.SILK_MOTH_ENTITY.get().create(level);
        if (silkMoth != null) {
            silkMoth.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0.0F, 0.0F);

            SilkMothVariant variant = SilkMothVariant.byId(random.nextInt(SilkMothVariant.values().length));
            silkMoth.setVariant(variant);

            level.addFreshEntity(silkMoth);
        }
        level.gameEvent(null, GameEvent.BLOCK_DESTROY, pos);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        return switch (direction) {
            case SOUTH -> SOUTH_SHAPE;
            case WEST -> WEST_SHAPE;
            case EAST -> EAST_SHAPE;
            default -> NORTH_SHAPE;
        };
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState state1, LevelAccessor levelAccessor, BlockPos pos, BlockPos state2) {
        if (direction.getOpposite() == state.getValue(FACING) && !state.canSurvive(levelAccessor, pos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, direction, state1, levelAccessor, pos, state2);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction facing = state.getValue(FACING);
        BlockPos oppositePos = pos.relative(facing.getOpposite());
        BlockState oppositeState = level.getBlockState(oppositePos);
        return oppositeState.is(ModTags.Blocks.COCOON_LOGS);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor level, BlockPos pos, Rotation direction) {
        return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, HATCH);
    }
}
