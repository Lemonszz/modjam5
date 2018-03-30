package party.lemons.yatm.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import party.lemons.yatm.playermobs.PlayerMob;
import party.lemons.yatm.playermobs.PlayerMobRegistry;
import party.lemons.yatm.playermobs.PlayerMobs;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Sam on 31/03/2018.
 */
public interface PlayerData
{
	@CapabilityInject(PlayerData.class)
	Capability<PlayerData> CAPABILITY = null;

	PlayerMob getMob();
	void setMob(PlayerMob mob);

	class Impl implements PlayerData
	{
		private PlayerMob mob = PlayerMobs.PLAYER;

		@Override
		public PlayerMob getMob()
		{
			return mob;
		}

		@Override
		public void setMob(PlayerMob mob)
		{
			this.mob = mob;
		}
	}

	class Storage implements Capability.IStorage<PlayerData>
	{

		@Nullable
		@Override
		public NBTBase writeNBT(Capability<PlayerData> capability, PlayerData instance, EnumFacing side)
		{
			NBTTagCompound tags = new NBTTagCompound();
			tags.setString("mob", instance.getMob().getRegistryName().toString());

			return tags;
		}

		@Override
		public void readNBT(Capability<PlayerData> capability, PlayerData instance, EnumFacing side, NBTBase nbt)
		{
			NBTTagCompound tags = (NBTTagCompound) nbt;

			String mob = tags.getString("mob");
			instance.setMob(PlayerMobRegistry.fromString(mob));
		}
	}

	class Provider implements ICapabilitySerializable<NBTBase>
	{
		private PlayerData instance = CAPABILITY.getDefaultInstance();

		@Override
		public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
		{
			return capability == CAPABILITY;
		}

		@Nullable
		@Override
		public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
		{
			return capability == CAPABILITY ? CAPABILITY.cast(instance) : null;
		}

		@Override
		public NBTBase serializeNBT()
		{
			return CAPABILITY.getStorage().writeNBT(CAPABILITY, instance, null);
		}

		@Override
		public void deserializeNBT(NBTBase nbt)
		{
			CAPABILITY.getStorage().readNBT(CAPABILITY, instance, null, nbt);
		}
	}
}
