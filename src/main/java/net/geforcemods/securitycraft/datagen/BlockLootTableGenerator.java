package net.geforcemods.securitycraft.datagen;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.geforcemods.securitycraft.SCContent;
import net.geforcemods.securitycraft.blocks.reinforced.ReinforcedDoorBlock;
import net.geforcemods.securitycraft.misc.conditions.TileEntityNBTCondition;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.item.Item;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootContext.EntityTarget;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableManager;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.conditions.EntityHasProperty;
import net.minecraft.loot.conditions.Inverted;
import net.minecraft.loot.conditions.SurvivesExplosion;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.ResourceLocation;

public class BlockLootTableGenerator implements IDataProvider
{
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
	protected final Map<Supplier<Block>,LootTable.Builder> lootTables = new HashMap<>();
	private final DataGenerator generator;

	public BlockLootTableGenerator(DataGenerator generator)
	{
		this.generator = generator;
	}

	private void addTables()
	{
		putStandardBlockLootTable(SCContent.ALARM);
		putMineLootTable(SCContent.ANCIENT_DEBRIS_MINE);
		putStandardBlockLootTable(SCContent.BLOCK_POCKET_MANAGER);
		putStandardBlockLootTable(SCContent.BLOCK_POCKET_WALL);
		putMineLootTable(SCContent.BOUNCING_BETTY);
		putStandardBlockLootTable(SCContent.CAGE_TRAP);
		putStandardBlockLootTable(SCContent.CHISELED_CRYSTAL_QUARTZ);
		putMineLootTable(SCContent.CLAYMORE);
		putMineLootTable(SCContent.COAL_ORE_MINE);
		putMineLootTable(SCContent.COBBLESTONE_MINE);
		putStandardBlockLootTable(SCContent.CRYSTAL_QUARTZ);
		putStandardBlockLootTable(SCContent.CRYSTAL_QUARTZ_PILLAR);
		putStandardBlockLootTable(SCContent.CRYSTAL_QUARTZ_SLAB);
		putMineLootTable(SCContent.DIAMOND_ORE_MINE);
		putMineLootTable(SCContent.DIRT_MINE);
		putMineLootTable(SCContent.EMERALD_ORE_MINE);
		putMineLootTable(SCContent.FURNACE_MINE);
		putMineLootTable(SCContent.GOLD_ORE_MINE);
		putMineLootTable(SCContent.GILDED_BLACKSTONE_MINE);
		putMineLootTable(SCContent.GRAVEL_MINE);
		putStandardBlockLootTable(SCContent.INVENTORY_SCANNER);
		putStandardBlockLootTable(SCContent.IRON_FENCE);
		putMineLootTable(SCContent.IRON_ORE_MINE);
		putStandardBlockLootTable(SCContent.KEYCARD_READER);
		putStandardBlockLootTable(SCContent.KEYPAD_CHEST);
		putDoorLootTable(SCContent.KEYPAD_DOOR, SCContent.KEYPAD_DOOR_ITEM);
		putStandardBlockLootTable(SCContent.FRAME);
		putStandardBlockLootTable(SCContent.KEYPAD_FURNACE);
		putStandardBlockLootTable(SCContent.KEYPAD);
		putMineLootTable(SCContent.LAPIS_ORE_MINE);
		putStandardBlockLootTable(SCContent.LASER_BLOCK);
		putMineLootTable(SCContent.MINE);
		putStandardBlockLootTable(SCContent.MOTION_ACTIVATED_LIGHT);
		putMineLootTable(SCContent.NETHER_GOLD_ORE_MINE);
		putStandardBlockLootTable(SCContent.PANIC_BUTTON);
		putStandardBlockLootTable(SCContent.PORTABLE_RADAR);
		putStandardBlockLootTable(SCContent.PROJECTOR);
		putStandardBlockLootTable(SCContent.PROTECTO);
		putMineLootTable(SCContent.QUARTZ_ORE_MINE);
		putMineLootTable(SCContent.REDSTONE_ORE_MINE);
		putDoorLootTable(SCContent.REINFORCED_DOOR, SCContent.REINFORCED_DOOR_ITEM);
		putStandardBlockLootTable(SCContent.REINFORCED_FENCEGATE);
		lootTables.put(SCContent.REINFORCED_IRON_BARS,
				LootTable.builder()
				.addLootPool(LootPool.builder()
						.rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(SCContent.REINFORCED_IRON_BARS.get())
								.acceptCondition(TileEntityNBTCondition.builder().equals("canDrop", true)))
						.acceptCondition(SurvivesExplosion.builder())));
		putStandardBlockLootTable(SCContent.REINFORCED_IRON_TRAPDOOR);
		putStandardBlockLootTable(SCContent.REINFORCED_HOPPER);
		putStandardBlockLootTable(SCContent.RETINAL_SCANNER);
		putMineLootTable(SCContent.SAND_MINE);
		putDoorLootTable(SCContent.SCANNER_DOOR, SCContent.SCANNER_DOOR_ITEM);
		putStandardBlockLootTable(SCContent.SECRET_ACACIA_SIGN);
		putStandardBlockLootTable(SCContent.SECRET_ACACIA_WALL_SIGN);
		putStandardBlockLootTable(SCContent.SECRET_BIRCH_SIGN);
		putStandardBlockLootTable(SCContent.SECRET_BIRCH_WALL_SIGN);
		putStandardBlockLootTable(SCContent.SECRET_CRIMSON_SIGN);
		putStandardBlockLootTable(SCContent.SECRET_CRIMSON_WALL_SIGN);
		putStandardBlockLootTable(SCContent.SECRET_DARK_OAK_SIGN);
		putStandardBlockLootTable(SCContent.SECRET_DARK_OAK_WALL_SIGN);
		putStandardBlockLootTable(SCContent.SECRET_JUNGLE_SIGN);
		putStandardBlockLootTable(SCContent.SECRET_JUNGLE_WALL_SIGN);
		putStandardBlockLootTable(SCContent.SECRET_OAK_SIGN);
		putStandardBlockLootTable(SCContent.SECRET_OAK_WALL_SIGN);
		putStandardBlockLootTable(SCContent.SECRET_SPRUCE_SIGN);
		putStandardBlockLootTable(SCContent.SECRET_SPRUCE_WALL_SIGN);
		putStandardBlockLootTable(SCContent.SECRET_WARPED_SIGN);
		putStandardBlockLootTable(SCContent.SECRET_WARPED_WALL_SIGN);
		putStandardBlockLootTable(SCContent.SECURITY_CAMERA);
		putStandardBlockLootTable(SCContent.STAIRS_CRYSTAL_QUARTZ);
		putMineLootTable(SCContent.STONE_MINE);
		putMineLootTable(SCContent.TRACK_MINE);
		putStandardBlockLootTable(SCContent.TROPHY_SYSTEM);
		putStandardBlockLootTable(SCContent.USERNAME_LOGGER);
	}

	protected final LootTable.Builder createStandardBlockLootTable(Supplier<Block> block)
	{
		return LootTable.builder()
				.addLootPool(LootPool.builder()
						.rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(block.get()))
						.acceptCondition(SurvivesExplosion.builder()));
	}

	protected final void putDoorLootTable(Supplier<Block> door, Supplier<Item> doorItem)
	{
		lootTables.put(door, LootTable.builder()
				.addLootPool(LootPool.builder()
						.rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(doorItem.get())
								.acceptCondition(BlockStateProperty.builder(door.get())
										.fromProperties(StatePropertiesPredicate.Builder.newBuilder()
												.withProp(ReinforcedDoorBlock.HALF, DoubleBlockHalf.LOWER)))
								.acceptCondition(SurvivesExplosion.builder()))));
	}

	protected final void putStandardBlockLootTable(Supplier<Block> block)
	{
		lootTables.put(block, createStandardBlockLootTable(block));
	}

	protected final void putMineLootTable(Supplier<Block> mine)
	{
		lootTables.put(mine, LootTable.builder()
				.addLootPool(LootPool.builder()
						.rolls(ConstantRange.of(1))
						.addEntry(ItemLootEntry.builder(mine.get()))
						.acceptCondition(SurvivesExplosion.builder())
						.acceptCondition(Inverted.builder(EntityHasProperty.builder(EntityTarget.THIS)))));
	}

	@Override
	public void act(DirectoryCache cache) throws IOException
	{
		Map<ResourceLocation,LootTable> tables = new HashMap<>();

		addTables();

		for(Map.Entry<Supplier<Block>,LootTable.Builder> entry : lootTables.entrySet())
		{
			tables.put(entry.getKey().get().getLootTable(), entry.getValue().setParameterSet(LootParameterSets.BLOCK).build());
		}

		tables.forEach((key, lootTable) -> {
			try
			{
				IDataProvider.save(GSON, cache, LootTableManager.toJson(lootTable), generator.getOutputFolder().resolve("data/" + key.getNamespace() + "/loot_tables/" + key.getPath() + ".json"));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		});
	}

	@Override
	public String getName()
	{
		return "SecurityCraft Block Loot Tables";
	}
}
