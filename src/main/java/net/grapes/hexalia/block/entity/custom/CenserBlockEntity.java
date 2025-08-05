package net.grapes.hexalia.block.entity.custom;

import net.grapes.hexalia.block.custom.CenserBlock;
import net.grapes.hexalia.block.custom.censer.CenserEffectHandler;
import net.grapes.hexalia.block.custom.censer.HerbCombination;
import net.grapes.hexalia.block.entity.ModBlockEntityTypes;
import net.grapes.hexalia.util.ModUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CenserBlockEntity extends BlockEntity {

    private static final int SIZE = 2;
    private final NonNullList<ItemStack> items = NonNullList.withSize(SIZE, ItemStack.EMPTY);
    private HerbCombination activeCombination = null;
    private int burnTime = 0;
    private static final int EFFECT_INTERVAL = 40;
    private boolean effectActive = false;

    public CenserBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.CENSER.get(), pos, state);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (!state.getValue(CenserBlock.LIT)) return;

        if (burnTime > 0) {
            burnTime--;

            if (burnTime % EFFECT_INTERVAL == 0 && activeCombination != null) {
                CenserEffectHandler.applyEffects(level, pos, activeCombination);

                if (!effectActive) {
                    CenserEffectHandler.registerActiveEffect(level, pos, activeCombination, burnTime);
                    effectActive = true;
                }
            }

            if (burnTime <= 0) {
                extinguish(level, pos, state);
            }

            setChanged();
        }
    }

    private void extinguish(Level level, BlockPos pos, BlockState state) {
        if (activeCombination != null) {
            CenserEffectHandler.clearPlayerEffectsInRange(level, pos);
            CenserEffectHandler.removeActiveEffect(pos);
            activeCombination = null;
            effectActive = false;
        }

        level.setBlockAndUpdate(pos, state.setValue(CenserBlock.LIT, false));
        level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5f, 1.0f);
        setChanged();
        sendUpdate();
    }

    public void reactivateEffect() {
        if (activeCombination != null && burnTime > 0 && !effectActive && level != null) {
            CenserEffectHandler.registerActiveEffect(level, worldPosition, activeCombination, burnTime);
            effectActive = true;
        }
    }

    public void setBurnTime(int time) {
        this.burnTime = time;
        setChanged();
    }

    public int getBurnTime() {
        return burnTime;
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

    public void clearItems() {
        for (int i = 0; i < SIZE; i++) {
            items.set(i, ItemStack.EMPTY);
        }
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

    public void setActiveCombination(HerbCombination combo) {
        this.activeCombination = combo;
        setChanged();
    }

    public HerbCombination getActiveCombination() {
        return this.activeCombination;
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        ContainerHelper.loadAllItems(tag, items, registries);

        for (int i = 0; i < items.size(); i++) {
            ItemStack stack = items.get(i);
            if (stack.isEmpty()) continue;
            if (stack.getCount() <= 0 || stack.getCount() > stack.getMaxStackSize()) {
                stack.setCount(1);
            }
        }

        if (tag.contains("ActiveCombination")) {
            CompoundTag comboTag = tag.getCompound("ActiveCombination");
            Item item1 = BuiltInRegistries.ITEM.byId(comboTag.getInt("Item1"));
            Item item2 = BuiltInRegistries.ITEM.byId(comboTag.getInt("Item2"));
            this.activeCombination = new HerbCombination(item1, item2);
        }

        burnTime = tag.getInt("BurnTime");
        effectActive = tag.getBoolean("EffectActive");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, items, registries);
        tag.putInt("BurnTime", burnTime);
        tag.putBoolean("EffectActive", effectActive);

        if (activeCombination != null) {
            CompoundTag comboTag = new CompoundTag();
            comboTag.putInt("Item1", BuiltInRegistries.ITEM.getId(activeCombination.item1()));
            comboTag.putInt("Item2", BuiltInRegistries.ITEM.getId(activeCombination.item2()));
            tag.put("ActiveCombination", comboTag);
        }
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (level != null && !level.isClientSide && activeCombination != null && burnTime > 0) {
            reactivateEffect();
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
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
}
