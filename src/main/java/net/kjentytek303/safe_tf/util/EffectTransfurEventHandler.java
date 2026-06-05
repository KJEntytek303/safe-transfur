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
import net.minecraftforge.eventbus.api.EventPriority;
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
	@SubscribeEvent( priority = EventPriority.HIGH )
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

		boolean ftkc = event.player.level().getGameRules().getBoolean(ChangedAddonGameRules.FIGHT_TO_KEEP_CONSCIOUSNESS);
		if (event.player instanceof ServerPlayer player ) {
			event.shouldKeepConscious = true;
			ChangedAddonVariables.PlayerVariables vars = ChangedAddonVariables.ofOrDefault(player);
			vars.isTransfuredBySafeMethod = true;

			if (!ftkc) {
				FightToKeepConsciousness.MinigameType minigameType = FightToKeepConsciousness.MinigameType.getRandom(player.getRandom());
				vars.isTransfuredBySafeMethod = false;
				updatePlayerVariables(vars, minigameType, 0, player);
				vars.syncPlayerVariables(player);
				ChangedAddonMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> player), new ClientboundOpenFTKCScreenPacket(minigameType));
				return;
			}
			vars.syncPlayerVariables(player);
		}
	}

	public static boolean handleCaddonCompatCfg(ProcessTransfur.KeepConsciousEvent event) {

		if ( event.player.hasEffect(UNSAFE_SERUM.get()) && UNSAFE_SERUM_CADDON_HANDLING.get() == ServerCfg.CAddonHandleMode.FORCE_SAFE_TF  ) {
			return true;
		}

		if ( event.player.hasEffect(SUSPICIOUS_SERUM.get()) && SUSPICIOUS_SERUM_CADDON_HANDLING.get() == ServerCfg.CAddonHandleMode.FORCE_SAFE_TF ) {
			return true;
		}

		return false;
	}


	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase != TickEvent.Phase.END) { return; }
		if (event.player instanceof ServerPlayer player) {
			if (!player.isAlive()) { return; }


			if (player.level().getGameRules().getBoolean(ChangedAddonGameRules.FIGHT_TO_KEEP_CONSCIOUSNESS) ) {
				return;
			}

			TransfurVariantInstance<?> instance = ProcessTransfur.getPlayerTransfurVariant(player);
			ChangedAddonVariables.PlayerVariables vars = ChangedAddonVariables.ofOrDefault(player);
			vars.timeAfterVictoryOfFTK = 0;
			vars.syncPlayerVariables(player);

			if (vars.FTKCminigameType == null) {
				return;
			}

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
