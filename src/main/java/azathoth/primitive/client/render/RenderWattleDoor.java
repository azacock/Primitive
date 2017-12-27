package azathoth.primitive.client.render;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;
import team.chisel.ctmlib.Drawing;

public class RenderWattleDoor implements ISimpleBlockRenderingHandler {

	public static int id;

	public RenderWattleDoor() {
		//id = RenderingRegistry.getNextAvailableRenderId();
	}

	@Override
	public void renderInventoryBlock(Block block, int meta, int modelID, RenderBlocks renderer) {
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		GL11.glScalef(1.25f, 1.25f, 1.25f);
		renderer.setRenderBounds(0.0f, 0.0f, 0.5f - 0.0625f, 1.0f, 1.0f, 0.5f + 0.0625f);
		Drawing.drawBlock(block, block.getIcon(1, meta), renderer);
	}

	class PaneRenderer {

		Tessellator tessellator;

		double i0u0;
		double i0uh;
		double i0u1;
		double i0v0;
		double i0v1;

		double i1u0;
		double i1u1;
		double i1v0;
		double i1vh;
		double i1v1;
		double i1v;

		double i2u0;
		double i2u1;
		double i2v0;
		double i2v1;
		double i2v;

		double x0;
		double xh;
		double x1;
		double xp0;
		double xp1;

		double z0;
		double zh;
		double z1;
		double zp0;
		double zp1;

		double y0;
		double y1;

		void set(double x, double y, double z, IIcon icon, IIcon icon1, IIcon icon2, int facing, int hinge, boolean open, boolean center) {
			tessellator = Tessellator.instance;

			i0u0 = icon.getMinU();
			i0uh = icon.getInterpolatedU(8.0D);
			i0u1 = icon.getMaxU();
			i0v0 = icon.getMinV();
			i0v1 = icon.getMaxV();

			i1u0 = icon1.getInterpolatedU(7.0D);
			i1u1 = icon1.getInterpolatedU(9.0D);
			i1v0 = icon1.getMinV();
			i1vh = icon1.getInterpolatedV(8.0D);
			i1v1 = icon1.getMaxV();
			i1v = i1v1 - i1v0;

			i2u0 = icon2.getInterpolatedU(7.0D);
			i2u1 = icon2.getInterpolatedU(9.0D);
			i2v0 = icon2.getMinV();
			i2v1 = icon2.getMaxV();
			i2v = i2v1 - i2v0;

			if ((facing == 1 && hinge == 0) || (facing == 3 && hinge == 1)) {
				x0 = x + 0.1;
				x1 = x + 1;
			} else if ((facing == 3 && hinge == 0) || (facing == 1 && hinge == 1)) {
				x0 = x;
				x1 = x + 0.9;
			} else {
				x0 = x + 0.01;
				x1 = x + 0.99;
			}

			if ((facing == 2 && hinge == 0) || (facing == 0 && hinge == 1)) {
				z0 = z + 0.1;
				z1 = z + 1;
			} else if ((facing == 0 && hinge == 0) || (facing == 2 && hinge == 1)) {
				z0 = z;
				z1 = z + 0.9;
			} else {
				z0 = z + 0.01;
				z1 = z + 0.99;
			}

			if (center) {
				if ((facing == 1 && !open) || (facing == 2 && open)) {
					z0 += 0.5d;
					z1 += 0.5d;
				} else if ((facing == 3 && !open) || (facing == 0 && open)) {
					z0 -= 0.5d;
					z1 -= 0.5d;
				} else if ((facing == 0 && !open) || (facing == 1 && open)) {
					x0 += 0.5d;
					x1 += 0.5d;

				} else if ((facing == 2 && !open) || (facing == 3 && open)) {
					x0 -= 0.5d;
					x1 -= 0.5d;
				}
			}

			if (facing == 0)
				xh = x0;
			else
				xh = x1;

			xp0 = x0 + 0.5D - 0.0625D;
			xp1 = x0 + 0.5D + 0.0625D;

			if (facing == 1)
				zh = z0;
			else
				zh = z1;

			zp0 = z0 + 0.5D - 0.0625D;
			zp1 = z0 + 0.5D + 0.0625D;

			y0 = y;
			y1 = y + 1;
		}

