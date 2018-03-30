package party.lemons.yatm.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import party.lemons.yatm.playermobs.PlayerMob;

/**
 * Created by Sam on 31/03/2018.
 */
public class GuiButtonPlayerMob extends GuiButtonExt
{
	private final PlayerMob mob;

	public GuiButtonPlayerMob(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, PlayerMob mob)
	{
		super(buttonId, x, y, widthIn, heightIn, buttonText);

		this.mob = mob;
	}

	public PlayerMob getMob()
	{
		return mob;
	}
}
