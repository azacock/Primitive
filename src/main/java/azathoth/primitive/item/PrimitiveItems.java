package azathoth.primitive.item;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class PrimitiveItems {
	public static Item frame;
	public static Item daub;
	public static Item brick;
	public static Item mold;
	public static Item knife;
	public static Item handaxe;
	public static Item wattle_door;

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
		knife = new ItemKnife().setUnlocalizedName("knife");
		GameRegistry.registerItem(knife, "knife");
		handaxe = new ItemHandaxe();
		GameRegistry.registerItem(handaxe, "handaxe");
		wattle_door = new ItemDoorWattle().setUnlocalizedName("wattle_door");
		GameRegistry.registerItem(wattle_door, "item_wattle_door");
	}
}
