package net.astralya.hexalia.screen.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import net.astralya.hexalia.HexaliaMod;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SmallCauldronScreen extends HandledScreen<net.astralya.hexalia.screen.custom.SmallCauldronScreenHandler> {
    private static final Identifier GUI_TEXTURE =
            Identifier.of(HexaliaMod.MODID, "textures/gui/small_cauldron_gui.png");

    public SmallCauldronScreen(net.astralya.hexalia.screen.custom.SmallCauldronScreenHandler handler, PlayerInventory inv, Text title) {
        super(handler, inv, title);
    }

    @Override
    protected void init() {
        super.init();
        titleY = 1000;
        playerInventoryTitleY = 1000;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(GUI_TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);

        if (handler.isCrafting()) {
            int h = handler.getScaledProgress();
            context.drawTexture(GUI_TEXTURE, x + 89, y + 25, 176, 15, h + 1, 17);
        }
        if (handler.isHeated()) {
            context.drawTexture(GUI_TEXTURE, x + 124, y + 52, 176, 0, 17, 15);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
