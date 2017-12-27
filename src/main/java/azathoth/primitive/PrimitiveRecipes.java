package azathoth.primitive;

import azathoth.primitive.item.PrimitiveItems;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

public final class PrimitiveRecipes {
	public static void init() {
		// rocks
		GameRegistry.addShapelessRecipe(new ItemStack(PrimitiveItems.handaxe, 1), new Object[] {new ItemStack(PrimitiveItems.brick, 1, 2), new ItemStack(PrimitiveItems.brick, 1, 2)});
		GameRegistry.addShapedRecipe(new ItemStack(Blocks.cobblestone, 1), new Object[] {"rrr", "rrr", "rrr", 'r', new ItemStack(PrimitiveItems.brick, 1, 2)});

		// flint knife
		GameRegistry.addShapelessRecipe(new ItemStack(PrimitiveItems.knife, 1), new Object[] {new ItemStack(Items.flint, 1), new ItemStack(PrimitiveItems.handaxe, 1, OreDictionary.WILDCARD_VALUE)});
		GameRegistry.addShapelessRecipe(new ItemStack(Items.stick, 1), new Object[] {new ItemStack(PrimitiveItems.knife, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Blocks.leaves, 1, OreDictionary.WILDCARD_VALUE)});
		GameRegistry.addShapelessRecipe(new ItemStack(Items.stick, 1), new Object[] {new ItemStack(PrimitiveItems.knife, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Blocks.sapling, 1, OreDictionary.WILDCARD_VALUE)});

		// crafting spot
		GameRegistry.addShapedRecipe(new ItemStack(Primitive.crafting_spot, 1), new Object[] {"ss", "ds", 's', new ItemStack(Items.stick, 1), 'd', new ItemStack(Blocks.dirt, 1, OreDictionary.WILDCARD_VALUE)});

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
		
		// frames
		GameRegistry.addShapedRecipe(new ItemStack(PrimitiveItems.frame, 3, 1), new Object[] {"sss", 's', new ItemStack(Blocks.wooden_slab, 1, 0)});
		GameRegistry.addShapelessRecipe(new ItemStack(PrimitiveItems.frame, 1, 0), new Object[] {new ItemStack(PrimitiveItems.frame, 1, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(PrimitiveItems.frame, 1, 4), new Object[] {new ItemStack(PrimitiveItems.frame, 1, 0)});
		GameRegistry.addShapelessRecipe(new ItemStack(PrimitiveItems.frame, 1, 5), new Object[] {new ItemStack(PrimitiveItems.frame, 1, 4)});
		GameRegistry.addShapelessRecipe(new ItemStack(PrimitiveItems.frame, 1, 6), new Object[] {new ItemStack(PrimitiveItems.frame, 1, 5)});
		GameRegistry.addShapelessRecipe(new ItemStack(PrimitiveItems.frame, 1, 7), new Object[] {new ItemStack(PrimitiveItems.frame, 1, 6)});
		GameRegistry.addShapelessRecipe(new ItemStack(PrimitiveItems.frame, 1, 1), new Object[] {new ItemStack(PrimitiveItems.frame, 1, 7)});

		GameRegistry.addShapedRecipe(new ItemStack(PrimitiveItems.frame, 3, 2), new Object[] {"s  ", " s ", "  s", 's', new ItemStack(Blocks.wooden_slab, 1, 0)});
		GameRegistry.addShapelessRecipe(new ItemStack(PrimitiveItems.frame, 1, 3), new Object[] {new ItemStack(PrimitiveItems.frame, 1, 2)});
		GameRegistry.addShapelessRecipe(new ItemStack(PrimitiveItems.frame, 1, 2), new Object[] {new ItemStack(PrimitiveItems.frame, 1, 3)});

		GameRegistry.addShapelessRecipe(new ItemStack(PrimitiveItems.frame, 1, 8), new Object[] {new ItemStack(Blocks.wooden_slab, 1, 0)});
		GameRegistry.addShapelessRecipe(new ItemStack(PrimitiveItems.frame, 1, 9), new Object[] {new ItemStack(PrimitiveItems.frame, 1, 8)});
		GameRegistry.addShapelessRecipe(new ItemStack(PrimitiveItems.frame, 1, 10), new Object[] {new ItemStack(PrimitiveItems.frame, 1, 9)});
		GameRegistry.addShapelessRecipe(new ItemStack(PrimitiveItems.frame, 1, 11), new Object[] {new ItemStack(PrimitiveItems.frame, 1, 10)});
		GameRegistry.addShapelessRecipe(new ItemStack(PrimitiveItems.frame, 1, 8), new Object[] {new ItemStack(PrimitiveItems.frame, 1, 11)});

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
		GameRegistry.addRecipe(new ItemStack(Primitive.stick_pile, 1), new Object[] {"###", "###", "###", '#', new ItemStack(Items.stick, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(Items.stick, 9), new Object[] {new ItemStack(Primitive.stick_pile, 1)});

		// lattice
		GameRegistry.addRecipe(new ItemStack(Primitive.lattice, 1, 0), new Object[] {" # ", "# #", " # ", '#', new ItemStack(Items.stick, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(Primitive.lattice, 1, 1), new Object[] {new ItemStack(Primitive.lattice, 1, 0)});
		GameRegistry.addShapelessRecipe(new ItemStack(Primitive.lattice, 1, 2), new Object[] {new ItemStack(Primitive.lattice, 1, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(Primitive.lattice, 1, 3), new Object[] {new ItemStack(Primitive.lattice, 1, 2)});
		GameRegistry.addShapelessRecipe(new ItemStack(Primitive.lattice, 1, 0), new Object[] {new ItemStack(Primitive.lattice, 1, 3)});

		// basket
		GameRegistry.addShapedRecipe(new ItemStack(Primitive.basket, 1), new Object[] {"# #", "# #", "###", '#', new ItemStack(Primitive.wattle, 1)});
	}
}
