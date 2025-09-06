package net.astralya.hexalia.compat.rei.ritual_table;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.astralya.hexalia.HexaliaMod;
import net.astralya.hexalia.compat.rei.HexaliaREIClientPlugin;
import net.astralya.hexalia.item.ModItems;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.LinkedList;
import java.util.List;

public final class RitualTableCategory implements DisplayCategory<RitualTableDisplay> {

    public static final Identifier TEXTURE = Identifier.of(HexaliaMod.MODID, "textures/gui/ritual_table_gui.png");
    public static final CategoryIdentifier<RitualTableDisplay> RITUAL_TABLE =
            CategoryIdentifier.of(HexaliaMod.MODID, "ritual_table");

    @Override
    public CategoryIdentifier<? extends RitualTableDisplay> getCategoryIdentifier() {
        return RITUAL_TABLE;
    }

    @Override
    public Text getTitle() {
        return Text.translatable("block.hexalia.ritual_table");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModItems.RITUAL_TABLE.getDefaultStack());
    }

    @Override
    public List<Widget> setupDisplay(RitualTableDisplay display, Rectangle bounds) {
        Point origin = bounds.getLocation();
        List<Widget> widgets = new LinkedList<>();
        widgets.add(Widgets.createRecipeBase(bounds));

        final int guiWidth = 118;
        final int guiHeight = 80;
        final int fixedY = 39;
        final int fixedX = 28;

        Rectangle startPoint = HexaliaREIClientPlugin.centeredIntoRecipeBase(origin, guiWidth, guiHeight);

        widgets.add(Widgets.createTexturedWidget(TEXTURE, startPoint.x, startPoint.y, 0, -8, guiWidth, guiHeight));

        List<me.shedaniel.rei.api.common.entry.EntryIngredient> ingredientEntries = display.getInputEntries();
        if (ingredientEntries != null && !ingredientEntries.isEmpty()) {
            widgets.add(Widgets.createSlot(new Point(startPoint.x + fixedX, startPoint.y + fixedY))
                    .entries(ingredientEntries.get(0))
                    .markInput()
                    .disableBackground());

            if (ingredientEntries.size() >= 5) {
                widgets.add(Widgets.createSlot(new Point(startPoint.x + 4, startPoint.y + fixedY))
                        .entries(ingredientEntries.get(1))
                        .markInput()
                        .disableBackground());

                widgets.add(Widgets.createSlot(new Point(startPoint.x + 52, startPoint.y + fixedY))
                        .entries(ingredientEntries.get(2))
                        .markInput()
                        .disableBackground());

                widgets.add(Widgets.createSlot(new Point(startPoint.x + fixedX, startPoint.y + 15))
                        .entries(ingredientEntries.get(3))
                        .markInput()
                        .disableBackground());

                widgets.add(Widgets.createSlot(new Point(startPoint.x + fixedX, startPoint.y + 63))
                        .entries(ingredientEntries.get(4))
                        .markInput()
                        .disableBackground());

                widgets.add(Widgets.createTooltip(
                        new Rectangle(startPoint.x + 4, startPoint.y + 63, 16, 16),
                        Text.translatable("tooltip.hexalia.hex_focus_gui")));
            }
        }

        List<me.shedaniel.rei.api.common.entry.EntryIngredient> outputs = display.getOutputEntries();
        if (outputs != null && !outputs.isEmpty()) {
            widgets.add(Widgets.createSlot(new Point(startPoint.x + 89, startPoint.y + fixedY))
                    .entries(outputs.get(0))
                    .markOutput()
                    .disableBackground());
        }

        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return 90;
    }
}