package party.lemons.yatm.playermobs;

import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by Sam on 31/03/2018.
 */
public class PlayerMobHuman extends PlayerMob
{
	@Override
	public Class getMobClass()
	{
		return EntityPlayer.class;
	}

	public boolean shouldMobsAttack()
	{
		return true;
	}

	public boolean shouldPlayersAttack()
	{
		return false;
	}
}
