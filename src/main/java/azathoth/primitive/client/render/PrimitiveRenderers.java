package azathoth.primitive.client.render;

import cpw.mods.fml.client.registry.RenderingRegistry;

public final class PrimitiveRenderers {
	public static int rendererWattle;
	public static int rendererFramedDaub;
	public static int rendererDryingBrick;

	public static void init() {
		RenderWattle wattle = new RenderWattle();
		rendererWattle = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(rendererWattle, wattle);

		RenderFramedDaub daub = new RenderFramedDaub();
		rendererFramedDaub = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(rendererFramedDaub, daub);

		RenderDryingBrick brick = new RenderDryingBrick();
		rendererDryingBrick = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(rendererDryingBrick, brick);
	}
}
