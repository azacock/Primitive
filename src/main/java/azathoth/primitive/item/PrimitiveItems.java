package azathoth.primitive.item;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PrimitiveItems {
	public static Item frame;
	public static Item daub;
	public static Item brick;
	public static Item mold;

	public static void preInit() {
		frame = new ItemFrame().setUnlocalizedName("frame").setCreativeTab(CreativeTabs.tabMisc);
		GameRegistry.registerItem(frame, "frame");
		daub = new ItemMud().setUnlocalizedName("mud_ball");
		GameRegistry.registerItem(daub, "mud_ball");
		brick = new ItemBrick().setUnlocalizedName("mud_brick");
		GameRegistry.registerItem(brick, "mud_brick");
		mold = new ItemMold().setUnlocalizedName("mold");
		mold.setContainerItem(mold);
		GameRegistry.registerItem(mold, "mold");
	}
}
