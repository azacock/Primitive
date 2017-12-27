package azathoth.primitive.client.gui;

import azathoth.primitive.Primitive;
import azathoth.primitive.tileentity.BasketTileEntity;
import azathoth.primitive.tileentity.ContainerBasketTileEntity;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class GuiBasketTileEntity extends GuiContainer {
	private IInventory player_inv;
	private BasketTileEntity te;
	public GuiBasketTileEntity(IInventory player_inv, BasketTileEntity te) {
		super(new ContainerBasketTileEntity(player_inv, te));

		this.player_inv = player_inv;
		this.te = te;

		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partial_ticks, int mouse_x, int mouse_y) {
		GL11.glColor4f(1f, 1f, 1f, 1f);
		this.mc.getTextureManager().bindTexture(new ResourceLocation(Primitive.MODID + ":textures/gui/container/basket.png"));
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouse_x, int mouse_y) {
		String s = this.te.getDisplayName().getUnformattedText();
		this.fontRendererObj.drawString(s, 88 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752); // #404040
		// this.fontRendererObj.drawString(this.player_inv.getDisplayName().getUnformattedText(), 8, 72, 4210752); // #404040
	}

	@Override
	public void onGuiClosed() {
		te.markBlockForRenderUpdate();
	}
}
