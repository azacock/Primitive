package azathoth.primitive.block;

import azathoth.primitive.client.render.PrimitiveRenderers;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class BlockPathSlab extends Block {
	public BlockPathSlab() {
		super(Material.rock);
		this.setBlockBounds(0f, -1f / 16f, 0f, 1f, 7f / 16f, 1f);
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		meta = meta & 7;
		switch (meta) {
			case 0:
				return Blocks.dirt.getIcon(side, 0);
			case 1:
				return Blocks.cobblestone.getIcon(side, 0);
			case 2:
				return Blocks.gravel.getIcon(side, 0);
			default:
				return Blocks.cobblestone.getIcon(side, 0);
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		super.onBlockPlacedBy(world, x, y, z, entity, stack);
		int meta = world.getBlockMetadata(x, y, z);
		int facing = MathHelper.floor_double((double)((entity.rotationYaw * 4F) / 360F) + 0.5D) & 3;
		if (facing == 1 || facing == 3) {
			world.setBlockMetadataWithNotify(x, y, z, meta | 8, 3);
		}
	}

	@Override
	public int damageDropped(int meta) {
		return meta & 7;
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < 3; i++)
			list.add(new ItemStack(item, 1, i));
	}

	@Override
	public int getRenderType() {
		return PrimitiveRenderers.rendererPathSlab;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
}
