package azathoth.primitive.block;

import azathoth.primitive.Primitive;
import azathoth.primitive.item.PrimitiveItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BlockAdobe extends Block {
	public IIcon[] icons = new IIcon[4];
	public BlockAdobe () {
		super(Material.rock);
		this.setHardness(1.2f);
	}

	@Override
	public void registerBlockIcons(IIconRegister r) {
		this.icons[0] = r.registerIcon(Primitive.MODID + ":adobe_bricks");
		this.icons[1] = r.registerIcon(Primitive.MODID + ":adobe_smooth");
		this.icons[2] = r.registerIcon(Primitive.MODID + ":adobe_bricks_test");
		this.icons[3] = r.registerIcon(Primitive.MODID + ":stucco");
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		if (meta > 3)
			meta = 0;
		if (meta == 2 && (side == 0 || side == 1))
			meta = 1;
		return icons[meta];
	}

	@Override
	public int damageDropped(int meta) {
		if (meta > 3)
			return 0;
		return meta;
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < 4; i++)
			list.add(new ItemStack(item, 1, i));
	}

	@Override
	public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player) {
		if (meta > 2)
			return;

		if (!world.isRemote) {
			ItemStack i = player.getCurrentEquippedItem();

			if (i == null || !i.getItem().getToolClasses(i).contains("pickaxe")) {
				EntityItem e;
				int r = ThreadLocalRandom.current().nextInt(1, 5);
				for (int j = 0; j < r; j++) {
					e = new EntityItem(world, x, y, z, new ItemStack(PrimitiveItems.brick, 1, 1));
					world.spawnEntityInWorld(e);
				}
			}
		}
	}

}
