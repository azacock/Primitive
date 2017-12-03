package azathoth.primitive.block;

import azathoth.primitive.Primitive;
import azathoth.primitive.tileentity.FramedDaubTileEntity;
import azathoth.primitive.client.render.PrimitiveRenderers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockFramedDaub extends BlockContainer {
	public IIcon[] icons = new IIcon[13];

	public BlockFramedDaub() {
		super(Material.rock);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new FramedDaubTileEntity();
	}

	// @Override
	// public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
	// 	int rot = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 1;
	// 	world.setBlockMetadataWithNotify(x, y, z, rot, 2);
	// }

	@Override
	public void registerBlockIcons(IIconRegister r) {
		this.icons[0] = r.registerIcon(this.textureName);
		this.icons[1] = r.registerIcon(Primitive.MODID + ":daub_frame_vertical");
		this.icons[2] = r.registerIcon(Primitive.MODID + ":daub_frame_horizontal");
		this.icons[3] = r.registerIcon(Primitive.MODID + ":daub_frame_diag_left");
		this.icons[4] = r.registerIcon(Primitive.MODID + ":daub_frame_diag_right");
		this.icons[5] = r.registerIcon(Primitive.MODID + ":daub_frame_edge_top");
		this.icons[6] = r.registerIcon(Primitive.MODID + ":daub_frame_edge_bottom");
		this.icons[7] = r.registerIcon(Primitive.MODID + ":daub_frame_edge_left");
		this.icons[8] = r.registerIcon(Primitive.MODID + ":daub_frame_edge_right");
		this.icons[9] = r.registerIcon(Primitive.MODID + ":daub_frame_corner_top_left");
		this.icons[10] = r.registerIcon(Primitive.MODID + ":daub_frame_corner_top_right");
		this.icons[11] = r.registerIcon(Primitive.MODID + ":daub_frame_corner_bottom_left");
		this.icons[12] = r.registerIcon(Primitive.MODID + ":daub_frame_corner_bottom_right");
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return icons[meta];
	// 	if (meta == 0) {
	// 		if (side == 2 || side == 3) {
	// 			return icons[0];
	// 		} else {
	// 			return icons[1];
	// 		}
	// 	} else { // meta == 1
	// 		if (side == 4 || side == 5) {
	// 			return icons[0];
	// 		} else {
	// 			return icons[1];
	// 		}
	// 	}
	// 	// if (side == (meta + 2)) {
	// 	// 	return icons[0];
	// 	// } else {
	// 	// 	return icons[1];
	// 	// }
	}

	@Override
	public int getRenderType() {
		return PrimitiveRenderers.rendererFramedDaub;
	}

	@Override
	public boolean isOpaqueCube() {
		return true;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
}
