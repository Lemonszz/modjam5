package party.lemons.yatm.capability;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import party.lemons.yatm.YATM;
import party.lemons.yatm.config.ModConstants;
import party.lemons.yatm.message.MessageSendPlayerInfo;
import party.lemons.yatm.message.MessageSetMobFromServer;
import party.lemons.yatm.playermobs.PlayerMob;
import party.lemons.yatm.playermobs.PlayerMobs;

/**
 * Created by Sam on 31/03/2018.
 */
@Mod.EventBusSubscriber
public class CapabilityEvents
{
	@SubscribeEvent
	public static void attachCapability(AttachCapabilitiesEvent<Entity> event)
	{
		if(event.getObject() instanceof EntityPlayer)
		{
			event.addCapability(new ResourceLocation(ModConstants.MODID, "playermob"),  new PlayerData.Provider());
		}
	}

	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event)
	{
		PlayerMob mob = event.player.getCapability(PlayerData.CAPABILITY, null).getMob();
		if(!event.player.world.isRemote)
		{
			YATM.NETWORK.sendTo(new MessageSetMobFromServer(mob), (EntityPlayerMP) event.player);
			YATM.NETWORK.sendTo(new MessageSendPlayerInfo(event.player.getUniqueID(), mob.getRegistryName().toString()), (EntityPlayerMP) event.player);

			for(EntityPlayer pl : ((EntityPlayerMP) event.player).world.getMinecraftServer().getPlayerList().getPlayers())
			{
				PlayerData data = pl.getCapability(PlayerData.CAPABILITY, null);
				YATM.NETWORK.sendTo(new MessageSendPlayerInfo(pl.getUniqueID(), data.getMob().getRegistryName().toString()), (EntityPlayerMP) event.player);
			}
		}
	}
}
