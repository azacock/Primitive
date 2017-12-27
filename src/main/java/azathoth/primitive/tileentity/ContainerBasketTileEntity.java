package azathoth.primitive.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBasketTileEntity extends Container {
	private BasketTileEntity te;

	public ContainerBasketTileEntity(IInventory player_inv, BasketTileEntity te) {
		this.te = te;

		// Tile Entity, Slot 0-14, Slot IDs 0-14
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 5; x++) {
				this.addSlotToContainer(new Slot(te, x + y * 5, 44 + x * 18, 17 + y * 18));
			}
		}

		// Player Inventory, Slot 9-35, Slot IDs 15-41
    for (int y = 0; y < 3; ++y) {
        for (int x = 0; x < 9; ++x) {
            this.addSlotToContainer(new Slot(player_inv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
        }
    }

    // Player Inventory, Slot 0-8, Slot IDs 42-50
    for (int x = 0; x < 9; ++x) {
        this.addSlotToContainer(new Slot(player_inv, x, 8 + x * 18, 142));
    }
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.te.isUseableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) {
		ItemStack previous = null;
		Slot slot = (Slot) this.inventorySlots.get(fromSlot);

		if (slot != null && slot.getHasStack()) {
			ItemStack current = slot.getStack();
			previous = current.copy();

			if (fromSlot < 15) {
				// From TE Inventory to Player Inventory
				if (!this.mergeItemStack(current, 15, 51, true))
					return null;
				} else {
				// From Player Inventory to TE Inventory
				if (!this.mergeItemStack(current, 0, 15, false))
					return null;
			}

			if (current.stackSize == 0)
				slot.putStack((ItemStack) null);
			else
					slot.onSlotChanged();

			if (current.stackSize == previous.stackSize)
				return null;
			slot.onPickupFromSlot(playerIn, current);
		}

		return previous;
	}

}
