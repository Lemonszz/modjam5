package party.lemons.yatm.entity;

import net.minecraft.entity.EntityCreature;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

/**
 * Created by Sam on 31/03/2018.
 */
public class EntityHuman extends EntityCreature
{
	public static final DataParameter<Integer> VAR = EntityDataManager.createKey(EntityHuman.class, DataSerializers.VARINT);

	public EntityHuman(World worldIn)
	{
		super(worldIn);

		this.setSize(0.6F, 1.95F);
	}

	protected void entityInit()
	{
		super.entityInit();
		this.getDataManager().register(VAR, Integer.valueOf(-1));
	}

	public void onEntityUpdate()
	{
		super.onEntityUpdate();

		if(this.getDataManager().get(VAR).intValue() == -1)
		{
			this.getDataManager().set(VAR, world.rand.nextInt(999));
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		compound.setInteger("var", this.getDataManager().get(VAR));
		super.writeEntityToNBT(compound);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);

		this.getDataManager().set(VAR, compound.getInteger("var"));
	}
}
