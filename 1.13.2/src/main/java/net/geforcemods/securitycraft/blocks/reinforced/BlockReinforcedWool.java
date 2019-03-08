package net.geforcemods.securitycraft.blocks.reinforced;

import java.util.Arrays;
import java.util.Random;

import net.geforcemods.securitycraft.SCContent;
import net.geforcemods.securitycraft.compat.IOverlayDisplay;
import net.geforcemods.securitycraft.tileentity.TileEntityOwnable;
import net.geforcemods.securitycraft.util.BlockUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

//TODO: delete and break up into seperate blocks instantiated with BlockReinforcedBase
public class BlockReinforcedWool extends BlockColored implements ITileEntityProvider, IOverlayDisplay, IReinforcedBlock
{
	public BlockReinforcedWool()
	{
		super(Material.CLOTH);
		setSoundType(SoundType.CLOTH);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityOwnable();
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 1;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(this);
	}

	@Override
	public ItemStack getDisplayStack(World world, IBlockState state, BlockPos pos)
	{
		return new ItemStack(Item.getItemFromBlock(SCContent.reinforcedWool), 1, BlockUtils.getBlockMeta(world, pos));
	}

	@Override
	public boolean shouldShowSCInfo(World world, IBlockState state, BlockPos pos)
	{
		return true;
	}

	@Override
	public Block getVanillaBlock()
	{
		return Arrays.asList(new Block[] {
				Blocks.WOOL
		});
	}
}