		public void renderNorthPane() {
			tessellator.addVertexWithUV(xh, y1, z0, i0uh, i0v0);
			tessellator.addVertexWithUV(xh, y0, z0, i0uh, i0v1);
			tessellator.addVertexWithUV(xh, y0, zh, i0u0, i0v1);
			tessellator.addVertexWithUV(xh, y1, zh, i0u0, i0v0);
			tessellator.addVertexWithUV(xh, y1, zh, i0u0, i0v0);
			tessellator.addVertexWithUV(xh, y0, zh, i0u0, i0v1);
			tessellator.addVertexWithUV(xh, y0, z0, i0uh, i0v1);
			tessellator.addVertexWithUV(xh, y1, z0, i0uh, i0v0);
		}

		public void renderSouthPane() {
			tessellator.addVertexWithUV(xh, y1, zh, i0u1, i0v0);
			tessellator.addVertexWithUV(xh, y0, zh, i0u1, i0v1);
			tessellator.addVertexWithUV(xh, y0, z1, i0uh, i0v1);
			tessellator.addVertexWithUV(xh, y1, z1, i0uh, i0v0);
			tessellator.addVertexWithUV(xh, y1, z1, i0uh, i0v0);
			tessellator.addVertexWithUV(xh, y0, z1, i0uh, i0v1);
			tessellator.addVertexWithUV(xh, y0, zh, i0u1, i0v1);
			tessellator.addVertexWithUV(xh, y1, zh, i0u1, i0v0);
		}

		public void renderWestPane() {
			tessellator.addVertexWithUV(x0, y1, zh, i0uh, i0v0);
			tessellator.addVertexWithUV(x0, y0, zh, i0uh, i0v1);
			tessellator.addVertexWithUV(xh, y0, zh, i0u0, i0v1);
			tessellator.addVertexWithUV(xh, y1, zh, i0u0, i0v0);
			tessellator.addVertexWithUV(xh, y1, zh, i0u0, i0v0);
			tessellator.addVertexWithUV(xh, y0, zh, i0u0, i0v1);
			tessellator.addVertexWithUV(x0, y0, zh, i0uh, i0v1);
			tessellator.addVertexWithUV(x0, y1, zh, i0uh, i0v0);
		}

		public void renderEastPane() {
			tessellator.addVertexWithUV(xh, y1, zh, i0u1, i0v0);
			tessellator.addVertexWithUV(xh, y0, zh, i0u1, i0v1);
			tessellator.addVertexWithUV(x1, y0, zh, i0uh, i0v1);
			tessellator.addVertexWithUV(x1, y1, zh, i0uh, i0v0);
			tessellator.addVertexWithUV(x1, y1, zh, i0uh, i0v0);
			tessellator.addVertexWithUV(x1, y0, zh, i0uh, i0v1);
			tessellator.addVertexWithUV(xh, y0, zh, i0u1, i0v1);
			tessellator.addVertexWithUV(xh, y1, zh, i0u1, i0v0);
		}

		public void renderVerticalNS(double y, double zoff0, double zoff1, double v0, double v1) {
			tessellator.addVertexWithUV(xp0, y0 + y, z0 + zoff0, i1u0, i1v0 + i1v * v0);
			tessellator.addVertexWithUV(xp0, y0 + y, z0 + zoff1, i1u0, i1v0 + i1v * v1);
			tessellator.addVertexWithUV(xp1, y0 + y, z0 + zoff1, i1u1, i1v0 + i1v * v1);
			tessellator.addVertexWithUV(xp1, y0 + y, z0 + zoff0, i1u1, i1v0 + i1v * v0);
			tessellator.addVertexWithUV(xp0, y0 + y, z0 + zoff1, i1u0, i1v0 + i1v * v1);
			tessellator.addVertexWithUV(xp0, y0 + y, z0 + zoff0, i1u0, i1v0 + i1v * v0);
			tessellator.addVertexWithUV(xp1, y0 + y, z0 + zoff0, i1u1, i1v0 + i1v * v0);
			tessellator.addVertexWithUV(xp1, y0 + y, z0 + zoff1, i1u1, i1v0 + i1v * v1);
		}

