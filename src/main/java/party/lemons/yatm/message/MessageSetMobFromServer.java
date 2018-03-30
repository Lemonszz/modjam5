package party.lemons.yatm.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import party.lemons.yatm.capability.PlayerData;
import party.lemons.yatm.events.PlayerEvents;
import party.lemons.yatm.playermobs.PlayerMob;
import party.lemons.yatm.playermobs.PlayerMobRegistry;
import party.lemons.yatm.playermobs.PlayerMobs;

/**
 * Created by Sam on 31/03/2018.
 */
public class MessageSetMobFromServer implements IMessage
{
	PlayerMob mob;

	public MessageSetMobFromServer(){};
	public MessageSetMobFromServer(PlayerMob mob)
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

	public static class Handler implements IMessageHandler<MessageSetMobFromServer, IMessage>
	{

		@Override
		public IMessage onMessage(MessageSetMobFromServer message, MessageContext ctx)
		{
			Minecraft.getMinecraft().addScheduledTask(() -> Minecraft.getMinecraft().player.getCapability(PlayerData.CAPABILITY, null).setMob(message.mob));

			return null;
		}
	}
}
