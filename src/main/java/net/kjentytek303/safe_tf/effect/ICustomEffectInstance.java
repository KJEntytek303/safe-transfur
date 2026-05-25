package net.kjentytek303.safe_tf.effect;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;


public interface ICustomEffectInstance {
	MobEffectInstance loadInstance(CompoundTag tag);
	void writeInstance( CompoundTag tag);
	Object checkAdditionalData (String data);
}