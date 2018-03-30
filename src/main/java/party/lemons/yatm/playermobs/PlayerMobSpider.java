package party.lemons.yatm.playermobs;

import net.minecraft.entity.monster.EntitySpider;

/**
 * Created by Sam on 31/03/2018.
 */
public class PlayerMobSpider extends PlayerMob
{
	@Override
	public Class getMobClass()
	{
		return EntitySpider.class;
	}
}
