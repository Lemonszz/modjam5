package party.lemons.yatm.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import party.lemons.yatm.playermobs.PlayerMob;
import party.lemons.yatm.playermobs.PlayerMobRegistry;

/**
 * Created by Sam on 31/03/2018.
 */
public class GuiSelectMob extends GuiScreen
{
	final int BUTTON_WIDTH = 100;
	final int BUTTON_HEIGHT = 25;
	final int BUTTON_X = 200;
	final int BUTTON_START_Y = 200;

	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	public void initGui()
	{
		int ind = 0;
		for(PlayerMob mob : PlayerMobRegistry.REGISTRY.getValuesCollection())
		{
			this.buttonList.add(new GuiButton(ind++, BUTTON_X, BUTTON_START_Y + (BUTTON_HEIGHT * ind - 1), BUTTON_WIDTH, BUTTON_HEIGHT, mob.getRegistryName().toString()));
		}
	}
}
