package party.lemons.yatm.playermobs;

import net.minecraft.entity.monster.EntityZombie;

/**
 * Created by Sam on 31/03/2018.
 */
public class PlayerMobZombie extends PlayerMob
{
	@Override
	public Class getMobClass()
	{
		return EntityZombie.class;
	}
}
