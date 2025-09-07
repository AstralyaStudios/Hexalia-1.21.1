package net.astralya.hexalia.block.entity.custom;

import net.astralya.hexalia.HexaliaMod;
import net.astralya.hexalia.block.ModBlocks;
import net.astralya.hexalia.block.custom.RitualBrazierBlock;
import net.astralya.hexalia.block.entity.ModBlockEntityTypes;
import net.astralya.hexalia.recipe.ModRecipes;
import net.astralya.hexalia.recipe.RitualBrazierRecipe;
import net.astralya.hexalia.recipe.RitualBrazierRecipeInput;
import net.astralya.hexalia.util.ModUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.Optional;

@EventBusSubscriber(modid = HexaliaMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class RitualBrazierBlockEntity extends SyncBlockEntity {

    public enum RitualResult {
        SUCCESS,
        NO_CELESTIAL_BLOOMS,
        INVALID_ITEM
    }

    private final ItemStackHandler inventory;
    private final RecipeManager.CachedCheck<RitualBrazierRecipeInput, RitualBrazierRecipe> quickCheck;
    private boolean isRitualFocusItem;
    private float rotation;

    public RitualBrazierBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.RITUAL_BRAZIER.get(), pos, state);
        this.inventory = createHandler();
        isRitualFocusItem = false;
        this.quickCheck = RecipeManager.createCheck(ModRecipes.RITUAL_BRAZIER_TYPE.get());
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                inventoryChanged();
            }

            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }
        };
    }

    public RitualResult tryCelestialRitual() {
        if (level == null || isRitualFocusItem) return RitualResult.INVALID_ITEM;

        if (!hasEnoughNearbyCelestial_Blooms()) {
            return RitualResult.NO_CELESTIAL_BLOOMS;
        }

        Optional<RecipeHolder<RitualBrazierRecipe>> matchingRecipe = level.getRecipeManager()
                .getRecipeFor(ModRecipes.RITUAL_BRAZIER_TYPE.get(), new RitualBrazierRecipeInput(getStoredItem()), level);

        if (matchingRecipe.isEmpty()) {
            return RitualResult.INVALID_ITEM;
        }

        ItemStack resultStack = matchingRecipe.get().value().getResultItem(level.registryAccess());

        Direction direction = getBlockState().getValue(RitualBrazierBlock.FACING).getCounterClockWise();
        ModUtil.spawnItemEntity(level, resultStack.copy(),
                worldPosition.getX() + 0.5 + (direction.getStepX() * 0.2),
                worldPosition.getY() + 0.2,
                worldPosition.getZ() + 0.5 + (direction.getStepZ() * 0.2),
                direction.getStepX() * 0.2F, 0.0F, direction.getStepZ() * 0.2F);

        removeItem();
        return RitualResult.SUCCESS;
    }


    public boolean hasEnoughNearbyCelestial_Blooms() {
        if (level == null) return false;

        int count = 0;
        BlockPos origin = getBlockPos();

        for (int dx = -3; dx <= 3; dx++) {
            for (int dz = -3; dz <= 3; dz++) {
                if (dx == 0 && dz == 0) continue;
                BlockPos checkPos = origin.offset(dx, 0, dz);
                if (level.getBlockState(checkPos).is(ModBlocks.CELESTIAL_BLOOM.get())) {
                    count++;
                    if (count >= 2) return true;
                }
            }
        }
        return false;
    }


    public void playSound(SoundEvent sound, float volume, float pitch) {
        if (level != null)
            level.playSound(null, worldPosition.getX() + 0.5F, worldPosition.getY() + 0.5F, worldPosition.getZ() + 0.5F, sound, SoundSource.BLOCKS, volume, pitch);
    }

    public boolean addItem(ItemStack itemStack) {
        if (isEmpty() && !itemStack.isEmpty()) {
            inventory.setStackInSlot(0, itemStack.split(1));
            isRitualFocusItem = false;
            inventoryChanged();
            return true;
        }
        return false;
    }

    public ItemStack removeItem() {
        if (!isEmpty()) {
            isRitualFocusItem = false;
            ItemStack item = getStoredItem().split(1);
            inventoryChanged();
            return item;
        }
        return ItemStack.EMPTY;
    }

    public IItemHandler getInventory() {
        return inventory;
    }

    public ItemStack getStoredItem() {
        return inventory.getStackInSlot(0);
    }

    public boolean isEmpty() {
        return inventory.getStackInSlot(0).isEmpty();
    }

    public float getRenderingRotation() {
        rotation += 0.5f;
        if (rotation >= 360) {
            rotation = 0;
        }
        return rotation;
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("IsItemImbued", this.isRitualFocusItem);
        tag.put("Inventory", this.inventory.serializeNBT(provider));
        return tag;
    }

    @Override
    public @org.jetbrains.annotations.Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        isRitualFocusItem = tag.getBoolean("IsItemImbued");
        inventory.deserializeNBT(provider, tag.getCompound("Inventory"));
    }

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        tag.putBoolean("IsItemImbued", isRitualFocusItem);
        tag.put("Inventory", inventory.serializeNBT(provider));
    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                ModBlockEntityTypes.RITUAL_BRAZIER.get(),
                (be, ctx) -> be.getInventory()
        );
    }
}
