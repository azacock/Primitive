package azathoth.primitive.client.render;

import azathoth.primitive.Primitive;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

public final class RenderRock implements ISimpleBlockRenderingHandler {
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block b, int modelID, RenderBlocks renderer) {
		int p0 = 277;
		int p1 = 523;
		int p2 = 859;
		int p3 = 941;
		int _x = (x <= 0) ? 0 - x : x;
		int _z = (z <= 0) ? 0 - z : z;
		int xh = ((_x * p0 + _z) * p1 + y) % 5;
		int zh = ((_x * p2 + _z) * p3 + y) % 5;
		GL11.glPushMatrix();
		if (world.getBlock(x, y - 1, z) == Primitive.path) {
			Primitive.logger.info("fuck?!?!");
			GL11.glTranslatef(0f, -0.5f, 0f);
		}
		renderer.setRenderBounds(0.2f * xh, 0, 0.2f * zh, (0.2f * xh) + 0.2f, 0.1f, (0.2f * zh) + 0.2f);
		renderer.renderStandardBlockWithAmbientOcclusion(b, x, y, z, 1f, 1f, 1f);
		GL11.glPopMatrix();
		return true;
	}

	@Override
	public void renderInventoryBlock(Block b, int meta, int modelID, RenderBlocks renderer) {
		return;
	}

	@Override
	public int getRenderId() {
		return PrimitiveRenderers.rendererRock;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelID) {
		return false;
	}
}
