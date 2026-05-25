package net.kjentytek303.safe_tf.mixin;


import net.kjentytek303.safe_tf.effect.SuspiciousSerumInstance;
import net.kjentytek303.safe_tf.init.InitEffects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;



@Mixin(net.minecraft.world.effect.MobEffectInstance.class)
public class EffectInstanceMixin {

	@Inject( at=@At("RETURN"), method= "loadSpecifiedEffect", cancellable = true)
	private static void safe_tf_mixin_loadSpecifiedEffectMixin(MobEffect pEffect, CompoundTag pNbt, CallbackInfoReturnable<MobEffectInstance> cir) {

		if( pEffect != InitEffects.SUSPICIOUS_SERUM.get() ) {return; }

		SuspiciousSerumInstance instance = new SuspiciousSerumInstance( cir.getReturnValue());
		instance.should_survive = pNbt.getBoolean("safe_tf:should_survive");
		instance.should_insta_tf = pNbt.getBoolean( "safe_tf:should_insta_tf");
		cir.setReturnValue(instance);

	}

	@Inject( at=@At("RETURN"), method= "writeDetailsTo")
	private void safe_tf_mixin_writeDetailsTo(CompoundTag pNbt, CallbackInfo ci) {

		MobEffectInstance instance = ((MobEffectInstance) (Object) this);
		if ( instance.effect != InitEffects.SUSPICIOUS_SERUM.get() ) { return; }

		if( instance instanceof SuspiciousSerumInstance experimentalSerumInstance ) {
			pNbt.putBoolean( "safe_tf:should_survive", experimentalSerumInstance.should_survive );
			pNbt.putBoolean( "safe_tf:should_insta_tf", experimentalSerumInstance.should_insta_tf);
		}
	}
}
