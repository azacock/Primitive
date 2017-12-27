package azathoth.primitive.world;

import azathoth.primitive.Primitive;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

import java.util.Random;

public class RockGenerator {
	public boolean generate(World world, Random random, int x, int y, int z) {
		Block b = world.getBlock(x, y - 1, z);
		if (world.getBlock(x, y, z).getMaterial() == Material.air && b.isOpaqueCube() && (b.getMaterial() == Material.grass || b.getMaterial() == Material.ground || b.getMaterial() == Material.rock)) {
			world.setBlock(x, y, z, Primitive.rock);
			return true;
		}
		return false;
	}
}
