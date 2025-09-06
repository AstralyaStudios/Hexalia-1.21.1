package net.astralya.hexalia.block.entity.custom;

import net.astralya.hexalia.block.custom.CenserBlock;
import net.astralya.hexalia.block.custom.censer.CenserEffectHandler;
import net.astralya.hexalia.block.custom.censer.HerbCombination;
import net.astralya.hexalia.block.entity.ModBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CenserBlockEntity extends SyncBlockEntity {

    private static final int SIZE = 2;
    private static final int EFFECT_INTERVAL = 40;

    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(SIZE, ItemStack.EMPTY);

    private HerbCombination activeCombination = null;
    private int burnTime = 0;
    private boolean effectActive = false;

    public CenserBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.CENSER, pos, state);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (!world.isClient && activeCombination != null && burnTime > 0 && !effectActive) {
            CenserEffectHandler.registerActiveEffect(world, pos, activeCombination, burnTime);
            effectActive = true;
        }

        if (!state.get(CenserBlock.LIT)) return;

        if (burnTime > 0) {
            burnTime--;

            if (burnTime % EFFECT_INTERVAL == 0 && activeCombination != null) {
                CenserEffectHandler.applyEffects(world, pos, activeCombination);
                if (!effectActive) {
                    CenserEffectHandler.registerActiveEffect(world, pos, activeCombination, burnTime);
                    effectActive = true;
                }
            }

            if (burnTime <= 0) {
                extinguish(world, pos, state);
            }

            markDirty();
        }
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup lookup) {
        super.readNbt(nbt, lookup);
        Inventories.readNbt(nbt, items, lookup);

        for (ItemStack stack : items) {
            if (stack.isEmpty()) continue;
            if (stack.getCount() <= 0 || stack.getCount() > stack.getMaxCount()) {
                stack.setCount(1);
            }
        }

        if (nbt.contains("ActiveCombination", NbtElement.COMPOUND_TYPE)) {
            NbtCompound comboTag = nbt.getCompound("ActiveCombination");
            if (comboTag.contains("Item1", NbtElement.STRING_TYPE) && comboTag.contains("Item2", NbtElement.STRING_TYPE)) {
                Identifier id1 = Identifier.of(comboTag.getString("Item1"));
                Identifier id2 = Identifier.of(comboTag.getString("Item2"));
                Item item1 = Registries.ITEM.get(id1);
                Item item2 = Registries.ITEM.get(id2);
                activeCombination = new HerbCombination(item1, item2);
            }
        }

        burnTime = nbt.getInt("BurnTime");
        effectActive = nbt.getBoolean("EffectActive");
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup lookup) {
        super.writeNbt(nbt, lookup);
        Inventories.writeNbt(nbt, items, lookup);
        nbt.putInt("BurnTime", burnTime);
        nbt.putBoolean("EffectActive", effectActive);

        if (activeCombination != null) {
            NbtCompound comboTag = new NbtCompound();
            comboTag.putString("Item1", Registries.ITEM.getId(activeCombination.item1()).toString());
            comboTag.putString("Item2", Registries.ITEM.getId(activeCombination.item2()).toString());
            nbt.put("ActiveCombination", comboTag);
        }
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public void inventoryChanged() {
        if (world == null) return;
        markDirty();
        if (!world.isClient) {
            world.updateListeners(pos, getCachedState(), getCachedState(), 3);
            ((ServerWorld) world).getChunkManager().markForUpdate(pos);
        }
    }

    public int getBurnTime() {
        return burnTime;
    }

    public void setBurnTime(int time) {
        burnTime = time;
        inventoryChanged();
    }

    public ItemStack getItem(int slot) {
        if (slot < 0 || slot >= SIZE) return ItemStack.EMPTY;
        return items.get(slot);
    }

    public void setItem(int slot, ItemStack stack) {
        if (slot < 0 || slot >= SIZE) return;
        items.set(slot, stack);
        inventoryChanged();
    }

    public void clearItems() {
        for (int i = 0; i < SIZE; i++) items.set(i, ItemStack.EMPTY);
        inventoryChanged();
    }

    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    public HerbCombination getActiveCombination() {
        return activeCombination;
    }

    public void setActiveCombination(HerbCombination combo) {
        activeCombination = combo;
        inventoryChanged();
    }

    public void reactivateEffect() {
        if (activeCombination != null && burnTime > 0 && !effectActive && world != null) {
            CenserEffectHandler.registerActiveEffect(world, pos, activeCombination, burnTime);
            effectActive = true;
        }
    }

    public boolean isEmpty() {
        for (ItemStack item : items) {
            if (!item.isEmpty()) return false;
        }
        return true;
    }

    public ItemStack removeStack(int slot) {
        if (slot < 0 || slot >= SIZE) return ItemStack.EMPTY;
        ItemStack stack = items.get(slot).copy();
        items.set(slot, ItemStack.EMPTY);
        inventoryChanged();
        return stack;
    }

    private void extinguish(World world, BlockPos pos, BlockState state) {
        if (activeCombination != null) {
            CenserEffectHandler.clearPlayerEffectsInRange(world, pos);
            CenserEffectHandler.removeActiveEffect(pos);
            activeCombination = null;
            effectActive = false;
        }
        world.setBlockState(pos, state.with(CenserBlock.LIT, false), 3);
        world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5f, 1.0f);
        inventoryChanged();
    }
}