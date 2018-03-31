package party.lemons.yatm.entity.render;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import party.lemons.yatm.entity.EntityHuman;

/**
 * Created by Sam on 31/03/2018.
 */
public class RenderInit
{
	public static void init()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityHuman.class, RenderHuman::new);
	}
}
