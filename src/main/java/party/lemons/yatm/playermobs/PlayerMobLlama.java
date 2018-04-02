package party.lemons.yatm.playermobs;

import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLlamaSpit;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import party.lemons.yatm.entity.EntityPlayerLLamaSpit;

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
	public boolean hasAbility()
	{
		return true;
	}

	public int getCooldownTime()
	{
		return 15;
	}

	public void onActivateAbility(EntityPlayer player)
	{
		if(player.world.isRemote)
			return;

		World worldIn = player.world;
		worldIn.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_LLAMA_SPIT, SoundCategory.NEUTRAL, 0.5F, 0.4F / (player.getRNG().nextFloat() * 0.4F + 0.8F));

		if (!worldIn.isRemote)
		{
			EntityPlayerLLamaSpit entitysnowball = new EntityPlayerLLamaSpit(worldIn, player);
			entitysnowball.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
			worldIn.spawnEntity(entitysnowball);
		}
	}

}
