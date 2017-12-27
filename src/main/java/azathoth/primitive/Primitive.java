package azathoth.primitive;

import azathoth.primitive.block.BlockAdobe;
import azathoth.primitive.block.BlockAsh;
import azathoth.primitive.block.BlockBasket;
import azathoth.primitive.block.BlockCampfire;
import azathoth.primitive.block.BlockCraftingSpot;
import azathoth.primitive.block.BlockDaub;
import azathoth.primitive.block.BlockDryingBrick;
import azathoth.primitive.block.BlockFirepit;
import azathoth.primitive.block.BlockFramedDaub;
import azathoth.primitive.block.BlockLattice;
import azathoth.primitive.block.BlockMoundPile;
import azathoth.primitive.block.BlockMoundPileActive;
import azathoth.primitive.block.BlockMoundPileExposed;
import azathoth.primitive.block.BlockPath;
import azathoth.primitive.block.BlockPathSlab;
import azathoth.primitive.block.BlockRock;
import azathoth.primitive.block.BlockStuccoPainted;
import azathoth.primitive.block.BlockThatch;
import azathoth.primitive.block.BlockThatchStairs;
import azathoth.primitive.block.BlockWattle;
import azathoth.primitive.block.BlockWattleDoor;
import azathoth.primitive.block.ItemBlockAdobe;
import azathoth.primitive.block.ItemBlockLattice;
import azathoth.primitive.block.ItemBlockPath;
import azathoth.primitive.block.ItemBlockPathSlab;
import azathoth.primitive.block.ItemBlockStuccoPainted;
import azathoth.primitive.item.PrimitiveItems;
import azathoth.primitive.proxy.CommonProxy;
import azathoth.primitive.tileentity.PrimitiveTileEntities;
import azathoth.primitive.world.PrimitiveWorldGen;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

import static cpw.mods.fml.common.Mod.EventHandler;
import static cpw.mods.fml.common.Mod.Instance;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Primitive.MODID, name = Primitive.NAME, version = Primitive.VERSION)
public class Primitive {
	public static final String MODID = "primitive";
	public static final String NAME = "Primitive";
	public static final String VERSION = "0.0.1";

	@Instance
	public static Primitive instance = new Primitive();

	public static Logger logger = LogManager.getLogger("Primitive");

	public static Block wattle;
	public static Block thatch;
	public static Block thatch_stairs;
	public static Block daub;
	public static Block daub_framed;
	public static Block adobe;
	public static Block drying_brick;
	public static Block lattice;
	public static Block ash;
	public static Block log_pile;
	public static Block active_log_pile;
	public static Block exposed_log_pile;
	public static Block stick_ash;
	public static Block stick_pile;
	public static Block active_stick_pile;
	public static Block exposed_stick_pile;
	public static Block wattle_door;
	public static Block rock;
	public static Block crafting_spot;
	public static Block campfire;
	public static Block firepit;
	public static Block path;
	public static Block path_slab;
	public static Block stucco_line;
	public static Block stucco_circle;
	public static Block basket;

