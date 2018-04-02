package party.lemons.yatm.playermobs;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;

/**
 * Created by Sam on 31/03/2018.
 */
public class PlayerMobZombie extends PlayerMobBurnable
{
	@Override
	public Class getMobClass()
	{
		return EntityZombie.class;
	}

	public void onKill(EntityPlayer player, EntityLivingBase target)
	{
		if(target instanceof EntityVillager)
		{
			EntityVillager entityvillager = (EntityVillager)target;
			EntityZombieVillager entityzombievillager = new EntityZombieVillager(((EntityVillager) target).world);
			entityzombievillager.copyLocationAndAnglesFrom(entityvillager);
			((EntityVillager) target).world.removeEntity(entityvillager);
			entityzombievillager.onInitialSpawn(((EntityVillager) target).world.getDifficultyForLocation(new BlockPos(entityzombievillager)), null);
			entityzombievillager.setProfession(entityvillager.getProfession());
			entityzombievillager.setChild(entityvillager.isChild());
			entityzombievillager.setNoAI(entityvillager.isAIDisabled());

			if (entityvillager.hasCustomName())
			{
				entityzombievillager.setCustomNameTag(entityvillager.getCustomNameTag());
				entityzombievillager.setAlwaysRenderNameTag(entityvillager.getAlwaysRenderNameTag());
			}

			((EntityVillager) target).world.spawnEntity(entityzombievillager);
			((EntityVillager) target).world.playEvent(null, 1026, new BlockPos(player), 0);
		}
	}

	public void onActivateAbility(EntityPlayer player)
	{
		if(player.world.isRemote)
			return;

		EntityZombie zombie = new EntityZombie(player.world);
		zombie.setPosition(player.posX, player.posY, player.posZ);
		player.world.spawnEntity(zombie);
	}

	public boolean hasAbility()
	{
		return true;
	}

	@Override
	public int getCooldownTime()
	{
		return 500;
	}

	public double getMeleeAttackFactor()
	{
		return 1.8D;
	}
}
