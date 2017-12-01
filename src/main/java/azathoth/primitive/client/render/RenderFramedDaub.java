package azathoth.primitive.client.render;

import azathoth.primitive.tileentity.FramedDaubTileEntity;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

public final class RenderFramedDaub implements ISimpleBlockRenderingHandler {
	@Override
	public void renderInventoryBlock(Block block, int meta, int modelID, RenderBlocks renderer) {
		return;
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess blockAccess, int x, int y, int z, Block block, int modelID, RenderBlocks renderer) {
		FramedDaubTileEntity te = (FramedDaubTileEntity) blockAccess.getTileEntity(x, y, z);
		if (te == null)
			return false;

		Tessellator tessellator = Tessellator.instance;
		IIcon iicon;
		float u;
		float v;
		float U;
		float V;
		double offset = 0.0001;

		// tessellator.addTranslation(x, y, z);
		tessellator.setColorOpaque_F(1f, 1f, 1f);
		// tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, x, y, z));
		tessellator.setBrightness(255);

		renderer.renderStandardBlockWithAmbientOcclusion(block, x, y, z, 1f, 1f, 1f);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		for (int i = 1; i <= 12; i++) {
			double ioffset = offset * i;
			short frame = (short) (1 << (i - 1));
			iicon = block.getIcon(0, i);
			u = iicon.getMinU();
			v = iicon.getMinV();
			U = iicon.getMaxU();
			V = iicon.getMaxV();

			// bottom
			if (te.hasFrame(0, frame)) {
				tessellator.setNormal(0f, -1f, 0f);
				tessellator.addVertexWithUV(x, y - ioffset, z, U, V);
				tessellator.addVertexWithUV(x + 1f, y - ioffset, z, U, v);
				tessellator.addVertexWithUV(x + 1f, y - ioffset, z + 1f, u, v);
				tessellator.addVertexWithUV(x, y - ioffset, z + 1f, u, V);
			}

			// top
			if (te.hasFrame(1, frame)) {
				tessellator.setNormal(0f, 1f, 0f);
				tessellator.addVertexWithUV(x, y + 1f + ioffset, z, U, V);
				tessellator.addVertexWithUV(x, y + 1f + ioffset, z + 1f, U, v);
				tessellator.addVertexWithUV(x + 1f, y + 1f + ioffset, z + 1f, u, v);
				tessellator.addVertexWithUV(x + 1f, y + 1f + ioffset, z, u, V);
			}

			// north
			if (te.hasFrame(2, frame)) {
				tessellator.setNormal(0f, 0f, -1f);
				tessellator.addVertexWithUV(x, y, z - ioffset, U, V);
				tessellator.addVertexWithUV(x, y + 1f, z - ioffset, U, v);
				tessellator.addVertexWithUV(x + 1f, y + 1f, z - ioffset, u, v);
				tessellator.addVertexWithUV(x + 1f, y, z - ioffset, u, V);
			}

			// south
			if (te.hasFrame(3, frame)) {
				tessellator.setNormal(0f, 0f, 1f);
				tessellator.addVertexWithUV(x + 1f, y, z + 1f + ioffset, U, V);
				tessellator.addVertexWithUV(x + 1f, y + 1f, z + 1f + ioffset, U, v);
				tessellator.addVertexWithUV(x, y + 1f, z + 1f + ioffset, u, v);
				tessellator.addVertexWithUV(x, y, z + 1f + ioffset, u, V);
			}

			// west
			if (te.hasFrame(4, frame)) {
				tessellator.setNormal(-1f, 0f, 0f);
				tessellator.addVertexWithUV(x - ioffset, y, z + 1f, U, V);
				tessellator.addVertexWithUV(x - ioffset, y + 1f, z + 1f, U, v);
				tessellator.addVertexWithUV(x - ioffset, y + 1f, z, u, v);
				tessellator.addVertexWithUV(x - ioffset, y, z, u, V);
			}

			// east
			if (te.hasFrame(5, frame)) {
				tessellator.setNormal(1f, 0f, 0f);
				tessellator.addVertexWithUV(x + 1f + ioffset, y, z, U, V);
				tessellator.addVertexWithUV(x + 1f + ioffset, y + 1f, z, U, v);
				tessellator.addVertexWithUV(x + 1f + ioffset, y + 1f, z + 1f, u, v);
				tessellator.addVertexWithUV(x + 1f + ioffset, y, z + 1f, u, V);
			}
		}
		GL11.glDisable(GL11.GL_BLEND);

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelID) {
		return false;
	}

	@Override
	public int getRenderId() {
		return PrimitiveRenderers.rendererFramedDaub;
	}
}
