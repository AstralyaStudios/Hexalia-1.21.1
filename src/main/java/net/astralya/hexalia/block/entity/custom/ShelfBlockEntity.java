package net.astralya.hexalia.block.entity.custom;

import net.astralya.hexalia.block.entity.ModBlockEntityTypes;
import net.astralya.hexalia.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ShelfBlockEntity extends SyncBlockEntity {

    private static final int SIZE = 6;
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(SIZE, ItemStack.EMPTY);

    public ShelfBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.SHELF, pos, state);
    }

    public ItemStack getItem(int slot) {
        if (slot < 0 || slot >= SIZE) return ItemStack.EMPTY;
        return items.get(slot);
    }

    public void setItem(int slot, ItemStack stack) {
        if (slot < 0 || slot >= SIZE) return;
        items.set(slot, stack.isEmpty() ? ItemStack.EMPTY : stack.copyWithCount(Math.min(Math.max(stack.getCount(), 1), stack.getMaxCount())));
        markDirty();
        sendUpdate();
    }

    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    public boolean isEmpty() {
        for (ItemStack s : items) if (!s.isEmpty()) return false;
        return true;
    }

    public ItemStack removeStack(int slot) {
        if (slot < 0 || slot >= SIZE) return ItemStack.EMPTY;
        ItemStack out = items.get(slot);
        items.set(slot, ItemStack.EMPTY);
        markDirty();
        sendUpdate();
        return out;
    }

    private void sendUpdate() {
        World w = this.world;
        if (w == null || w.isClient) return;
        ServerWorld sw = (ServerWorld) w;
        Packet<ClientPlayPacketListener> pkt = toUpdatePacket();
        if (pkt != null) {
            for (ServerPlayerEntity p : ModUtil.tracking(sw, pos)) {
                p.networkHandler.sendPacket(pkt);
            }
        }
        w.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
        sw.getChunkManager().markForUpdate(pos);
    }

    public void clearContents() {
        for (int i = 0; i < items.size(); i++) {
            items.set(i, ItemStack.EMPTY);
        }
        markDirty();
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        net.minecraft.inventory.Inventories.writeNbt(nbt, items, registryLookup);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        items.replaceAll(s -> ItemStack.EMPTY);
        net.minecraft.inventory.Inventories.readNbt(nbt, items, registryLookup);
        for (int i = 0; i < items.size(); i++) {
            ItemStack s = items.get(i);
            if (s.isEmpty()) continue;
            int clamped = Math.min(Math.max(s.getCount(), 1), s.getMaxCount());
            if (clamped != s.getCount()) items.set(i, s.copyWithCount(clamped));
        }
    }
}
