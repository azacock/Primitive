package azathoth.primitive.block;

import azathoth.primitive.client.render.PrimitiveRenderers;
import azathoth.primitive.item.PrimitiveItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import java.util.Random;

public class BlockRock extends Block {
	public BlockRock() {
		super(Material.ground);
		this.setStepSound(soundTypeStone);
		this.setHardness(0.5f);
		this.setBlockBounds(0.4f, 0f, 0.4f, 0.6f, 0.1f, 0.6f);
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return Blocks.stone.getIcon(side, meta);
	}

	@Override
	public int getRenderType() {
		return PrimitiveRenderers.rendererRock;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public Item getItemDropped(int meta, Random r, int fortune) {
		return PrimitiveItems.brick;
	}

	@Override
	public int damageDropped(int i) {
		return 2;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		int p0 = 277;
		int p1 = 523;
		int p2 = 859;
		int p3 = 941;
		int _x = (x <= 0) ? 0 - x : x;
		int _z = (z <= 0) ? 0 - z : z;
		int xh = ((_x * p0 + _z) * p1 + y) % 5;
		int zh = ((_x * p2 + _z) * p3 + y) % 5;
		this.setBlockBounds(0.2f * xh, 0, 0.2f * zh, (0.2f * xh) + 0.2f, 0.1f, (0.2f * zh) + 0.2f);
	}

}
