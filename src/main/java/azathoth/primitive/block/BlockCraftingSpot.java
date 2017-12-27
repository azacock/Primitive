package azathoth.primitive.block;

import azathoth.primitive.Primitive;
import azathoth.primitive.client.render.PrimitiveRenderers;
import azathoth.primitive.network.PrimitiveGuiHandler;

import net.minecraft.block.BlockWorkbench;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockCraftingSpot extends BlockWorkbench {
	public IIcon icon;
	
	public BlockCraftingSpot() {
		super();
		this.setStepSound(soundTypeGravel);
		this.setBlockBounds(0f, 0f, 0f, 1f, 1f / 16f, 1f);
		this.setHardness(0.2f);
	}

	@Override
	public void registerBlockIcons(IIconRegister r) {
		this.icon = r.registerIcon(Primitive.MODID + ":crafting_spot");
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		if (meta == 1)
			return this.icon;
		else
			return Blocks.dirt.getIcon(0, 0);
	}

	@Override
	public int getRenderType() {
		return PrimitiveRenderers.rendererCraftingSpot;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float hitx, float hity, float hitz) {
		if (!world.isRemote) {
			player.openGui(Primitive.instance, PrimitiveGuiHandler.CRAFTING_SPOT_GUI, world, x, y, z);
		}
		return true;
	}

}
