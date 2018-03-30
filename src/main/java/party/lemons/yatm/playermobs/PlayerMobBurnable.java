package party.lemons.yatm.playermobs;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

/**
 * Created by Sam on 31/03/2018.
 */
public abstract class PlayerMobBurnable extends PlayerMob
{
	public void onPlayerTick(EntityPlayer player)
	{
		if (player.world.isDaytime() && !player.world.isRemote)
		{
			float f = player.getBrightness();

			if (f > 0.5F && player.getRNG().nextFloat() * 30.0F < (f - 0.4F) * 2.0F && player.world.canSeeSky(new BlockPos(player.posX, player.posY + (double)player.getEyeHeight(), player.posZ)))
			{
				boolean flag = true;
				ItemStack itemstack = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);

				if (!itemstack.isEmpty())
				{
					if (itemstack.isItemStackDamageable())
					{
						itemstack.setItemDamage(itemstack.getItemDamage() + player.getRNG().nextInt(2));

						if (itemstack.getItemDamage() >= itemstack.getMaxDamage())
						{
							player.renderBrokenItemStack(itemstack);
							player.setItemStackToSlot(EntityEquipmentSlot.HEAD, ItemStack.EMPTY);
						}
					}

					flag = false;
				}

				if (flag)
				{
					player.setFire(8);
				}
			}
		}
	}
}
