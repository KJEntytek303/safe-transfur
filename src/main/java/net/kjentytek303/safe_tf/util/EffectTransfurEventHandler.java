package net.kjentytek303.safe_tf.util;

import net.ltxprogrammer.changed.process.ProcessTransfur;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import static net.kjentytek303.safe_tf.init.InitEffects.*;
import static net.kjentytek303.safe_tf.util.CAddonHandler.handleCAddonMinigame;
import static net.kjentytek303.safe_tf.util.CAddonHandler.handleCaddonCompatCfg;
import static net.kjentytek303.safe_tf.util.CAddonHandler.onPlayerTickHandleCAddon;


@Mod.EventBusSubscriber
public class EffectTransfurEventHandler {
	@SubscribeEvent
	public static void onPlayerTransfur(ProcessTransfur.KeepConsciousEvent event) {
		//if already safe tfed or not a player
		if (event.keepConscious || event.shouldKeepConscious || event.player == null) {return;}

		//if doesn't have the effect, return.
		if (! ( event.player.hasEffect(UNSAFE_SERUM.get()) || event.player.hasEffect(SUSPICIOUS_SERUM.get()) ) ) {
			return;
		}

		//Player must have the effect. If CAddon isn't loaded, handle it normally.
		if (event.player.level().isClientSide()) { return; }

		if (!ModList.get().isLoaded("changed_addon") ) {
			event.shouldKeepConscious = true;
			return;
		}

		//CAddon compat start
		event.shouldKeepConscious = handleCaddonCompatCfg(event);
		if (event.shouldKeepConscious) { return; }
		handleCAddonMinigame(event);
	}


	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if(!ModList.get().isLoaded("changed_addon")) {
			return;
		}
		onPlayerTickHandleCAddon(event);
	}
}
