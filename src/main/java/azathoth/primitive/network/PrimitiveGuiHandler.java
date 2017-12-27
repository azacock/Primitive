package azathoth.primitive.network;

import azathoth.primitive.client.gui.GuiBasketTileEntity;
import azathoth.primitive.client.gui.GuiCraftingSpot;
import azathoth.primitive.tileentity.BasketTileEntity;
import azathoth.primitive.tileentity.ContainerBasketTileEntity;
import azathoth.primitive.tileentity.ContainerCraftingSpot;

import cpw.mods.fml.common.network.IGuiHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class PrimitiveGuiHandler implements IGuiHandler {
	public static final int CRAFTING_SPOT_GUI = 0;
	public static final int BASKET_TILE_ENTITY_GUI = 1;

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if (id == BASKET_TILE_ENTITY_GUI)
			return new ContainerBasketTileEntity(player.inventory, (BasketTileEntity) world.getTileEntity(x, y, z));
		if (id == CRAFTING_SPOT_GUI)
			return new ContainerCraftingSpot(player.inventory, world, x, y, z);

		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if (id == BASKET_TILE_ENTITY_GUI)
			return new GuiBasketTileEntity(player.inventory, (BasketTileEntity) world.getTileEntity(x, y, z));
		if (id == CRAFTING_SPOT_GUI)
			return new GuiCraftingSpot(player.inventory, world, x, y, z);

		return null;
	}
}
