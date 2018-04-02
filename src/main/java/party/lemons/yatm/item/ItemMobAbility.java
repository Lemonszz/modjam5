package party.lemons.yatm.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import party.lemons.yatm.capability.PlayerData;
import party.lemons.yatm.config.ModConstants;
import party.lemons.yatm.playermobs.PlayerMob;

/**
 * Created by Sam on 2/04/2018.
 */
public class ItemMobAbility extends net.minecraft.item.Item
{
	public ItemMobAbility()
	{
		this.setRegistryName(ModConstants.MODID, "mob_ability");
		this.setMaxStackSize(1);
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
}
