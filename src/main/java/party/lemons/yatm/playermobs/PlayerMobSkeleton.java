package party.lemons.yatm.playermobs;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/**
 * Created by Sam on 31/03/2018.
 */
public class PlayerMobSkeleton extends PlayerMobBurnable
{
	@Override
	public Class getMobClass()
	{
		return EntitySkeleton.class;
	}

	@Override
	public void onPlayerTick(EntityPlayer player)
	{
		super.onPlayerTick(player);
	}

	@Override
	public void onInitialSpawn(EntityPlayer player)
	{
		ItemStack bowStack = new ItemStack(Items.BOW);
		bowStack.addEnchantment(Enchantments.UNBREAKING, 5);
		bowStack.addEnchantment(Enchantments.INFINITY, 1);
		bowStack.setStackDisplayName("Skeleton Bow");

		player.addItemStackToInventory(bowStack);

		ItemStack arrowStack = new ItemStack(Items.ARROW, 10);
		player.addItemStackToInventory(arrowStack);
	}
}
