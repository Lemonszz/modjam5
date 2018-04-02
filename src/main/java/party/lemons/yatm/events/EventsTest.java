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
	static boolean DEBUG = false;

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onTest(PlayerInteractEvent.RightClickBlock event)
	{
		if(DEBUG && event.getEntityPlayer().world.isRemote && event.getEntityPlayer().isSneaking())
		{
			RenderEvents.cache.remove(Minecraft.getMinecraft().player);
			RenderEvents.type_cache.remove(Minecraft.getMinecraft().player.getUniqueID());
			Minecraft.getMinecraft().displayGuiScreen(new GuiSelectMob());
		}
	}
}
