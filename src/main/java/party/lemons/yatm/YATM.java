package party.lemons.yatm;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
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

}
