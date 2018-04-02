package party.lemons.yatm.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import party.lemons.yatm.config.ModConstants;

/**
 * Created by Sam on 2/04/2018.
 */
@Mod.EventBusSubscriber
@GameRegistry.ObjectHolder(ModConstants.MODID)
public class ModItems
{
	@GameRegistry.ObjectHolder("mob_ability")
	public static Item MOB_ABILITY;

	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(
				new ItemMobAbility()
		);
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event)
	{
		ModelLoader.setCustomModelResourceLocation(MOB_ABILITY, 0, new ModelResourceLocation(MOB_ABILITY.getRegistryName(), "inventory"));
	}
}
