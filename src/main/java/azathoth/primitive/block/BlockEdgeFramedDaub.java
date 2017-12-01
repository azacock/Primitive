package azathoth.primitive.block;

import azathoth.primitive.Primitive;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockEdgeFramedDaub extends BlockDaub {
	public IIcon[] icons = new IIcon[3];

	public BlockEdgeFramedDaub() {
		super();
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		int rot = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		world.setBlockMetadataWithNotify(x, y, z, rot, 2);
	}

	@Override
	public void registerBlockIcons(IIconRegister r) {
		this.icons[0] = r.registerIcon(this.textureName);
		this.icons[1] = r.registerIcon(Primitive.MODID + ":daub");
		if (this.textureName.contains("left")) {
			this.icons[2] = r.registerIcon(this.textureName.replace("left", "right"));
		} else if (this.textureName.contains("right")) {
			this.icons[2] = r.registerIcon(this.textureName.replace("right", "left"));
		} else {
			this.icons[2] = r.registerIcon(this.textureName);
		}
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		if (meta == 0) {
			if (side == 2) {
				return icons[0];
			} else if (side == 3) {
				return icons[2];
			} else {
				return icons[1];
			}
		} else if (meta == 1) {
			if (side == 5) {
				return icons[0];
			} else if (side == 4) {
				return icons[2];
			} else {
				return icons[1];
			}
		} else if (meta == 2) {
			if (side == 3) {
				return icons[0];
			} else if (side == 2) {
				return icons[2];
			} else {
				return icons[1];
			}
		} else { // meta == 3
			if (side == 4) {
				return icons[0];
			} else if (side == 5) {
				return icons[2];
			} else {
				return icons[1];
			}
		}
		// if (side == (meta + 2)) {
		// 	return icons[0];
		// } else {
		// 	return icons[1];
		// }
	}
}
