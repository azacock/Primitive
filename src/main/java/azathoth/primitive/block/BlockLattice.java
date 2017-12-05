package azathoth.primitive.block;

import azathoth.primitive.Primitive;
import azathoth.primitive.client.render.PrimitiveRenderers;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class BlockLattice extends BlockPane {
	protected IIcon[] icons = new IIcon[7];

	public BlockLattice() {
		super(Primitive.MODID + ":lattice", Primitive.MODID + ":lattice_side", Material.wood, true);
		this.setStepSound(soundTypeWood);
		this.setBlockName("lattice");
		this.setBlockTextureName(Primitive.MODID + ":lattice");
		this.setHardness(0.3f);
	}

	@Override
	public int getRenderType() {
		return PrimitiveRenderers.rendererWattle;
	}

	@Override
	public void registerBlockIcons(IIconRegister r) {
		this.icons[0] = r.registerIcon(this.textureName + "_0");
		this.icons[1] = r.registerIcon(this.textureName + "_1");
		this.icons[2] = r.registerIcon(this.textureName + "_2");
		this.icons[3] = r.registerIcon(this.textureName + "_3");
		this.icons[4] = r.registerIcon(this.textureName + "_side_0");
		this.icons[5] = r.registerIcon(this.textureName + "_side_1");
		this.icons[6] = r.registerIcon(this.textureName + "_side_2");
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		if (meta > 3)
			meta = 0;

		if (side == 2 || side == 0) {
			if (meta == 0)
				meta = 4;
			else if (meta == 1)
				meta = 5;
			else if (meta == 2)
				meta = 6;
			else if (meta == 3)
				meta = 5;
		}

		return this.icons[meta];
	}

	@Override
	public int damageDropped(int meta) {
		if (meta > 3)
			meta = 0;
		return meta;
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i <= 3; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}
}
