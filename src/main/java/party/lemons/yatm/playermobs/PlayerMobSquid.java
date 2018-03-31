package party.lemons.yatm.playermobs;

import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

/**
 * Created by Sam on 31/03/2018.
 */
public class PlayerMobSquid extends PlayerMob
{
	@Override
	public Class getMobClass()
	{
		return EntitySquid.class;
	}

	public void onPlayerTick(EntityPlayer player)
	{
		if(player.isInWater())
		{
			player.removePotionEffect(MobEffects.SLOWNESS);
			player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 10, 100));
			player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 300, 100));
		}
		else
			{
			player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 10, 10));
			player.removePotionEffect(MobEffects.WATER_BREATHING);
			player.removePotionEffect(MobEffects.NIGHT_VISION);

			if(player.world.getTotalWorldTime() % 10 == 0)
			{
				player.attackEntityFrom(DamageSource.DROWN,  1);
			}
		}
	}

	public double getMeleeAttackFactor()
	{
		return 0.0D;
	}
}
