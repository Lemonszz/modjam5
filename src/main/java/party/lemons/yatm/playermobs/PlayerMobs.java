package party.lemons.yatm.playermobs;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import party.lemons.yatm.config.ModConstants;

/**
 * Created by Sam on 31/03/2018.
 */
@Mod.EventBusSubscriber
@GameRegistry.ObjectHolder(ModConstants.MODID)
public class PlayerMobs
{
	@GameRegistry.ObjectHolder("zombie")
	public static PlayerMob ZOMBIE;

	@GameRegistry.ObjectHolder("skeleton")
	public static PlayerMob SKELETON;

	@GameRegistry.ObjectHolder("creeper")
	public static PlayerMob CREEPER;

	@GameRegistry.ObjectHolder("player")
	public static PlayerMob PLAYER;

	@SubscribeEvent
	public static void onRegisterPlayerMob(RegistryEvent.Register<PlayerMob> event)
	{
		event.getRegistry().registerAll(
				new PlayerMob().setRegistryName(ModConstants.MODID, "zombie"),
				new PlayerMob().setRegistryName(ModConstants.MODID, "player")
		);
	}
}
