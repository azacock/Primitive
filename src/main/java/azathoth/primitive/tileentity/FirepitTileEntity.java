package azathoth.primitive.tileentity;

import azathoth.primitive.Primitive;
import azathoth.primitive.item.PrimitiveItems;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class FirepitTileEntity extends TileEntity {
	protected int fuel = 0;
	protected int fuel_max = 20 * 60;
	protected boolean lit = false;
	protected boolean used = false;

	public boolean queried = false;
	public boolean counted = false;
	public boolean fuel_set = false;

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("fuel", this.fuel);
		tag.setInteger("fuel_max", this.fuel_max);
		tag.setBoolean("lit", this.lit);
		tag.setBoolean("used", this.used);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		this.fuel = tag.getInteger("fuel");
		this.fuel_max = tag.getInteger("fuel_max");
		this.lit = tag.getBoolean("lit");
		this.used = tag.getBoolean("used");
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound tag = new NBTTagCompound();
		this.writeToNBT(tag);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		this.readFromNBT(packet.func_148857_g());
	}

	@Override
	public void updateEntity() {
		if (this.lit) {
			float stage = (float) Math.ceil((double) ((float) this.getFuel() / (float) this.getMaxFuel()) * 4d) / 4f;
			this.fuel -= 1;
			if (this.fuel <= 0) {
				this.extinguish();
				this.markBlockForUpdate();
			} else if ((float) Math.ceil((double) ((float) this.getFuel() / (float) this.getMaxFuel()) * 4d) / 4f < stage) {
				this.markBlockForUpdate();
			}
			this.markDirty();
		}
		this.queried = false;
		this.counted = false;
		this.fuel_set = false;
	}

	public void markBlockForUpdate() {
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		if (this.worldObj.isRemote)
			Minecraft.getMinecraft().renderGlobal.markBlockForRenderUpdate(this.xCoord, this.yCoord, this.zCoord);
	}

	public boolean addFuelFromItem(ItemStack stack) {
		int f = this.getFuelValue(stack);
		if (f == 0) // fuel had a value of 0, not valid fuel
			return false;
		return this.addFuel(f);
	}

	public boolean addFuel(int fuel) {
		int f = this.fuel + fuel;

		Primitive.logger.info(f);
		Primitive.logger.info(this.fuel_max);

		if (f > this.fuel_max)
			return false; // fuel would put us over the maximum, return false so as not to waste fuel
		else if (f < 0)
			f = 0;
		this.fuel = f;
		this.equalize();
		this.ignite();
		this.markBlockForUpdate();
		this.markDirty();
		return true;
	}

	public int getFuel() {
		return this.fuel;
	}

	public int getMaxFuel() {
		return this.fuel_max;
	}

	public void setMaxFuel(int fuel) {
		if (fuel > 0)
			this.fuel_max = fuel;
	}

	public int getFuelValue(ItemStack stack) {
		int mult = 4;
		int value = 0;
		Primitive.logger.info(stack.getItem());
		Primitive.logger.info(Items.coal);
		if (stack.getItem() == Items.stick)
			value = 100;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.sapling))
			value = 100;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.log))
			value = 300;
		else if (stack.getItem() == Item.getItemFromBlock(Blocks.planks))
			value = 300;
		else if (stack.getItem() == Items.coal)
			value = 1600;
		return value * mult;
	}

	public boolean isLit() {
		return this.lit;
	}

	public boolean isUsed() {
		return this.used;
	}

	public boolean isValidNeighbor(int x, int y, int z) {
		Block b = this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord);
		int meta = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
		if (this.worldObj.getBlock(x, y, z) == b && this.worldObj.getBlockMetadata(x, y, z) == meta) {
			TileEntity te = this.worldObj.getTileEntity(x, y, z);
			if (te != null && te instanceof FirepitTileEntity)
				return true;
		}
		return false;
	}

	public boolean ignite() {
		if (this.lit || this.fuel <= 0)
			return false;

		FirepitTileEntity pit;

		this.lit = true;
		this.used = true;

		this.markBlockForUpdate();

		if (this.isValidNeighbor(this.xCoord, this.yCoord, this.zCoord + 1)) {
			pit = (FirepitTileEntity) this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord + 1);
			if (!pit.isLit())
				pit.ignite();
		}

		if (this.isValidNeighbor(this.xCoord, this.yCoord, this.zCoord - 1)) {
			pit = (FirepitTileEntity) this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord - 1);
			if (!pit.isLit())
				pit.ignite();
		}

		if (this.isValidNeighbor(this.xCoord + 1, this.yCoord, this.zCoord)) {
			pit = (FirepitTileEntity) this.worldObj.getTileEntity(this.xCoord + 1, this.yCoord, this.zCoord);
			if (!pit.isLit())
				pit.ignite();
		}

		if (this.isValidNeighbor(this.xCoord - 1, this.yCoord, this.zCoord)) {
			pit = (FirepitTileEntity) this.worldObj.getTileEntity(this.xCoord - 1, this.yCoord, this.zCoord);
			if (!pit.isLit())
				pit.ignite();
		}

		this.markDirty();

		return true;
	}

	public boolean extinguish() {
		if (!this.lit)
			return false;
		this.lit = false;
		this.markDirty();
		this.markBlockForUpdate();
		return true;
	}

	public void setFuel(int fuel) {
		this.fuel = fuel;
		this.markDirty();
		this.markBlockForUpdate();
	}

	public int getTotalFuel() {
		FirepitTileEntity pit;

		int f = this.fuel;
		this.queried = true;

		if (this.isValidNeighbor(this.xCoord, this.yCoord, this.zCoord + 1)) {
			pit = (FirepitTileEntity) this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord + 1);
			if (!pit.queried)
				f += pit.getTotalFuel();
		}

		if (this.isValidNeighbor(this.xCoord, this.yCoord, this.zCoord - 1)) {
			pit = (FirepitTileEntity) this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord - 1);
			if (!pit.queried)
				f += pit.getTotalFuel();
		}

		if (this.isValidNeighbor(this.xCoord + 1, this.yCoord, this.zCoord)) {
			pit = (FirepitTileEntity) this.worldObj.getTileEntity(this.xCoord + 1, this.yCoord, this.zCoord);
			if (!pit.queried)
				f += pit.getTotalFuel();
		}

		if (this.isValidNeighbor(this.xCoord - 1, this.yCoord, this.zCoord)) {
			pit = (FirepitTileEntity) this.worldObj.getTileEntity(this.xCoord - 1, this.yCoord, this.zCoord);
			if (!pit.queried)
				f += pit.getTotalFuel();
		}

		return f;
	}

	public int getNodeCount() {
		TileEntity te;
		FirepitTileEntity pit;

		int c = 1;
		this.counted = true;

		if (this.isValidNeighbor(this.xCoord, this.yCoord, this.zCoord + 1)) {
			pit = (FirepitTileEntity) this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord + 1);
			if (!pit.counted)
				c += pit.getNodeCount();
		}

		if (this.isValidNeighbor(this.xCoord, this.yCoord, this.zCoord - 1)) {
			pit = (FirepitTileEntity) this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord - 1);
			if (!pit.counted)
				c += pit.getNodeCount();
		}

		if (this.isValidNeighbor(this.xCoord + 1, this.yCoord, this.zCoord)) {
			pit = (FirepitTileEntity) this.worldObj.getTileEntity(this.xCoord + 1, this.yCoord, this.zCoord);
			if (!pit.counted)
				c += pit.getNodeCount();
		}

		if (this.isValidNeighbor(this.xCoord - 1, this.yCoord, this.zCoord)) {
			pit = (FirepitTileEntity) this.worldObj.getTileEntity(this.xCoord - 1, this.yCoord, this.zCoord);
			if (!pit.counted)
				c += pit.getNodeCount();
		}

		return c;
	}
	
	public void propagateFuel(int fuel) {
		TileEntity te;
		FirepitTileEntity pit;

		this.fuel = fuel;
		this.fuel_set = true;
		this.markDirty();
		this.markBlockForUpdate();

		if (this.isValidNeighbor(this.xCoord, this.yCoord, this.zCoord + 1)) {
			pit = (FirepitTileEntity) this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord + 1);
			if (!pit.fuel_set)
				pit.propagateFuel(fuel);
		}

		if (this.isValidNeighbor(this.xCoord, this.yCoord, this.zCoord - 1)) {
			pit = (FirepitTileEntity) this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord - 1);
			if (!pit.fuel_set)
				pit.propagateFuel(fuel);
		}

		if (this.isValidNeighbor(this.xCoord + 1, this.yCoord, this.zCoord)) {
			pit = (FirepitTileEntity) this.worldObj.getTileEntity(this.xCoord + 1, this.yCoord, this.zCoord);
			if (!pit.fuel_set)
				pit.propagateFuel(fuel);
		}

		if (this.isValidNeighbor(this.xCoord - 1, this.yCoord, this.zCoord)) {
			pit = (FirepitTileEntity) this.worldObj.getTileEntity(this.xCoord - 1, this.yCoord, this.zCoord);
			if (!pit.fuel_set)
				pit.propagateFuel(fuel);
		}
	}

	public void resetEqualization() {
		TileEntity te;
		FirepitTileEntity pit;

		this.fuel_set = false;
		this.queried = false;
		this.counted = false;

		if (this.isValidNeighbor(this.xCoord, this.yCoord, this.zCoord + 1)) {
			pit = (FirepitTileEntity) this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord + 1);
			if (pit.counted)
				pit.resetEqualization();
		}

		if (this.isValidNeighbor(this.xCoord, this.yCoord, this.zCoord - 1)) {
			pit = (FirepitTileEntity) this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord - 1);
			if (pit.counted)
				pit.resetEqualization();
		}

		if (this.isValidNeighbor(this.xCoord + 1, this.yCoord, this.zCoord)) {
			pit = (FirepitTileEntity) this.worldObj.getTileEntity(this.xCoord + 1, this.yCoord, this.zCoord);
			if (pit.counted)
				pit.resetEqualization();
		}

		if (this.isValidNeighbor(this.xCoord - 1, this.yCoord, this.zCoord)) {
			pit = (FirepitTileEntity) this.worldObj.getTileEntity(this.xCoord - 1, this.yCoord, this.zCoord);
			if (pit.counted)
				pit.resetEqualization();
		}
	}

	public void equalize() {
		int fuel = this.getTotalFuel() / this.getNodeCount();
		this.propagateFuel(fuel);
		this.resetEqualization();
	}
}
