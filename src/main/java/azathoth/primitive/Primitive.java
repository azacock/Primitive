package azathoth.primitive;

import azathoth.primitive.block.BlockWattle;
import azathoth.primitive.block.BlockThatch;
import azathoth.primitive.block.BlockThatchStairs;
import azathoth.primitive.block.BlockDaub;
import azathoth.primitive.block.BlockEdgeFramedDaub;
import azathoth.primitive.block.BlockFramedDaub;
import azathoth.primitive.block.BlockAdobe;
import azathoth.primitive.block.BlockDryingBrick;
import azathoth.primitive.block.ItemBlockAdobe;
import azathoth.primitive.proxy.ClientProxy;
import azathoth.primitive.proxy.CommonProxy;
import azathoth.primitive.item.PrimitiveItems;
import azathoth.primitive.tileentity.PrimitiveTileEntities;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Mod(modid = Primitive.MODID, name = Primitive.NAME, version = Primitive.VERSION)
public class Primitive {
	public static final String MODID = "primitive";
	public static final String NAME = "Primitive";
	public static final String VERSION = "0.0.1";

	public static Logger logger = LogManager.getLogger("Primitive");

	public static Block wattle;
	public static Block thatch;
	public static Block thatch_stairs;
	public static Block daub;
	public static Block daub_framed;
	public static Block adobe;
	public static Block drying_brick;

	@SidedProxy(clientSide = "azathoth.primitive.proxy.ClientProxy", serverSide = "azathoth.primitive.proxy.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		PrimitiveTileEntities.preInit();
		PrimitiveItems.preInit();
		GameRegistry.registerBlock(wattle = new BlockWattle(), "wattle");
		GameRegistry.registerBlock(thatch = new BlockThatch(), "thatch");
		GameRegistry.registerBlock(thatch_stairs = new BlockThatchStairs(), "thatch_stairs");
		GameRegistry.registerBlock(daub = new BlockDaub().setBlockName("daub").setBlockTextureName(Primitive.MODID + ":daub"), "daub");
		GameRegistry.registerBlock(daub_framed = new BlockFramedDaub().setBlockName("daub_test").setBlockTextureName(Primitive.MODID + ":daub"), "daub_test");
		GameRegistry.registerBlock(adobe = new BlockAdobe().setBlockName("adobe").setBlockTextureName(Primitive.MODID + ":adobe"), ItemBlockAdobe.class, "adobe");
		GameRegistry.registerBlock(drying_brick = new BlockDryingBrick().setBlockName("drying_brick").setBlockTextureName(Primitive.MODID + ":drying_brick"), "drying_brick");
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init();
		PrimitiveItems.init();
		GameRegistry.addRecipe(new ItemStack(Primitive.wattle, 4), new Object[] {"sgs", "sgs", "sgs", 's', Items.stick, 'g', new ItemStack(Blocks.tallgrass, 1, 1)});
		GameRegistry.addRecipe(new ItemStack(Primitive.thatch, 2), new Object[] {"ggg", "ggg", "ggg", 'g', new ItemStack(Blocks.tallgrass, 1, 1)});
	}
}
