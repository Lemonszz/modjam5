package party.lemons.yatm.playermobs;

import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;

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

	@Override
	public void onPlayerTick(EntityPlayer entityPlayer)
	{
		if(entityPlayer.collidedHorizontally)
		{
			entityPlayer.motionY = 0.25;
			entityPlayer.fallDistance = 0;
		}
	}
}
