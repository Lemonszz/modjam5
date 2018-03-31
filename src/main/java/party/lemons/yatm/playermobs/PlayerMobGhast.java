package party.lemons.yatm.playermobs;

import net.minecraft.entity.monster.EntityGhast;

/**
 * Created by Sam on 31/03/2018.
 */
public class PlayerMobGhast extends PlayerMob
{
	@Override
	public Class getMobClass()
	{
		return EntityGhast.class;
	}
}
