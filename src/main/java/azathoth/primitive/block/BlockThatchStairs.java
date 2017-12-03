package azathoth.primitive.block;

import azathoth.primitive.Primitive;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;

public class BlockThatchStairs extends BlockStairs {
	public BlockThatchStairs() {
		super(Primitive.thatch, 0);
		this.setBlockName("thatch_stairs");
		this.useNeighborBrightness = true;
	}
}
