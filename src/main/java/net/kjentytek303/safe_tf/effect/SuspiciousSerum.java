package net.kjentytek303.safe_tf.effect;

import net.ltxprogrammer.changed.entity.variant.TransfurVariant;
import net.ltxprogrammer.changed.process.ProcessTransfur;
import net.minecraft.Util;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static net.kjentytek303.safe_tf.init.InitEffects.SUSPICIOUS_SERUM;

public class SuspiciousSerum extends MobEffect {

	public SuspiciousSerum() {
		super(MobEffectCategory.NEUTRAL, 0xfffff);
	}

	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}

	@Override
	public List<ItemStack> getCurativeItems() {
		return new ArrayList<>();
	}

	@Override
	public void applyEffectTick(@NotNull LivingEntity pLivingEntity, int pAmplifier) {
		pLivingEntity.getRandom().nextDouble();
		MobEffectInstance effect_instance = pLivingEntity.getEffect(SUSPICIOUS_SERUM.get());
		if (effect_instance == null ) {	return; }

		if( effect_instance instanceof SuspiciousSerumInstance experimentalSerumInstance ) {
			if ( experimentalSerumInstance.should_insta_tf ) {
				ProcessTransfur.progressTransfur(pLivingEntity, 3402823466385288598.0f, (TransfurVariant) Util.getRandom((List) TransfurVariant.getPublicTransfurVariants().collect(Collectors.toList()), pLivingEntity.getRandom()));
				pLivingEntity.removeEffect(SUSPICIOUS_SERUM.get());
				return;
			}
			if (pLivingEntity.getHealth() > 3.5F) { pLivingEntity.hurt(pLivingEntity.damageSources().wither(), 2.25F); return; }

			if (!experimentalSerumInstance.should_survive) {
				pLivingEntity.hurt(pLivingEntity.damageSources().wither(), 3.0F);
			}
			else {
				if (pLivingEntity.getHealth() > 1.5f) {
					pLivingEntity.hurt(pLivingEntity.damageSources().wither(), 1.0F);
				}
			}
		}
	}



}
