package azathoth.primitive.block;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.Random;

/* meta states:
 * 0, 4, 8: log pile
 * 1, 5, 9: log pile activated with fire
 * 2, 6, 10: burning pile
 * 3, 7, 11: uncovered burning pile
 * 12: ash pile
 */

public class BlockMoundPileExposed extends BlockMoundPileActive {
	protected BlockMoundPileActive prev;

	public BlockMoundPileExposed() {
		super();
	}

	public void setPrevious(BlockMoundPileActive _prev) {
		this.prev = _prev;
	}

	public boolean isCovered(World world, int x, int y, int z) {
		return world.getBlock(x, y + 1, z).isOpaqueCube() && world.getBlock(x, y - 1, z).isOpaqueCube() && world.getBlock(x + 1, y, z).isOpaqueCube() && world.getBlock(x - 1, y, z).isOpaqueCube() && world.getBlock(x, y, z + 1).isOpaqueCube() && world.getBlock(x, y, z - 1).isOpaqueCube();
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block b) {
		// noop
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random r) {
		int meta = world.getBlockMetadata(x, y, z);
		if (meta == 0 || meta == 4 || meta == 8) {
			if (this.isCovered(world, x, y, z)) {
				world.setBlock(x, y, z, this.prev, meta, 3);
				world.scheduleBlockUpdate(x, y, z, this.prev, 20 * 30);
			} else {
				world.setBlock(x, y, z, Blocks.fire);
			}
		}
	}
}