	@SidedProxy(clientSide = "azathoth.primitive.proxy.ClientProxy", serverSide = "azathoth.primitive.proxy.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		PrimitiveTileEntities.preInit();
		PrimitiveItems.preInit();
		GameRegistry.registerBlock(wattle = new BlockWattle(), "wattle");
		GameRegistry.registerBlock(lattice = new BlockLattice(), ItemBlockLattice.class, "lattice");
		GameRegistry.registerBlock(thatch = new BlockThatch(), "thatch");
		GameRegistry.registerBlock(thatch_stairs = new BlockThatchStairs(), "thatch_stairs");
		GameRegistry.registerBlock(daub = new BlockDaub().setBlockName("daub").setBlockTextureName(Primitive.MODID + ":daub"), "daub");
		GameRegistry.registerBlock(adobe = new BlockAdobe().setBlockName("adobe").setBlockTextureName(Primitive.MODID + ":adobe"), ItemBlockAdobe.class, "adobe");
		GameRegistry.registerBlock(stucco_line = new BlockStuccoPainted().setBlockName("stucco_line").setBlockTextureName(Primitive.MODID + ":stucco_line"), ItemBlockStuccoPainted.class, "stucco_line");
		GameRegistry.registerBlock(stucco_circle = new BlockStuccoPainted().setBlockName("stucco_circle").setBlockTextureName(Primitive.MODID + ":stucco_circle"), ItemBlockStuccoPainted.class, "stucco_circle");
		GameRegistry.registerBlock(daub_framed = new BlockFramedDaub().setBlockName("daub_framed").setBlockTextureName(Primitive.MODID + ":daub"), "daub_framed");
		GameRegistry.registerBlock(drying_brick = new BlockDryingBrick().setBlockName("drying_brick").setBlockTextureName(Primitive.MODID + ":adobe_smooth"), "drying_brick");

		// charcoal mound
		GameRegistry.registerBlock(ash = new BlockAsh(Items.coal, 1, 7, 10).setBlockName("ash_pile").setBlockTextureName(Primitive.MODID + ":ash_pile"), "ash_pile");
		log_pile = new BlockMoundPile().setBlockName("log_pile").setBlockTextureName(Primitive.MODID + ":log_pile");
		active_log_pile = new BlockMoundPileActive().setBlockName("active_log_pile").setBlockTextureName(Primitive.MODID + ":active_pile");
		exposed_log_pile = new BlockMoundPileExposed().setBlockName("exposed_active_log_pile").setBlockTextureName(Primitive.MODID + ":active_pile");
		((BlockMoundPile) log_pile).setNext((BlockMoundPileActive) active_log_pile);
		((BlockMoundPileActive) active_log_pile).setNext((BlockMoundPileExposed) exposed_log_pile);
		((BlockMoundPileActive) active_log_pile).setResult(ash);
		((BlockMoundPileExposed) exposed_log_pile).setPrevious((BlockMoundPileActive) active_log_pile);
		GameRegistry.registerBlock(log_pile, "log_pile");
		GameRegistry.registerBlock(active_log_pile, "active_log_pile");
		GameRegistry.registerBlock(exposed_log_pile, "exposed_active_log_pile");

		// charcoal mound (sticks)
		GameRegistry.registerBlock(stick_ash = new BlockAsh(Items.coal, 1, 1, 1).setBlockName("stick_ash_pile").setBlockTextureName(Primitive.MODID + ":ash_pile"), "stick_ash_pile");
		stick_pile = new BlockMoundPile().setBlockName("stick_pile").setBlockTextureName(Primitive.MODID + ":log_pile");
		active_stick_pile = new BlockMoundPileActive().setBlockName("active_stick_pile").setBlockTextureName(Primitive.MODID + ":active_pile");
		exposed_stick_pile = new BlockMoundPileExposed().setBlockName("exposed_active_stick_pile").setBlockTextureName(Primitive.MODID + ":active_pile");
		((BlockMoundPile) stick_pile).setNext((BlockMoundPileActive) active_stick_pile);
		((BlockMoundPileActive) active_stick_pile).setNext((BlockMoundPileExposed) exposed_stick_pile);
		((BlockMoundPileActive) active_stick_pile).setResult(stick_ash);
		((BlockMoundPileExposed) exposed_stick_pile).setPrevious((BlockMoundPileActive) active_stick_pile);
		GameRegistry.registerBlock(stick_pile, "stick_pile");
		GameRegistry.registerBlock(active_stick_pile, "active_stick_pile");
		GameRegistry.registerBlock(exposed_stick_pile, "exposed_active_stick_pile");

		GameRegistry.registerBlock(wattle_door = new BlockWattleDoor().setBlockName("wattle_door"), "wattle_door");

		GameRegistry.registerBlock(rock = new BlockRock().setBlockName("rock"), "rock");
		GameRegistry.registerBlock(crafting_spot = new BlockCraftingSpot().setBlockName("crafting_spot"), "crafting_spot");
		GameRegistry.registerBlock(campfire = new BlockCampfire().setBlockName("campfire"), "campfire");
		GameRegistry.registerBlock(firepit = new BlockFirepit().setBlockName("firepit"), "firepit");
		GameRegistry.registerBlock(path = new BlockPath().setBlockName("path"), ItemBlockPath.class, "path");
		GameRegistry.registerBlock(path_slab = new BlockPathSlab().setBlockName("path_slab"), ItemBlockPathSlab.class, "path_slab");

		GameRegistry.registerBlock(basket = new BlockBasket().setBlockName("basket"), "basket");
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init();
		PrimitiveRecipes.init();
		GameRegistry.registerWorldGenerator(new PrimitiveWorldGen(), 100);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		Blocks.log.setHarvestLevel("axe", 10);
	}
}
