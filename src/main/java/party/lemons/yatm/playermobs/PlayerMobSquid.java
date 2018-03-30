package party.lemons.yatm.playermobs;

import net.minecraft.entity.passive.EntitySquid;

/**
 * Created by Sam on 31/03/2018.
 */
public class PlayerMobSquid extends PlayerMob
{
	@Override
	public Class getMobClass()
	{
		return EntitySquid.class;
	}
}
