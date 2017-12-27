package azathoth.primitive.client.render;

import azathoth.primitive.Primitive;
import azathoth.primitive.tileentity.DryingBrickTileEntity;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public final class RenderDryingBrick implements ISimpleBlockRenderingHandler {
	protected float dx = 6.5f * (1f / 16f);
	protected float dy = 2f * (1f / 16f);
	protected float dz = 4f * (1f / 16f);
	protected float gap = 1f / 16f;
	protected float wet = 0.1f;

	@Override
	public void renderInventoryBlock(Block block, int meta, int modelID, RenderBlocks renderer) {
		return;
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess blockAccess, int x, int y, int z, Block block, int modelID, RenderBlocks renderer) {
		byte brick;
		DryingBrickTileEntity te = (DryingBrickTileEntity) blockAccess.getTileEntity(x, y, z);

		if (te == null)
			return false;

		int b = 0;
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 3; j++, b++) {
				brick = te.getBrickState(b);
				if (brick > 0)
					this.renderBrick(blockAccess, block, x, y, z, ((i + 1) * gap) + (i * dx), ((j + 1) * gap) + (j * dz), wet * (brick - 1), renderer);
			}
		}
		return true;
	}
	protected void renderBrick(IBlockAccess access, Block b, int x, int y, int z, float xo, float zo, float wet, RenderBlocks renderer) {
		Tessellator tess = Tessellator.instance;
		IIcon icon = Primitive.adobe.getIcon(0, 1);
		float u = icon.getMinU();
		float v = icon.getMinV();
		float U = icon.getMaxU();
		float V = icon.getMaxV();

		int mode = tess.drawMode;
		if (tess.isDrawing) {
			tess.draw();
		}

		// top
		if (!tess.isDrawing)
			tess.startDrawingQuads();
		tess.setColorOpaque_F(1f - wet, 1f - wet, 1f - wet);
		tess.setBrightness(b.getMixedBrightnessForBlock(access, x, y + 1, z));
		tess.setNormal(0f, 1f, 0f);
		tess.addVertexWithUV(x + xo, y + dy, z + zo + dz, U, V);
		tess.addVertexWithUV(x + xo + dx, y + dy, z + zo + dz, U, v);
		tess.addVertexWithUV(x + xo + dx, y + dy, z + zo, u, v);
		tess.addVertexWithUV(x + xo, y + dy, z + zo, u, V);
		tess.draw();

		// north
		if (!tess.isDrawing)
			tess.startDrawingQuads();
		tess.setColorOpaque_F(0.8f - wet, 0.8f - wet, 0.8f - wet);
		tess.setBrightness(b.getMixedBrightnessForBlock(access, x, y, z - 1));
		tess.setNormal(0f, 0f, -1f);
		tess.addVertexWithUV(x + xo, y, z + zo, U, V);
		tess.addVertexWithUV(x + xo, y + dy, z + zo, U, v);
		tess.addVertexWithUV(x + xo + dx, y + dy, z + zo, u, v);
		tess.addVertexWithUV(x + xo + dx, y, z + zo, u, V);
		tess.draw();

		// south
		if (!tess.isDrawing)
			tess.startDrawingQuads();
		tess.setColorOpaque_F(0.8f - wet, 0.8f - wet, 0.8f - wet);
		tess.setBrightness(b.getMixedBrightnessForBlock(access, x, y, z + 1));
		tess.setNormal(0f, 0f, 1f);
		tess.addVertexWithUV(x + xo + dx, y, z + zo + dz, U, V);
		tess.addVertexWithUV(x + xo + dx, y + dy, z + zo + dz, U, v);
		tess.addVertexWithUV(x + xo, y + dy, z + zo + dz, u, v);
		tess.addVertexWithUV(x + xo, y, z + zo + dz, u, V);
		tess.draw();

		// west
		if (!tess.isDrawing)
			tess.startDrawingQuads();
		tess.setColorOpaque_F(0.6f - wet, 0.6f - wet, 0.6f - wet);
		tess.setBrightness(b.getMixedBrightnessForBlock(access, x - 1, y, z));
		tess.setNormal(-1f, 0f, 0f);
		tess.addVertexWithUV(x + xo, y, z + zo + dz, U, V);
		tess.addVertexWithUV(x + xo, y + dy, z + zo + dz, U, v);
		tess.addVertexWithUV(x + xo, y + dy, z + zo, u, v);
		tess.addVertexWithUV(x + xo, y, z + zo, u, V);
		tess.draw();

		// east
		if (!tess.isDrawing)
			tess.startDrawingQuads();
		tess.setColorOpaque_F(0.6f - wet, 0.6f - wet, 0.6f - wet);
		tess.setBrightness(b.getMixedBrightnessForBlock(access, x + 1, y, z));
		tess.setNormal(1f, 0f, 0f);
		tess.addVertexWithUV(x + xo + dx, y, z + zo, U, V);
		tess.addVertexWithUV(x + xo + dx, y + dy, z + zo, U, v);
		tess.addVertexWithUV(x + xo + dx, y + dy, z + zo + dz, u, v);
		tess.addVertexWithUV(x + xo + dx, y, z + zo + dz,  u, V);
		tess.draw();

		if (!tess.isDrawing) {
			tess.startDrawing(mode);
		}
	}

	@Override 
	public int getRenderId() {
		return PrimitiveRenderers.rendererDryingBrick;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelID) {
		return false;
	}
}
