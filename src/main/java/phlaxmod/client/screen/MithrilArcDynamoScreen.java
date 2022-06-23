package phlaxmod.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import phlaxmod.PhlaxMod;
import phlaxmod.container.ContainerMithrilArcDynamo;
import phlaxmod.tileentity.TileMithrilArcDynamo;

public class MithrilArcDynamoScreen extends ContainerScreen<ContainerMithrilArcDynamo> {
    private final ResourceLocation GUI = new ResourceLocation(PhlaxMod.MODID,
            "textures/gui/mithril_arc_dynamo_gui.png");
    public TileMithrilArcDynamo tileEntity;
    public MithrilArcDynamoScreen(ContainerMithrilArcDynamo pMenu, PlayerInventory pPlayerInventory, ITextComponent pTitle) {
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
        int energyBarPixelCount = (int)(60 * this.tileEntity.getEnergyBarProgress());
        this.blit(pMatrixStack, i, j,0,0, this.getXSize(),this.getYSize());
        this.blit(pMatrixStack, i+78, j+33,190,22,(int)(30 * this.tileEntity.getBurnProgress()),12);
        this.blit(pMatrixStack, i+115, j+ 9 + 60 - energyBarPixelCount ,189,39 + 60 - energyBarPixelCount,12,energyBarPixelCount);

    }
}
