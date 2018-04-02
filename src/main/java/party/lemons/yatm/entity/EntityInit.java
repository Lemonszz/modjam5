package party.lemons.yatm.entity;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import party.lemons.yatm.config.ModConstants;

import java.util.Collection;

/**
 * Created by Sam on 31/03/2018.
 */
@Mod.EventBusSubscriber
public class EntityInit
{
	@SubscribeEvent
	public static void registerEntityEntry(RegistryEvent.Register<EntityEntry> event)
	{
		if(allBiomes == null)
		{
			Collection<Biome> allBiomesList = ForgeRegistries.BIOMES.getValuesCollection();
			allBiomes = new Biome[allBiomesList.size()];
			allBiomes = allBiomesList.toArray(allBiomes);
		}

		event.getRegistry().registerAll(
				createEntityEntry("human", EntityHumanInventory.class, 0x124574, 0xABCEFD),
				EntityEntryBuilder.create().entity(EntityHumanDespawnable.class).id(new ResourceLocation(ModConstants.MODID, "human_despawnable"), ind++)
						.name("human_despawnable").tracker(60, 3, false)
						.spawn(EnumCreatureType.AMBIENT, 2, 1, 1, allBiomes)
						.spawn(EnumCreatureType.MONSTER, 40, 1, 1, allBiomes)
						.build(),
				EntityEntryBuilder.create().entity(EntityPlayerLLamaSpit.class).id(new ResourceLocation(ModConstants.MODID, "spit_player"), ind++)
				.name("human_spit").tracker(60, 3, true).build()
		);
	}

	public static Biome[] allBiomes;
	private static int ind = 0;
	public static EntityEntry createEntityEntry(String name, Class clazz, int eggColourPrimary, int eggColourSecondary)
	{
		if(allBiomes == null)
		{
			Collection<Biome> allBiomesList = ForgeRegistries.BIOMES.getValuesCollection();
			allBiomes = new Biome[allBiomesList.size()];
			allBiomes = allBiomesList.toArray(allBiomes);
		}

		return EntityEntryBuilder.create().entity(clazz).id(new ResourceLocation(ModConstants.MODID, name), ind++)
				.name(name).tracker(60, 3, false).egg(eggColourPrimary, eggColourSecondary)
				.spawn(EnumCreatureType.CREATURE, 4, 1, 4, allBiomes)
				.build();
	}
}
