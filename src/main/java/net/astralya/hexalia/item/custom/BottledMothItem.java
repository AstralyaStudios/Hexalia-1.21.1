package net.astralya.hexalia.item.custom;

import net.astralya.hexalia.component.ModComponents;
import net.astralya.hexalia.component.item.MothData;
import net.astralya.hexalia.entity.ModEntities;
import net.astralya.hexalia.entity.custom.SilkMothEntity;
import net.astralya.hexalia.entity.custom.variant.SilkMothVariant;
import net.astralya.hexalia.item.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class BottledMothItem extends Item {
    public BottledMothItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext ctx) {
        World world = ctx.getWorld();
        PlayerEntity player = ctx.getPlayer();
        if (player == null) return ActionResult.PASS;

        ItemStack stack = ctx.getStack();
        BlockPos pos = ctx.getBlockPos().offset(ctx.getSide());

        if (!world.isClient()) {
            SilkMothEntity moth = new SilkMothEntity(ModEntities.SILK_MOTH_ENTITY, world);

            MothData data = stack.get(ModComponents.MOTH);
            if (data != null) {
                moth.setVariant(SilkMothVariant.byId(data.variantId()));
                if (!data.name().isEmpty()) {
                    moth.setCustomName(Text.literal(data.name()));
                }
            }

            moth.refreshPositionAndAngles(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0, 0);
            world.spawnEntity(moth);

            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 1.0F, 1.0F);

            stack.decrement(1);
            ItemStack empty = new ItemStack(ModItems.RUSTIC_BOTTLE);
            if (!player.getInventory().insertStack(empty)) {
                player.dropStack(empty);
            }
        }

        return ActionResult.success(world.isClient());
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        MothData data = stack.get(ModComponents.MOTH);
        if (data != null && !data.name().isEmpty()) {
            tooltip.add(Text.translatable("tooltip.hexalia.bottled_moth", data.name())
                    .formatted(Formatting.ITALIC, Formatting.GREEN));
        }
        tooltip.add(Text.literal("Variant: " + (data == null ? -1 : data.variantId())));
    }
}
