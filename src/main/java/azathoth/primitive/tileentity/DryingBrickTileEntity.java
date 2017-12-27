package azathoth.primitive.tileentity;

import azathoth.primitive.Primitive;
import azathoth.primitive.item.PrimitiveItems;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class DryingBrickTileEntity extends TileEntity {
	protected short tick_treshold = 20 * 60;
	protected short ticks = 0;
	protected byte[] bricks = new byte[6];
	// for each brick, 0 represents no brick, 1 represents a dry brick, >1 represets wetness levels

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		for (int i = 0; i < 6; i++) {
			tag.setByte(Integer.toString(i), this.bricks[i]);
		}
		tag.setShort("ticks", this.ticks);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		for (int i = 0; i < 6; i++) {
			this.bricks[i] = tag.getByte(Integer.toString(i));
		}
		this.ticks = tag.getShort("ticks");
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
		if (this.ticks < this.tick_treshold) {
			this.ticks++;
			return;
		}

		this.ticks = 0;

		if (this.worldObj.getBlockLightValue(this.xCoord, this.yCoord, this.zCoord) < 13 || !this.worldObj.canBlockSeeTheSky(this.xCoord, this.yCoord, this.zCoord))
			return;

		for (int i = 0; i < 6; i++) {
			if (this.bricks[i] > 1) {
				this.bricks[i]--;
				if (this.worldObj.isRemote) {
					Minecraft.getMinecraft().renderGlobal.markBlockForRenderUpdate(this.xCoord, this.yCoord, this.zCoord);
				}
			}
		}
	}

	public byte getBrickState(int i) {
		return this.bricks[i];
	}

	public boolean addBrick() {
		for (int i = 0; i < 6; i++) {
			if (this.bricks[i] == 0) {
				this.bricks[i] = 5;
				if (this.worldObj.isRemote) {
					Minecraft.getMinecraft().renderGlobal.markBlockForRenderUpdate(this.xCoord, this.yCoord, this.zCoord);
				}
				return true;
			}
		}
		return false;
	}

	public boolean removeBrick() {
		boolean r = false;
		for (int i = 0; i < 6; i++) {
			if (this.bricks[i] == 1) {
				this.bricks[i] = 0;
				if (this.worldObj.isRemote) {
					Minecraft.getMinecraft().renderGlobal.markBlockForRenderUpdate(this.xCoord, this.yCoord, this.zCoord);
				}
				r = true;
				break;
			}
		}

		int n = 0;
		for (int i = 0; i < 6; i++) {
			n += this.bricks[i];
		}

		if (n == 0 && !this.worldObj.isRemote)
			this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, Blocks.air);

		return r;
	}

	public void dropBricks() {
		for (int i = 0; i < 6; i++) {
			if (this.bricks[i] > 0) {
				EntityItem e;
				if (this.bricks[i] == 1)
					e = new EntityItem(this.worldObj, this.xCoord, this.yCoord, this.zCoord, new ItemStack(PrimitiveItems.brick, 1, 1));
				else
					e = new EntityItem(this.worldObj, this.xCoord, this.yCoord, this.zCoord, new ItemStack(PrimitiveItems.brick, 1, 0));
				this.worldObj.spawnEntityInWorld(e);
			}
		}
	}
}
