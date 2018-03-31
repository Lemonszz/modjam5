package party.lemons.yatm.playermobs;

import net.minecraft.entity.monster.EntityCreeper;

/**
 * Created by Sam on 31/03/2018.
 */
public class PlayerMobCreeper extends PlayerMob
{
	@Override
	public Class getMobClass()
	{
		return EntityCreeper.class;
	}
}
