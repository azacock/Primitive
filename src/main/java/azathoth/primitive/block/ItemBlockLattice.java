package azathoth.primitive.block;

import azathoth.primitive.Primitive;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemBlockLattice extends ItemBlockWithMetadata {
	public ItemBlockLattice(Block b) {
		super(b, b);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return this.getUnlocalizedName() + " _ " + stack.getItemDamage();
	}

	@Override
	public IIcon getIconFromDamage(int meta) {
		return Primitive.lattice.getIcon(1, meta);
	}
}
