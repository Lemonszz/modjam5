package party.lemons.yatm.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import party.lemons.yatm.entity.render.RenderInit;

/**
 * Created by Sam on 31/03/2018.
 */
public class ClientProxy implements IProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		RenderInit.init();
	}

	@Override
	public void init(FMLInitializationEvent event)
	{

	}

	@Override
	public void postInit(FMLPostInitializationEvent event)
	{

	}
}
