package party.lemons.yatm.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import party.lemons.yatm.capability.PlayerData;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Sam on 31/03/2018.
 */
@Mod.EventBusSubscriber(Side.CLIENT)
@SideOnly(Side.CLIENT)
public class RenderEvents
{
	public static HashMap<EntityPlayer, EntityLivingBase> cache = new HashMap<>();


	@SubscribeEvent
	public static void onUpdate(TickEvent.PlayerTickEvent event)
	{
		cache.forEach((p, e) ->
				{
					e.setPosition(p.posX, p.posY, p.posZ);

					e.rotationPitch = p.rotationPitch;
					e.rotationYaw = p.rotationYaw;
					e.rotationYawHead = p.rotationYawHead;
					e.prevLimbSwingAmount = p.prevLimbSwingAmount;
					e.prevRotationYawHead = p.prevRotationYawHead;
					e.prevDistanceWalkedModified = p.prevDistanceWalkedModified;
					e.prevPosX = p.prevPosX;
					e.prevPosY = p.prevPosY;
					e.prevPosZ = p.prevPosZ;

					e.getEquipmentAndArmor();
					e.setItemStackToSlot(EntityEquipmentSlot.CHEST, p.getItemStackFromSlot(EntityEquipmentSlot.CHEST));
					e.setItemStackToSlot(EntityEquipmentSlot.LEGS, p.getItemStackFromSlot(EntityEquipmentSlot.LEGS));
					e.setItemStackToSlot(EntityEquipmentSlot.FEET, p.getItemStackFromSlot(EntityEquipmentSlot.FEET));
					e.setItemStackToSlot(EntityEquipmentSlot.HEAD, p.getItemStackFromSlot(EntityEquipmentSlot.HEAD));
					e.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, p.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND));
					e.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, p.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND));

					e.setSneaking(p.isSneaking());
					e.onLivingUpdate();
					e.onEntityUpdate();
					e.onUpdate();

				}
		);
	}

	@SubscribeEvent
	public static void onRenderPlayer(RenderPlayerEvent.Pre event)
	{
		event.setCanceled(true);
		EntityPlayer player = event.getEntityPlayer();

		if(!cache.containsKey(player) || cache.get(player) == null || player.getCapability(PlayerData.CAPABILITY, null).getMob().getMobClass() != cache.get(player).getClass())
		{
			cachePlayer(player);
		}

		RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
		if(cache.get(player) != null)
		{
			EntityLivingBase toDraw = cache.get(player);
			/*toDraw.onLivingUpdate();
			//toDraw.onEntityUpdate();
			//toDraw.onUpdate();
			toDraw.rotationPitch = player.rotationPitch;
			toDraw.rotationYaw = player.rotationYaw;
			toDraw.rotationYawHead = player.rotationYawHead;
			toDraw.prevLimbSwingAmount = player.prevLimbSwingAmount;
			toDraw.prevDistanceWalkedModified = player.prevDistanceWalkedModified;
			toDraw.prevPosX = player.prevPosX;
			toDraw.prevPosY = player.prevPosY;
			toDraw.prevPosZ = player.prevPosZ;*/

			//toDraw.setPositionAndRotationDirect(player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch, 1, false);
			//toDraw.setPosition(player.posX, player.posY, player.posZ);
			GlStateManager.translate(-player.posX, -player.posY, -player.posZ);
			rendermanager.renderEntity(toDraw, player.posX, player.posY, player.posZ, player.rotationYaw, event.getPartialRenderTick(), false);
		}
		else
			event.setCanceled(false);
	}

	public static void cachePlayer(EntityPlayer player)
	{
		try
		{
			Class livingClass = player.getCapability(PlayerData.CAPABILITY, null).getMob().getMobClass();
			EntityLivingBase inst = (EntityLivingBase) livingClass.getConstructor(World.class).newInstance(player.world);

			cache.put(player, inst);
		}
		catch(Exception e)
		{

		}
	}
}
