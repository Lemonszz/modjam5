package party.lemons.yatm.util;

import java.util.Random;

/**
 * Created by Sam on 1/04/2018.
 */
public class ModUtils
{
	public static <T> T choose(Random random, T... obj)
	{
		return obj[random.nextInt(obj.length)];
	}

	public static boolean percentChance(Random random, int percent)
	{
		return random.nextInt(100) <= percent;
	}
}
