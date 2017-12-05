package azathoth.primitive.block;

import azathoth.primitive.Primitive;
import azathoth.primitive.item.PrimitiveItems;
import azathoth.primitive.client.render.PrimitiveRenderers;
import azathoth.primitive.tileentity.DryingBrickTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import java.util.Random;

public class BlockDryingBrick extends BlockContainer {
	protected IIcon icon;

	public BlockDryingBrick() {
		super(Material.clay);
		this.setBlockBounds(0, 0, 0, 1f, 2f / 16f, 1f);
		this.setBlockTextureName(Primitive.MODID + ":adobe_smooth");
		this.setHardness(0.3f);
	}

	@Override
	public void registerBlockIcons(IIconRegister r) {
		this.icon = r.registerIcon(this.textureName);
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return this.icon;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new DryingBrickTileEntity();
	}

	@Override
	public int getRenderType() {
		return PrimitiveRenderers.rendererDryingBrick;
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
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float hitx, float hity, float hitz) {
		ItemStack hand = player.getCurrentEquippedItem();

		if (hand != null && hand.getItem() != PrimitiveItems.brick && hand.getItemDamage() != 1)
			return false;

		if (((DryingBrickTileEntity) world.getTileEntity(x, y, z)).removeBrick()) {
			ItemStack brick = new ItemStack(PrimitiveItems.brick, 1, 1);
			player.inventory.addItemStackToInventory(brick);
			return true;
		}

		return false;
	}

	@Override
	public Item getItemDropped(int meta, Random r, int fortune) {
		return null;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block b, int side) {
		DryingBrickTileEntity te = (DryingBrickTileEntity) world.getTileEntity(x, y, z);
		if (te != null)
			te.dropBricks();
		super.breakBlock(world, x, y, z, b, side);
	}
}
