package party.lemons.yatm.gen;

import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Sam on 31/03/2018.
 */
public class GenerationInit
{
	public static void Init()
	{
		GameRegistry.registerWorldGenerator(new GenPlayerHouse(), 0);
	}
}
