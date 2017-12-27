package azathoth.primitive.client.render;

import cpw.mods.fml.client.registry.RenderingRegistry;

public final class PrimitiveRenderers {
	public static int rendererWattle;
	public static int rendererWattleDoor;
	public static int rendererFramedDaub;
	public static int rendererDryingBrick;
	public static int rendererRock;
	public static int rendererCraftingSpot;
	public static int rendererCampfire;
	public static int rendererFirepit;
	public static int rendererPath;
	public static int rendererPathSlab;
	public static int rendererBasket;

	public static void init() {
		RenderWattle wattle = new RenderWattle();
		rendererWattle = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(rendererWattle, wattle);

		RenderWattleDoor wattle_door = new RenderWattleDoor();
		rendererWattleDoor = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(rendererWattleDoor, wattle_door);

		RenderFramedDaub daub = new RenderFramedDaub();
		rendererFramedDaub = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(rendererFramedDaub, daub);

		RenderDryingBrick brick = new RenderDryingBrick();
		rendererDryingBrick = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(rendererDryingBrick, brick);

		RenderRock rock = new RenderRock();
		rendererRock = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(rendererRock, rock);

		RenderCraftingSpot crafting_spot = new RenderCraftingSpot();
		rendererCraftingSpot = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(rendererCraftingSpot, crafting_spot);

		RenderCampfire campfire = new RenderCampfire();
		rendererCampfire = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(rendererCampfire, campfire);

		RenderFirepit firepit = new RenderFirepit();
		rendererFirepit = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(rendererFirepit, firepit);

		RenderPath path = new RenderPath();
		rendererPath = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(rendererPath, path);

		RenderPathSlab path_slab = new RenderPathSlab();
		rendererPathSlab = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(rendererPathSlab, path_slab);

		RenderBasket basket = new RenderBasket();
		rendererBasket = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(rendererBasket, basket);
	}
}
