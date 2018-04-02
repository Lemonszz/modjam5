package party.lemons.yatm.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
/**
 * Created by Sam on 2/04/2018.
 */
public class EntityPlayerLLamaSpit extends EntityThrowable
{

	public EntityPlayerLLamaSpit(World worldIn)
	{
		super(worldIn);
	}

	public EntityPlayerLLamaSpit(World worldIn, double x, double y, double z)
	{
		this(worldIn);
		this.setPosition(x, y, z);
	}

	public EntityPlayerLLamaSpit(World worldIn, EntityLivingBase throwerIn)
	{
		super(worldIn, throwerIn);
	}

	@Override
	protected void onImpact(RayTraceResult result)
	{
		if(result.entityHit != null)
		{
			result.entityHit.attackEntityFrom(DamageSource.GENERIC, 1F);
		}
	}
}
