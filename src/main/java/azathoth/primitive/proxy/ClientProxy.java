package azathoth.primitive.proxy;

import azathoth.primitive.client.render.PrimitiveRenderers;

public class ClientProxy extends CommonProxy {
	@Override
	public void init() {
		super.init();
		PrimitiveRenderers.init();
	}
}
