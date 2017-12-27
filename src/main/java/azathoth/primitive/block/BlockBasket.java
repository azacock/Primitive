package azathoth.primitive.block;

import azathoth.primitive.Primitive;
import azathoth.primitive.client.render.PrimitiveRenderers;
import azathoth.primitive.network.PrimitiveGuiHandler;
import azathoth.primitive.tileentity.BasketTileEntity;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.ArrayList;

public class BlockBasket extends BlockContainer {
	public BlockBasket() {
		super(Material.plants);
		this.setHardness(0.3f);
		this.setBlockBounds(0.25f, 0f, 0.25f, 0.75f, 0.8f, 0.75f);
		this.setBlockTextureName(Primitive.MODID + ":wattle");
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		if (side == 0 || side == 1)
			return Primitive.wattle.getIcon(1, 3);
		else
			return Primitive.wattle.getIcon(1, 2);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new BasketTileEntity();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float hitx, float hity, float hitz) {
		if (!world.isRemote) {
			player.openGui(Primitive.instance, PrimitiveGuiHandler.BASKET_TILE_ENTITY_GUI, world, x, y, z);
		}
		return true;
	}

	@Override
	public int getRenderType() {
		return PrimitiveRenderers.rendererBasket;
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
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune) {
		return null;
	}
}
