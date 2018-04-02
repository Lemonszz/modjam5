package party.lemons.yatm.playermobs;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.registries.IForgeRegistryEntry;
import party.lemons.yatm.item.ModItems;

/**
 * Created by Sam on 31/03/2018.
 */
public abstract class PlayerMob extends IForgeRegistryEntry.Impl<PlayerMob>
{
	public PlayerMob()
	{

	}

	public void onPlayerTick(EntityPlayer player)
	{

	}

	public abstract Class getMobClass();

	public boolean isHostileMob()
	{
		return true;
	}

	public boolean shouldPlayersAttack()
	{
		return true;
	}

	public boolean shouldMobsAttack()
	{
		return false;
	}

	public void onInitialSpawn(EntityPlayer player)
	{
		if(hasAbility())
		{
			player.addItemStackToInventory(new ItemStack(ModItems.MOB_ABILITY));
		}
	}

	public void onKill(EntityPlayer player, EntityLivingBase target)
	{

	}

	public double getMeleeAttackFactor()
	{
		return 1.0D;
	}

	public int getCooldownTime()
	{
		return 100;
	}

	public void onActivateAbility(EntityPlayer player)
	{

	}
	public boolean hasAbility()
	{
		return false;
	}


}
