package party.lemons.yatm.events;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.world.GetCollisionBoxesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import party.lemons.yatm.YATM;
import party.lemons.yatm.capability.PlayerData;
import party.lemons.yatm.entity.EntityHuman;
import party.lemons.yatm.message.MessageForceGui;
import party.lemons.yatm.playermobs.PlayerMob;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by Sam on 31/03/2018.
 */
@Mod.EventBusSubscriber
public class PlayerEvents
{

	@SubscribeEvent
	public static void onTarget(LivingSetAttackTargetEvent event)
	{
		if(event.getTarget() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.getTarget();
			PlayerMob mob = player.getCapability(PlayerData.CAPABILITY, null).getMob();

			if(event.getEntityLiving() instanceof IMob)
			{
				if(!mob.shouldMobsAttack())
				{
					event.getEntityLiving().setRevengeTarget(null);
					((EntityLiving)event.getEntityLiving()).setAttackTarget(null);
				}
			}
		}

		if(event.getEntityLiving() instanceof EntityHuman)
		{
			if(event.getTarget() instanceof EntityCreeper)
			{
				event.getEntityLiving().setRevengeTarget(null);
				((EntityLiving)event.getEntityLiving()).setAttackTarget(null);
			}

			if(event.getTarget() instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) event.getTarget();
				PlayerMob mob = player.getCapability(PlayerData.CAPABILITY, null).getMob();

				if(!mob.shouldPlayersAttack())
				{
					event.getEntityLiving().setRevengeTarget(null);
					((EntityLiving)event.getEntityLiving()).setAttackTarget(null);
				}
			}
		}
	}

	@SubscribeEvent
	public static void onPlayerAttack(LivingDamageEvent event)
	{
		if(event.getSource().getImmediateSource() instanceof EntityPlayer)
		{
			event.setAmount(event.getAmount() * (float) event.getSource().getImmediateSource().getCapability(PlayerData.CAPABILITY, null).getMob().getMeleeAttackFactor());
		}
	}

	@SubscribeEvent
	public static void onLivingDeath(LivingDeathEvent event)
	{
		if(event.getSource().getTrueSource() instanceof EntityPlayer)
		{
			event.getSource().getTrueSource().getCapability(PlayerData.CAPABILITY, null).getMob().onKill((EntityPlayer) event.getSource().getTrueSource(), event.getEntityLiving());
		}

		if(event.getEntityLiving() instanceof EntityPlayer)
		{
			event.getEntityLiving().getCapability(PlayerData.CAPABILITY, null).setSelected(false);
		}
	}

	@SubscribeEvent
	public static void onUpdate(TickEvent.PlayerTickEvent event)
	{
		if(event.phase != TickEvent.Phase.START)
			return;

		EntityPlayer player = event.player;

		if(!player.getCapability(PlayerData.CAPABILITY, null).hasSelected() && !player.world.isRemote)
		{
			YATM.NETWORK.sendTo(new MessageForceGui(), (EntityPlayerMP) player);
		}

		if(!cache.containsKey(player) || cache.get(player) == null || player.getCapability(PlayerData.CAPABILITY, null).getMob().getMobClass() != cache.get(player).getClass())
		{
			cachePlayer(player);
		}

		player.getCapability(PlayerData.CAPABILITY, null).getMob().onPlayerTick(player);
	}

	public static void setPlayerSize(EntityPlayer player, float width, float height, float eye)
	{
		try
		{
			setSizeMethod.invoke(player, width, height);
			player.eyeHeight = eye;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static final Method setSizeMethod = getSetSizeMethod();
	private static Method getSetSizeMethod()
	{
		Method met= ReflectionHelper.findMethod(Entity.class, "setSize", "func_70105_a", float.class, float.class);
		met.setAccessible(true);

		return met;
	}


	public static HashMap<EntityPlayer, EntityLivingBase> cache = new HashMap<>();
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
