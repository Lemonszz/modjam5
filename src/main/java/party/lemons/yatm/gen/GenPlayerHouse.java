package party.lemons.yatm.gen;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockLog;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraftforge.fml.common.IWorldGenerator;
import party.lemons.yatm.config.ModConstants;
import party.lemons.yatm.entity.EntityHuman;
import party.lemons.yatm.entity.EntityHumanInventory;

import java.util.Map;
import java.util.Random;

/**
 * Created by Sam on 31/03/2018.
 */
public class GenPlayerHouse implements IWorldGenerator
{
	private static final ResourceLocation[] LOCATIONS = new ResourceLocation[]
			{
					new ResourceLocation(ModConstants.MODID, "player_house_1"),
					new ResourceLocation(ModConstants.MODID, "player_house_2")
			};


	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{
		if(world.getBiome(new BlockPos(8 + (chunkX * 16), 100, 8 + (chunkZ * 16))) == Biomes.HELL ||  random.nextInt(75) != 0)
			return;

		final BlockPos.MutableBlockPos basePos = new BlockPos.MutableBlockPos(8 + (chunkX * 16 + random.nextInt(16)), 100, 8 + (chunkZ * 16 + random.nextInt(16)));
		basePos.setY(world.getTopSolidOrLiquidBlock(basePos).getY() - 1);
		if(world.getBlockState(basePos).getBlock() instanceof BlockLiquid || world.getBlockState(basePos.up()).getBlock() instanceof BlockLiquid)
			return;

		while(world.getBlockState(basePos).getBlock() instanceof BlockLeaves || world.getBlockState(basePos).getBlock() instanceof BlockLog)
		{
			basePos.setY(basePos.getY() - 1);
		}

		final PlacementSettings settings = new PlacementSettings();
		final Template template = world.getSaveHandler().getStructureTemplateManager().getTemplate(world.getMinecraftServer(), LOCATIONS[random.nextInt(LOCATIONS.length)]);
		settings.setRotation(Rotation.values()[random.nextInt(Rotation.values().length)]);

		for(int i = 0; i < 10; i++)
		{
			if(world.isAirBlock(basePos.offset(EnumFacing.DOWN, i).offset(EnumFacing.EAST, 5).offset(EnumFacing.SOUTH, 5)))
			{
				return;
			}
		}

		template.addBlocksToWorld(world, basePos, settings);

		Map<BlockPos, String> dataBlocks = template.getDataBlocks(basePos, settings);
		for(Map.Entry<BlockPos, String> entry : dataBlocks.entrySet())
		{
			String[] data = entry.getValue().split(" ");
			if(data.length == 0)
				return;


			BlockPos pos = entry.getKey();
			if(data[0].equalsIgnoreCase("furnace"))
			{
				TileEntity furnace = world.getTileEntity(pos.down());
				if(furnace != null)
				{
					((TileEntityFurnace)furnace).setInventorySlotContents(2, new ItemStack(Items.IRON_INGOT, 1 + random.nextInt(10)));
				}
			}
			else if(data[0].equalsIgnoreCase("entity_player"))
			{
				EntityHumanInventory human = new EntityHumanInventory(world);
				human.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
				world.spawnEntity(human);

			}
			else if(data[0].equalsIgnoreCase("chest_player"))
			{
				world.setBlockToAir(pos.down());
				world.setBlockState(pos.down(), Blocks.CHEST.getDefaultState());
				TileEntity chest = world.getTileEntity(pos.down());
				if(chest != null)
				{
					if(chest instanceof TileEntityLockableLoot)
					{
						String[] loot = new String[]
								{
										"spawn_bonus_chest",
										"woodland_mansion",
										"stronghold_corridor"
								};

						fillWithLoot(pos.down(), (TileEntityLockableLoot) chest, new ResourceLocation(loot[random.nextInt(loot.length)]));
					}
				}
			}


			world.setBlockToAir(pos);
		}
	}

	public void fillWithLoot(BlockPos pos, TileEntityLockableLoot te, ResourceLocation loot)
	{
		if(te.getWorld().isRemote)
			return;

			LootTable loottable = te.getWorld().getLootTableManager().getLootTableFromLocation(loot);
			Random random = new Random();

			LootContext.Builder lootcontext$builder = new LootContext.Builder((WorldServer)te.getWorld());


			loottable.fillInventory(te, random, lootcontext$builder.build());
	}
}
