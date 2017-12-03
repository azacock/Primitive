package azathoth.primitive.block;

import azathoth.primitive.Primitive;
import azathoth.primitive.client.render.PrimitiveRenderers;
import java.util.List;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockWattle extends BlockPane {
	protected IIcon[] icons = new IIcon[6];

	public BlockWattle() {
		super(Primitive.MODID + ":wattle", Primitive.MODID + ":wattle_side", Material.plants, true);
		this.setStepSound(soundTypeGrass);
		this.setBlockName("wattle");
		this.setBlockTextureName(Primitive.MODID + ":wattle");
	}

	@Override
	public int getRenderType() {
		return PrimitiveRenderers.rendererWattle;
	}

	@Override
	public void registerBlockIcons(IIconRegister r) {
		this.icons[1] = r.registerIcon(this.textureName);
		this.icons[0] = r.registerIcon(this.textureName + "_top");
		this.icons[2] = r.registerIcon(this.textureName + "_side");
		this.icons[3] = r.registerIcon(this.textureName + "_1");
		this.icons[4] = r.registerIcon(this.textureName + "_2");
		this.icons[5] = r.registerIcon(this.textureName + "_3");
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		switch (side) {
			case 2:
				return this.icons[2];
			case 0:
				return this.icons[0];
			default:
				switch (meta) {
					case 1:
						return this.icons[3];
					case 2:
						return this.icons[4];
					case 3:
						return this.icons[5];
					default:
						return this.icons[1];
				}
		}
	}

	 @Override
	 public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		 return super.getCollisionBoundingBoxFromPool(world, x, y, z).offset(0, 0.35f, 0);
     // return AxisAlignedBB.getBoundingBox((double) x, (double) y, (double) z, (double) x, (double) y + 1.2f, (double) z + 1f);
	 }

	// public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity) {
	// 	super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
	// }

}
