package party.lemons.yatm.entity.render;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import org.apache.commons.io.FilenameUtils;
import party.lemons.yatm.config.ModConstants;
import party.lemons.yatm.entity.EntityHuman;

import java.util.UUID;

/**
 * Created by Sam on 31/03/2018.
 */
public class RenderHuman extends RenderBiped<EntityHuman>
{

	protected ResourceLocation[] backup = new ResourceLocation[]
			{
					new ResourceLocation(ModConstants.MODID, "textures/entity/skins/" + "generic_1.png"),
					new ResourceLocation(ModConstants.MODID, "textures/entity/skins/" + "generic_2.png"),
					new ResourceLocation(ModConstants.MODID, "textures/entity/skins/" + "generic_3.png"),
					new ResourceLocation(ModConstants.MODID, "textures/entity/skins/" + "generic_4.png"),
					new ResourceLocation(ModConstants.MODID, "textures/entity/skins/" + "generic_5.png"),
					new ResourceLocation(ModConstants.MODID, "textures/entity/skins/" + "lemons.png"),
					new ResourceLocation(ModConstants.MODID, "textures/entity/skins/" + "nayxerr.png"),
					new ResourceLocation(ModConstants.MODID, "textures/entity/skins/" + "mickson.png"),
					new ResourceLocation(ModConstants.MODID, "textures/entity/skins/" + "weather.png"),
					new ResourceLocation(ModConstants.MODID, "textures/entity/skins/" + "searge.png"),
					new ResourceLocation(ModConstants.MODID, "textures/entity/skins/" + "dinnerbone.png"),
					new ResourceLocation(ModConstants.MODID, "textures/entity/skins/" + "rad.png"),
					new ResourceLocation(ModConstants.MODID, "textures/entity/skins/" + "froot.png"),
					new ResourceLocation(ModConstants.MODID, "textures/entity/skins/" + "boi.png"),
					new ResourceLocation(ModConstants.MODID, "textures/entity/skins/" + "sayolo.png"),
					new ResourceLocation(ModConstants.MODID, "textures/entity/skins/" + "panda.png"),
					new ResourceLocation(ModConstants.MODID, "textures/entity/skins/" + "shawstin.png"),
					new ResourceLocation(ModConstants.MODID, "textures/entity/skins/" + "asus.png"),
					new ResourceLocation(ModConstants.MODID, "textures/entity/skins/" + "jodha.png"),
			};

	public RenderHuman(RenderManager renderManagerIn)
	{
		super(renderManagerIn, new ModelPlayer(0, false),0.5F);

		LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this);
		this.addLayer(layerbipedarmor);
		this.addLayer(new LayerBipedArmor(this));
		this.addLayer(new LayerHeldItem(this));
		this.addLayer(new LayerArrow(this));
		this.addLayer(new LayerCustomHead(((ModelBiped)this.getMainModel()).bipedHead));
	}

	public void doRender(EntityHuman entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		double d0 = y;

		if (entity.isSneaking())
		{
			d0 = y - 0.125D;
		}

		this.setModelVisibilities(entity);
		GlStateManager.enableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
		super.doRender(entity, x, d0, z, entityYaw, partialTicks);
		GlStateManager.disableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);
	}


	private void setModelVisibilities(EntityHuman clientPlayer)
	{
		ModelPlayer modelplayer = (ModelPlayer) this.getMainModel();

		ItemStack itemstack = clientPlayer.getHeldItemMainhand();
		ItemStack itemstack1 = clientPlayer.getHeldItemOffhand();
		modelplayer.setVisible(true);
		modelplayer.bipedHeadwear.showModel = true;
		modelplayer.bipedBodyWear.showModel = true;
		modelplayer.bipedLeftLegwear.showModel = true;
		modelplayer.bipedRightLegwear.showModel = true;
		modelplayer.bipedLeftArmwear.showModel = true;
		modelplayer.bipedRightArmwear.showModel = true;
		modelplayer.isSneak = clientPlayer.isSneaking();
		ModelBiped.ArmPose modelbiped$armpose = ModelBiped.ArmPose.EMPTY;
		ModelBiped.ArmPose modelbiped$armpose1 = ModelBiped.ArmPose.EMPTY;

		if (!itemstack.isEmpty())
		{
			modelbiped$armpose = ModelBiped.ArmPose.ITEM;

			if (clientPlayer.getItemInUseCount() > 0)
			{
				EnumAction enumaction = itemstack.getItemUseAction();

				if (enumaction == EnumAction.BLOCK)
				{
					modelbiped$armpose = ModelBiped.ArmPose.BLOCK;
				}
				else if (enumaction == EnumAction.BOW)
				{
					modelbiped$armpose = ModelBiped.ArmPose.BOW_AND_ARROW;
				}
			}
		}

		if (!itemstack1.isEmpty())
		{
			modelbiped$armpose1 = ModelBiped.ArmPose.ITEM;

			if (clientPlayer.getItemInUseCount() > 0)
			{
				EnumAction enumaction1 = itemstack1.getItemUseAction();

				if (enumaction1 == EnumAction.BLOCK)
				{
					modelbiped$armpose1 = ModelBiped.ArmPose.BLOCK;
				}
				// FORGE: fix MC-88356 allow offhand to use bow and arrow animation
				else if (enumaction1 == EnumAction.BOW)
				{
					modelbiped$armpose1 = ModelBiped.ArmPose.BOW_AND_ARROW;
				}
			}
		}

		if (clientPlayer.getPrimaryHand() == EnumHandSide.RIGHT)
		{
			modelplayer.rightArmPose = modelbiped$armpose;
			modelplayer.leftArmPose = modelbiped$armpose1;
		}
		else
		{
			modelplayer.rightArmPose = modelbiped$armpose1;
			modelplayer.leftArmPose = modelbiped$armpose;
		}
	}


	@Override
	protected ResourceLocation getEntityTexture(EntityHuman entity)
	{

		int var = entity.getDataManager().get(EntityHuman.VAR).intValue();
		if(var == -1)
			return super.getEntityTexture(entity);

		return backup[var % backup.length];
	}

}
