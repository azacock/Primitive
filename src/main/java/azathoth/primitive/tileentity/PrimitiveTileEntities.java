package azathoth.primitive.tileentity;

import cpw.mods.fml.common.registry.GameRegistry;

public class PrimitiveTileEntities {
	public static void preInit() {
		GameRegistry.registerTileEntity(FramedDaubTileEntity.class, "framed_daub_tile_entity");
		GameRegistry.registerTileEntity(DryingBrickTileEntity.class, "drying_brick_tile_entity");
	}
}
