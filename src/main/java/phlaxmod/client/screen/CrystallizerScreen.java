package phlaxmod.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import phlaxmod.PhlaxMod;
import phlaxmod.container.ContainerCrystalizer;
import phlaxmod.tileentity.TileMachine;

public class CrystallizerScreen extends ContainerScreen<ContainerCrystalizer> {
    private final ResourceLocation GUI = new ResourceLocation(PhlaxMod.MODID, "textures/gui/crystallizer_gui.png");
    private final int PROGRESS_BAR_MAX_HEIGHT = 17;
    public TileMachine tileEntity;
    public CrystallizerScreen(ContainerCrystalizer pMenu, PlayerInventory pPlayerInventory, ITextComponent pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.tileEntity = pMenu.getBlockEntity();
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
        int currentProgressBarHeight = (int)(PROGRESS_BAR_MAX_HEIGHT * this.tileEntity.guiBarLookUpTable.get("productProgress").get());
        // Draw Main Background
        this.blit(pMatrixStack, i, j,0,0, this.getXSize(),this.getYSize());
        // Draw Progress Bar
        this.blit(pMatrixStack, i+80, j+ 30 + PROGRESS_BAR_MAX_HEIGHT - currentProgressBarHeight , 176, 30 + PROGRESS_BAR_MAX_HEIGHT - currentProgressBarHeight, 13, currentProgressBarHeight);
    }
}
