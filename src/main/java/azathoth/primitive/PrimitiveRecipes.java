package azathoth.primitive;

import azathoth.primitive.item.PrimitiveItems;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public final class PrimitiveRecipes {
	public static void init() {
		// molds
		GameRegistry.addRecipe(new ItemStack(PrimitiveItems.mold, 1, 0), new Object[] {"sss", "   ", "sss", 's', Items.stick});

		// wattle
		GameRegistry.addRecipe(new ItemStack(Primitive.wattle, 4), new Object[] {"sgs", "sgs", "sgs", 's', Items.stick, 'g', new ItemStack(Blocks.tallgrass, 1, 1)});

		// thatch
		GameRegistry.addRecipe(new ItemStack(Primitive.thatch, 4), new Object[] {"ggg", "ggg", "ggg", 'g', new ItemStack(Blocks.tallgrass, 1, 1)});
		GameRegistry.addRecipe(new ItemStack(Primitive.thatch_stairs, 6), new Object[] {"  t", " tt", "ttt", 't', new ItemStack(Primitive.thatch, 1)});

		// daub
		GameRegistry.addShapelessRecipe(new ItemStack(PrimitiveItems.daub, 4, 1), new Object[] {Items.clay_ball, new ItemStack(Blocks.tallgrass, 1, 1), new ItemStack(Blocks.dirt, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(PrimitiveItems.daub, 4, 1), new Object[] {Items.clay_ball, new ItemStack(Blocks.leaves, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Blocks.dirt, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(PrimitiveItems.daub, 8, 0), new Object[] {new ItemStack(PrimitiveItems.daub, 1, 1), new ItemStack(PrimitiveItems.daub, 1, 1), new ItemStack(PrimitiveItems.daub, 1, 1), new ItemStack(PrimitiveItems.daub, 1, 1), new ItemStack(PrimitiveItems.daub, 1, 1), new ItemStack(PrimitiveItems.daub, 1, 1), new ItemStack(PrimitiveItems.daub, 1, 1), new ItemStack(PrimitiveItems.daub, 1, 1), Items.water_bucket});

		// adobe
		GameRegistry.addShapelessRecipe(new ItemStack(PrimitiveItems.daub, 4, 3), new Object[] {Items.clay_ball, new ItemStack(Blocks.tallgrass, 1, 1), new ItemStack(Blocks.sand, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(PrimitiveItems.daub, 4, 3), new Object[] {Items.clay_ball, new ItemStack(Blocks.leaves, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Blocks.sand, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(PrimitiveItems.daub, 8, 2), new Object[] {new ItemStack(PrimitiveItems.daub, 1, 3), new ItemStack(PrimitiveItems.daub, 1, 3), new ItemStack(PrimitiveItems.daub, 1, 3), new ItemStack(PrimitiveItems.daub, 1, 3), new ItemStack(PrimitiveItems.daub, 1, 3), new ItemStack(PrimitiveItems.daub, 1, 3), new ItemStack(PrimitiveItems.daub, 1, 3), new ItemStack(PrimitiveItems.daub, 1, 3), Items.water_bucket});
		GameRegistry.addShapelessRecipe(new ItemStack(PrimitiveItems.brick, 1, 0), new Object[] {new ItemStack(PrimitiveItems.daub, 1, 2), new ItemStack(PrimitiveItems.mold, 1)});
		GameRegistry.addRecipe(new ItemStack(Primitive.adobe, 3, 0), new Object[] {"bmb", "mbm", "bmb", 'b', new ItemStack(PrimitiveItems.brick, 1, 1), 'm', new ItemStack(PrimitiveItems.daub, 1, 2)});
		GameRegistry.addRecipe(new ItemStack(Primitive.adobe, 3, 2), new Object[] {"mmm", "bbb", "bbb", 'b', new ItemStack(PrimitiveItems.brick, 1, 1), 'm', new ItemStack(PrimitiveItems.daub, 1, 2)});

		// log pile
		GameRegistry.addRecipe(new ItemStack(Primitive.log_pile, 1), new Object[] {"###", "###", "###", '#', new ItemStack(Blocks.log, 1, 0)});
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.log, 9), new Object[] {new ItemStack(Primitive.log_pile, 1)});
	}
}
