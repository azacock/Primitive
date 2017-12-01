package azathoth.primitive.item;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class PrimitiveItems {
	public static Item frame;

	public static void preInit() {
		frame = new ItemFrame().setUnlocalizedName("frame").setCreativeTab(CreativeTabs.tabMisc);
		GameRegistry.registerItem(frame, "frame");
	}
}
