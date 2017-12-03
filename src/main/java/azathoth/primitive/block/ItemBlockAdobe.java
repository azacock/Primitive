package azathoth.primitive.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;

public class ItemBlockAdobe extends ItemBlockWithMetadata {
	public ItemBlockAdobe(Block b) {
		super(b, b);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return this.getUnlocalizedName() + "_" + stack.getItemDamage();
	}
}
