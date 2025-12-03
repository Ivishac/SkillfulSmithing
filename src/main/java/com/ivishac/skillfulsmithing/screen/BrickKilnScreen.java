package com.ivishac.skillfulsmithing.screen;

import com.ivishac.skillfulsmithing.SkillfulSmithing;
import com.ivishac.skillfulsmithing.block.custom.BrickKiln;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class BrickKilnScreen extends AbstractContainerScreen<BrickKilnMenu> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(SkillfulSmithing.MOD_ID, "textures/gui/brick_kiln.png");

    public BrickKilnScreen(BrickKilnMenu menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title);
        this.imageWidth = 256; // full width of your texture
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        // Draw the full background
        graphics.blit(TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight);

        // Draw animated flame if we're burning & have a crucible
        int flamePixels = menu.getBurnProgressScaled(13); // 0..13
        if (flamePixels > 0 && hasCrucibleOnKiln()) {
            // Source region in texture: 13x13 @ (176, 0)
            int srcX = 176;
            int srcY = 13 - flamePixels;   // start higher as it shrinks
            int width = 13;
            int height = flamePixels;

            // Destination: same relative place as the source, in the right bar.
            // These may need 1–2 px tweaking by eye.
            int destX = leftPos + 176;
            int destY = topPos + (13 - flamePixels);

            graphics.blit(TEXTURE, destX, destY, srcX, srcY, width, height);
        }
    }

    private boolean hasCrucibleOnKiln() {
        if (minecraft == null || minecraft.level == null) {
            return false;
        }
        Level level = minecraft.level;
        BlockPos pos = menu.getKilnPos();
        BlockState state = level.getBlockState(pos);

        return state.getBlock() instanceof BrickKiln
                && state.hasProperty(BrickKiln.HAS_CRUCIBLE)
                && state.getValue(BrickKiln.HAS_CRUCIBLE);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.drawString(this.font, this.title, 8, 6, 0x404040, false);
        graphics.drawString(this.font, this.playerInventoryTitle,
                8, this.imageHeight - 96 + 2, 0x404040, false);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);
    }
}