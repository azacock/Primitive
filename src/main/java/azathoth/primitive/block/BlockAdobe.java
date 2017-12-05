package azathoth.primitive.block;

import azathoth.primitive.Primitive;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockAdobe extends Block {
	public IIcon[] icons = new IIcon[3];
	public BlockAdobe () {
		super(Material.rock);
		this.setHardness(1.2f);
	}

	@Override
	public void registerBlockIcons(IIconRegister r) {
		this.icons[0] = r.registerIcon(Primitive.MODID + ":adobe_bricks");
		this.icons[1] = r.registerIcon(Primitive.MODID + ":adobe_smooth");
		this.icons[2] = r.registerIcon(Primitive.MODID + ":adobe_bricks_test");
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		if (meta > 2)
			meta = 0;
		if (meta == 2 && (side == 0 || side == 1))
			meta = 1;
		return icons[meta];
	}

	@Override
	public int damageDropped(int meta) {
		if (meta > 2)
			return 0;
		return meta;
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < 3; i++)
			list.add(new ItemStack(item, 1, i));
	}

}
