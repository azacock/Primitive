package azathoth.primitive.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IIconRegister;
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

public class BlockMoundPileActive extends BlockLog {
	public IIcon[] icons = new IIcon[2];
	protected BlockMoundPileExposed next;
	protected Block result;

	public BlockMoundPileActive() {
		super();
		this.setLightLevel(9);
	}

	public Block getNext() {
		return this.next;
	}

	public void setNext(BlockMoundPileExposed _next) {
		this.next = _next;
	}

	public void setResult(Block _result) {
		this.result = _result;
	}

	@Override
	public void randomDisplayTick(World world, int _x, int _y, int _z, Random r) {
		float x = _x + 0.5F;
		float y = _y + 1.0F;
		float z = _z + 0.5F;

		for (int i = 0; i < 10; i++) {
			world.spawnParticle("smoke", (double) (x + r.nextFloat() - 0.5f), (double) y, (double) (z + r.nextFloat() - 0.5f), 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public void registerBlockIcons(IIconRegister r) {
		this.icons[0] = r.registerIcon(this.textureName);
		this.icons[1] = r.registerIcon(this.textureName + "_side");
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		// log pile
		if (meta == 0) {
			if (side == 0 || side == 1)
				return this.icons[0];
			else
				return this.icons[1];
		} else if (meta == 4) {
			if (side == 4 || side == 5)
				return this.icons[0];
			else
				return this.icons[1];
		} else if (meta == 8) {
			if (side == 2 || side == 3)
				return this.icons[0];
			else
				return this.icons[1];
		}
		return this.icons[0];
	}

	@Override
	public int damageDropped(int meta) {
		return 0;
	}

	public boolean isCovered(World world, int x, int y, int z) {
		return world.getBlock(x, y + 1, z).isOpaqueCube() && world.getBlock(x, y - 1, z).isOpaqueCube() && world.getBlock(x + 1, y, z).isOpaqueCube() && world.getBlock(x - 1, y, z).isOpaqueCube() && world.getBlock(x, y, z + 1).isOpaqueCube() && world.getBlock(x, y, z - 1).isOpaqueCube();
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block b) {
		int meta = world.getBlockMetadata(x, y, z);
		if (meta == 0 || meta == 4 || meta == 8) {
			if (!this.isCovered(world, x, y, z)) {
				world.setBlock(x, y, z, this.next, meta, 3);
				world.scheduleBlockUpdate(x, y, z, this.next, 20 * 3);
			}
		}
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random r) {
		int meta = world.getBlockMetadata(x, y, z);
		if (meta == 0 || meta == 4 || meta == 8) {
			world.setBlock(x, y, z, this.result, meta, 3);
		}
	}
}
