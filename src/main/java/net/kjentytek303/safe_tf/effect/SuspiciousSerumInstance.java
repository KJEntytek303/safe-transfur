package net.kjentytek303.safe_tf.effect;

import net.kjentytek303.safe_tf.SafeTF;
import net.kjentytek303.safe_tf.config.ServerCfg;
import net.kjentytek303.safe_tf.init.InitEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Random;


public class SuspiciousSerumInstance extends MobEffectInstance {

	public boolean should_survive;
	public boolean should_insta_tf;

	public SuspiciousSerumInstance() {
		this(InitEffects.SUSPICIOUS_SERUM.get());
	}

	public SuspiciousSerumInstance(MobEffect pEffect) {
		this(pEffect, 0);
	}
	public SuspiciousSerumInstance(MobEffect pEffect, int pDuration) {
		this(pEffect, pDuration, 0);
	}
	public SuspiciousSerumInstance(MobEffect pEffect, int pDuration, int pAmplifier) {
		this(pEffect, pDuration, pAmplifier, false, true);
	}
	public SuspiciousSerumInstance(MobEffect pEffect, int pDuration, int pAmplifier, boolean pAmbient, boolean pVisible) {
		this(pEffect, pDuration, pAmplifier, pAmbient, pVisible, true);
	}
	public SuspiciousSerumInstance(MobEffect pEffect, int pDuration, int pAmplifier, boolean pAmbient, boolean pVisible, boolean pShowIcon) {
		this(pEffect, pDuration, pAmplifier, pAmbient, pVisible, pShowIcon, null, pEffect.createFactorData() );
	}
	public SuspiciousSerumInstance(MobEffect pEffect, int pDuration, int pAmplifier, boolean pAmbient, boolean pVisible, boolean pShowIcon, @Nullable MobEffectInstance pHiddenEffect, Optional<FactorData> pFactorData) {
		super(pEffect, pDuration, pAmplifier, pAmbient, pVisible, pShowIcon, pHiddenEffect, pFactorData);
		java.util.Random random = new Random();
		double random_value = random.nextDouble();
		this.should_survive = ( ServerCfg.SUSPICIOUS_SERUM_SAFE_CHANCE.get() >= random_value );
		this.should_insta_tf = ( ServerCfg.SUSPICIOUS_SERUM_INSTA_TF_CHANCE.get() >= random.nextDouble() );
	}
	public SuspiciousSerumInstance(MobEffectInstance pOther) {
		super(pOther);


		if ( pOther instanceof SuspiciousSerumInstance other) {
			this.should_survive = other.should_survive;
			this.should_insta_tf = other.should_insta_tf;
		} else {
			java.util.Random random = new Random();
			double random_value = random.nextDouble();
			this.should_survive = ( ServerCfg.SUSPICIOUS_SERUM_SAFE_CHANCE.get() >= random_value );
			this.should_insta_tf = ( ServerCfg.SUSPICIOUS_SERUM_INSTA_TF_CHANCE.get()  >= random.nextDouble() );
		}
	}

	public boolean update(MobEffectInstance pOther) {
		if (this.effect != pOther.effect) {
			SafeTF.LOGGER.warn("This method should only be called for matching effects!");
		}

		int i = this.duration;
		boolean flag = false;
		if (pOther.amplifier > this.amplifier) {
			if (pOther.isShorterDurationThan(this)) {
				MobEffectInstance mobeffectinstance = this.hiddenEffect;
				this.hiddenEffect = new SuspiciousSerumInstance(this);
				this.hiddenEffect.hiddenEffect = mobeffectinstance;
			}

			this.amplifier = pOther.amplifier;
			this.duration = pOther.duration;
			flag = true;
		}
		else if (this.isShorterDurationThan(pOther)) {
			if (pOther.amplifier == this.amplifier) {
				this.duration = pOther.duration;
				flag = true;
			}
			else if (this.hiddenEffect == null) {
				this.hiddenEffect = new SuspiciousSerumInstance(pOther);
			}
			else {
				this.hiddenEffect.update(pOther);
			}
		}

		if (!pOther.ambient && this.ambient || flag) {
			this.ambient = pOther.ambient;
			flag = true;
		}

		if (pOther.visible != this.visible) {
			this.visible = pOther.visible;
			flag = true;
		}

		if (pOther.showIcon != this.showIcon) {
			this.showIcon = pOther.showIcon;
			flag = true;
		}

		return flag;
	}
}
