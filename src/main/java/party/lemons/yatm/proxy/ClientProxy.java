package party.lemons.yatm.proxy;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import party.lemons.yatm.entity.render.RenderInit;
import party.lemons.yatm.gui.GuiSelectMob;

/**
 * Created by Sam on 31/03/2018.
 */
public class ClientProxy implements IProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		RenderInit.init();
	}

	@Override
	public void init(FMLInitializationEvent event)
	{

	}

	@Override
	public void postInit(FMLPostInitializationEvent event)
	{

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void openSelectGui()
	{
		Minecraft.getMinecraft().displayGuiScreen(new GuiSelectMob());
	}
}
