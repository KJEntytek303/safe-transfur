package net.kjentytek303.safe_tf.init;

import net.kjentytek303.safe_tf.SafeTF;
import net.kjentytek303.safe_tf.item.ConsciousnessSyringeItem;
import net.kjentytek303.safe_tf.item.SuspiciousSyringeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class InitItems {
	
	public static final DeferredRegister<Item> ITEM_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, SafeTF.MODID);

	public static final RegistryObject<Item> CONSCIOUSNESS_SYRINGE = ITEM_REGISTRY.register(
		"consciousness_syringe",
		()-> new ConsciousnessSyringeItem( new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON) )
	);

	public static final RegistryObject<Item> SUSPICIOUS_SYRINGE = ITEM_REGISTRY.register(
		"suspicious_syringe",
		() -> new SuspiciousSyringeItem( new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON))
	);
}
