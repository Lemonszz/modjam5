package party.lemons.yatm;

import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import party.lemons.yatm.capability.PlayerData;
import party.lemons.yatm.config.ModConstants;
import party.lemons.yatm.proxy.IProxy;

/**
 * Created by Sam on 31/03/2018.
 */
@Mod(modid = ModConstants.MODID, name = ModConstants.NAME, version = ModConstants.VERSION)
public class YATM
{

	@Mod.Instance(ModConstants.MODID)
	public static YATM instance;

	@SidedProxy(clientSide = "party.lemons.yatm.proxy.ClientProxy", serverSide = "party.lemons.yatm.proxy.ServerProxy")
	public static IProxy proxy;

	@Mod.EventHandler
	public static void preInit(FMLPreInitializationEvent event)
	{
		proxy.preInit(event);
		CapabilityManager.INSTANCE.register(PlayerData.class, new PlayerData.Storage(), PlayerData.Impl::new);
	}

	@Mod.EventHandler
	public static void init(FMLInitializationEvent event)
	{
		proxy.init(event);
	}

	@Mod.EventHandler
	public static void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit(event);
	}
}
