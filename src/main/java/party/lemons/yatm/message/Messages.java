package party.lemons.yatm.message;

import net.minecraftforge.fml.relauncher.Side;
import party.lemons.yatm.YATM;

/**
 * Created by Sam on 31/03/2018.
 */
public class Messages
{
	private static int id = 1;

	public static void init()
	{
		YATM.NETWORK.registerMessage(MessageSetMobFromGui.Handler.class, MessageSetMobFromGui.class, id++, Side.SERVER);
	}
}