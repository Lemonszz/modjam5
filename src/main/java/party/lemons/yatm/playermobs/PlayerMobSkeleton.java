package party.lemons.yatm.playermobs;

import net.minecraft.entity.monster.EntitySkeleton;

/**
 * Created by Sam on 31/03/2018.
 */
public class PlayerMobSkeleton extends PlayerMobBurnable
{
	@Override
	public Class getMobClass()
	{
		return EntitySkeleton.class;
	}
}
