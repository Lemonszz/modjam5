package party.lemons.yatm.entity;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import party.lemons.yatm.util.ModUtils;

import javax.annotation.Nullable;

/**
 * Created by Sam on 31/03/2018.
 */
public class EntityHuman extends EntityCreature
{
	public static final DataParameter<Integer> VAR = EntityDataManager.createKey(EntityHuman.class, DataSerializers.VARINT);

	private boolean doesDespawn;

	public EntityHuman(World worldIn)
	{
		this(worldIn, false);
	}

	protected EntityHuman(World world, boolean doesDespawn)
	{
		super(world);
		this.doesDespawn = doesDespawn;
		this.setSize(0.6F, 1.95F);
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

	protected void entityInit()
	{
		super.entityInit();
		this.getDataManager().register(VAR, Integer.valueOf(-1));
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);

		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20D);
	}


	public void onEntityUpdate()
	{
		super.onEntityUpdate();

		if(this.getDataManager().get(VAR).intValue() == -1)
		{
			this.getDataManager().set(VAR, world.rand.nextInt(999));
		}
	}

	public boolean attackEntityAsMob(Entity entityIn)
	{
		this.swingArm(EnumHand.MAIN_HAND);
		float f = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
		int i = 0;

		if (entityIn instanceof EntityLivingBase)
		{
			f += EnchantmentHelper.getModifierForCreature(this.getHeldItemMainhand(), ((EntityLivingBase)entityIn).getCreatureAttribute());
			i += EnchantmentHelper.getKnockbackModifier(this);
		}

		boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), f);

		if (flag)
		{
			if (i > 0 && entityIn instanceof EntityLivingBase)
			{
				((EntityLivingBase)entityIn).knockBack(this, (float)i * 0.5F, (double) MathHelper.sin(this.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(this.rotationYaw * 0.017453292F)));
				this.motionX *= 0.6D;
				this.motionZ *= 0.6D;
			}

			int j = EnchantmentHelper.getFireAspectModifier(this);

			if (j > 0)
			{
				entityIn.setFire(j * 4);
			}

			if (entityIn instanceof EntityPlayer)
			{
				EntityPlayer entityplayer = (EntityPlayer)entityIn;
				ItemStack itemstack = this.getHeldItemMainhand();
				ItemStack itemstack1 = entityplayer.isHandActive() ? entityplayer.getActiveItemStack() : ItemStack.EMPTY;

				if (!itemstack.isEmpty() && !itemstack1.isEmpty() && itemstack.getItem().canDisableShield(itemstack, itemstack1, entityplayer, this) && itemstack1.getItem().isShield(itemstack1, entityplayer))
				{
					float f1 = 0.25F + (float)EnchantmentHelper.getEfficiencyModifier(this) * 0.05F;

					if (this.rand.nextFloat() < f1)
					{
						entityplayer.getCooldownTracker().setCooldown(itemstack1.getItem(), 100);
						this.world.setEntityState(entityplayer, (byte)30);
					}
				}
			}

			this.applyEnchantments(this, entityIn);
		}

		return flag;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		compound.setBoolean("despawn", doesDespawn);
		compound.setInteger("var", this.getDataManager().get(VAR));
		super.writeEntityToNBT(compound);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);

		this.doesDespawn = compound.getBoolean("despawn");
		this.getDataManager().set(VAR, compound.getInteger("var"));
	}

	@Override
	protected boolean canDespawn()
	{
		return doesDespawn;
	}

	@Override
	protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
	{
		super.setEquipmentBasedOnDifficulty(difficulty);

		Item chest = ModUtils.choose(rand, Items.IRON_CHESTPLATE, Items.LEATHER_CHESTPLATE, Items.GOLDEN_CHESTPLATE, Items.LEATHER_CHESTPLATE, Items.LEATHER_CHESTPLATE);
		Item feet = ModUtils.choose(rand, Items.IRON_BOOTS, Items.LEATHER_BOOTS, Items.GOLDEN_BOOTS, Items.LEATHER_BOOTS, Items.LEATHER_BOOTS);
		Item legs = ModUtils.choose(rand, Items.IRON_LEGGINGS, Items.LEATHER_LEGGINGS, Items.GOLDEN_LEGGINGS, Items.LEATHER_LEGGINGS, Items.LEATHER_LEGGINGS);
		Item head = ModUtils.choose(rand, Items.IRON_HELMET, Items.LEATHER_HELMET, Items.GOLDEN_HELMET, Items.LEATHER_HELMET, Items.LEATHER_HELMET);

		if(ModUtils.percentChance(rand, 20))
		{
			if(ModUtils.percentChance(rand, 10))
			{
				chest = Items.DIAMOND_CHESTPLATE;
				legs = Items.DIAMOND_LEGGINGS;
				head = Items.DIAMOND_HELMET;
				feet = Items.DIAMOND_BOOTS;
			}
		}

		if(ModUtils.percentChance(rand, 70))
		{
			if(ModUtils.percentChance(rand, 25))
				this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(chest));
			if(ModUtils.percentChance(rand, 25))
				this.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(feet));
			if(ModUtils.percentChance(rand, 25))
				this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(head));
			if(ModUtils.percentChance(rand, 25))
				this.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(legs));
		}

		ItemStack main = new ItemStack(ModUtils.choose(rand, Items.GOLDEN_SWORD, Items.GOLDEN_AXE, Items.IRON_AXE, Items.IRON_SWORD));
		if(ModUtils.percentChance(rand, 50))
			this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, main);

		if(world.rand.nextInt(10) == 0)
		{
			ItemStack off = new ItemStack(Items.SHIELD);
			this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, off);
		}
	}

	@Nullable
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
	{
		this.setEquipmentBasedOnDifficulty(difficulty);

		return livingdata;
	}
}
