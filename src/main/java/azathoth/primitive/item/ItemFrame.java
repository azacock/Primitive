package azathoth.primitive.item;

import azathoth.primitive.Primitive;
import azathoth.primitive.tileentity.FramedDaubTileEntity;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import java.util.List;

public class ItemFrame extends Item {
	public IIcon[] icons = new IIcon[12];

	public ItemFrame() {
		super();
		this.setHasSubtypes(true);
	}

	@Override
	public void registerIcons(IIconRegister r) {
		this.icons[0] = r.registerIcon(Primitive.MODID + ":daub_frame_vertical");
		this.icons[1] = r.registerIcon(Primitive.MODID + ":daub_frame_horizontal");
		this.icons[2] = r.registerIcon(Primitive.MODID + ":daub_frame_diag_left");
		this.icons[3] = r.registerIcon(Primitive.MODID + ":daub_frame_diag_right");
		this.icons[4] = r.registerIcon(Primitive.MODID + ":daub_frame_edge_top");
		this.icons[5] = r.registerIcon(Primitive.MODID + ":daub_frame_edge_bottom");
		this.icons[6] = r.registerIcon(Primitive.MODID + ":daub_frame_edge_left");
		this.icons[7] = r.registerIcon(Primitive.MODID + ":daub_frame_edge_right");
		this.icons[8] = r.registerIcon(Primitive.MODID + ":daub_frame_corner_top_left");
		this.icons[9] = r.registerIcon(Primitive.MODID + ":daub_frame_corner_top_right");
		this.icons[10] = r.registerIcon(Primitive.MODID + ":daub_frame_corner_bottom_left");
		this.icons[11] = r.registerIcon(Primitive.MODID + ":daub_frame_corner_bottom_right");
	}

	@Override
	public IIcon getIconFromDamage(int meta) {
		if (meta > 11)
			meta = 0;

		return icons[meta];
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < 12; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return this.getUnlocalizedName() + "_" + stack.getItemDamage();
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitx, float hity, float hitz) {
		if (world.getBlock(x, y, z) == Primitive.daub) {
			world.setBlock(x, y, z, Primitive.daub_framed);
		} else if (world.getBlock(x, y, z) != Primitive.daub_framed) {
			return false;
		}

		FramedDaubTileEntity te = (FramedDaubTileEntity) world.getTileEntity(x, y, z);

		short frame = (short) (1 << stack.getItemDamage());

		if (te != null && !te.hasFrame(side, frame)) {
			te.updateFrames(side, frame);
			return true;
		}

		return false;
	}
}
