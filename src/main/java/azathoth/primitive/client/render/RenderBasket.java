package azathoth.primitive.client.render;

import azathoth.primitive.Primitive;
import azathoth.primitive.tileentity.BasketTileEntity;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

public final class RenderBasket implements ISimpleBlockRenderingHandler {
	@Override
	public void renderInventoryBlock(Block b, int meta, int modelID, RenderBlocks renderer) {
		return;
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block b, int modelID, RenderBlocks renderer) {
		float dx = 0.5f;
		float dy = 0.8f;
		float dz = 0.5f;

		Tessellator tess = Tessellator.instance;
		IIcon icon = Primitive.wattle.getIcon(1, 3);
		float u = icon.getMinU();
		float v = icon.getMinV();
		float U = icon.getMaxU();
		float V = icon.getMaxV();

		int mode = tess.drawMode;
		if (tess.isDrawing) {
			tess.draw();
		}

		GL11.glPushMatrix();
		GL11.glTranslatef(0.25f, 0f, 0.25f);

		// bottom
		if (!tess.isDrawing)
			tess.startDrawingQuads();
		tess.setColorOpaque_F(0.5f, 0.5f, 0.5f);
		tess.setBrightness(b.getMixedBrightnessForBlock(world, x, y - 1, z));
		tess.setNormal(0f, -1f, 0f);
		tess.addVertexWithUV(x, y, z, u, V);
		tess.addVertexWithUV(x + dx, y, z, u, v);
		tess.addVertexWithUV(x + dx, y, z + dz, U, v);
		tess.addVertexWithUV(x, y, z + dz, U, V);
		tess.draw();
		if (!tess.isDrawing)
			tess.startDrawingQuads();
		tess.setColorOpaque_F(1f, 1f, 1f);
		tess.setBrightness(b.getMixedBrightnessForBlock(world, x, y, z));
		tess.setNormal(0f, 1f, 0f);
		tess.addVertexWithUV(x, y + 0.001f, z + dz, U, V);
		tess.addVertexWithUV(x + dx, y + 0.001f, z + dz, U, v);
		tess.addVertexWithUV(x + dx, y + 0.001f, z, u, v);
		tess.addVertexWithUV(x, y + 0.001f, z, u, V);
		tess.draw();

		// top
		float fill = ((BasketTileEntity) world.getTileEntity(x, y, z)).getFilledPercentage();
		if (fill > 0f) {
			fill *= (14f / 16f) * dy;
			icon = ((BasketTileEntity) world.getTileEntity(x, y, z)).getFillIcon();
			u = icon.getMinU();
			v = icon.getMinV();
			U = icon.getMaxU();
			V = icon.getMaxV();
			if (!tess.isDrawing)
				tess.startDrawingQuads();
			tess.setColorOpaque_F(1f, 1f, 1f);
			tess.setBrightness(b.getMixedBrightnessForBlock(world, x, y, z));
			tess.setNormal(0f, 1f, 0f);
			tess.addVertexWithUV(x, y + fill, z + dz, U, V);
			tess.addVertexWithUV(x + dx, y + fill, z + dz, U, v);
			tess.addVertexWithUV(x + dx, y + fill, z, u, v);
			tess.addVertexWithUV(x, y + fill, z, u, V);
			tess.draw();
		}


		// north
		icon = Primitive.wattle.getIcon(1, 2);
		u = icon.getMinU();
		v = icon.getMinV();
		U = icon.getMaxU();
		V = icon.getMaxV();
		if (!tess.isDrawing)
			tess.startDrawingQuads();
		tess.setColorOpaque_F(0.8f, 0.8f, 0.8f);
		tess.setBrightness(b.getMixedBrightnessForBlock(world, x, y, z));
		tess.setNormal(0f, 0f, -1f);
		tess.addVertexWithUV(x, y, z, U, V);
		tess.addVertexWithUV(x, y + dy, z, U, v);
		tess.addVertexWithUV(x + dx, y + dy, z, u, v);
		tess.addVertexWithUV(x + dx, y, z, u, V);
		tess.draw();
		icon = Primitive.wattle.getIcon(1, 1);
		u = icon.getMinU();
		v = icon.getMinV();
		U = icon.getMaxU();
		V = icon.getMaxV();
		if (!tess.isDrawing)
			tess.startDrawingQuads();
		tess.setColorOpaque_F(0.8f, 0.8f, 0.8f);
		tess.setBrightness(b.getMixedBrightnessForBlock(world, x, y, z));
		tess.setNormal(0f, 0f, 1f);
		tess.addVertexWithUV(x + dx, y, z, u, v);
		tess.addVertexWithUV(x + dx, y + dy, z, u, V);
		tess.addVertexWithUV(x, y + dy, z, U, V);
		tess.addVertexWithUV(x, y, z, U, v);
		tess.draw();

		// south
		icon = Primitive.wattle.getIcon(1, 2);
		u = icon.getMinU();
		v = icon.getMinV();
		U = icon.getMaxU();
		V = icon.getMaxV();
		if (!tess.isDrawing)
			tess.startDrawingQuads();
		tess.setColorOpaque_F(0.8f, 0.8f, 0.8f);
		tess.setBrightness(b.getMixedBrightnessForBlock(world, x, y, z));
		tess.setNormal(0f, 0f, 1f);
		tess.addVertexWithUV(x + dx, y, z + dz, U, V);
		tess.addVertexWithUV(x + dx, y + dy, z + dz, U, v);
		tess.addVertexWithUV(x, y + dy, z + dz, u, v);
		tess.addVertexWithUV(x, y, z + dz, u, V);
		tess.draw();
		icon = Primitive.wattle.getIcon(1, 1);
		u = icon.getMinU();
		v = icon.getMinV();
		U = icon.getMaxU();
		V = icon.getMaxV();
		if (!tess.isDrawing)
			tess.startDrawingQuads();
		tess.setColorOpaque_F(0.8f, 0.8f, 0.8f);
		tess.setBrightness(b.getMixedBrightnessForBlock(world, x, y, z));
		tess.setNormal(0f, 0f, -1f);
		tess.addVertexWithUV(x, y, z + dz, u, v);
		tess.addVertexWithUV(x, y + dy, z + dz, u, V);
		tess.addVertexWithUV(x + dx, y + dy, z + dz, U, V);
		tess.addVertexWithUV(x + dx, y, z + dz, U, v);
		tess.draw();

		// west
		icon = Primitive.wattle.getIcon(1, 2);
		u = icon.getMinU();
		v = icon.getMinV();
		U = icon.getMaxU();
		V = icon.getMaxV();
		if (!tess.isDrawing)
			tess.startDrawingQuads();
		tess.setColorOpaque_F(0.6f, 0.6f, 0.6f);
		tess.setBrightness(b.getMixedBrightnessForBlock(world, x, y, z));
		tess.setNormal(-1f, 0f, 0f);
		tess.addVertexWithUV(x, y, z + dz, U, V);
		tess.addVertexWithUV(x, y + dy, z + dz, U, v);
		tess.addVertexWithUV(x, y + dy, z, u, v);
		tess.addVertexWithUV(x, y, z, u, V);
		tess.draw();
		icon = Primitive.wattle.getIcon(1, 1);
		u = icon.getMinU();
		v = icon.getMinV();
		U = icon.getMaxU();
		V = icon.getMaxV();
		if (!tess.isDrawing)
			tess.startDrawingQuads();
		tess.setColorOpaque_F(0.6f, 0.6f, 0.6f);
		tess.setBrightness(b.getMixedBrightnessForBlock(world, x, y, z));
		tess.setNormal(1f, 0f, 0f);
		tess.addVertexWithUV(x, y, z, u, v);
		tess.addVertexWithUV(x, y + dy, z, u, V);
		tess.addVertexWithUV(x, y + dy, z + dz, U, V);
		tess.addVertexWithUV(x, y, z + dz, U, v);
		tess.draw();

		// east
		icon = Primitive.wattle.getIcon(1, 2);
		u = icon.getMinU();
		v = icon.getMinV();
		U = icon.getMaxU();
		V = icon.getMaxV();
		if (!tess.isDrawing)
			tess.startDrawingQuads();
		tess.setColorOpaque_F(0.6f, 0.6f, 0.6f);
		tess.setBrightness(b.getMixedBrightnessForBlock(world, x, y, z));
		tess.setNormal(1f, 0f, 0f);
		tess.addVertexWithUV(x + dx, y, z, U, V);
		tess.addVertexWithUV(x + dx, y + dy, z, U, v);
		tess.addVertexWithUV(x + dx, y + dy, z + dz, u, v);
		tess.addVertexWithUV(x + dx, y, z + dz,  u, V);
		tess.draw();
		icon = Primitive.wattle.getIcon(1, 1);
		u = icon.getMinU();
		v = icon.getMinV();
		U = icon.getMaxU();
		V = icon.getMaxV();
		if (!tess.isDrawing)
			tess.startDrawingQuads();
		tess.setColorOpaque_F(0.6f, 0.6f, 0.6f);
		tess.setBrightness(b.getMixedBrightnessForBlock(world, x, y, z));
		tess.setNormal(-1f, 0f, 0f);
		tess.addVertexWithUV(x + dx, y, z + dz,  u, v);
		tess.addVertexWithUV(x + dx, y + dy, z + dz, u, V);
		tess.addVertexWithUV(x + dx, y + dy, z, U, V);
		tess.addVertexWithUV(x + dx, y, z, U, v);
		tess.draw();

		GL11.glPopMatrix();

		if (!tess.isDrawing) {
			tess.startDrawing(mode);
		}

		return true;
	}

	@Override
	public int getRenderId() {
		return PrimitiveRenderers.rendererBasket;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelID) {
		return false;
	}
}
