package azathoth.primitive.block;

import azathoth.primitive.Primitive;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class BlockStuccoPainted extends Block {
	public IIcon[] icons = new IIcon[17];
	
	public BlockStuccoPainted() {
		super(Material.rock);
		this.setHardness(0.6f);
	}

	@Override
	public void registerBlockIcons(IIconRegister r) {
		for (int i = 0; i < 16; i++) {
			this.icons[i] = r.registerIcon(this.textureName + "_" + i);
		}
		this.icons[16] = r.registerIcon(Primitive.MODID + ":stucco");
	}

	@Override
	public IIcon getIcon (int side, int meta) {
		if (side <= 1)
			return icons[16];

		if (meta > 16)
			meta = 16;

		return icons[meta];
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < 16; i++)
			list.add(new ItemStack(item, 1, i));
	}
}
