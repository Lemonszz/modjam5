package party.lemons.yatm.events;

import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import party.lemons.yatm.gui.GuiSelectMob;

/**
 * Created by Sam on 31/03/2018.
 */
@Mod.EventBusSubscriber
public class EventsTest
{
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onTest(PlayerInteractEvent.RightClickBlock event)
	{
		if(event.getEntityPlayer().world.isRemote)
		{
			RenderEvents.cache.clear();
			Minecraft.getMinecraft().displayGuiScreen(new GuiSelectMob());
		}
	}
}
