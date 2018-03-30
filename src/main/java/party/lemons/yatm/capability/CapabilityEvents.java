package party.lemons.yatm.capability;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import party.lemons.yatm.config.ModConstants;

/**
 * Created by Sam on 31/03/2018.
 */
@Mod.EventBusSubscriber
public class CapabilityEvents
{
	@SubscribeEvent
	public static void attachCapability(AttachCapabilitiesEvent<Entity> event)
	{
		if(event.getObject() instanceof EntityPlayer)
		{
			event.addCapability(new ResourceLocation(ModConstants.MODID, "playermob"),  new PlayerData.Provider());
		}
	}
}
