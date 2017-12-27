package azathoth.primitive.client.render;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

public final class RenderCraftingSpot implements ISimpleBlockRenderingHandler {
	@Override
	public void renderInventoryBlock(Block b, int meta, int modelID, RenderBlocks renderer) {
		GL11.glTranslatef(-0.5f, -0.5f, - 0.5f);
		Tessellator tess = Tessellator.instance;
		renderer.setRenderBounds(0f, 0f, 0f, 1f, 1f / 16f, 1f);

		tess.startDrawingQuads();
		tess.setNormal(0f, -1f, 0f);
		renderer.renderFaceYNeg(b, 0d, 0d, 0d, renderer.getBlockIconFromSideAndMetadata(b, 0, 0));
		tess.draw();

		tess.startDrawingQuads();
		tess.setNormal(0f, 1f, 0f);
		renderer.renderFaceYPos(b, 0d, 0d, 0d, renderer.getBlockIconFromSideAndMetadata(b, 0, 0));
		tess.draw();

		tess.startDrawingQuads();
		tess.setNormal(0f, 0f, -1f);
		renderer.renderFaceZNeg(b, 0d, 0d, 0d, renderer.getBlockIconFromSideAndMetadata(b, 0, 0));
		tess.draw();

		tess.startDrawingQuads();
		tess.setNormal(0f, 0f, 1f);
		renderer.renderFaceZPos(b, 0d, 0d, 0d, renderer.getBlockIconFromSideAndMetadata(b, 0, 0));
		tess.draw();

		tess.startDrawingQuads();
		tess.setNormal(-1f, 0f, 0f);
		renderer.renderFaceXNeg(b, 0d, 0d, 0d, renderer.getBlockIconFromSideAndMetadata(b, 0, 0));
		tess.draw();

		tess.startDrawingQuads();
		tess.setNormal(1f, 0f, 0f);
		renderer.renderFaceXPos(b, 0d, 0d, 0d, renderer.getBlockIconFromSideAndMetadata(b, 0, 0));
		tess.draw();

		renderer.setRenderBounds(0f, 0f, 0f, 1f, (1f / 16f) + 0.001f, 1f);
		tess.startDrawingQuads();
		tess.setNormal(0, 1f, 0f);
		renderer.renderFaceYPos(b, 0d, 0d, 0d, renderer.getBlockIconFromSideAndMetadata(b, 1, 1));
		tess.draw();

		GL11.glTranslatef(0.5f, 0.5f, 0.5f);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block b, int modelID, RenderBlocks renderer) {
		renderer.setRenderBounds(0f, 0f, 0f, 1f, 1f / 16f, 1f);
		renderer.renderStandardBlockWithAmbientOcclusion(b, x, y, z, 1f, 1f, 1f);

		Tessellator tess = Tessellator.instance;
		IIcon iicon = b.getIcon(1, 1);
		float u = iicon.getMinU();
		float v = iicon.getMinV();
		float U = iicon.getMaxU();
		float V = iicon.getMaxV();
		float off = (1f / 16f) + 0.001f;

		int mode = tess.drawMode;

		if (tess.isDrawing) {
			tess.draw();
		}

		if (!tess.isDrawing)
			tess.startDrawingQuads();
		tess.setColorOpaque_F(1f, 1f, 1f);
		tess.setBrightness(b.getMixedBrightnessForBlock(world, x, y + 1, z));
		tess.setNormal(0f, 1f, 0f);
		tess.addVertexWithUV(x, y + off, z, U, V);
		tess.addVertexWithUV(x, y + off, z + 1f, U, v);
		tess.addVertexWithUV(x + 1f, y + off, z + 1f, u, v);
		tess.addVertexWithUV(x + 1f, y + off, z, u, V);
		tess.draw();

		if (!tess.isDrawing) {
			tess.startDrawing(mode);
		}

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelID) {
		return true;
	}

	@Override
	public int getRenderId() {
		return PrimitiveRenderers.rendererCraftingSpot;
	}
}
