package azathoth.primitive.tileentity;

import azathoth.primitive.Primitive;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IIcon;

import java.util.HashMap;
import java.util.Map;

public class BasketTileEntity extends TileEntity implements IInventory {

	private ItemStack[] inventory;
	private String name;

	public BasketTileEntity() {
		this.inventory = new ItemStack[this.getSizeInventory()];
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.name : "container.basket_tile_entity";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.name != null && !this.name.equals("");
	}

	public IChatComponent getDisplayName() {
		return this.hasCustomInventoryName() ? new ChatComponentText(this.getInventoryName()) : new ChatComponentTranslation(this.getInventoryName());
	}

	@Override
	public int getSizeInventory() {
		return 15;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		if (index < 0 || index >= this.getSizeInventory())
			return null;
		return this.inventory[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		if (this.getStackInSlot(index) != null) {
			ItemStack itemstack;

			if (this.getStackInSlot(index).stackSize <= count) {
				itemstack = this.getStackInSlot(index);
				this.setInventorySlotContents(index, null);
				this.markDirty();
				return itemstack;
			} else {
				itemstack = this.getStackInSlot(index).splitStack(count);

				if (this.getStackInSlot(index).stackSize <= 0) {
					this.setInventorySlotContents(index, null);
				} else {
						//Just to show that changes happened
						this.setInventorySlotContents(index, this.getStackInSlot(index));
				}

				this.markDirty();
				return itemstack;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int index) {
		ItemStack stack = this.getStackInSlot(index);
		this.setInventorySlotContents(index, null);
		return stack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		if (index < 0 || index >= this.getSizeInventory())
			return;

		if (stack != null && stack.stackSize > this.getInventoryStackLimit())
			stack.stackSize = this.getInventoryStackLimit();

		if (stack != null && stack.stackSize == 0)
			stack = null;

		this.inventory[index] = stack;
		this.markDirty();
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && player.getDistanceSq(this.xCoord + 0.5f, this.yCoord + 0.5f, this.zCoord + 0.5f) <= 64;
	}

	@Override
	public void openInventory() {
		// stub
	}

	@Override
	public void closeInventory() {
		// stub
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		NBTTagList list = new NBTTagList();
		for (int i = 0; i < this.getSizeInventory(); ++i) {
			if (this.getStackInSlot(i) != null) {
				NBTTagCompound stackTag = new NBTTagCompound();
				stackTag.setByte("Slot", (byte) i);
				this.getStackInSlot(i).writeToNBT(stackTag);
				list.appendTag(stackTag);
			}
		}
		nbt.setTag("Items", list);

		if (this.hasCustomInventoryName()) {
			nbt.setString("CustomName", this.getCustomName());
		}
	}


	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		NBTTagList list = nbt.getTagList("Items", 10);
		for (int i = 0; i < list.tagCount(); ++i) {
			NBTTagCompound stackTag = list.getCompoundTagAt(i);
			int slot = stackTag.getByte("Slot") & 255;
			this.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(stackTag));
		}

		if (nbt.hasKey("CustomName", 8)) {
			this.setCustomName(nbt.getString("CustomName"));
		}
	}

	public String getCustomName() {
		return this.name;
	}

	public void setCustomName(String name) {
		this.name = name;
	}

	public void markBlockForRenderUpdate() {
		if (this.worldObj.isRemote)
			Minecraft.getMinecraft().renderGlobal.markBlockForRenderUpdate(this.xCoord, this.yCoord, this.zCoord);
	}

	public IIcon getFillIcon() {
		HashMap<HashMap<Block, Integer>, Integer> map = new HashMap<HashMap<Block, Integer>, Integer>();
		Block b = null;
		ItemStack stack;
		Item item;

		for (int i = 0; i < this.getSizeInventory(); i++) {
			stack = this.inventory[i];
			if (stack != null) {
				item = stack.getItem();
				if (item instanceof ItemBlock) {
					b = Block.getBlockFromItem((ItemBlock) item);
					if (b.isOpaqueCube()) {
						HashMap<Block, Integer> submap = new HashMap<Block, Integer>();
						submap.put(b, stack.getItemDamage());
						if (map.containsKey(submap))
							map.put(submap, map.get(submap) + stack.stackSize);
						else
							map.put(submap, stack.stackSize);
					}
				}
			}
		}

		int max = 0;
		int meta = 0;
		if (map.size() > 0) {
			for (Map.Entry<HashMap<Block, Integer>, Integer> entry : map.entrySet()) {
				if (entry.getValue() > max) {
					max = entry.getValue();
					for (Map.Entry<Block, Integer> subentry : entry.getKey().entrySet()) {
						b = subentry.getKey();
						meta = subentry.getValue();
					}
				}
			}
		}

		if (max > 0 && b != null)
			return b.getIcon(1, meta);
		else
			return Primitive.campfire.getIcon(0, 0);
	}

	public float getFilledPercentage() {
		ItemStack stack;
		int max = 0;
		int curr = 0;

		for (int i = 0; i < this.getSizeInventory(); i++) {
			stack = this.inventory[i];
			if (stack != null) {
				max += stack.getMaxStackSize();
				curr += stack.stackSize;
			} else {
				max += 64;
			}
		}

		return (float) curr / (float) max;
	}
}
