package party.lemons.yatm.playermobs;

import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

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

	public void onActivateAbility(EntityPlayer player)
	{
		if(player.world.isRemote)
			return;

		player.world.createExplosion(player, player.posX, player.posY, player.posZ, 3,  player.world.getGameRules().getBoolean("mobGriefing"));
		player.attackEntityFrom(DamageSource.causePlayerDamage(player), player.getHealth());
	}

	public boolean hasAbility()
	{
		return true;
	}
}
