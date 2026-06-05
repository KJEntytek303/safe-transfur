package net.kjentytek303.safe_tf.util;

import net.foxyas.changedaddon.ChangedAddonMod;
import net.foxyas.changedaddon.init.ChangedAddonGameRules;
import net.foxyas.changedaddon.network.ChangedAddonVariables;
import net.foxyas.changedaddon.network.packet.ClientboundOpenFTKCScreenPacket;
import net.foxyas.changedaddon.qte.FightToKeepConsciousness;
import net.kjentytek303.safe_tf.config.ServerCfg;
import net.ltxprogrammer.changed.entity.variant.TransfurVariantInstance;
import net.ltxprogrammer.changed.process.ProcessTransfur;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.network.PacketDistributor;

import static net.foxyas.changedaddon.qte.FightToKeepConsciousness.failFTKC;
import static net.foxyas.changedaddon.qte.FightToKeepConsciousness.getStruggleNeed;
import static net.foxyas.changedaddon.qte.FightToKeepConsciousness.getStruggleTime;
import static net.foxyas.changedaddon.qte.FightToKeepConsciousness.successFTKC;
import static net.kjentytek303.safe_tf.config.ServerCfg.SUSPICIOUS_SERUM_CADDON_HANDLING;
import static net.kjentytek303.safe_tf.config.ServerCfg.UNSAFE_SERUM_CADDON_HANDLING;
import static net.kjentytek303.safe_tf.init.InitEffects.*;


public class EffectTransfurEventHandler {
	@SubscribeEvent
	public static void onTransfur(ProcessTransfur.KeepConsciousEvent event) {
		if (event.shouldKeepConscious || event.player == null) {return;}

		if (event.player.getEffect(UNSAFE_SERUM.get()) == null && event.player.getEffect(SUSPICIOUS_SERUM.get()) == null) {
			return;
		}

		//Player must have the effect.
		if (!ModList.get().isLoaded("changed_addon") ) {
			event.shouldKeepConscious = true;
			return;
		}


		event.shouldKeepConscious = handleCaddonCfg(event);
		if (event.shouldKeepConscious) {
			return;
		}

		//CAddon is loaded
		//Thx foxyas for help.
		Level level = event.player.level();
		if (event.player instanceof ServerPlayer player) {

			ChangedAddonVariables.PlayerVariables vars1 = ChangedAddonVariables.ofOrDefault(player);
			FightToKeepConsciousness.MinigameType minigameType = FightToKeepConsciousness.MinigameType.getRandom(player.getRandom());
			vars1.isTransfuredBySafeMethod = false;
			updatePlayerVariables(vars1, minigameType, 0, player);
			ChangedAddonMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> player), new ClientboundOpenFTKCScreenPacket(minigameType));
			event.shouldKeepConscious = true;

		}
	}

	public static boolean handleCaddonCfg(ProcessTransfur.KeepConsciousEvent event) {
		ChangedAddonVariables.PlayerVariables vars = ChangedAddonVariables.ofOrDefault(event.player);

		vars.isTransfuredBySafeMethod = true;
		vars.syncPlayerVariables(event.player);

		boolean ftkc = event.player.level().getGameRules().getBoolean(ChangedAddonGameRules.FIGHT_TO_KEEP_CONSCIOUSNESS);

		if(ftkc && event.player instanceof ServerPlayer serverPlayer) {
			successFTKC(vars, serverPlayer);
			vars.syncPlayerVariables(serverPlayer);
			return true;
		}

		if ( event.player.hasEffect(UNSAFE_SERUM.get()) && UNSAFE_SERUM_CADDON_HANDLING.get() == ServerCfg.CAddonHandleMode.FORCE_SAFE_TF  ) {
			return true;
		}

		if ( event.player.hasEffect(SUSPICIOUS_SERUM.get()) && SUSPICIOUS_SERUM_CADDON_HANDLING.get() == ServerCfg.CAddonHandleMode.FORCE_SAFE_TF ) {
			return true;
		}

		return false;
	}


	@SubscribeEvent
	public static void onTick(TickEvent.PlayerTickEvent event) {
		if (ModList.get().isLoaded("changed_addon")) {
			if (event.phase != TickEvent.Phase.END) {return;}
			if (!event.player.isAlive()) {return;}
			if (!(event.player instanceof ServerPlayer player)) {return;}

			ChangedAddonVariables.PlayerVariables vars = ChangedAddonVariables.ofOrDefault(player);

			if (vars.FTKCminigameType == null) {
				return;
			}

			TransfurVariantInstance<?> instance = ProcessTransfur.getPlayerTransfurVariant(player);

			if (instance == null) {
				successFTKC(vars, player);
				return;
			}

			++vars.ticksFightingForConsciousness;
			vars.syncPlayerVariables(player);
			if (vars.ticksFightingForConsciousness >= getStruggleTime()) {
				if ((double) vars.consciousnessFightProgress >= getStruggleNeed()) {
					successFTKC(vars, player);
					return;
				}
				failFTKC(vars, player);
			}
		}
	}
	public static void updatePlayerVariables(ChangedAddonVariables.PlayerVariables vars, FightToKeepConsciousness.MinigameType minigameType, int progress, Entity entity) {
		vars.FTKCminigameType = minigameType;
		vars.consciousnessFightProgress = progress;
		vars.syncPlayerVariables(entity);
	}
}
