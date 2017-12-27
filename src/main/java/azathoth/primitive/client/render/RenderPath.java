package azathoth.primitive.client.render;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

public final class RenderPath implements ISimpleBlockRenderingHandler {
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block b, int modelID, RenderBlocks renderer) {
		Tessellator tess = Tessellator.instance;
		renderer.setRenderBounds(0f, 0f, 0f, 1f, 15f / 16f, 1f);
		if ((world.getBlockMetadata(x, y, z) & 8) == 8)
			renderer.uvRotateTop = 1;
		renderer.renderStandardBlockWithAmbientOcclusion(b, x, y, z, 2f, 2f, 2f);
		renderer.uvRotateTop = 0;
		return true;
	}

	@Override
	public void renderInventoryBlock(Block b, int meta, int modelID, RenderBlocks renderer) {
		return;
	}

	@Override
	public int getRenderId() {
		return PrimitiveRenderers.rendererPath;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelID) {
		return false;
	}
}
