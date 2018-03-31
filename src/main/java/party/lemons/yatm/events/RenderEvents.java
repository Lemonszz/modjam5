package party.lemons.yatm.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import party.lemons.yatm.capability.PlayerData;
import party.lemons.yatm.playermobs.PlayerMob;
import party.lemons.yatm.playermobs.PlayerMobRegistry;

import java.lang.reflect.Field;
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
	public static HashMap<UUID, String> type_cache = new HashMap<>();


	@SubscribeEvent
	public static void onUpdate(TickEvent.PlayerTickEvent event)
	{
		if(event.phase != TickEvent.Phase.START)
			return;

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
					e.swingProgress = p.swingProgress;
					e.swingProgressInt = p.swingProgressInt;
					e.swingingHand = p.swingingHand;
					e.limbSwing = p.limbSwing;
					e.limbSwingAmount = p.limbSwingAmount;
					e.prevLimbSwingAmount = p.prevLimbSwingAmount;

					e.hurtTime = p.hurtTime;
					if(p.getActiveHand() != null)
						e.setActiveHand(p.getActiveHand());
					else
						e.resetActiveHand();

					e.setVelocity(p.motionX, p.motionY, p.motionZ);
					if(p != Minecraft.getMinecraft().player)
					{
						e.setAlwaysRenderNameTag(true);
						e.setCustomNameTag(p.getName());
					}

					if(p.isBurning())
					{
						e.setFire(2);
					}

					e.ticksExisted++;
					e.setItemStackToSlot(EntityEquipmentSlot.CHEST, p.getItemStackFromSlot(EntityEquipmentSlot.CHEST));
					e.setItemStackToSlot(EntityEquipmentSlot.LEGS, p.getItemStackFromSlot(EntityEquipmentSlot.LEGS));
					e.setItemStackToSlot(EntityEquipmentSlot.FEET, p.getItemStackFromSlot(EntityEquipmentSlot.FEET));
					e.setItemStackToSlot(EntityEquipmentSlot.HEAD, p.getItemStackFromSlot(EntityEquipmentSlot.HEAD));
					e.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, p.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND));
					e.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, p.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND));

					e.setSneaking(p.isSneaking());
					if(Minecraft.getMinecraft().player != null)
						e.onUpdate();
				}
		);
	}

	@SubscribeEvent
	public static void onLivingDeath(LivingDeathEvent event)
	{
		if(event.getEntityLiving() == Minecraft.getMinecraft().player)
		{
			cache.clear();
			type_cache.clear();
		}
	}

	@SubscribeEvent
	public static void onRenderPlayer(RenderPlayerEvent.Pre event)
	{
		event.setCanceled(true);
		EntityPlayer player = event.getEntityPlayer();

		if(!cache.containsKey(player) || cache.get(player) == null || PlayerMobRegistry.fromString(type_cache.get(player.getUniqueID())).getMobClass() != cache.get(player).getClass())
		{
			cachePlayer(player);
		}

		RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
		if(cache.get(player) != null)
		{
			GlStateManager.pushMatrix();
			EntityLivingBase toDraw = cache.get(player);
			GlStateManager.translate(-Minecraft.getMinecraft().player.posX, -Minecraft.getMinecraft().player.posY, -Minecraft.getMinecraft().player.posZ);

			toDraw.swingProgress = player.swingProgress;
			toDraw.swingProgressInt = player.swingProgressInt;
			toDraw.swingingHand = player.swingingHand;
			toDraw.limbSwing = player.limbSwing;
			toDraw.limbSwingAmount = player.limbSwingAmount;
			toDraw.prevLimbSwingAmount = player.prevLimbSwingAmount;
			rendermanager.renderEntity(toDraw, player.posX, player.posY, player.posZ, player.rotationYaw, event.getPartialRenderTick(), false);
			GlStateManager.popMatrix();
		}
		else
			event.setCanceled(false);
	}

	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent event)
	{
		if(event.phase == TickEvent.Phase.END)
		{
			if(Minecraft.getMinecraft().currentScreen instanceof GuiMainMenu)
			{
			//	cache.clear();
			//	type_cache.clear();
			}
		}
	}

	public static void cachePlayer(EntityPlayer player)
	{
		try
		{
			PlayerMob mob = PlayerMobRegistry.fromString(type_cache.get(player.getUniqueID()));
			Class livingClass = mob.getMobClass();
			EntityLivingBase inst = (EntityLivingBase) livingClass.getConstructor(World.class).newInstance(player.world);

			cache.put(player, inst);
		}
		catch(Exception e)
		{

		}
	}
}
