package party.lemons.yatm.entity;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

/**
 * Created by Sam on 31/03/2018.
 */
public class EntityHumanInventory extends EntityHuman implements IMob
{
	ItemStackHandler inventory = new ItemStackHandler(26);

	public EntityHumanInventory(World worldIn)
	{
		super(worldIn, false);
	}

	protected void initEntityAI()
	{
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
		this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
		this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityHuman.class, 8.0F));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));

		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityMob.class, true));
		this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityAnimal.class, false));
		this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));

		super.initEntityAI();
	}

	@Override
	protected void updateEquipmentIfNeeded(EntityItem itemEntity)
	{
		ItemStack stack = itemEntity.getItem();
		EntityEquipmentSlot equipSlot = getSlotForItemStack(stack);
		if(this.getItemStackFromSlot(equipSlot).isEmpty())
		{
			setItemStackToSlot(equipSlot, stack);
			itemEntity.setDead();
			return;
		}

		if(ItemHandlerHelper.insertItem(inventory, stack, true).isEmpty())
		{
			ItemHandlerHelper.insertItem(inventory, stack, false);
			itemEntity.setDead();
		}
	}

	@Override
	public void onDeath(DamageSource cause)
	{
		for(int i = 0; i < inventory.getSlots(); i++)
		{
			world.spawnEntity(new EntityItem(world, posX, posY, posZ, inventory.getStackInSlot(i)));
		}

		super.onDeath(cause);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
	{
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return true;

		return super.hasCapability(capability, facing);
	}


	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		compound.setTag("inventory", inventory.serializeNBT());
		super.writeEntityToNBT(compound);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		super.readEntityFromNBT(compound);
	}

	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
	{
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return (T) inventory;

		return super.getCapability(capability, facing);
	}

	public boolean canPickUpLoot()
	{
		return true;
	}
}
