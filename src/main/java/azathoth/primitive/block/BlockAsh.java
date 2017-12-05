package azathoth.primitive.block;

import azathoth.primitive.Primitive;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import java.util.Random;

public class BlockAsh extends Block {
	public IIcon[] icons = new IIcon[4];
	public BlockAsh() {
		super(Material.sand);
		this.setStepSound(soundTypeSand);
		this.setHardness(0.5f);
		this.setBlockTextureName(Primitive.MODID + ":ash");
	}

	@Override
	public void registerBlockIcons(IIconRegister r) {
		this.icons[0] = r.registerIcon(this.textureName);
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return icons[0];
	}

	@Override
	public Item getItemDropped(int meta, Random r, int fortune) {
		return Items.coal;
	}

	@Override
	public int damageDropped(int meta) {
		return 1;
	}

	@Override
	public int quantityDropped(int meta, int fortune, Random r) {
		return 7 + r.nextInt(5);
	}
}
