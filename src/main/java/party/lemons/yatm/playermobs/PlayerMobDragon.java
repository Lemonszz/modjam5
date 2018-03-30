package party.lemons.yatm.playermobs;

import net.minecraft.entity.boss.EntityDragon;

/**
 * Created by Sam on 31/03/2018.
 */
public class PlayerMobDragon extends PlayerMob
{
	@Override
	public Class getMobClass()
	{
		return EntityDragon.class;
	}
}
