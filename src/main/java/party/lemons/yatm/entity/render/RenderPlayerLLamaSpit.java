package party.lemons.yatm.entity.render;

import net.minecraft.client.model.ModelLlamaSpit;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLlamaSpit;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.projectile.EntityLlamaSpit;
import net.minecraft.util.ResourceLocation;
import party.lemons.yatm.entity.EntityPlayerLLamaSpit;

/**
 * Created by Sam on 2/04/2018.
 */
public class RenderPlayerLLamaSpit extends Render<EntityPlayerLLamaSpit>
{
	private static final ResourceLocation LLAMA_SPIT_TEXTURE = new ResourceLocation("textures/entity/llama/spit.png");
	private final ModelLlamaSpit model = new ModelLlamaSpit();

	public RenderPlayerLLamaSpit(RenderManager p_i47202_1_)
	{
		super(p_i47202_1_);
	}

	/**
	 * Renders the desired {@code T} type Entity.
	 */
	public void doRender(EntityPlayerLLamaSpit entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate((float)x, (float)y + 0.15F, (float)z);
		GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
		this.bindEntityTexture(entity);

		if (this.renderOutlines)
		{
			GlStateManager.enableColorMaterial();
			GlStateManager.enableOutlineMode(this.getTeamColor(entity));
		}

		this.model.render(entity, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

		if (this.renderOutlines)
		{
			GlStateManager.disableOutlineMode();
			GlStateManager.disableColorMaterial();
		}

		GlStateManager.popMatrix();
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(EntityPlayerLLamaSpit entity)
	{
		return LLAMA_SPIT_TEXTURE;
	}
}
