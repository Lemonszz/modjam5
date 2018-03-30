package party.lemons.yatm.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import party.lemons.yatm.capability.PlayerData;
import party.lemons.yatm.events.RenderEvents;
import party.lemons.yatm.playermobs.PlayerMob;
import party.lemons.yatm.playermobs.PlayerMobRegistry;

import java.util.UUID;

/**
 * Created by Sam on 31/03/2018.
 */
public class MessageSendPlayerInfo implements IMessage
{
	String mob;
	UUID uuid;

	public MessageSendPlayerInfo(){};
	public MessageSendPlayerInfo(UUID player, String mob)
	{
		this.mob = mob;
		this.uuid = player;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		mob = ByteBufUtils.readUTF8String(buf);
		uuid = UUID.fromString(ByteBufUtils.readUTF8String(buf));
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, mob);
		ByteBufUtils.writeUTF8String(buf, uuid.toString());
	}

	public static class Handler implements IMessageHandler<MessageSendPlayerInfo, IMessage>
	{

		@Override
		public IMessage onMessage(MessageSendPlayerInfo message, MessageContext ctx)
		{
			Minecraft.getMinecraft().addScheduledTask(() -> RenderEvents.type_cache.put(message.uuid, message.mob));

			return null;
		}
	}
}
