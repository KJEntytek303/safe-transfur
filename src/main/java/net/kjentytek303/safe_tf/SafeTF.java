package net.kjentytek303.safe_tf;

import net.kjentytek303.safe_tf.config.ServerCfg;
import net.kjentytek303.safe_tf.init.InitCTTabs;
import net.kjentytek303.safe_tf.init.InitEffects;
import net.kjentytek303.safe_tf.init.InitItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(SafeTF.MODID)
public class SafeTF
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "safe_tf";
    public static final Logger LOGGER = LogManager.getLogger(SafeTF.class);

    public SafeTF(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(InitCTTabs::fillCTTabs);

        MinecraftForge.EVENT_BUS.register(this);

        InitItems.ITEM_REGISTRY.register(modEventBus);
        InitEffects.EFFECT_REGISTRY.register(modEventBus);
        InitCTTabs.CT_TAB_REGISTRY.register(modEventBus);

        context.registerConfig(ModConfig.Type.SERVER, ServerCfg.SPEC, "safe-tf-server.toml");
    }

    private void commonSetup(final FMLCommonSetupEvent event) { }

}
