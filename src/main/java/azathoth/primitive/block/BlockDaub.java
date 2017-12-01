package azathoth.primitive.block;

import azathoth.primitive.Primitive;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockDaub extends Block {
	public BlockDaub() {
		super(Material.rock);
		this.setStepSound(soundTypeStone);
	}
}
