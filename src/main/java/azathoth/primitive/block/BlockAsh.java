package azathoth.primitive.block;

import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

import java.util.Random;

public class BlockAsh extends BlockLog {
	public IIcon[] icons = new IIcon[2];

	protected Item yield;
	protected int yield_meta;
	protected int min;
	protected int max;

	public BlockAsh(Item item, int meta, int min, int max) {
		super();
		this.setStepSound(soundTypeSand);
		this.setHardness(0.5f);
		this.yield = item;
		this.yield_meta = meta;
		this.min = min;
		this.max = max;
	}

	@Override
	public void registerBlockIcons(IIconRegister r) {
		this.icons[0] = r.registerIcon(this.textureName);
		this.icons[1] = r.registerIcon(this.textureName + "_side");
	}

	@Override
	public IIcon getIcon(int side, int meta) {
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

	@Override
	public Item getItemDropped(int meta, Random r, int fortune) {
		return this.yield;
	}

	@Override
	public int damageDropped(int meta) {
		return this.yield_meta;
	}

	@Override
	public int quantityDropped(int meta, int fortune, Random r) {
		return this.min + r.nextInt(this.max - this.min + 1);
	}
}
