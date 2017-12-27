package azathoth.primitive.block;

import azathoth.primitive.Primitive;
import azathoth.primitive.client.render.PrimitiveRenderers;
import azathoth.primitive.tileentity.FirepitTileEntity;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockFirepit extends BlockContainer {
	public BlockFirepit() {
		super(Material.rock);
		this.setHardness(0.5f);
		this.setBlockTextureName(Primitive.MODID + ":adobe_smooth");
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null && te instanceof FirepitTileEntity && ((FirepitTileEntity) te).isLit())
			return 14;
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

	public boolean shouldIgnite(World world, int x, int y, int z) {
		TileEntity te;
		FirepitTileEntity pit;
		int meta = world.getBlockMetadata(x, y, z);

		if (world.getBlock(x, y, z + 1) == this && world.getBlockMetadata(x, y, z + 1) == meta) {
			te = world.getTileEntity(x, y, z + 1);
			if (te != null && te instanceof FirepitTileEntity) {
				pit = (FirepitTileEntity) te;
				if (pit.isLit())
					return true;
			}
		}

		if (world.getBlock(x, y, z - 1) == this && world.getBlockMetadata(x, y, z - 1) == meta) {
			te = world.getTileEntity(x, y, z - 1);
			if (te != null && te instanceof FirepitTileEntity) {
				pit = (FirepitTileEntity) te;
				if (pit.isLit())
					return true;
			}
		}

		if (world.getBlock(x + 1, y, z) == this && world.getBlockMetadata(x + 1, y, z) == meta) {
			te = world.getTileEntity(x + 1, y, z);
			if (te != null && te instanceof FirepitTileEntity) {
				pit = (FirepitTileEntity) te;
				if (pit.isLit())
					return true;
			}
		}

		if (world.getBlock(x - 1, y, z) == this && world.getBlockMetadata(x - 1, y, z) == meta) {
			te = world.getTileEntity(x - 1, y, z);
			if (te != null && te instanceof FirepitTileEntity) {
				pit = (FirepitTileEntity) te;
				if (pit.isLit())
					return true;
			}
		}

		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		FirepitTileEntity pit = new FirepitTileEntity();
		pit.setMaxFuel(25600);
		return pit;
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
		return PrimitiveRenderers.rendererFirepit;
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
