package azathoth.primitive.item;

import azathoth.primitive.Primitive;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

public class ItemMold extends Item {
	IIcon[] icons = new IIcon[1];

	public ItemMold() {
		super();
		this.setHasSubtypes(true);
	}

	@Override
	public void registerIcons(IIconRegister r) {
		this.icons[0] = r.registerIcon(Primitive.MODID + ":brick_mold");
	}

	@Override
	public IIcon getIconFromDamage(int meta) {
		if (meta > 0)
			meta = 0;
		return this.icons[meta];

	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tav, List list) {
		list.add(new ItemStack(item, 1, 0));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return this.getUnlocalizedName() + "_" + stack.getItemDamage();
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitx, float hity, float hitz) {
		return false;
	}

	@Override
	public boolean doesContainerItemLeaveCraftingGrid(ItemStack stack) {
		return false;
	}

	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return true;
	}

	@Override
	public ItemStack getContainerItem(ItemStack stack) {
		return stack.copy();
	}
}
