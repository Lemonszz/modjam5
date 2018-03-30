package party.lemons.yatm.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import party.lemons.yatm.YATM;
import party.lemons.yatm.capability.PlayerData;
import party.lemons.yatm.message.MessageSetMobFromGui;
import party.lemons.yatm.playermobs.PlayerMob;
import party.lemons.yatm.playermobs.PlayerMobRegistry;
import party.lemons.yatm.playermobs.PlayerMobs;

import java.io.IOException;

/**
 * Created by Sam on 31/03/2018.
 */
public class GuiSelectMob extends GuiScreen
{
	final int BUTTON_WIDTH = 100;
	final int BUTTON_HEIGHT = 20;
	final int BUTTON_X = 10;
	final int BUTTON_START_Y = 10;

	private PlayerMob selected = PlayerMobs.PLAYER;
	private EntityLivingBase renderMob = null;

	private int button_done = -1;

	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
		int x_mid = res.getScaledWidth() / 2;
		int y_mid = res.getScaledHeight() / 2;

		this.drawDefaultBackground();

		try
		{

			if(renderMob == null)
			{
				if(selected == PlayerMobs.PLAYER)
					renderMob = Minecraft.getMinecraft().player;
				else
					renderMob = (EntityLivingBase) selected.getMobClass().getConstructor(World.class).newInstance(Minecraft.getMinecraft().world);
			}
		}
		catch(Exception e)
		{
			System.out.println("REEE");
		}

		if(renderMob != null)
		{
			GuiInventory.drawEntityOnScreen(x_mid, y_mid , 50, 25, 25, renderMob);
		}

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	public void initGui()
	{
		int ind = 0;
		for(PlayerMob mob : PlayerMobRegistry.REGISTRY.getValuesCollection())
		{
			this.buttonList.add(new GuiButtonPlayerMob(ind++, BUTTON_X, BUTTON_START_Y + (BUTTON_HEIGHT * ind - 1), BUTTON_WIDTH, BUTTON_HEIGHT, mob.getRegistryName().toString(), mob));
		}

		button_done = ind++;
		this.buttonList.add(new GuiButtonExt(button_done, BUTTON_X, BUTTON_START_Y + (BUTTON_HEIGHT * button_done + 1) + (BUTTON_HEIGHT * 3), BUTTON_WIDTH, BUTTON_HEIGHT, "yatm.gui.done"));
	}

	protected void actionPerformed(GuiButton button) throws IOException
	{
		if(button instanceof GuiButtonPlayerMob)
		{
			PlayerMob mob = ((GuiButtonPlayerMob)button).getMob();
			selected = mob;
			renderMob = null;
		}

		if(button.id == button_done)
		{
			YATM.NETWORK.sendToServer(new MessageSetMobFromGui(selected));
			Minecraft.getMinecraft().player.getCapability(PlayerData.CAPABILITY, null).setMob(selected);

			Minecraft.getMinecraft().displayGuiScreen(null);
		}

		super.actionPerformed(button);
	}
}
