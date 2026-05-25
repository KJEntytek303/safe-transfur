package net.kjentytek303.safe_tf.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static net.kjentytek303.safe_tf.SafeTF.MODID;


public class InitCTTabs {
	public static final DeferredRegister<CreativeModeTab> CT_TAB_REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

	public static final RegistryObject<CreativeModeTab> SAFE_TF_CT_TAB = CT_TAB_REGISTRY.register(
		"safe_tf",
		() -> CreativeModeTab.builder()
			.title(Component.translatable("safe_tf.ct_tabs.safe_tf"))
			.icon( () -> InitItems.CONSCIOUSNESS_SYRINGE.get().getDefaultInstance() )
			.build()
		);


	public static void fillCTTabs(BuildCreativeModeTabContentsEvent event) {
		if( event.getTabKey() == InitCTTabs.SAFE_TF_CT_TAB.getKey() ) {
			event.accept(InitItems.CONSCIOUSNESS_SYRINGE.get());
			event.accept(InitItems.SUSPICIOUS_SYRINGE.get());
		}
	}
}
