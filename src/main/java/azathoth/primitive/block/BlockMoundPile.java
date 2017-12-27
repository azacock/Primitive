package azathoth.primitive.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.Random;

/* meta states:
 * 0, 4, 8: log pile
 * 1, 5, 9: log pile activated with fire
 * 2, 6, 10: burning pile
 * 3, 7, 11: uncovered burning pile
 * 12: ash pile
 */

public class BlockMoundPile extends BlockLog {
	public IIcon[] icons = new IIcon[2];
	protected BlockMoundPileActive next;

	public BlockMoundPile() {
		super();
	}

	public void setNext(BlockMoundPileActive _next) {
		this.next = _next;
	}

	@Override
	public void registerBlockIcons(IIconRegister r) {
		this.icons[0] = r.registerIcon(this.textureName);
		this.icons[1] = r.registerIcon(this.textureName + "_side");
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		// log pile
		if (meta == 0 || meta == 1) {
			if (side == 0 || side == 1)
				return this.icons[0];
			else
				return this.icons[1];
		} else if (meta == 4 || meta == 5) {
			if (side == 4 || side == 5)
				return this.icons[0];
			else
				return this.icons[1];
		} else if (meta == 8 || meta == 9) {
			if (side == 2 || side == 3)
				return this.icons[0];
			else
				return this.icons[1];
		}
		return this.icons[0];
	}

	public Block getNext() {
		return this.next;
	}

	@Override
	public int damageDropped(int meta) {
		return 0;
	}

	public boolean isCovered(World world, int x, int y, int z) {
		return world.getBlock(x, y + 1, z).isOpaqueCube() && world.getBlock(x, y - 1, z).isOpaqueCube() && world.getBlock(x + 1, y, z).isOpaqueCube() && world.getBlock(x - 1, y, z).isOpaqueCube() && world.getBlock(x, y, z + 1).isOpaqueCube() && world.getBlock(x, y, z - 1).isOpaqueCube();
	}

	public boolean nearActivePile(World world, int x, int y, int z) {
		int meta;

		if (world.getBlock(x - 1, y, z) == this.next || world.getBlock(x - 1, y, z) == this.next.getNext()) {
			return true;
		}

		if (world.getBlock(x + 1, y, z) == this.next || world.getBlock(x + 1, y, z) == this.next.getNext()) {
			return true;
		}

		if (world.getBlock(x, y - 1, z) == this.next || world.getBlock(x, y - 1, z) == this.next.getNext()) {
			return true;
		}

		if (world.getBlock(x, y + 1, z) == this.next || world.getBlock(x, y + 1, z) == this.next.getNext()) {
			return true;
		}

		if (world.getBlock(x, y, z - 1) == this.next || world.getBlock(x, y, z - 1) == this.next.getNext()) {
			return true;
		}

		if (world.getBlock(x, y, z + 1) == this.next || world.getBlock(x, y, z + 1) == this.next.getNext()) {
			return true;
		}

		return false;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block b) {
		int meta = world.getBlockMetadata(x, y, z);
		if (meta == 0 || meta == 4 || meta == 8) {
			if (world.getBlock(x, y + 1, z) == Blocks.fire || this.nearActivePile(world, x, y, z)) {
				world.setBlock(x, y, z, this, meta + 1, 3);
				world.scheduleBlockUpdate(x, y, z, this, 20 * 3);
			}
		} else if (meta == 1 || meta == 5 || meta == 9) {
			// noop?
		}
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random r) {
		int meta = world.getBlockMetadata(x, y, z);
		if (meta == 1 || meta == 5 || meta == 9) {
			if (world.getBlock(x, y + 1, z) == Blocks.fire) {
				world.setBlock(x, y, z, Blocks.fire);
			} else if (this.nearActivePile(world, x, y, z)) {
				if (this.isCovered(world, x, y, z)) {
					world.setBlock(x, y, z, this.next, meta - 1, 3);
					world.scheduleBlockUpdate(x, y, z, this.next, 20 * 30);
				} else {
					world.setBlock(x, y, z, this.next.getNext(), meta - 1, 3);
					world.scheduleBlockUpdate(x, y, z, this.next.getNext(), 20 * 3);
				}
			} else if (this.isCovered(world, x, y, z)) {
				world.setBlock(x, y, z, this.next, meta - 1, 3);
				world.scheduleBlockUpdate(x, y, z, this.next, 20 * 30);
			} else {
				world.setBlock(x, y, z, this, meta - 1, 3);
			}
		}
	}
}
