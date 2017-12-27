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

public final class RenderCampfire implements ISimpleBlockRenderingHandler {
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block b, int modelID, RenderBlocks renderer) {
		float full = 0f;
		boolean lit = false;
		boolean used = false;

		this.renderRocks(world, x, y, z, b, renderer);

		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null && te instanceof FirepitTileEntity) {
			FirepitTileEntity pit = (FirepitTileEntity) te;
			full = (float) Math.ceil((double) ((float) pit.getFuel() / (float) pit.getMaxFuel()) * 4d) / 4f;
			lit = pit.isLit();
			used = pit.isUsed();
		}

		if (used)
			this.renderCoals(world, x, y, z, b, lit, renderer);

		float xt = (x >> 4 << 4) - x - 0.5f;
		float yt = (y >> 4 << 4) - y - 0.5f;
		float zt = (z >> 4 << 4) - z - 0.5f;

		if (full > 0f) {
			int c = getDirectNeighborCount(world, x, y, z, b);
			if (c == 4)
				c += getIndirectNeighborCount(world, x, y, z, b);

			GL11.glPushMatrix();
			GL11.glTranslatef(-xt, -yt, -zt);
			GL11.glTranslatef(0f, 0.25f * (c / 4f) + (0.5f * full) - 0.5f, 0f);
			GL11.glScalef((1f + 0.125f * c) * full, (1f + 0.125f * c) * full, (1f + 0.125f * c) * full);
			GL11.glRotatef(45f, 0f, 1f, 0f);
			GL11.glTranslatef(xt, yt, zt);

			this.renderSticks(world, x, y, z, b, renderer);

			if (lit)
				this.renderFire(world, x, y, z, b, renderer);
	
			GL11.glPopMatrix();
		}
		return true;
	}

	public boolean renderSticks(IBlockAccess world, int x, int y, int z, Block b, RenderBlocks renderer) {
		this.renderStick(world, x, y, z, 0.5f, 3f / 16f, 0, b, renderer);
		this.renderStick(world, x, y, z, 3f / 16f, 0.5f, 3, b, renderer);
		this.renderStick(world, x, y, z, 1f - (3f / 16f), 0.5f, 1, b, renderer);
		this.renderStick(world, x, y, z, 0.5f, 1f - (3f / 16f), 2, b, renderer);
		return true;
	}

	public boolean renderRocks(IBlockAccess world, int x, int y, int z, Block b, RenderBlocks renderer) {
		int c = getDirectNeighborCount(world, x, y, z, b);
		float cx = 0;
		float cz = 0;
		float s = 0.2f;
		float gap = 0.01f;

		if (c > 0) {
			cx = (0.5f * c) / 16f;
			cz = (0.5f * c) / 16f;
		}

		// south
		if (world.getBlock(x, y, z + 1) != Primitive.campfire) {
			this.renderRock(world, x, y, z, 1 * s - gap, 1f - s + cz, renderer);
			this.renderRock(world, x, y, z, 2 * s, 1f - s + (s * 0.25f) + cz, renderer);
			this.renderRock(world, x, y, z, 3 * s + gap, 1f - s + cz, renderer);
		} else {
			if (world.getBlock(x + 1, y, z) != Primitive.campfire && world.getBlock(x + 1, y, z + 1) != Primitive.campfire) {
				this.renderRock(world, x, y, z, 1f - s, 1f - s + 2 * gap, s, 2 * s - 4 * gap, renderer);
			} else if (world.getBlock(x + 1, y, z) != Primitive.campfire && world.getBlock(x + 1, y, z + 1) == Primitive.campfire) {
				this.renderRock(world, x, y, z, 1f - s, 1f - s + 2 * gap, s, s - 2 * gap, renderer);
			} else if (world.getBlock(x + 1, y, z + 1) != Primitive.campfire && world.getBlock(x + 1, y, z) == Primitive.campfire) {
				this.renderRock(world, x, y, z, 1f - s, 1f, s, s - 2 * gap, renderer);
			}
		}

		// north
		if (world.getBlock(x, y, z - 1) != Primitive.campfire) {
			this.renderRock(world, x, y, z, 1 * s - gap, 0f - cz, renderer);
			this.renderRock(world, x, y, z, 2 * s, 0f - (s * 0.25f) - cz, renderer);
			this.renderRock(world, x, y, z, 3 * s + gap, 0f - cz, renderer);
		} else {
			if (world.getBlock(x - 1, y, z) != Primitive.campfire && world.getBlock(x - 1, y, z - 1) != Primitive.campfire) {
				this.renderRock(world, x, y, z, 0f, -s + 2 * gap, s, 2 * s - 4 * gap, renderer);
			} else if (world.getBlock(x - 1, y, z) != Primitive.campfire && world.getBlock(x - 1, y, z - 1) == Primitive.campfire) {
				this.renderRock(world, x, y, z, 0f, 0f, s, s - 2 * gap, renderer);
			} else if (world.getBlock(x - 1, y, z - 1) != Primitive.campfire && world.getBlock(x - 1, y, z) == Primitive.campfire) {
				this.renderRock(world, x, y, z, 0f, -s + 2 * gap, s, s - 2 * gap, renderer);
			}
		}

		// west
		if (world.getBlock(x - 1, y, z) != Primitive.campfire) {
			this.renderRock(world, x, y, z, 0f - cx, 1 * s - gap, renderer);
			this.renderRock(world, x, y, z, 0f - (s * 0.25f) - cx, 2 * s, renderer);
			this.renderRock(world, x, y, z, 0f - cx, 3 * s + gap, renderer);
		} else {
			if (world.getBlock(x - 1, y, z + 1) != Primitive.campfire && world.getBlock(x, y, z + 1) != Primitive.campfire) {
				this.renderRock(world, x, y, z, -s + 2 * gap, 1f -s, 2 * s - 4 * gap, s, renderer);
			} else if (world.getBlock(x, y, z + 1) != Primitive.campfire && world.getBlock(x - 1, y, z + 1) == Primitive.campfire) {
				this.renderRock(world, x, y, z, 0f, 1f - s, s - 2 * gap, s, renderer);
			} else if (world.getBlock(x - 1, y, z + 1) != Primitive.campfire && world.getBlock(x, y, z + 1) == Primitive.campfire) {
				this.renderRock(world, x, y, z, -s + 2 * gap, 1f - s, s - 2 * gap, s, renderer);
			}
		}

		// east
		if (world.getBlock(x + 1, y, z) != Primitive.campfire) {
			this.renderRock(world, x, y, z, 1f - s + cx, 1 * s - gap, renderer);
			this.renderRock(world, x, y, z, 1f - s + (s * 0.25f) + cx, 2 * s, renderer);
			this.renderRock(world, x, y, z, 1f - s + cx, 3 * s + gap, renderer);
		} else {
			if (world.getBlock(x + 1, y, z - 1) != Primitive.campfire && world.getBlock(x, y, z - 1) != Primitive.campfire) {
				this.renderRock(world, x, y, z, 1f - s + 2 * gap, 0f, 2 * s - 4 * gap, s, renderer);
			} else if (world.getBlock(x, y, z - 1) != Primitive.campfire && world.getBlock(x + 1, y, z - 1) == Primitive.campfire) {
				this.renderRock(world, x, y, z, 1f - s + 2 * gap, 0f, s - 2 * gap, s, renderer);
			} else if (world.getBlock(x + 1, y, z - 1) != Primitive.campfire && world.getBlock(x, y, z - 1) == Primitive.campfire) {
				this.renderRock(world, x, y, z, 1f, 0f, s - 2 * gap, s, renderer);
			}
		}
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

	public boolean renderFire(IBlockAccess world, int x, int y, int z, Block b, RenderBlocks renderer) {
		IIcon icon = Blocks.fire.getIcon(0, 0);
		float u = icon.getMinU();
		float v = icon.getMinV();
		float U = icon.getMaxU();
		float V = icon.getMaxV();
		float offset = 4f / 16f;
		float lean = 3f / 16f;

		Tessellator tess = Tessellator.instance;

		int mode = tess.drawMode;

		if (tess.isDrawing)
			tess.draw();

		if (!tess.isDrawing)
			tess.startDrawingQuads();
		tess.setColorOpaque_F(1f, 1f, 1f);
		tess.setBrightness(15728880);
		tess.setNormal(0f, 0f, -1f);
		tess.addVertexWithUV(x + offset, y, z + offset, U, V);
		tess.addVertexWithUV(x + offset, y + 1f, z + offset + lean, U, v);
		tess.addVertexWithUV(x + 1f - offset, y + 1f, z + offset + lean, u, v);
		tess.addVertexWithUV(x + 1f - offset, y, z + offset, u, V);
		tess.draw();

		if (!tess.isDrawing)
			tess.startDrawingQuads();
		tess.setColorOpaque_F(1f, 1f, 1f);
		tess.setBrightness(15728880);
		tess.setNormal(0f, 0f, 1f);
		tess.addVertexWithUV(x + 1f - offset, y, z + 1f - offset, U, V);
		tess.addVertexWithUV(x + 1f - offset, y + 1f, z + 1f - offset - lean, U, v);
		tess.addVertexWithUV(x + offset, y + 1f, z + 1f - offset - lean, u, v);
		tess.addVertexWithUV(x + offset, y, z + 1f - offset, u, V);
		tess.draw();

		if (!tess.isDrawing)
			tess.startDrawingQuads();
		tess.setColorOpaque_F(1f, 1f, 1f);
		tess.setBrightness(15728880);
		tess.setNormal(-1f, 0f, 0f);
		tess.addVertexWithUV(x + offset, y, z + 1f - offset, U, V);
		tess.addVertexWithUV(x + offset + lean, y + 1f, z + 1f - offset, U, v);
		tess.addVertexWithUV(x + offset + lean, y + 1f, z + offset, u, v);
		tess.addVertexWithUV(x + offset, y, z + offset, u, V);
		tess.draw();

		if (!tess.isDrawing)
			tess.startDrawingQuads();
		tess.setColorOpaque_F(1f, 1f, 1f);
		tess.setBrightness(15728880);
		tess.setNormal(1f, 0f, 0f);
		tess.addVertexWithUV(x + 1f - offset, y, z + offset, U, V);
		tess.addVertexWithUV(x + 1f - offset - lean, y + 1f, z + offset, U, v);
		tess.addVertexWithUV(x + 1f - offset - lean, y + 1f, z + 1f - offset, u, v);
		tess.addVertexWithUV(x + 1f - offset, y, z + 1f - offset, u, V);
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

	public boolean renderCoals(IBlockAccess world, int x, int y, int z, Block b, boolean hot, RenderBlocks renderer) {
		int c = getDirectNeighborCount(world, x, y, z, b);
		float c_xz = c * (0.5f / 16f);
		float c_uv = c * 0.5f;
		IIcon icon;
		if (hot)
			icon = b.getIcon(0, 1);
		else
			icon = b.getIcon(0, 0);

		float _y = y + 0.001f;

		float[] s_0, s_1, n_0, n_1, e_0, e_1, w_0, w_1;

		// {x, z, u, v}
		// south
		s_0 = new float[] {x + (4f / 16f) - c_xz, z + (14f / 16f) + c_xz, icon.getInterpolatedU(12f + c_uv), icon.getInterpolatedV(2f - c_uv)};
		s_1 = new float[] {x + (12f / 16f) + c_xz, z + (14f / 16f) + c_xz, icon.getInterpolatedU(4f - c_uv), icon.getInterpolatedV(2f - c_uv)};

		// north
		n_0 = new float[] {x + (4f / 16f) - c_xz, z + (2f / 16f) - c_xz, icon.getInterpolatedU(12f + c_uv), icon.getInterpolatedV(14f + c_uv)};
		n_1 = new float[] {x + (12f / 16f) + c_xz, z + (2f / 16f) - c_xz, icon.getInterpolatedU(4 - c_uv), icon.getInterpolatedV(14 + c_uv)};

		// east
		e_0 = new float[] {x + (14f / 16f) + c_xz, z + (4f / 16f) - c_xz, icon.getInterpolatedU(2 - c_uv), icon.getInterpolatedV(12 + c_uv)};
		e_1 = new float[] {x + (14f / 16f) + c_xz, z + (12f / 16f) + c_xz, icon.getInterpolatedU(2 - c_uv), icon.getInterpolatedV(4 - c_uv)};

		// west
		w_0 = new float[] {x + (2f / 16f) - c_xz, z + (4f / 16f) - c_xz, icon.getInterpolatedU(14 + c_uv), icon.getInterpolatedV(12 + c_uv)};
		w_1 = new float[] {x + (2f / 16f) - c_xz, z + (12f / 16f) + c_xz, icon.getInterpolatedU(14 + c_uv), icon.getInterpolatedV(4 - c_uv)};

		// south
		if (world.getBlock(x, y, z + 1) == b) {
			s_0[0] = x + (2f / 16f) - c_xz;
			s_0[1] = z + 1f;
			s_0[2] = icon.getInterpolatedU(14f + c_uv);
			s_0[3] = icon.getMinV();
			s_1[0] = x + (14f / 16f) + c_xz;
			s_1[1] = z + 1f;
			s_1[2] = icon.getInterpolatedU(2f - c_uv);
			s_1[3] = icon.getMinV();
		}

		// north
		if (world.getBlock(x, y, z - 1) == b) {
			n_0[0] = x + (2f / 16f) - c_xz;
			n_0[1] = z;
			n_0[2] = icon.getInterpolatedU(14f + c_uv);
			n_0[3] = icon.getMaxV();
			n_1[0] = x + (14f / 16f) + c_xz;
			n_1[1] = z;
			n_1[2] = icon.getInterpolatedU(2f - c_uv);
			n_1[3] = icon.getMaxV();
		}

		// east
		if (world.getBlock(x + 1, y, z) == b) {
			e_0[0] = x + 1f;
			e_0[1] = z + (2f / 16f) - c_xz;
			e_0[2] = icon.getMinU();
			e_0[3] = icon.getInterpolatedV(14f + c_uv);
			e_1[0] = x + 1f;
			e_1[1] = z + (14f / 16f) + c_xz;
			e_1[2] = icon.getMinU();
			e_1[3] = icon.getInterpolatedV(2f - c_uv);
		}
		
		// west
		if (world.getBlock(x - 1, y, z) == b) {
			w_0[0] = x;
			w_0[1] = z + (2f / 16f) - c_xz;
			w_0[2] = icon.getMaxU();
			w_0[3] = icon.getInterpolatedV(14f + c_uv);
			w_1[0] = x;
			w_1[1] = z + (14f / 16f) + c_xz;
			w_1[2] = icon.getMaxU();
			w_1[3] = icon.getInterpolatedV(2f -c_uv);
		}

		Tessellator tess = Tessellator.instance;

		int mode = tess.drawMode;

		if (tess.isDrawing)
			tess.draw();

		if (!tess.isDrawing)
			tess.startDrawingQuads();

		tess.setColorOpaque_F(1f, 1f, 1f);
		tess.setBrightness(hot ? 15728880 : b.getMixedBrightnessForBlock(world, x, y + 1, z));
		tess.setNormal(0f, 1f, 0f);
		tess.addVertexWithUV(n_0[0], _y, n_0[1], n_0[2], n_0[3]);
		tess.addVertexWithUV(w_0[0], _y, w_0[1], w_0[2], w_0[3]);
		tess.addVertexWithUV(w_1[0], _y, w_1[1], w_1[2], w_1[3]);
		tess.addVertexWithUV(s_0[0], _y, s_0[1], s_0[2], s_0[3]);

		tess.addVertexWithUV(n_1[0], _y, n_1[1], n_1[2], n_1[3]);
		tess.addVertexWithUV(n_0[0], _y, n_0[1], n_0[2], n_0[3]);
		tess.addVertexWithUV(s_0[0], _y, s_0[1], s_0[2], s_0[3]);
		tess.addVertexWithUV(s_1[0], _y, s_1[1], s_1[2], s_1[3]);

		tess.addVertexWithUV(e_0[0], _y, e_0[1], e_0[2], e_0[3]);
		tess.addVertexWithUV(n_1[0], _y, n_1[1], n_1[2], n_1[3]);
		tess.addVertexWithUV(s_1[0], _y, s_1[1], s_1[2], s_1[3]);
		tess.addVertexWithUV(e_1[0], _y, e_1[1], e_1[2], e_1[3]);

		tess.draw();

		if (!tess.isDrawing)
			tess.startDrawing(mode);

		return true;
	}

	public boolean renderStick(IBlockAccess world, int x, int y, int z, float x_offset, float z_offset, int facing, Block b, RenderBlocks renderer) {
		IIcon side = Blocks.log.getIcon(2, 0);
		IIcon top = Blocks.log.getIcon(0, 0);
		// IIcon side = Primitive.active_log_pile.getIcon(2, 0);
		// IIcon top = Primitive.active_log_pile.getIcon(0, 0);
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
		return PrimitiveRenderers.rendererCampfire;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelID) {
		return false;
	}
}
