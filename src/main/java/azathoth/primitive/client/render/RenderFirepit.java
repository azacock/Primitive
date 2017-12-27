package azathoth.primitive.client.render;

import azathoth.primitive.Primitive;
import azathoth.primitive.tileentity.FirepitTileEntity;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

public final class RenderFirepit implements ISimpleBlockRenderingHandler {
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block b, int modelID, RenderBlocks renderer) {
		float full;
		boolean lit = false;

		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null && te instanceof FirepitTileEntity) {
			FirepitTileEntity pit = (FirepitTileEntity) te;
			full = (float) pit.getFuel() / (float) pit.getMaxFuel();
			lit = pit.isLit();
		} else {
			full = 0f;
		}

		this.renderRocks(world, x, y, z, b, lit, renderer);

		Primitive.logger.info("Firepit fullness on render: " + full);

		if (full > 0f) {
			this.renderCoals(world, x, y, z, full, b, renderer);
			if (lit)
				this.renderFire(world, x, y, z, full, b, renderer);
		}

		// float xt = (x >> 4 << 4) - x - 0.5f;
		// float yt = (y >> 4 << 4) - y - 0.5f;
		// float zt = (z >> 4 << 4) - z - 0.5f;

		// int c = getDirectNeighborCount(world, x, y, z, b);
		// if (c == 4)
		// 	c += getIndirectNeighborCount(world, x, y, z, b);

		// GL11.glPushMatrix();
		// GL11.glTranslatef(-xt, -yt, -zt);
		// GL11.glTranslatef(0f, 0.25f * (c / 4f), 0f);
		// GL11.glScalef(1f + 0.125f * c, 1f + 0.125f * c, 1f + 0.125f * c);
		// GL11.glRotatef(45f, 0f, 1f, 0f);
		// GL11.glTranslatef(xt, yt, zt);

		// this.renderSticks(world, x, y, z, b, renderer);


		// GL11.glPopMatrix();

		return true;
	}

	public boolean renderSticks(IBlockAccess world, int x, int y, int z, Block b, RenderBlocks renderer) {
		this.renderStick(world, x, y, z, 0.5f, 3f / 16f, 0, b, renderer);
		this.renderStick(world, x, y, z, 3f / 16f, 0.5f, 3, b, renderer);
		this.renderStick(world, x, y, z, 1f - (3f / 16f), 0.5f, 1, b, renderer);
		this.renderStick(world, x, y, z, 0.5f, 1f - (3f / 16f), 2, b, renderer);
		return true;
	}

	public boolean renderRocks(IBlockAccess world, int x, int y, int z, Block b, boolean lit, RenderBlocks renderer) {
		int c = getDirectNeighborCount(world, x, y, z, b);
		float cx = 0;
		float cz = 0;
		float s = 0.2f;
		float gap = 0.01f;
		
		IIcon icon = Blocks.cobblestone.getIcon(0, 0);
		float u = icon.getMinU();
		float v = icon.getMinV();
		float u_0 = icon.getInterpolatedU(2);
		float v_0 = icon.getInterpolatedV(2);
		float u_1 = icon.getInterpolatedU(14);
		float v_1 = icon.getInterpolatedV(14);
		float U = icon.getMaxU();
		float V = icon.getMaxV();
		boolean north = world.getBlock(x, y, z - 1) != b;
		boolean south = world.getBlock(x, y, z + 1) != b;
		boolean east = world.getBlock(x + 1, y, z) != b;
		boolean west = world.getBlock(x - 1, y, z) != b;
		boolean ne = world.getBlock(x + 1, y, z - 1) != b;
		boolean se = world.getBlock(x + 1, y, z + 1) != b;
		boolean nw = world.getBlock(x - 1, y, z - 1) != b;
		boolean sw = world.getBlock(x - 1, y, z + 1) != b;

		float off = 2f / 16f;

		Tessellator tess = Tessellator.instance;

		int mode = tess.drawMode;

		if (tess.isDrawing)
			tess.draw();

		renderer.setRenderBounds(0f, 0f, 0f, 1f, 0.5f, 1f);


		// outside
		// bottom
		if (!tess.isDrawing)
			tess.startDrawingQuads();
		tess.setColorOpaque_F(0.5f, 0.5f, 0.5f);
		tess.setNormal(0f, -1f, 0f);
		tess.setBrightness(b.getMixedBrightnessForBlock(world, x, y - 1, z));
		renderer.renderFaceYNeg(Blocks.cobblestone, x, y, z, icon);
		tess.draw();

		// south
		if (south) {
			if (!tess.isDrawing)
				tess.startDrawingQuads();
			tess.setColorOpaque_F(0.8f, 0.8f, 0.8f);
			tess.setBrightness(b.getMixedBrightnessForBlock(world, x, y, z + 1));
			tess.setNormal(0f, 0f, 1f);
			renderer.renderFaceZPos(Blocks.cobblestone, x, y, z, icon);
			tess.draw();
		}

		// north
		if (north) {
			if (!tess.isDrawing)
				tess.startDrawingQuads();
			tess.setColorOpaque_F(0.8f, 0.8f, 0.8f);
			tess.setNormal(0f, 0f, -1f);
			tess.setBrightness(b.getMixedBrightnessForBlock(world, x, y, z - 1));
			renderer.renderFaceZNeg(Blocks.cobblestone, x, y, z, icon);
		}

		// east
		if (east) {
			if (!tess.isDrawing)
				tess.startDrawingQuads();
			tess.setColorOpaque_F(0.6f, 0.6f, 0.6f);
			tess.setBrightness(b.getMixedBrightnessForBlock(world, x + 1, y, z));
			tess.setNormal(1f, 0f, 0f);
			renderer.renderFaceXPos(Blocks.cobblestone, x, y, z, icon);
		}

		// west
		if (west) {
			if (!tess.isDrawing)
				tess.startDrawingQuads();
			tess.setColorOpaque_F(0.6f, 0.6f, 0.6f);
			tess.setBrightness(b.getMixedBrightnessForBlock(world, x - 1, y, z));
			tess.setNormal(-1f, 0f, 0f);
			renderer.renderFaceXNeg(Blocks.cobblestone, x, y, z, icon);
		}


		// inside
		// south
		int brightness = lit ? 15728880 : b.getMixedBrightnessForBlock(world, x, y, z);
		float ns_color = lit ? 1f : 0.8f;
		float ew_color = lit ? 1f : 0.6f;
		if (south) {
			if (!tess.isDrawing)
				tess.startDrawingQuads();
			tess.setColorOpaque_F(ns_color, ns_color, ns_color);
			tess.setBrightness(brightness);
			tess.setNormal(0f, 0f, -1f);
			tess.addVertexWithUV(x + 1f, y, z + 1f - off, U, V);
			tess.addVertexWithUV(x, y, z + 1f - off, u, V);
			tess.addVertexWithUV(x, y + 0.5f, z + 1f - off, u, v);
			tess.addVertexWithUV(x + 1f, y + 0.5f, z + 1f - off, U, v);
			tess.draw();
		}

		// north
		if (north) {
			if (!tess.isDrawing)
				tess.startDrawingQuads();
			tess.setColorOpaque_F(ns_color, ns_color, ns_color);
			tess.setNormal(0f, 0f, 1f);
			tess.setBrightness(brightness);
			tess.addVertexWithUV(x, y, z + off, U, V);
			tess.addVertexWithUV(x + 1f, y, z + off, u, V);
			tess.addVertexWithUV(x + 1f, y + 0.5f, z + off, u, v);
			tess.addVertexWithUV(x, y + 0.5f, z + off, U, v);
			tess.draw();
		}

		// east
		if (east) {
			if (!tess.isDrawing)
				tess.startDrawingQuads();
			tess.setColorOpaque_F(ew_color, ew_color, ew_color);
			tess.setBrightness(brightness);
			tess.setNormal(-1f, 0f, 0f);
			tess.addVertexWithUV(x + 1f - off, y, z, U, V);
			tess.addVertexWithUV(x + 1f - off, y, z + 1f, u, V);
			tess.addVertexWithUV(x + 1f - off, y + 0.5f, z + 1f, u, v);
			tess.addVertexWithUV(x + 1f - off, y + 0.5f, z, U, v);
			tess.draw();
		}
		
		// west
		if (west) {
			if (!tess.isDrawing)
				tess.startDrawingQuads();
			tess.setColorOpaque_F(ew_color, ew_color, ew_color);
			tess.setBrightness(brightness);
			tess.setNormal(1f, 0f, 0f);
			tess.addVertexWithUV(x + off, y, z + 1f, U, V);
			tess.addVertexWithUV(x + off, y, z, u, V);
			tess.addVertexWithUV(x + off, y + 0.5f, z, u, v);
			tess.addVertexWithUV(x + off, y + 0.5f, z + 1f, U, v);
			tess.draw();
		}

		// bottom
			if (!tess.isDrawing)
				tess.startDrawingQuads();
			tess.setColorOpaque_F(1f, 1f, 1f);
			tess.setNormal(0f, 1f, 0f);
			tess.setBrightness(brightness);
			tess.addVertexWithUV(x, y, z, u, v);
			tess.addVertexWithUV(x, y, z + 1f, u, V);
			tess.addVertexWithUV(x + 1f, y, z + 1f, U, V);
			tess.addVertexWithUV(x + 1f, y, z, U, v);
			tess.draw();

		if (!south && !east && se) {
			// south
			if (!tess.isDrawing)
				tess.startDrawingQuads();
			tess.setColorOpaque_F(ns_color, ns_color, ns_color);
			tess.setBrightness(brightness);
			tess.setNormal(-1f, 0f, 0f);
			tess.addVertexWithUV(x + (14f / 16f), y, z + (14f / 16f), u_0, V);
			tess.addVertexWithUV(x + (14f / 16f), y + 0.5f, z + (14f / 16f), u_0, v);
			tess.addVertexWithUV(x + 1f, y + 0.5f, z + (14f / 16f), u, v);
			tess.addVertexWithUV(x + 1f, y, z + (14f / 16f), u, V);
			tess.draw();
	
			// east
			if (!tess.isDrawing)
				tess.startDrawingQuads();
			tess.setColorOpaque_F(ew_color, ew_color, ew_color);
			tess.setBrightness(brightness);
			tess.setNormal(0f, 0f, -1f);
			tess.addVertexWithUV(x + (14f / 16f), y, z + 1f, U, V);
			tess.addVertexWithUV(x + (14f / 16f), y + 0.5f, z + 1f, U, v);
			tess.addVertexWithUV(x + (14f / 16f), y + 0.5f, z + (14f / 16f), u_1, v);
			tess.addVertexWithUV(x + (14f / 16f), y, z + (14f / 16f), u_1, V);
			tess.draw();
		}
		
		if (!south && !west && sw) {
			// south
			if (!tess.isDrawing)
				tess.startDrawingQuads();
			tess.setColorOpaque_F(ns_color, ns_color, ns_color);
			tess.setBrightness(brightness);
			tess.setNormal(-1f, 0f, 0f);
			tess.addVertexWithUV(x, y, z + (14f / 16f), U, V);
			tess.addVertexWithUV(x, y + 0.5f, z + (14f / 16f), U, v);
			tess.addVertexWithUV(x + (2f / 16f), y + 0.5f, z + (14f / 16f), u_1, v);
			tess.addVertexWithUV(x + (2f / 16f), y, z + (14f / 16f), u_1, V);
			tess.draw();
			
			// west
			if (!tess.isDrawing)
				tess.startDrawingQuads();
			tess.setColorOpaque_F(ew_color, ew_color, ew_color);
			tess.setBrightness(brightness);
			tess.setNormal(0f, 0f, 1f);
			tess.addVertexWithUV(x + (2f / 16f), y, z + (14f / 16f), u_0, V);
			tess.addVertexWithUV(x + (2f / 16f), y + 0.5f, z + (14f / 16f), u_0, v);
			tess.addVertexWithUV(x + (2f / 16f), y + 0.5f, z + 1f, u, v);
			tess.addVertexWithUV(x + (2f / 16f), y, z + 1f, u, V);
			tess.draw();
		}
		
		if (!north && !east && ne) {
			// north
			if (!tess.isDrawing)
				tess.startDrawingQuads();
			tess.setColorOpaque_F(ns_color, ns_color, ns_color);
			tess.setBrightness(brightness);
			tess.setNormal(1f, 0f, 0f);
			tess.addVertexWithUV(x + 1f, y, z + (2f / 16f), U, V);
			tess.addVertexWithUV(x + 1f, y + 0.5f, z + (2f / 16f), U, v);
			tess.addVertexWithUV(x + (14f / 16f), y + 0.5f, z + (2f / 16f), u_1, v);
			tess.addVertexWithUV(x + (14f / 16f), y, z + (2f / 16f), u_1, V);
			tess.draw();

			// east
			if (!tess.isDrawing)
				tess.startDrawingQuads();
			tess.setColorOpaque_F(ew_color, ew_color, ew_color);
			tess.setBrightness(brightness);
			tess.setNormal(0f, 0f, -1f);
			tess.addVertexWithUV(x + (14f / 16f), y, z + (2f / 16f), u_0, V);
			tess.addVertexWithUV(x + (14f / 16f), y + 0.5f, z + (2f / 16f), u_0, v);
			tess.addVertexWithUV(x + (14f / 16f), y + 0.5f, z, u, v);
			tess.addVertexWithUV(x + (14f / 16f), y, z, u, V);
			tess.draw();
		}

		if (!north && !west && nw) {
			// north
			if (!tess.isDrawing)
				tess.startDrawingQuads();
			tess.setColorOpaque_F(ns_color, ns_color, ns_color);
			tess.setBrightness(brightness);
			tess.setNormal(1f, 0f, 0f);
			tess.addVertexWithUV(x + (2f / 16f), y, z + (2f / 16f), u_0, V);
			tess.addVertexWithUV(x + (2f / 16f), y + 0.5f, z + (2f / 16f), u_0, v);
			tess.addVertexWithUV(x, y + 0.5f, z + (2f / 16f), u, v);
			tess.addVertexWithUV(x, y, z + (2f / 16f), u, V);
			tess.draw();

			// west
			if (!tess.isDrawing)
				tess.startDrawingQuads();
			tess.setColorOpaque_F(ew_color, ew_color, ew_color);
			tess.setBrightness(brightness);
			tess.setNormal(0f, 0f, 1f);
			tess.addVertexWithUV(x + (2f / 16f), y, z, U, V);
			tess.addVertexWithUV(x + (2f / 16f), y + 0.5f, z, U, v);
			tess.addVertexWithUV(x + (2f / 16f), y + 0.5f, z + (2f / 16f), u_1, v);
			tess.addVertexWithUV(x + (2f / 16f), y, z + (2f / 16f), u_1, V);
			tess.draw();
		}

		// top

		if (south) {
			if (!tess.isDrawing)
				tess.startDrawingQuads();
			tess.setColorOpaque_F(1f, 1f, 1f);
			tess.setNormal(0f, 1f, 0f);
			tess.setBrightness(b.getMixedBrightnessForBlock(world, x, y, z));
			tess.addVertexWithUV(x, y + 0.5f, z + (14f / 16f), u, v_1);
			tess.addVertexWithUV(x, y + 0.5f, z + 1f, u, V);
			tess.addVertexWithUV(x + 1f, y + 0.5f, z + 1f, U, V);
			tess.addVertexWithUV(x + 1f, y + 0.5f, z + (14f / 16f), U, v_1);
			tess.draw();
		}

		if (north) {
			if (!tess.isDrawing)
				tess.startDrawingQuads();
			tess.setColorOpaque_F(1f, 1f, 1f);
			tess.setNormal(0f, 1f, 0f);
			tess.setBrightness(b.getMixedBrightnessForBlock(world, x, y, z));
			tess.addVertexWithUV(x, y + 0.5f, z, u, v);
			tess.addVertexWithUV(x, y + 0.5f, z + (2f / 16f), u, v_0);
			tess.addVertexWithUV(x + 1f, y + 0.5f, z + (2f / 16f), U, v_0);
			tess.addVertexWithUV(x + 1f, y + 0.5f, z, U, v);
			tess.draw();
		}

		if (east) {
			if (!tess.isDrawing)
				tess.startDrawingQuads();
			tess.setColorOpaque_F(1f, 1f, 1f);
			tess.setNormal(0f, 1f, 0f);
			tess.setBrightness(b.getMixedBrightnessForBlock(world, x, y, z));
			tess.addVertexWithUV(x + (14f / 16f), y + 0.5f, z, u_1, v);
			tess.addVertexWithUV(x + (14f / 16f), y + 0.5f, z + 1f, u_1, V);
			tess.addVertexWithUV(x + 1f, y + 0.5f, z + 1f, U, V);
			tess.addVertexWithUV(x + 1f, y + 0.5f, z, U, v);
			tess.draw();
		}

		if (west) {
			if (!tess.isDrawing)
				tess.startDrawingQuads();
			tess.setColorOpaque_F(1f, 1f, 1f);
			tess.setNormal(0f, 1f, 0f);
			tess.setBrightness(b.getMixedBrightnessForBlock(world, x, y, z));
			tess.addVertexWithUV(x + (2f / 16f), y + 0.5f, z + 1f, u_0, V);
			tess.addVertexWithUV(x + (2f / 16f), y + 0.5f, z, u_0, v);
			tess.addVertexWithUV(x, y + 0.5f, z, u, v);
			tess.addVertexWithUV(x, y + 0.5f, z + 1f, u, V);
			tess.draw();
		}

		if (!south && !east && se) {
			if (!tess.isDrawing)
				tess.startDrawingQuads();
			tess.setColorOpaque_F(1f, 1f, 1f);
			tess.setNormal(0f, 1f, 0f);
			tess.setBrightness(b.getMixedBrightnessForBlock(world, x, y, z));
			tess.addVertexWithUV(x + (14f / 16f), y + 0.5f, z + (14f / 16f), u_1, v_1);
			tess.addVertexWithUV(x + (14f / 16f), y + 0.5f, z + 1f, u_1, V);
			tess.addVertexWithUV(x + 1f, y + 0.5f, z + 1f, U, V);
			tess.addVertexWithUV(x + 1f, y + 0.5f, z + (14f / 16f), U, v_1);
			tess.draw();
		}

		if (!south && !west && sw) {
			if (!tess.isDrawing)
				tess.startDrawingQuads();
			tess.setColorOpaque_F(1f, 1f, 1f);
			tess.setNormal(0f, 1f, 0f);
			tess.setBrightness(b.getMixedBrightnessForBlock(world, x, y, z));
			tess.addVertexWithUV(x + (2f / 16f), y + 0.5f, z + 1f, u_0, V);
			tess.addVertexWithUV(x + (2f / 16f), y + 0.5f, z + (14f /  16f), u_0, v_1);
			tess.addVertexWithUV(x, y + 0.5f, z + (14f / 16f), u, v_1);
			tess.addVertexWithUV(x, y + 0.5f, z + 1f, u, V);
			tess.draw();
		}

		if (!north && !east && ne) {
			if (!tess.isDrawing)
				tess.startDrawingQuads();
			tess.setColorOpaque_F(1f, 1f, 1f);
			tess.setNormal(0f, 1f, 0f);
			tess.setBrightness(b.getMixedBrightnessForBlock(world, x, y, z));
			tess.addVertexWithUV(x + 1f, y + 0.5f, z + (2f / 16f), U, v_0);
			tess.addVertexWithUV(x + 1f, y + 0.5f, z, U, v);
			tess.addVertexWithUV(x + (14f / 16f), y + 0.5f, z, u_1, v);
			tess.addVertexWithUV(x + (14f/ 16f), y + 0.5f, z + (2f / 16f), u_1, v_0);
			tess.draw();
		}
		
		if (!north && !west && nw) {
			if (!tess.isDrawing)
				tess.startDrawingQuads();
			tess.setColorOpaque_F(1f, 1f, 1f);
			tess.setNormal(0f, 1f, 0f);
			tess.setBrightness(b.getMixedBrightnessForBlock(world, x, y, z));
			tess.addVertexWithUV(x + (2f / 16f), y + 0.5f, z + (2f / 16f), u_0, v_0);
			tess.addVertexWithUV(x + (2f / 16f), y + 0.5f, z, u_0, v);
			tess.addVertexWithUV(x, y + 0.5f, z, u, v);
			tess.addVertexWithUV(x, y + 0.5f, z + (2f / 16f), u, v_0);
			tess.draw();
		}

		if (!tess.isDrawing)
			tess.startDrawing(mode);

		return true;
	}

	public int getDirectNeighborCount(IBlockAccess world, int x, int y, int z, Block b) {
		int c = 0;
		if (world.getBlock(x - 1, y, z) == b)
			c++;
		if (world.getBlock(x + 1, y, z) == b)
			c++;
		if (world.getBlock(x, y, z - 1) == b)
			c++;
		if (world.getBlock(x, y, z + 1) == b)
			c++;
		return c;
	}


	public int getIndirectNeighborCount(IBlockAccess world, int x, int y, int z, Block b) {
		int c = 0;
		if (world.getBlock(x - 1, y, z - 1) == b)
			c++;
		if (world.getBlock(x + 1, y, z - 1) == b)
			c++;
		if (world.getBlock(x - 1, y, z + 1) == b)
			c++;
		if (world.getBlock(x + 1, y, z + 1) == b)
			c++;
		return c;
	}

	public boolean renderFire(IBlockAccess world, int x, int y, int z, float h, Block b, RenderBlocks renderer) {
		if (h > 1f)
			h = 1f;
		else if (h < 0f)
			h = 0f;
		IIcon icon = Blocks.fire.getIcon(0, 0);
		float u = icon.getMinU();
		float v = icon.getMinV();
		float U = icon.getMaxU();
		float V = icon.getMaxV();
		float offset = 3f / 16f;
		float n_off, s_off, e_off, w_off;
		float lean = 3f / 16f;
		float y_min = y + ((7f / 16f) * h);
		float y_max = y_min + 0.75f;
		boolean north = world.getBlock(x, y, z - 1) == b;
		boolean south = world.getBlock(x, y, z + 1) == b;
		boolean east = world.getBlock(x + 1, y, z) == b;
		boolean west = world.getBlock(x - 1, y, z) == b;

		if (north)
			n_off = -offset;
		else
			n_off = 0f;

		if (south)
			s_off = -offset;
		else
			s_off = 0f;

		if (east)
			e_off = -offset;
		else
			e_off = 0f;

		if (west)
			w_off = -offset;
		else
			w_off = 0f;

		Tessellator tess = Tessellator.instance;

		int mode = tess.drawMode;

		if (tess.isDrawing)
			tess.draw();

		// south
		if (!tess.isDrawing)
			tess.startDrawingQuads();
		tess.setColorOpaque_F(1f, 1f, 1f);
		tess.setBrightness(15728880);
		tess.setNormal(0f, 0f, 1f);
		tess.addVertexWithUV(x + 1f - offset - e_off, y_min, z + 1f - offset - (s_off * 0.5f), U, V);
		tess.addVertexWithUV(x + 1f - offset - e_off, y_max, z + 1f - offset - lean - (s_off * 0.5f), U, v);
		tess.addVertexWithUV(x + offset + w_off, y_max, z + 1f - offset - lean - (s_off * 0.5f), u, v);
		tess.addVertexWithUV(x + offset + w_off, y_min, z + 1f - offset - (s_off * 0.5f), u, V);
		tess.draw();

		// north
		if (!tess.isDrawing)
			tess.startDrawingQuads();
		tess.setColorOpaque_F(1f, 1f, 1f);
		tess.setBrightness(15728880);
		tess.setNormal(0f, 0f, -1f);
		tess.addVertexWithUV(x + offset + w_off, y_min, z + offset + (n_off * 0.5f), U, V);
		tess.addVertexWithUV(x + offset + w_off, y_max, z + offset + lean + (n_off * 0.5f), U, v);
		tess.addVertexWithUV(x + 1f - offset - e_off, y_max, z + offset + lean + (n_off * 0.5f), u, v);
		tess.addVertexWithUV(x + 1f - offset - e_off, y_min, z + offset + (n_off * 0.5f), u, V);
		tess.draw();

		// east
		if (!tess.isDrawing)
			tess.startDrawingQuads();
		tess.setColorOpaque_F(1f, 1f, 1f);
		tess.setBrightness(15728880);
		tess.setNormal(1f, 0f, 0f);
		tess.addVertexWithUV(x + 1f - offset - (e_off * 0.5f), y_min, z + offset + n_off, U, V);
		tess.addVertexWithUV(x + 1f - offset - lean - (e_off * 0.5f), y_max, z + offset + n_off, U, v);
		tess.addVertexWithUV(x + 1f - offset - lean - (e_off * 0.5f), y_max, z + 1f - offset - s_off, u, v);
		tess.addVertexWithUV(x + 1f - offset - (e_off * 0.5f), y_min, z + 1f - offset - s_off, u, V);
		tess.draw();

		// west
		if (!tess.isDrawing)
			tess.startDrawingQuads();
		tess.setColorOpaque_F(1f, 1f, 1f);
		tess.setBrightness(15728880);
		tess.setNormal(-1f, 0f, 0f);
		tess.addVertexWithUV(x + offset + (w_off * 0.5f), y_min, z + 1f - offset - s_off, U, V);
		tess.addVertexWithUV(x + offset + lean + (w_off * 0.5f), y_max, z + 1f - offset - s_off, U, v);
		tess.addVertexWithUV(x + offset + lean + (w_off * 0.5f), y_max, z + offset + n_off, u, v);
		tess.addVertexWithUV(x + offset + (w_off * 0.5f), y_min, z + offset + n_off, u, V);
		tess.draw();

		if (!tess.isDrawing)
			tess.startDrawing(mode);

	return true;
	}

	public boolean renderRock(IBlockAccess world, int x, int y, int z, float x_offset, float z_offset, RenderBlocks renderer) {
		return renderRock(world, x, y, z, x_offset, z_offset, 0.2f, 0.2f, renderer); 
	}

	public boolean renderRock(IBlockAccess world, int x, int y, int z, float x_offset, float z_offset, float length, float width, RenderBlocks renderer) {
		renderer.setRenderBounds(x_offset, 0f, z_offset, x_offset + length, 0.1f, z_offset + width);
		renderer.renderStandardBlockWithAmbientOcclusion(Primitive.rock, x, y, z, 1f, 1f, 1f);
		return true;
	}

	public boolean renderCoals(IBlockAccess world, int x, int y, int z, float h, Block b, RenderBlocks renderer) {
		// int c = getDirectNeighborCount(world, x, y, z, b);
		// float c_xz = c * (0.5f / 16f);
		// float c_uv = c * 0.5f;
		if (h > 1f)
			h = 1f;
		else if (h < 0f)
			h = 0f;
		IIcon icon = Primitive.active_log_pile.getIcon(0, 0);
		float u = icon.getMinU();
		float v = icon.getMinV();
		float U = icon.getMaxU();
		float V = icon.getMaxV();

		float y_ = y + ((7f / 16f) * h);

		Tessellator tess = Tessellator.instance;

		int mode = tess.drawMode;

		if (tess.isDrawing)
			tess.draw();

		if (!tess.isDrawing)
			tess.startDrawingQuads();

		tess.setColorOpaque_F(1f, 1f, 1f);
		tess.setBrightness(15728880);
		tess.setNormal(0f, 1f, 0f);

		tess.addVertexWithUV(x + 1f, y_, z + 1f, U, V);
		tess.addVertexWithUV(x + 1f, y_, z, U, v);
		tess.addVertexWithUV(x, y_, z, u, v);
		tess.addVertexWithUV(x, y_, z + 1f, u, V);

		tess.draw();

		if (!tess.isDrawing)
			tess.startDrawing(mode);

		return true;
	}

	public boolean renderStick(IBlockAccess world, int x, int y, int z, float x_offset, float z_offset, int facing, Block b, RenderBlocks renderer) {
		IIcon side = Blocks.log.getIcon(2, 0);
		IIcon top = Blocks.log.getIcon(0, 0);
		float u, v, U, V;
		float w = 2f / 16f;
		float h = 10f / 16f;
		// float x_ = x + x_offset - (w * 0.5f);
		// float z_ = z + z_offset - (w * 0.5f);
		float x_ = x;
		float y_ = y;
		float z_ = z;

		int cx = x >> 4 << 4;
		int cy = y >> 4 << 4;
		int cz = z >> 4 << 4;

		float xt = cx - x - (w * 0.5f);
		float zt = cz - z - (w * 0.5f);
		float yt = cy - y;

		float angle = 25f;

		Tessellator tess = Tessellator.instance;

		int mode = tess.drawMode;

		if (tess.isDrawing)
			tess.draw();


		u = top.getMinU();
		v = top.getMinV();
		U = top.getMaxU();
		V = top.getMaxV();

		GL11.glPushMatrix();
		GL11.glTranslatef(x_offset - (w * 0.5f), 0f, z_offset - (w * 0.5f));
		GL11.glTranslatef(-xt, -yt, -zt);
		switch (facing) {
			case 0:
				GL11.glRotatef(angle, 1f, 0f, 0f);
				break;
			case 1:
				GL11.glRotatef(angle, 0f, 0f, 1f);
				break;
			case 2:
				GL11.glRotatef(-angle, 1f, 0f, 0f);
				break;
			case 3:
				GL11.glRotatef(-angle, 0f, 0f, 1f);
				break;

		}
		GL11.glTranslatef(xt, yt, zt);

		// bottom
		if (!tess.isDrawing)
			tess.startDrawingQuads();
		tess.setColorOpaque_F(0.5f, 0.5f, 0.5f);
		tess.setBrightness(b.getMixedBrightnessForBlock(world, x, y - 1, z));
		tess.setNormal(0f, -1f, 0f);
		tess.addVertexWithUV(x_, y_, z_,  U, V);
		tess.addVertexWithUV(x_ + w, y_, z_, U, v);
		tess.addVertexWithUV(x_ + w, y_, z_ + w, u, v);
		tess.addVertexWithUV(x_, y_, z_ + w, u, V);
		tess.draw();

		// top
		if (!tess.isDrawing)
			tess.startDrawingQuads();
		tess.setColorOpaque_F(1f, 1f, 1f);
		tess.setBrightness(b.getMixedBrightnessForBlock(world, x, y + 1, z));
		tess.setNormal(0f, 1f, 0f);
		tess.addVertexWithUV(x_, y_ + h, z_, U, V);
		tess.addVertexWithUV(x_, y_ + h, z_ + w, U, v);
		tess.addVertexWithUV(x_ + w, y_ + h, z_ + w, u, v);
		tess.addVertexWithUV(x_ + w, y_ + h, z_, u, V);
		tess.draw();

		u = side.getMinU();
		v = side.getMinV();
		U = side.getMaxU();
		V = side.getMaxV();

		// north
		if (!tess.isDrawing)
			tess.startDrawingQuads();
		tess.setColorOpaque_F(0.8f, 0.8f, 0.8f);
		tess.setBrightness(b.getMixedBrightnessForBlock(world, x, y, z - 1));
		tess.setNormal(0f, 0f, -1f);
		tess.addVertexWithUV(x_, y_, z_, U, V);
		tess.addVertexWithUV(x_, y_ + h, z_, U, v);
		tess.addVertexWithUV(x_ + w, y_ + h, z_, u, v);
		tess.addVertexWithUV(x_ + w, y_, z_, u, V);
		tess.draw();

		// south
		if (!tess.isDrawing)
			tess.startDrawingQuads();
		tess.setColorOpaque_F(0.8f, 0.8f, 0.8f);
		tess.setBrightness(b.getMixedBrightnessForBlock(world, x, y, z + 1));
		tess.setNormal(0f, 0f, 1f);
		tess.addVertexWithUV(x_ + w, y_, z_ + w, U, V);
		tess.addVertexWithUV(x_ + w, y_ + h, z_ + w, U, v);
		tess.addVertexWithUV(x_, y_ + h, z_ + w, u, v);
		tess.addVertexWithUV(x_, y_, z_ + w, u, V);
		tess.draw();

		// west
		if (!tess.isDrawing)
			tess.startDrawingQuads();
		tess.setColorOpaque_F(0.6f, 0.6f, 0.6f);
		tess.setBrightness(b.getMixedBrightnessForBlock(world, x - 1, y, z));
		tess.setNormal(-1f, 0f, 0f);
		tess.addVertexWithUV(x_, y_, z_ + w, U, V);
		tess.addVertexWithUV(x_, y_ + h, z_ + w, U, v);
		tess.addVertexWithUV(x_, y_ + h, z_, u, v);
		tess.addVertexWithUV(x_, y_, z_, u, V);
		tess.draw();

		// east
		if (!tess.isDrawing)
			tess.startDrawingQuads();
		tess.setColorOpaque_F(0.6f, 0.6f, 0.6f);
		tess.setBrightness(b.getMixedBrightnessForBlock(world, x + 1, y, z));
		tess.setNormal(1f, 0f, 0f);
		tess.addVertexWithUV(x_+ w, y_, z_, U, V);
		tess.addVertexWithUV(x_ + w, y_ + h, z_, U, v);
		tess.addVertexWithUV(x_ + w, y_ + h, z_ + w, u, v);
		tess.addVertexWithUV(x_ + w, y_, z_ + w, u, V);
		tess.draw();

		GL11.glPopMatrix();

		if (!tess.isDrawing)
			tess.startDrawing(mode);

		return true;
	}

	@Override
	public void renderInventoryBlock(Block b, int meta, int modelID, RenderBlocks renderer) {
		return;
	}

	@Override
	public int getRenderId() {
		return PrimitiveRenderers.rendererFirepit;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelID) {
		return false;
	}
}
