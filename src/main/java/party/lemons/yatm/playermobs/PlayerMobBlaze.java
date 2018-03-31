package party.lemons.yatm.playermobs;

import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityGhast;

/**
 * Created by Sam on 31/03/2018.
 */
public class PlayerMobBlaze extends PlayerMob
{
	@Override
	public Class getMobClass()
	{
		return EntityBlaze.class;
	}
}
