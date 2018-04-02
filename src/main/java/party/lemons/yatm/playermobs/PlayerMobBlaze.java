package party.lemons.yatm.playermobs;

import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

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

	public boolean hasAbility()
	{
		return true;
	}

	public int getCooldownTime()
	{
		return 5;
	}

	public void onActivateAbility(EntityPlayer player)
	{
		if(player.world.isRemote)
			return;

		double d1 = player.posX - player.posX;
		double d2 = player.getEntityBoundingBox().minY + (double)(player.height / 2.0F) - (player.posY + (double)(player.height / 2.0F));
		double d3 = player.posZ - player.posZ;

		float f = MathHelper.sqrt(MathHelper.sqrt(10)) * 0.5F;
		player.world.playEvent((EntityPlayer)null, 1018, new BlockPos((int)player.posX, (int)player.posY, (int)player.posZ), 0);


		for (int i = 0; i < 1; ++i)
		{
			EntitySmallFireball entitysmallfireball = new EntitySmallFireball(player.world, player, d1 + player.getRNG().nextGaussian() * (double)f, d2, d3 + player.getRNG().nextGaussian() * (double)f);
			entitysmallfireball.posY = player.posY + (double)(player.height / 2.0F) + 0.5D;
			player.world.spawnEntity(entitysmallfireball);
		}
	}
}
