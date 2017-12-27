package azathoth.primitive.proxy;

import azathoth.primitive.Primitive;
import azathoth.primitive.network.PrimitiveGuiHandler;

import cpw.mods.fml.common.network.NetworkRegistry;

public class CommonProxy {
	public void init() {
		NetworkRegistry.INSTANCE.registerGuiHandler(Primitive.instance, new PrimitiveGuiHandler());
	}
}
