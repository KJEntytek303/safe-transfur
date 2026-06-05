package net.kjentytek303.safe_tf.effect;

import net.foxyas.changedaddon.ChangedAddonMod;
import net.foxyas.changedaddon.init.ChangedAddonGameRules;
import net.foxyas.changedaddon.network.ChangedAddonVariables;
import net.foxyas.changedaddon.network.packet.ClientboundOpenFTKCScreenPacket;
import net.foxyas.changedaddon.qte.FightToKeepConsciousness.MinigameType;
import net.kjentytek303.safe_tf.config.ServerCfg;
import net.kjentytek303.safe_tf.init.InitEffects;
import net.ltxprogrammer.changed.entity.variant.TransfurVariantInstance;
import net.ltxprogrammer.changed.process.ProcessTransfur;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static net.foxyas.changedaddon.qte.FightToKeepConsciousness.failFTKC;
import static net.foxyas.changedaddon.qte.FightToKeepConsciousness.getStruggleNeed;
import static net.foxyas.changedaddon.qte.FightToKeepConsciousness.getStruggleTime;
import static net.foxyas.changedaddon.qte.FightToKeepConsciousness.successFTKC;


public class UnsafeSerum extends MobEffect {

	public UnsafeSerum() {
		super(MobEffectCategory.NEUTRAL, 0x9F9F9F);
	}

	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}

	//Ban milk from curing this shit
	@Override
	public List<ItemStack> getCurativeItems() {
		return new ArrayList<>();
	}

	@Override
	public void applyEffectTick(@NotNull LivingEntity pLivingEntity, int pAmplifier) {
		//Deal damage similar to poison V
		if (pLivingEntity.getHealth() > 3.5F) {
			pLivingEntity.hurt(pLivingEntity.damageSources().wither(), 3.0F);
		}
		else if (pLivingEntity.getHealth() > 1.5f) {
			pLivingEntity.hurt(pLivingEntity.damageSources().wither(), 1.0F);
		}
	}
}
