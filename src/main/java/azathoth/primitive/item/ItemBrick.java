package azathoth.primitive.item;

import azathoth.primitive.Primitive;
import azathoth.primitive.tileentity.DryingBrickTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import java.util.List;

public class ItemBrick extends Item {
	IIcon[] icons = new IIcon[2];

	public ItemBrick() {
		super();
		this.setHasSubtypes(true);
	}

	@Override
	public void registerIcons(IIconRegister r) {
		this.icons[0] = r.registerIcon(Primitive.MODID + ":adobe_brick");
		this.icons[1] = r.registerIcon(Primitive.MODID + ":adobe_brick_dry");
	}

	@Override
	public IIcon getIconFromDamage(int meta) {
		if (meta > 1)
			meta = 0;
		return this.icons[meta];

	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tav, List list) {
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 1));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return this.getUnlocalizedName() + "_" + stack.getItemDamage();
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitx, float hity, float hitz) {
		if (side == 1 && stack.getItemDamage() == 0 && world.getBlock(x, y, z).isOpaqueCube() && y < 255 && world.getBlock(x, y + 1, z).getMaterial() == Material.air) {
			world.setBlock(x, y + 1, z, Primitive.drying_brick);
			if (((DryingBrickTileEntity) world.getTileEntity(x, y + 1, z)).addBrick()) {
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
		} else if (stack.getItemDamage() == 0 && world.getBlock(x, y, z) == Primitive.drying_brick) {
			DryingBrickTileEntity te = (DryingBrickTileEntity) world.getTileEntity(x, y, z);
			if (te != null) {
				if (te.addBrick()) {
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
		return false;
	}
}
