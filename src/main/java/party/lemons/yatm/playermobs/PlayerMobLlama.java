package party.lemons.yatm.playermobs;

import net.minecraft.entity.passive.EntityLlama;

/**
 * Created by Sam on 31/03/2018.
 */
public class PlayerMobLlama extends PlayerMob
{
	@Override
	public Class getMobClass()
	{
		return EntityLlama.class;
	}
}
