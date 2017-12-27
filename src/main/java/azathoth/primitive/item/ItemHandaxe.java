package azathoth.primitive.item;

import azathoth.primitive.Primitive;

import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

import net.minecraftforge.common.util.EnumHelper;

import static net.minecraft.item.Item.ToolMaterial;

public class ItemHandaxe extends ItemAxe {
	public static ToolMaterial PRIMITIVE = EnumHelper.addToolMaterial("PRIMITIVE", 0, 30, 0.25f, 0.5f, 1);

	public ItemHandaxe() {
		super(PRIMITIVE);
		this.setUnlocalizedName("handaxe");
		this.setTextureName(Primitive.MODID + ":handaxe");
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