		public void renderVerticalWE(double y, double xoff0, double xoff1, double v0, double v1) {
			tessellator.addVertexWithUV(x0 + xoff0, y0 + y, zp1, i1u1, i1v0 + i1v * v0);
			tessellator.addVertexWithUV(x0 + xoff1, y0 + y, zp1, i1u1, i1v0 + i1v * v1);
			tessellator.addVertexWithUV(x0 + xoff1, y0 + y, zp0, i1u0, i1v0 + i1v * v1);
			tessellator.addVertexWithUV(x0 + xoff0, y0 + y, zp0, i1u0, i1v0 + i1v * v0);
			tessellator.addVertexWithUV(x0 + xoff1, y0 + y, zp1, i1u1, i1v0 + i1v * v1);
			tessellator.addVertexWithUV(x0 + xoff0, y0 + y, zp1, i1u1, i1v0 + i1v * v0);
			tessellator.addVertexWithUV(x0 + xoff0, y0 + y, zp0, i1u0, i1v0 + i1v * v0);
			tessellator.addVertexWithUV(x0 + xoff1, y0 + y, zp0, i1u0, i1v0 + i1v * v1);
		}

		public void renderHorizontalNS(double zoff, double v0, double v1) {
			tessellator.addVertexWithUV(xp0, y1, z0 + zoff, i2u0, i2v0 + i2v * v1);
			tessellator.addVertexWithUV(xp0, y0, z0 + zoff, i2u0, i2v0 + i2v * v0);
			tessellator.addVertexWithUV(xp1, y0, z0 + zoff, i2u1, i2v0 + i2v * v0);
			tessellator.addVertexWithUV(xp1, y1, z0 + zoff, i2u1, i2v0 + i2v * v1);
			tessellator.addVertexWithUV(xp1, y1, z0 + zoff, i2u1, i2v0 + i2v * v1);
			tessellator.addVertexWithUV(xp1, y0, z0 + zoff, i2u1, i2v0 + i2v * v0);
			tessellator.addVertexWithUV(xp0, y0, z0 + zoff, i2u0, i2v0 + i2v * v0);
			tessellator.addVertexWithUV(xp0, y1, z0 + zoff, i2u0, i2v0 + i2v * v1);

		}

		public void renderHorizontalWE(double xoff, double v0, double v1) {
			tessellator.addVertexWithUV(x0 + xoff, y1, zp0, i2u0, i2v0 + i2v * v1);
			tessellator.addVertexWithUV(x0 + xoff, y0, zp0, i2u0, i2v0 + i2v * v0);
			tessellator.addVertexWithUV(x0 + xoff, y0, zp1, i2u1, i2v0 + i2v * v0);
			tessellator.addVertexWithUV(x0 + xoff, y1, zp1, i2u1, i2v0 + i2v * v1);
			tessellator.addVertexWithUV(x0 + xoff, y1, zp1, i2u1, i2v0 + i2v * v1);
			tessellator.addVertexWithUV(x0 + xoff, y0, zp1, i2u1, i2v0 + i2v * v0);
			tessellator.addVertexWithUV(x0 + xoff, y0, zp0, i2u0, i2v0 + i2v * v0);
			tessellator.addVertexWithUV(x0 + xoff, y1, zp0, i2u0, i2v0 + i2v * v1);
		}
	}

	PaneRenderer paneRenderer = new PaneRenderer();

