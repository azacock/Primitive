package azathoth.primitive.item;

import azathoth.primitive.Primitive;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import java.util.List;

public class ItemMud extends Item {
	IIcon[] icons = new IIcon[4];

	public ItemMud() {
		super();
		this.setHasSubtypes(true);
	}

	@Override
	public void registerIcons(IIconRegister r) {
		this.icons[0] = r.registerIcon(Primitive.MODID + ":daub");
		this.icons[1] = r.registerIcon(Primitive.MODID + ":daub_dry");
		this.icons[2] = r.registerIcon(Primitive.MODID + ":adobe");
		this.icons[3] = r.registerIcon(Primitive.MODID + ":adobe_dry");
	}

	@Override
	public IIcon getIconFromDamage(int meta) {
		if (meta > 3)
			meta = 0;
		return this.icons[meta];

	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tav, List list) {
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 1));
		list.add(new ItemStack(item, 1, 2));
		list.add(new ItemStack(item, 1, 3));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return this.getUnlocalizedName() + "_" + stack.getItemDamage();
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitx, float hity, float hitz) {
		int meta = stack.getItemDamage();
		if (meta == 1 || meta == 3) {
			Block b = null;
			switch (side) {
				case 0:
					if (y > 1)
						b = world.getBlock(x, y - 1, z);
					break;
				case 1:
					if (y < 255)
						b = world.getBlock(x, y + 1, z);
					break;
				case 2:
					b = world.getBlock(x, y, z - 1);
					break;
				case 3:
					b = world.getBlock(x, y, z + 1);
					break;
				case 4:
					b = world.getBlock(x - 1, y, z);
					break;
				case 5:
					b = world.getBlock(x + 1, y, z);
					break;
			}
			if (b == Blocks.water) {
				player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(stack.getItem(), stack.stackSize, meta - 1));
				return true;
			}
		} else if (meta == 0 && world.getBlock(x, y, z) == Primitive.wattle) {
			world.setBlock(x, y, z, Primitive.daub, 3, 3);
			world.scheduleBlockUpdate(x, y, z, Primitive.daub, 20 * 5);
			if (stack.stackSize == 1) {
				player.inventory.decrStackSize(player.inventory.currentItem, 1);
			} else {
				ItemStack i = player.inventory.getStackInSlot(player.inventory.currentItem);
				player.inventory.decrStackSize(player.inventory.currentItem, 1);
				player.inventory.setInventorySlotContents(player.inventory.currentItem, i.copy());
			}
			return true;
		}
		return false;
	}
}
