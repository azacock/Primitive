package azathoth.primitive.block;

import azathoth.primitive.Primitive;
import azathoth.primitive.client.render.PrimitiveRenderers;
import azathoth.primitive.tileentity.FirepitTileEntity;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockCampfire extends BlockContainer {
	public IIcon[] icons = new IIcon[2];

	public BlockCampfire() {
		super(Material.wood);
		this.setHardness(0.3f);
		this.setBlockTextureName(Primitive.MODID + ":coals");
	}

	@Override
	public void registerBlockIcons(IIconRegister r) {
		this.icons[0] = r.registerIcon(Primitive.MODID + ":coals");
		this.icons[1] = r.registerIcon(Primitive.MODID + ":hot_coals");
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		if (meta > 1)
			meta = 0;
		return icons[meta];
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null && te instanceof FirepitTileEntity && ((FirepitTileEntity) te).isLit()) {
			FirepitTileEntity pit = (FirepitTileEntity) te;
			float full = (float) Math.ceil((double) ((float) pit.getFuel() / (float) pit.getMaxFuel()) * 4d) / 4f;
			return 4 + (int) (8 * full);
		}
		return 0;
	}

	@Override
	public void randomDisplayTick(World world, int _x, int _y, int _z, Random r) {
		float x = _x + 0.5F;
		float y = _y + 0.5F;
		float z = _z + 0.5F;

		TileEntity te = world.getTileEntity(_x, _y, _z);
		if (te == null || !(te instanceof FirepitTileEntity) || !((FirepitTileEntity) te).isLit())
			return;

		for (int i = 0; i < 3; i++) {
			world.spawnParticle("smoke", (double) (x + (r.nextFloat() - 0.5f) * 0.75f), (double) (y + r.nextFloat() - 0.5f), (double) (z + (r.nextFloat() - 0.5f) * 0.75f), 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		FirepitTileEntity f = new FirepitTileEntity();
		f.setMaxFuel(12800);
		return f;
	}

	public boolean shouldIgnite(World world, int x, int y, int z) {
		TileEntity te;
		FirepitTileEntity pit;

		te = world.getTileEntity(x, y, z + 1);
		if (te != null && te instanceof FirepitTileEntity) {
			pit = (FirepitTileEntity) te;
			if (pit.isLit())
				return true;
		}

		te = world.getTileEntity(x, y, z - 1);
		if (te != null && te instanceof FirepitTileEntity) {
			pit = (FirepitTileEntity) te;
			if (pit.isLit())
				return true;
		}

		te = world.getTileEntity(x + 1, y, z);
		if (te != null && te instanceof FirepitTileEntity) {
			pit = (FirepitTileEntity) te;
			if (pit.isLit())
				return true;
		}

		te = world.getTileEntity(x - 1, y, z);
		if (te != null && te instanceof FirepitTileEntity) {
			pit = (FirepitTileEntity) te;
			if (pit.isLit())
				return true;
		}

		return false;
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null && te instanceof FirepitTileEntity) {
			FirepitTileEntity pit = (FirepitTileEntity) te;
			pit.equalize();
			if (this.shouldIgnite(world, x, y, z))
				pit.ignite();
			pit.markBlockForUpdate();
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float hitx, float hity, float hitz) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null && te instanceof FirepitTileEntity) {
			FirepitTileEntity pit = (FirepitTileEntity) te;
			return pit.addFuelFromItem(player.getCurrentEquippedItem());
		}
		
		return false;
	}

	@Override
	public int getRenderType() {
		return PrimitiveRenderers.rendererCampfire;
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
