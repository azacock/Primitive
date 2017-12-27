package azathoth.primitive.item;

import azathoth.primitive.Primitive;

import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;


// this class is mostly just copied from vanilla ItemShears

public class ItemKnife extends ItemShears {
	public ItemKnife() {
		this.setMaxStackSize(1);
		this.setMaxDamage(150);
		this.setTextureName(Primitive.MODID + ":knife");
	}

	@Override
	public boolean hasContainerItem() {
		return true;
	}

	@Override
	public boolean doesContainerItemLeaveCraftingGrid(ItemStack stack) {
		return false;
	}

	@Override
	public ItemStack getContainerItem(ItemStack stack) {
		stack.setItemDamage(stack.getItemDamage() + 1);
		return stack.copy();
	}
}
