package azathoth.primitive.tileentity;

import azathoth.primitive.Primitive;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class FramedDaubTileEntity extends TileEntity {
	protected short bottom = 0;
	protected short top = 0;
	protected short north = 0;
	protected short south = 0;
	protected short west = 0;
	protected short east = 0;

	/*
	 * 1 = vertical
	 * 2 = horizontal
	 * 4 = diagonal left
	 * 8 = diagonal right
	 * 16 = edge top
	 * 32 = edge bottom
	 * 64 = edge left
	 * 128 = edge right
	 * 256 = corner top left
	 * 512 = corner top right
	 * 1024 = corner bottom left
	 * 2048 = corner bottom right
	 */

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setShort("bottom", this.bottom);
		tag.setShort("top", this.top);
		tag.setShort("north", this.north);
		tag.setShort("south", this.south);
		tag.setShort("west", this.west);
		tag.setShort("east", this.east);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		this.bottom = tag.getShort("bottom");
		this.top = tag.getShort("top");
		this.north = (short) tag.getShort("north");
		this.south = tag.getShort("south");
		this.west = tag.getShort("west");
		this.east = tag.getShort("east");
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

	// @Override
	// public void updateEntity() {
		
	// }

	public boolean hasFrame(int side, short frame) {
		if (frame == 0)
			return true;

		switch (side) {
			case 1:
				return (this.top & frame) != 0;
			case 2:
				return (this.north & frame) != 0;
			case 3:
				return (this.south & frame) != 0;
			case 4:
				return (this.west & frame) != 0;
			case 5:
				return (this.east & frame) != 0;
			default:
				return (this.bottom & frame) != 0;
		}
	}

	public boolean updateFrames(int side, short frame) {
		short remove;
		switch (frame) {
			case 4:
				this.removeFrame(side, (short) (256 | 2048));
				break;
			case 8:
				this.removeFrame(side, (short) (512 | 1024));
				break;
			case 16:
				this.removeFrame(side, (short) (256 | 512));
				break;
			case 32:
				this.removeFrame(side, (short) (1024 | 2048));
				break;
			case 64:
				this.removeFrame(side, (short) (256 | 1024));
				break;
			case 128:
				this.removeFrame(side, (short) (512 | 2048));
				break;
			case 256:
				if (this.hasFrame(side, (short) (4 | 16 | 64)))
					return false;
				break;
			case 512:
				if (this.hasFrame(side, (short) (8 | 16 | 128)))
					return false;
				break;
			case 1024:
				if (this.hasFrame(side, (short) (8 | 32 | 64)))
					return false;
				break;
			case 2048:
				if (this.hasFrame(side, (short) (4 | 32 | 128)))
					return false;
				break;
		}

		switch (side) {
			case 1:
				this.top |= frame;
				break;
			case 2:
				this.north |= frame;
				break;
			case 3:
				this.south |= frame;
				break;
			case 4:
				this.west |= frame;
				break;
			case 5:
				this.east |= frame;
				break;
			default:
				this.bottom |= frame;
		}

		this.markDirty();
		return true;
	}

	public void removeFrame(int side, short frame) {
		switch (side) {
			case 1:
				this.top &= ~frame;
				break;
			case 2:
				this.north &= ~frame;
				break;
			case 3:
				this.south &= ~frame;
				break;
			case 4:
				this.west &= ~frame;
				break;
			case 5:
				this.east &= ~frame;
				break;
			default:
				this.bottom &= ~frame;
		}
		this.markDirty();
	}

	public void removeFrames(int side) {
		switch (side) {
			case 1:
				this.top = 0;
				break;
			case 2:
				this.north = 0;
				break;
			case 3:
				this.south = 0;
				break;
			case 4:
				this.west = 0;
				break;
			case 5:
				this.east = 0;
				break;
			default:
				this.bottom = 0;
		}
		this.markDirty();
	}
	
	public void removeAllFrames() {
		this.bottom = 0;
		this.top = 0;
		this.north = 0;
		this.south = 0;
		this.west = 0;
		this.east = 0;
		this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, Primitive.daub);
		this.markDirty();
	}
}
