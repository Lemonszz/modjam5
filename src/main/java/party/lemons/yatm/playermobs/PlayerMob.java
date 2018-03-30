package party.lemons.yatm.playermobs;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * Created by Sam on 31/03/2018.
 */
public class PlayerMob extends IForgeRegistryEntry.Impl<PlayerMob>
{
	public PlayerMob()
	{

	}

	public void onPlayerTick(EntityPlayer player)
	{

	}

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
}
