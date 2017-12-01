package azathoth.primitive.tileentity;

import net.minecraft.tileentity.TileEntity;

public class FramedDaubTileEntity extends TileEntity {
	protected short bottom = 0;
	protected short top = 0;
	protected short north = 8 | 256 | 2048;
	protected short south = 2 | 4;
	protected short west = 1 | 4;
	protected short east = 2 | 8;

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

	protected short normalizeFrame(int side, short frame) {
		if (frame == 0)
			return 0;

		if (side == 3 || side == 5) {
			switch (frame) {
				case 4:
					frame = 8;
					break;
				case 8:
					frame = 4;
					break;
				case 64:
					frame = 128;
					break;
				case 128:
					frame = 64;
					break;
				case 256:
					frame = 512;
					break;
				case 512:
					frame = 256;
					break;
				case 1024:
					frame = 2048;
					break;
				case 2048:
					frame = 1204;
					break;
				default:
			}
		}

		return frame;
	}

	public boolean hasFrame(int side, short frame) {
		if (frame == 0)
			return true;

		// frame = normalizeFrame(side, frame);

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

	public void updateFrames(int side, short frame) {
		// frame = normalizeFrame(side, frame);

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
	}

	public void removeFrame(int side, short frame) {
		// frame = normalizeFrame(side, frame);

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
	}
	
	public void removeAllFrames() {
		this.bottom = 0;
		this.top = 0;
		this.north = 0;
		this.south = 0;
		this.west = 0;
		this.east = 0;
	}
}
