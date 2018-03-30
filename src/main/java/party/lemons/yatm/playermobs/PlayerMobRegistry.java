package party.lemons.yatm.playermobs;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import party.lemons.yatm.config.ModConstants;

/**
 * Created by Sam on 31/03/2018.
 */
@Mod.EventBusSubscriber
public class PlayerMobRegistry
{
	public static IForgeRegistry<PlayerMob> REGISTRY;

	@SubscribeEvent
	public static void onCreateRegistry(RegistryEvent.NewRegistry event)
	{
		REGISTRY =
				new RegistryBuilder<PlayerMob>()
				.setType(PlayerMob.class)
				.setName(new ResourceLocation(ModConstants.MODID, "playermobs"))
				.create();
	}

	public static PlayerMob fromString(String mob)
	{
		for(PlayerMob m : REGISTRY.getValuesCollection())
		{
			if(m.getRegistryName().toString().equalsIgnoreCase(mob))
				return m;
		}

		return PlayerMobs.PLAYER;
	}
}
