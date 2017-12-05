package azathoth.primitive.block;

import azathoth.primitive.Primitive;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import java.util.Random;

public class BlockDaub extends Block {
	public IIcon[] icons = new IIcon[4];
	public BlockDaub() {
		super(Material.rock);
		this.setStepSound(soundTypeStone);
		this.setHardness(1.2f);
	}

	@Override
	public void registerBlockIcons(IIconRegister r) {
		this.icons[0] = r.registerIcon(this.textureName);
		this.icons[1] = r.registerIcon(this.textureName + "_1");
		this.icons[2] = r.registerIcon(this.textureName + "_2");
		this.icons[3] = r.registerIcon(this.textureName + "_3");
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		if (meta > 3)
			meta = 3;
		return icons[meta];
	}

	@Override
	public int damageDropped(int meta) {
		return 0;
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random r) {
		int meta = world.getBlockMetadata(x, y, z) - 1;
		world.setBlock(x, y, z, (Block) this, meta, 3);
		if (meta > 0)
			world.scheduleBlockUpdate(x, y, z, (Block) this, 20 * 60);
	}
}
