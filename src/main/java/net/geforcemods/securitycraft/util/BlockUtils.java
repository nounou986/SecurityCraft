package net.geforcemods.securitycraft.util;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

import net.geforcemods.securitycraft.SCContent;
import net.geforcemods.securitycraft.api.IModuleInventory;
import net.geforcemods.securitycraft.api.IOwnable;
import net.geforcemods.securitycraft.api.OwnableTileEntity;
import net.geforcemods.securitycraft.api.Owner;
import net.geforcemods.securitycraft.blocks.KeycardReaderBlock;
import net.geforcemods.securitycraft.blocks.KeypadBlock;
import net.geforcemods.securitycraft.blocks.LaserBlock;
import net.geforcemods.securitycraft.blocks.RetinalScannerBlock;
import net.geforcemods.securitycraft.blocks.reinforced.ReinforcedButtonBlock;
import net.geforcemods.securitycraft.blocks.reinforced.ReinforcedLeverBlock;
import net.geforcemods.securitycraft.blocks.reinforced.ReinforcedPressurePlateBlock;
import net.geforcemods.securitycraft.misc.ModuleType;
import net.geforcemods.securitycraft.tileentity.InventoryScannerTileEntity;
import net.geforcemods.securitycraft.tileentity.KeypadChestTileEntity;
import net.geforcemods.securitycraft.tileentity.KeypadFurnaceTileEntity;
import net.geforcemods.securitycraft.tileentity.KeypadTileEntity;
import net.geforcemods.securitycraft.tileentity.PortableRadarTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.Property;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class BlockUtils{
	private static final List<Block> PRESSURE_PLATES = Arrays.asList(new Block[] {
			SCContent.REINFORCED_STONE_PRESSURE_PLATE.get(),
			SCContent.REINFORCED_OAK_PRESSURE_PLATE.get(),
			SCContent.REINFORCED_SPRUCE_PRESSURE_PLATE.get(),
			SCContent.REINFORCED_BIRCH_PRESSURE_PLATE.get(),
			SCContent.REINFORCED_JUNGLE_PRESSURE_PLATE.get(),
			SCContent.REINFORCED_ACACIA_PRESSURE_PLATE.get(),
			SCContent.REINFORCED_DARK_OAK_PRESSURE_PLATE.get(),
			SCContent.REINFORCED_CRIMSON_PRESSURE_PLATE.get(),
			SCContent.REINFORCED_WARPED_PRESSURE_PLATE.get(),
			SCContent.REINFORCED_POLISHED_BLACKSTONE_PRESSURE_PLATE.get()
	});
	private static final List<Block> BUTTONS = Arrays.asList(new Block[]{
			SCContent.REINFORCED_STONE_BUTTON.get(),
			SCContent.REINFORCED_OAK_BUTTON.get(),
			SCContent.REINFORCED_SPRUCE_BUTTON.get(),
			SCContent.REINFORCED_BIRCH_BUTTON.get(),
			SCContent.REINFORCED_JUNGLE_BUTTON.get(),
			SCContent.REINFORCED_ACACIA_BUTTON.get(),
			SCContent.REINFORCED_DARK_OAK_BUTTON.get(),
			SCContent.REINFORCED_CRIMSON_BUTTON.get(),
			SCContent.REINFORCED_WARPED_BUTTON.get(),
			SCContent.REINFORCED_POLISHED_BLACKSTONE_BUTTON.get()
	});

	public static boolean isSideSolid(IWorldReader world, BlockPos pos, Direction side)
	{
		return world.getBlockState(pos).isSolidSide(world, pos, side);
	}

	/**
	 * Updates a block and notify's neighboring blocks of a change.
	 */
	public static void updateAndNotify(World world, BlockPos pos, Block block, int delay, boolean shouldUpdate){
		if(shouldUpdate)
			world.getPendingBlockTicks().scheduleTick(pos, block, delay);

		world.notifyNeighborsOfStateChange(pos, block);
	}

	public static Block getBlock(IBlockReader world, BlockPos pos){
		return world.getBlockState(pos).getBlock();
	}

	public static Block getBlock(World world, int x, int y, int z){
		return world.getBlockState(toPos(x, y, z)).getBlock();
	}

	public static void setBlockProperty(World world, BlockPos pos, BooleanProperty property, boolean value) {
		setBlockProperty(world, pos, property, value, false);
	}

	public static void setBlockProperty(World world, BlockPos pos, BooleanProperty property, boolean value, boolean retainOldTileEntity) {
		BlockState state = world.getBlockState(pos);

		if(!state.hasProperty(property))
			return;

		if(retainOldTileEntity){
			CompoundNBT modules = null;
			String password = "";
			Owner owner = null;
			int cooldown = -1;

			if(world.getTileEntity(pos) instanceof IModuleInventory)
				modules = ((IModuleInventory) world.getTileEntity(pos)).writeModuleInventory(new CompoundNBT());

			if(world.getTileEntity(pos) instanceof OwnableTileEntity && ((OwnableTileEntity) world.getTileEntity(pos)).getOwner() != null)
				owner = ((OwnableTileEntity) world.getTileEntity(pos)).getOwner();

			if(world.getTileEntity(pos) instanceof KeypadTileEntity && ((KeypadTileEntity) world.getTileEntity(pos)).getPassword() != null)
				password = ((KeypadTileEntity) world.getTileEntity(pos)).getPassword();

			if(world.getTileEntity(pos) instanceof KeypadFurnaceTileEntity && ((KeypadFurnaceTileEntity) world.getTileEntity(pos)).getPassword() != null)
				password = ((KeypadFurnaceTileEntity) world.getTileEntity(pos)).getPassword();

			if(world.getTileEntity(pos) instanceof KeypadChestTileEntity && ((KeypadChestTileEntity) world.getTileEntity(pos)).getPassword() != null)
				password = ((KeypadChestTileEntity) world.getTileEntity(pos)).getPassword();

			if(world.getTileEntity(pos) instanceof PortableRadarTileEntity && ((PortableRadarTileEntity) world.getTileEntity(pos)).getAttackCooldown() != 0)
				cooldown = ((PortableRadarTileEntity) world.getTileEntity(pos)).getAttackCooldown();

			TileEntity tileEntity = world.getTileEntity(pos);
			world.setBlockState(pos, state.with(property, value));
			world.setTileEntity(pos, tileEntity);

			if(modules != null)
				((IModuleInventory) world.getTileEntity(pos)).readModuleInventory(modules);

			if(owner != null)
				((OwnableTileEntity) world.getTileEntity(pos)).getOwner().set(owner);

			if(!password.isEmpty() && world.getTileEntity(pos) instanceof KeypadTileEntity)
				((KeypadTileEntity) world.getTileEntity(pos)).setPassword(password);

			if(!password.isEmpty() && world.getTileEntity(pos) instanceof KeypadFurnaceTileEntity)
				((KeypadFurnaceTileEntity) world.getTileEntity(pos)).setPassword(password);

			if(!password.isEmpty() && world.getTileEntity(pos) instanceof KeypadChestTileEntity)
				((KeypadChestTileEntity) world.getTileEntity(pos)).setPassword(password);

			if(cooldown != -1 && world.getTileEntity(pos) instanceof PortableRadarTileEntity)
				((PortableRadarTileEntity) world.getTileEntity(pos)).setAttackCooldown(cooldown);
		}
		else
			world.setBlockState(pos, state.with(property, value));
	}

	public static void setBlockProperty(World world, BlockPos pos, IntegerProperty property, int value) {
		BlockState state = world.getBlockState(pos);

		if(state.hasProperty(property))
			world.setBlockState(pos, state.with(property, value));
	}

	public static <T extends Comparable<T>> T getBlockProperty(World world, BlockPos pos, Property<T> property){
		return world.getBlockState(pos).get(property);
	}

	/**
	 * returns an AABB with corners x1, y1, z1 and x2, y2, z2
	 */
	public static AxisAlignedBB fromBounds(double x1, double y1, double z1, double x2, double y2, double z2)
	{
		double d6 = Math.min(x1, x2);
		double d7 = Math.min(y1, y2);
		double d8 = Math.min(z1, z2);
		double d9 = Math.max(x1, x2);
		double d10 = Math.max(y1, y2);
		double d11 = Math.max(z1, z2);
		return new AxisAlignedBB(d6, d7, d8, d9, d10, d11);
	}

	public static BlockPos toPos(int x, int y, int z){
		return new BlockPos(x, y, z);
	}

	public static int[] fromPos(BlockPos pos){
		return new int[]{pos.getX(), pos.getY(), pos.getZ()};
	}

	public static boolean hasActiveSCBlockNextTo(World world, BlockPos pos)
	{
		TileEntity thisTile = world.getTileEntity(pos);

		return hasActiveSCBlockNextTo(world, pos, thisTile, SCContent.LASER_BLOCK.get(), true, (state, te) -> state.get(LaserBlock.POWERED)) ||
				hasActiveSCBlockNextTo(world, pos, thisTile, SCContent.RETINAL_SCANNER.get(), true, (state, te) -> state.get(RetinalScannerBlock.POWERED)) ||
				hasActiveSCBlockNextTo(world, pos, thisTile, SCContent.KEYPAD.get(), true, (state, te) -> state.get(KeypadBlock.POWERED)) ||
				hasActiveSCBlockNextTo(world, pos, thisTile, SCContent.KEYCARD_READER.get(), true, (state, te) -> state.get(KeycardReaderBlock.POWERED)) ||
				hasActiveSCBlockNextTo(world, pos, thisTile, SCContent.INVENTORY_SCANNER.get(), true, (state, te) -> ((InventoryScannerTileEntity)te).hasModule(ModuleType.REDSTONE) && ((InventoryScannerTileEntity)te).shouldProvidePower()) ||
				hasActiveSCBlockNextTo(world, pos, thisTile, null, false, (state, te) -> PRESSURE_PLATES.contains(state.getBlock()) && state.get(ReinforcedPressurePlateBlock.POWERED)) ||
				hasActiveSCBlockNextTo(world, pos, thisTile, null, false, (state, te) -> BUTTONS.contains(state.getBlock()) && state.get(ReinforcedButtonBlock.POWERED)) ||
				hasActiveSCBlockNextTo(world, pos, thisTile, SCContent.REINFORCED_LEVER.get(), true, (state, te) -> state.get(ReinforcedLeverBlock.POWERED));
	}

	private static boolean hasActiveSCBlockNextTo(World world, BlockPos pos, TileEntity te, Block block, boolean checkForBlock, BiFunction<BlockState,TileEntity,Boolean> extraCondition)
	{
		for(Direction dir : Direction.values())
		{
			BlockPos offsetPos = pos.offset(dir);
			BlockState offsetState = world.getBlockState(offsetPos);

			if(!checkForBlock || offsetState.getBlock() == block)
			{
				TileEntity offsetTe = world.getTileEntity(offsetPos);

				if(extraCondition.apply(offsetState, offsetTe))
					return ((IOwnable)offsetTe).getOwner().owns((IOwnable)te);
			}

			if(world.getRedstonePower(offsetPos, dir) == 15 && !offsetState.canProvidePower())
			{
				for(Direction dirOffset : Direction.values())
				{
					if(dirOffset.getOpposite() == dir) //skip this, as it would just go back to the original position
						continue;

					BlockPos newOffsetPos = offsetPos.offset(dirOffset);

					offsetState = world.getBlockState(newOffsetPos);

					if(!checkForBlock || offsetState.getBlock() == block)
					{
						//checking that e.g. a lever/button is correctly attached to the block
						if(offsetState.hasProperty(BlockStateProperties.FACE) && offsetState.hasProperty(BlockStateProperties.HORIZONTAL_FACING))
						{
							Axis offsetAxis = dirOffset.getAxis();
							Direction offsetFacing = offsetState.get(BlockStateProperties.HORIZONTAL_FACING);
							AttachFace offsetAttachFace = offsetState.get(BlockStateProperties.FACE);

							switch(offsetAxis)
							{
								case X: case Z:
									if(offsetAttachFace != AttachFace.WALL || dirOffset != offsetFacing)
										return false;
									break;
								case Y:
									if((dirOffset == Direction.UP && offsetAttachFace != AttachFace.FLOOR) || (dirOffset == Direction.DOWN && offsetAttachFace != AttachFace.CEILING))
										return false;
									break;
							}
						}

						TileEntity offsetTe = world.getTileEntity(newOffsetPos);

						if(extraCondition.apply(offsetState, offsetTe))
							return ((IOwnable)offsetTe).getOwner().owns((IOwnable) te);
					}
				}
			}
		}

		return false;
	}
}