	public boolean shouldCenter(IBlockAccess world, int x, int y, int z, int facing) {
		int meta = world.getBlockMetadata(x, y, z);
		boolean center = false;

		if (facing == 0 || facing == 2) {
			center = world.getBlock(x, y, z - 1) instanceof BlockPane || world.getBlock(x, y, z + 1) instanceof BlockPane;
			if ((meta & 8) == 8) {
				center = center || world.getBlock(x, y - 1, z - 1) instanceof BlockPane || world.getBlock(x, y - 1, z + 1) instanceof BlockPane;
			} else {
				center = center || world.getBlock(x, y + 1, z - 1) instanceof BlockPane || world.getBlock(x, y + 1, z + 1) instanceof BlockPane;
			}
		} else {
			center = world.getBlock(x - 1, y, z) instanceof BlockPane || world.getBlock(x + 1, y, z) instanceof BlockPane;
			if ((meta & 8) == 8) {
				center = center || world.getBlock(x - 1, y - 1, z) instanceof BlockPane || world.getBlock(x + 1, y - 1, z) instanceof BlockPane;
			} else {
				center = center || world.getBlock(x - 1, y + 1, z) instanceof BlockPane || world.getBlock(x + 1, y + 1, z) instanceof BlockPane;
			}
		}

		return center;
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block b, int modelId, RenderBlocks renderer) {
		Block block = (Block) b;
		Tessellator tessellator = Tessellator.instance;

		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));

		float f = 1.0F;
		int i1 = block.colorMultiplier(world, x, y, z);
		float f1 = (i1 >> 16 & 255) / 255.0F;
		float f2 = (i1 >> 8 & 255) / 255.0F;
		float f3 = (i1 & 255) / 255.0F;

		if (EntityRenderer.anaglyphEnable) {
			float f4 = (f1 * 30.0F + f2 * 59.0F + f3 * 11.0F) / 100.0F;
			float f5 = (f1 * 30.0F + f2 * 70.0F) / 100.0F;
			float f6 = (f1 * 30.0F + f3 * 70.0F) / 100.0F;
			f1 = f4;
			f2 = f5;
			f3 = f6;
		}
		tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);

		int meta = world.getBlockMetadata(x, y, z);
		IIcon iconPane = block.getIcon(1, meta);
		IIcon iconTop = block.getIcon(0, meta);
		IIcon iconSide = block.getIcon(2, meta);
		if (iconPane == null || iconTop == null || iconSide == null)
			return false;

		boolean open;
		boolean center;
		int hinge; // 0 == right, 1 == left
		int facing; // 0 == east, 1 == south, 2 == west, 3 == north
		int top = meta & 8; // 0 == bottom, 8 == top
		if (top == 8) {
			hinge = meta & 1;
			open = (world.getBlockMetadata(x, y - 1, z) & 4) == 4;
			facing = world.getBlockMetadata(x, y - 1, z) & 3;
		} else {
			hinge = world.getBlockMetadata(x, y + 1, z) & 1;
			open = (meta & 4) == 4;
			facing = meta & 3;
		}
		center = shouldCenter(world, x, y, z, facing);

		if (open) {
			if (hinge == 0) {
				switch (facing) {
					case 0:
						facing = 1;
						break;
					case 1:
						facing = 2;
						break;
					case 2:
						facing = 3;
						break;
					case 3:
						facing = 0;
						break;
				}
				hinge = 1;
			} else if (hinge == 1) {
				switch (facing) {
					case 0:
						facing = 3;
						break;
					case 1:
						facing = 0;
						break;
					case 2:
						facing = 1;
						break;
					case 3:
						facing = 2;
						break;
				}
				hinge = 0;
			}
		}

		if (renderer.hasOverrideBlockTexture()) {
			paneRenderer.set(x, y, z, renderer.overrideBlockTexture, renderer.overrideBlockTexture, renderer.overrideBlockTexture, facing, hinge, open, center);
		} else {
			paneRenderer.set(x, y, z, iconPane, iconTop, iconSide, facing, hinge, open, center);
		}

		if (facing == 0) {
			paneRenderer.renderNorthPane();
			paneRenderer.renderSouthPane();
		} else if (facing == 1) {
			paneRenderer.renderEastPane();
			paneRenderer.renderWestPane();
		} else if (facing == 2) {
			paneRenderer.renderNorthPane();
			paneRenderer.renderSouthPane();
		} else if (facing == 3) {
			paneRenderer.renderEastPane();
			paneRenderer.renderWestPane();
		}

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int renderId) {
		return false;
	}

	@Override
	public int getRenderId() {
		return id;
	}
}
