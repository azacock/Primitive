package azathoth.primitive.block;

import azathoth.primitive.Primitive;
import azathoth.primitive.client.render.PrimitiveRenderers;
import azathoth.primitive.item.PrimitiveItems;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import java.util.Random;

public class BlockWattleDoor extends BlockDoor {
	public IIcon[] icons = new IIcon[2];

	public BlockWattleDoor() {
		super(Material.plants);
		this.setBlockTextureName(Primitive.MODID + ":wattle");
	}

	@Override
	public Item getItemDropped(int meta, Random r, int fortune) {
		return PrimitiveItems.wattle_door;
	}

	@Override
	public int getRenderType() {
		return PrimitiveRenderers.rendererWattleDoor;
	}

	@Override
	public void registerBlockIcons(IIconRegister r) {
		this.icons[0] = r.registerIcon(Primitive.MODID + ":wattle_1");
		this.icons[1] = r.registerIcon(Primitive.MODID + ":wattle_2");
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return this.icons[(meta & 8) >> 3];
	}

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
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		boolean top = (meta & 8) == 8;
		boolean open;
		boolean center;
		int hinge;
		int facing;
		float off = 0.01f;
		float xoff = 0f;
		float zoff = 0f;
		float dl = 0.01f;
		float dw = 0.9f;
		float dh = 2.0f;
		float miny;

		if (top) {
			hinge = meta & 1;
			facing = world.getBlockMetadata(x, y - 1, z) & 3;
			open = (world.getBlockMetadata(x, y - 1, z) & 4) == 4;
			miny = -1f;
		} else {
			hinge = world.getBlockMetadata(x, y + 1, z) & 1;
			facing = meta & 3;
			open = (meta & 4) == 4;
			miny = 0f;
		}

		center = shouldCenter(world, x, y, z, facing);


		if (center) {
			if (!open) {
				switch (facing) {
					case 0:
						xoff = 0.5f;
						break;
					case 1:
						zoff = 0.5f;
						break;
					case 2:
						xoff = 0.5f;
						break;
					case 3:
						zoff = 0.5f;
						break;
					default:
						this.setBlockBounds(0f, 0f, 0f, 1f, 2f, 1f);
						return;
				}
			} else {
				switch (facing) {
					case 0:
						xoff = 0.5f;
						break;
					case 1:
						zoff = 0.5f;
						break;
					case 2:
						xoff = 0.5f;
						break;
					case 3:
						zoff = 0.5f;
						break;
					default:
						this.setBlockBounds(0f, 0f, 0f, 1f, 2f, 1f);
						return;
				}
			}
		}

		if (open) {
			if (hinge == 0) {
				facing = (facing + 1) % 4;
				hinge = 1;
			} else {
				facing = (((facing - 1) % 4) + 4) % 4;
				hinge = 0;
			}
		}

		switch (facing) {
			case 0:
				if (hinge == 0)
					this.setBlockBounds(off + xoff, miny, 0f - zoff, dl - off + xoff, miny + dh, dw - zoff);
				else
					this.setBlockBounds(off + xoff, miny, 1f - dw - zoff, dl - off + xoff, miny + dh, 1f - zoff);
				return;
			case 1:
				if (hinge == 0)
					this.setBlockBounds(1f - dw + xoff, miny, off + zoff, 1f + xoff, miny + dh, dl - off + zoff);
				else
					this.setBlockBounds(xoff, miny, off + zoff, dw + xoff, miny + dh, dl - off + zoff);
				return;
			case 2:
				if (hinge == 0)
					this.setBlockBounds(1f - dl - off - xoff, miny, 1f - dw + zoff, 1f - off - xoff, miny + dh, 1f + zoff);
				else
					this.setBlockBounds(1f - dl - off - xoff, miny, 0f + zoff, 1f - off - xoff, miny + dh, dw + zoff);
				return;
			case 3:
				if (hinge == 0)
					this.setBlockBounds(0f - xoff, miny, 1f - off - zoff, dw - xoff, miny + dh, 1f - dl - off - zoff);
				else
					this.setBlockBounds(1f - dw - xoff, miny, 1f - dl - off - zoff, 1f - xoff, miny + dh, 1f - off - zoff);
				return;
			default:
				this.setBlockBounds(0f, 0f, 0f, 1f, 2f, 1f);
				return;
		}

	}
}
