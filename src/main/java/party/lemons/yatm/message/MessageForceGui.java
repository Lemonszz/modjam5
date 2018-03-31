package party.lemons.yatm.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import party.lemons.yatm.YATM;
import party.lemons.yatm.capability.PlayerData;
import party.lemons.yatm.gui.GuiSelectMob;
import party.lemons.yatm.playermobs.PlayerMob;
import party.lemons.yatm.playermobs.PlayerMobRegistry;

/**
 * Created by Sam on 31/03/2018.
 */
public class MessageForceGui implements IMessage
{

	public MessageForceGui(){}

	@Override
	public void fromBytes(ByteBuf buf)
	{

	}

	@Override
	public void toBytes(ByteBuf buf){
	}


	public static class Handler implements IMessageHandler<MessageForceGui, IMessage>
	{

		@Override
		public IMessage onMessage(MessageForceGui message, MessageContext ctx)
		{
			Minecraft.getMinecraft().addScheduledTask(() -> {
				YATM.proxy.openSelectGui();
			});

			return null;
		}
	}
}
