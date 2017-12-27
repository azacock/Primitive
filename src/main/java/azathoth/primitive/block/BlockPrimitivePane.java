package azathoth.primitive.block;

import azathoth.primitive.client.render.PrimitiveRenderers;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;

public abstract class BlockPrimitivePane extends BlockPane {
	public boolean canConnectToBlock(Block b) {
		if (b instanceof BlockWattleDoor) {
			return true;
		} else {
			return super.canPaneConnectToBlock(b);
		}
	}

	public BlockPrimitivePane(String t1, String t2, Material m, boolean b) {
		super(t1, t2, m, b);
	}

	@Override
	public int getRenderType() {
		return PrimitiveRenderers.rendererWattle;
	}
}
