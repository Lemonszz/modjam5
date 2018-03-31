package party.lemons.yatm.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import party.lemons.yatm.YATM;
import party.lemons.yatm.capability.PlayerData;
import party.lemons.yatm.events.PlayerEvents;
import party.lemons.yatm.playermobs.PlayerMob;
import party.lemons.yatm.playermobs.PlayerMobHuman;
import party.lemons.yatm.playermobs.PlayerMobRegistry;
import party.lemons.yatm.playermobs.PlayerMobs;

/**
 * Created by Sam on 31/03/2018.
 */
public class MessageSetMobFromGui implements IMessage
{
	PlayerMob mob;

	public MessageSetMobFromGui(){};
	public MessageSetMobFromGui(PlayerMob mob)
	{
		this.mob = mob;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		String s = ByteBufUtils.readUTF8String(buf);
		mob = PlayerMobRegistry.fromString(s);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, mob.getRegistryName().toString());
	}

	public static class Handler implements IMessageHandler<MessageSetMobFromGui, IMessage>
	{

		@Override
		public IMessage onMessage(MessageSetMobFromGui message, MessageContext ctx)
		{
			EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
			serverPlayer.getServerWorld().addScheduledTask(() ->
					{
						PlayerData data = serverPlayer.getCapability(PlayerData.CAPABILITY, null);
						data.setMob(message.mob);
						data.setSelected(true);
						try
						{
							if(message.mob != PlayerMobs.PLAYER)
							{
								EntityLivingBase livingBase = (EntityLivingBase) message.mob.getMobClass().getConstructor(World.class).newInstance(serverPlayer.getServerWorld());
								PlayerEvents.setPlayerSize(serverPlayer, livingBase.width, livingBase.height, livingBase.getEyeHeight());
								serverPlayer.setEntityBoundingBox(livingBase.getEntityBoundingBox().offset(serverPlayer.getPosition()));
							}
							else
							{
								PlayerEvents.setPlayerSize(serverPlayer, 0.6F, 1.8F, serverPlayer.getDefaultEyeHeight());
							}
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
						message.mob.onInitialSpawn(serverPlayer);
						YATM.NETWORK.sendToAll(new MessageSendPlayerInfo(serverPlayer.getUniqueID(), message.mob.getRegistryName().toString()));
					});

			return null;
		}
	}
}
