package net.astralya.hexalia.block.entity.custom;

import net.astralya.hexalia.block.entity.ModBlockEntityTypes;
import net.astralya.hexalia.util.ModUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ShelfBlockEntity extends SyncBlockEntity {

    private static final int SIZE = 6;
    private final NonNullList<ItemStack> items = NonNullList.withSize(SIZE, ItemStack.EMPTY);

    public ShelfBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntityTypes.SHELF.get(), pos, blockState);
    }

    public ItemStack getItem(int slot) {
        if (slot < 0 || slot >= SIZE) return ItemStack.EMPTY;
        return items.get(slot);
    }

    public void setItem(int slot, ItemStack stack) {
        if (slot < 0 || slot >= SIZE) return;
        items.set(slot, stack);
        setChanged();
        sendUpdate();
    }

    private void sendUpdate() {
        if (level != null && !level.isClientSide()) {
            Packet<ClientGamePacketListener> updatePacket = getUpdatePacket();
            for (ServerPlayer player : ModUtil.tracking((ServerLevel) level, worldPosition)) {
                player.connection.send(updatePacket);
            }
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public NonNullList<ItemStack> getItems() {
        return items;
    }

    public boolean isEmpty() {
        return items.stream().allMatch(ItemStack::isEmpty);
    }

    public ItemStack removeStack(int slot) {
        if (slot < 0 || slot >= SIZE) return ItemStack.EMPTY;
        ItemStack stack = items.get(slot).copy();
        items.set(slot, ItemStack.EMPTY);
        setChanged();
        sendUpdate();
        return stack;
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        ContainerHelper.loadAllItems(tag, items, registries);
        for (ItemStack stack : items) {
            if (stack.isEmpty()) continue;
            if (stack.getCount() <= 0 || stack.getCount() > stack.getMaxStackSize()) {
                stack.setCount(1);
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, items, registries);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
