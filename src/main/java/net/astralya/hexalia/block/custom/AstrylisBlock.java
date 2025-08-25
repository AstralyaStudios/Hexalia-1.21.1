package net.astralya.hexalia.block.custom;

import net.astralya.hexalia.block.entity.custom.AstrylisBlockEntity;
import net.astralya.hexalia.block.entity.ModBlockEntityTypes;
import net.astralya.hexalia.item.ModItems;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class AstrylisBlock  extends EnchantedPlantBlock implements BlockEntityProvider {

    public AstrylisBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (world.isClient) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof AstrylisBlockEntity astrylisBlockEntity && astrylisBlockEntity.isActive()) {
                for (int i = 0; i < 6; i++) {
                    double x = pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 0.3;
                    double y = pos.getY() + 0.7 + random.nextDouble() * 0.3;
                    double z = pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 0.3;
                    world.addParticle(ParticleTypes.EFFECT, x, y, z, 0, 0.01, 0);
                }
            }
        }
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        BlockEntity be = world.getBlockEntity(pos);
        if (!(be instanceof AstrylisBlockEntity astrylis)) {
            return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (itemStack.isEmpty()) {
            if (!astrylis.isActive()) {
                if (!world.isClient) {
                    player.sendMessage(Text.translatable("message.hexalia.astrylis.inactive"), true);
                }
            }
            return ItemActionResult.SUCCESS;
        }

        if (itemStack.getItem() == ModItems.CELESTIAL_CRYSTAL) {
            if (!astrylis.isActive()) {
                if (!world.isClient) {
                    astrylis.activate(world.getTime());
                    world.playSound(null, pos, SoundEvents.BLOCK_AMETHYST_BLOCK_HIT, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    if (!player.isCreative()) {
                        itemStack.decrement(1);
                    }
                    player.sendMessage(Text.translatable("message.hexalia.astrylis.activation"), true);
                }
                return ItemActionResult.SUCCESS;
            }
        }

        return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AstrylisBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (world.isClient) {
            return null;
        }
        return type == ModBlockEntityTypes.ASTRYLIS ? (world1, pos, state1, blockEntity) -> {
            if (blockEntity instanceof AstrylisBlockEntity astrylisBlockEntity) {
                AstrylisBlockEntity.tick(world1, pos, state1, astrylisBlockEntity);
            }
        } : null;
    }
}
