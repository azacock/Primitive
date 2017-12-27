package azathoth.primitive.world;

import cpw.mods.fml.common.IWorldGenerator;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class PrimitiveWorldGen implements IWorldGenerator {
	protected RockGenerator rock_gen = new RockGenerator();

	@Override
	public void generate(Random random, int cx, int cz, World world, IChunkProvider chunk_generator, IChunkProvider chunk_provider) {
		if (world.provider.dimensionId == 0 || world.provider.dimensionId == 7) {
			this.runGenerator(this.rock_gen, world, random, cx, cz);
		}
	}

	private void runGenerator(RockGenerator generator, World world, Random random, int cx, int cz) {
		int r = ThreadLocalRandom.current().nextInt(0, 5);
		for (int i = 0; i < r; i++) {
			int x = (cx << 4) + random.nextInt(16);
			int z = (cz << 4) + random.nextInt(16);
			int y = world.getTopSolidOrLiquidBlock(x, z);
			if (y != -1) {
				generator.generate(world, random, x, y, z);
			}
		}
	}
}
