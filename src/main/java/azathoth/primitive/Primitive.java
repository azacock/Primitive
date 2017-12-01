package azathoth.primitive;

import azathoth.primitive.block.BlockWattle;
import azathoth.primitive.block.BlockThatch;
import azathoth.primitive.block.BlockDaub;
import azathoth.primitive.block.BlockEdgeFramedDaub;
import azathoth.primitive.block.BlockFramedDaub;
import azathoth.primitive.proxy.ClientProxy;
import azathoth.primitive.proxy.CommonProxy;
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
	public static Block daub;
	public static Block daub_test;

	@SidedProxy(clientSide = "azathoth.primitive.proxy.ClientProxy", serverSide = "azathoth.primitive.proxy.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		PrimitiveTileEntities.preInit();
		GameRegistry.registerBlock(wattle = new BlockWattle(), "wattle");
		GameRegistry.registerBlock(thatch = new BlockThatch(), "thatch");
		GameRegistry.registerBlock(daub = new BlockDaub().setBlockName("daub").setBlockTextureName(Primitive.MODID + ":daub"), "daub");
		GameRegistry.registerBlock(daub_test = new BlockFramedDaub().setBlockName("daub_test").setBlockTextureName(Primitive.MODID + ":daub"), "daub_test");
		// GameRegistry.registerBlock(daub_vertical = new BlockFramedDaub().setBlockName("daub_vertical").setBlockTextureName(Primitive.MODID + ":daub_vertical"), "daub_vertical");
		// GameRegistry.registerBlock(daub_horizontal = new BlockFramedDaub().setBlockName("daub_horizontal").setBlockTextureName(Primitive.MODID + ":daub_horizontal"), "daub_horizontal");
		// GameRegistry.registerBlock(daub_cross = new BlockFramedDaub().setBlockName("daub_cross").setBlockTextureName(Primitive.MODID + ":daub_cross"), "daub_cross");
		// GameRegistry.registerBlock(daub_edge_bottom = new BlockEdgeFramedDaub().setBlockName("daub_edge_bottom").setBlockTextureName(Primitive.MODID + ":daub_edge_bottom"), "daub_edge_bottom");
		// GameRegistry.registerBlock(daub_edge_top = new BlockEdgeFramedDaub().setBlockName("daub_edge_top").setBlockTextureName(Primitive.MODID + ":daub_edge_top"), "daub_edge_top");
		// GameRegistry.registerBlock(daub_edge_left = new BlockEdgeFramedDaub().setBlockName("daub_edge_left").setBlockTextureName(Primitive.MODID + ":daub_edge_left"), "daub_edge_left");
		// GameRegistry.registerBlock(daub_edge_right = new BlockEdgeFramedDaub().setBlockName("daub_edge_right").setBlockTextureName(Primitive.MODID + ":daub_edge_right"), "daub_edge_right");
		// GameRegistry.registerBlock(daub_edge_topleft = new BlockEdgeFramedDaub().setBlockName("daub_edge_topleft").setBlockTextureName(Primitive.MODID + ":daub_edge_topleft"), "daub_edge_topleft");
		// GameRegistry.registerBlock(daub_edge_topright = new BlockEdgeFramedDaub().setBlockName("daub_edge_topright").setBlockTextureName(Primitive.MODID + ":daub_edge_topright"), "daub_edge_topright");
		// GameRegistry.registerBlock(daub_edge_bottomleft = new BlockEdgeFramedDaub().setBlockName("daub_edge_bottomleft").setBlockTextureName(Primitive.MODID + ":daub_edge_bottomleft"), "daub_edge_bottomleft");
		// GameRegistry.registerBlock(daub_edge_bottomright = new BlockEdgeFramedDaub().setBlockName("daub_edge_bottomright").setBlockTextureName(Primitive.MODID + ":daub_edge_bottomright"), "daub_edge_bottomright");
		// GameRegistry.registerBlock(daub_diag_1 = new BlockDaub().setBlockName("daub_diag_1").setBlockTextureName(Primitive.MODID + ":daub_diag_1"), "daub_diag_1");
		// GameRegistry.registerBlock(daub_diag_2 = new BlockDaub().setBlockName("daub_diag_2").setBlockTextureName(Primitive.MODID + ":daub_diag_2"), "daub_diag_2");
		// GameRegistry.registerBlock(daub_diag_cross = new BlockDaub().setBlockName("daub_diag_cross").setBlockTextureName(Primitive.MODID + ":daub_diag_cross"), "daub_diag_cross");
		// GameRegistry.registerBlock(daub_corner_topleft = new BlockEdgeFramedDaub().setBlockName("daub_corner_topleft").setBlockTextureName(Primitive.MODID + ":daub_corner_topleft"), "daub_corner_topleft");
		// GameRegistry.registerBlock(daub_corner_topright = new BlockEdgeFramedDaub().setBlockName("daub_corner_topright").setBlockTextureName(Primitive.MODID + ":daub_corner_topright"), "daub_corner_topright");
		// GameRegistry.registerBlock(daub_corner_bottomleft = new BlockEdgeFramedDaub().setBlockName("daub_corner_bottomleft").setBlockTextureName(Primitive.MODID + ":daub_corner_bottomleft"), "daub_corner_bottomleft");
		// GameRegistry.registerBlock(daub_corner_bottomright = new BlockEdgeFramedDaub().setBlockName("daub_corner_bottomright").setBlockTextureName(Primitive.MODID + ":daub_corner_bottomright"), "daub_corner_bottomright");
		// GameRegistry.registerBlock(daub_corner_top = new BlockEdgeFramedDaub().setBlockName("daub_corner_top").setBlockTextureName(Primitive.MODID + ":daub_corner_top"), "daub_corner_top");
		// GameRegistry.registerBlock(daub_corner_right = new BlockEdgeFramedDaub().setBlockName("daub_corner_right").setBlockTextureName(Primitive.MODID + ":daub_corner_right"), "daub_corner_right");
		// GameRegistry.registerBlock(daub_corner_bottom = new BlockEdgeFramedDaub().setBlockName("daub_corner_bottom").setBlockTextureName(Primitive.MODID + ":daub_corner_bottom"), "daub_corner_bottom");
		// GameRegistry.registerBlock(daub_corner_left = new BlockEdgeFramedDaub().setBlockName("daub_corner_left").setBlockTextureName(Primitive.MODID + ":daub_corner_left"), "daub_corner_left");
		// GameRegistry.registerBlock(daub_corner_notopright = new BlockEdgeFramedDaub().setBlockName("daub_corner_notopright").setBlockTextureName(Primitive.MODID + ":daub_corner_notopright"), "daub_corner_notopright");
		// GameRegistry.registerBlock(daub_corner_notopleft = new BlockEdgeFramedDaub().setBlockName("daub_corner_notopleft").setBlockTextureName(Primitive.MODID + ":daub_corner_notopleft"), "daub_corner_notopleft");
		// GameRegistry.registerBlock(daub_corner_nobottomright = new BlockEdgeFramedDaub().setBlockName("daub_corner_nobottomright").setBlockTextureName(Primitive.MODID + ":daub_corner_nobottomright"), "daub_corner_nobottomright");
		// GameRegistry.registerBlock(daub_corner_nobottomleft = new BlockEdgeFramedDaub().setBlockName("daub_corner_nobottomleft").setBlockTextureName(Primitive.MODID + ":daub_corner_nobottomleft"), "daub_corner_nobottomleft");
		// GameRegistry.registerBlock(daub_corner_all = new BlockEdgeFramedDaub().setBlockName("daub_corner_all").setBlockTextureName(Primitive.MODID + ":daub_corner_all"), "daub_corner_all");
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init();
		GameRegistry.addRecipe(new ItemStack(Primitive.wattle, 4), new Object[] {"sgs", "sgs", "sgs", 's', Items.stick, 'g', new ItemStack(Blocks.tallgrass, 1, 1)});
		GameRegistry.addRecipe(new ItemStack(Primitive.thatch, 2), new Object[] {"ggg", "ggg", "ggg", 'g', new ItemStack(Blocks.tallgrass, 1, 1)});
	}
}
