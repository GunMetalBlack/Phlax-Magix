package phlaxmod.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import phlaxmod.PhlaxMod;
import phlaxmod.container.CrystalizerContainer;

public class CrystallizerScreen extends ContainerScreen<CrystalizerContainer> {
    private final ResourceLocation GUI = new ResourceLocation(PhlaxMod.MODID,
            "textures/gui/crystallizer_gui.png");
    public CrystallizerScreen(CrystalizerContainer pMenu, PlayerInventory pPlayerInventory, ITextComponent pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    public void render(MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
        this.renderBackground(pMatrixStack);
        super.render(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
        this.renderTooltip(pMatrixStack,pMouseX,pMouseY);
    }

    @Override
    protected void renderBg(MatrixStack pMatrixStack, float pPartialTicks, int pX, int pY) {
        RenderSystem.color4f(1f,1f,1f,1f);
        this.minecraft.getTextureManager().bind(GUI);
        int i = this.leftPos;
        int j = this.topPos;
        this.blit(pMatrixStack, i, j,0,0, this.getXSize(),this.getYSize());
        //ToDo Render Purple Power GUI Time 30:58
    }
}
