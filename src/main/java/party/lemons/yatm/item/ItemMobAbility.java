package party.lemons.yatm.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import party.lemons.yatm.capability.PlayerData;
import party.lemons.yatm.config.ModConstants;
import party.lemons.yatm.playermobs.PlayerMob;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Sam on 2/04/2018.
 */
public class ItemMobAbility extends net.minecraft.item.Item
{
	public ItemMobAbility()
	{
		this.setRegistryName(ModConstants.MODID, "mob_ability");
		this.setMaxStackSize(1);
		this.setUnlocalizedName("yatm.mob_ability");
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack stack = playerIn.getHeldItem(handIn);
		PlayerMob mob = playerIn.getCapability(PlayerData.CAPABILITY, null).getMob();
		playerIn.getCooldownTracker().setCooldown(stack.getItem(), mob.getCooldownTime());
		mob.onActivateAbility(playerIn);

		return ActionResult.newResult(EnumActionResult.PASS, stack);
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		PlayerMob mob = Minecraft.getMinecraft().player.getCapability(PlayerData.CAPABILITY, null).getMob();

		if(mob.hasAbility())
		{
			tooltip.add(TextFormatting.DARK_PURPLE + I18n.format(mob.getRegistryName() +  ".desc"));
		}
	}
}
