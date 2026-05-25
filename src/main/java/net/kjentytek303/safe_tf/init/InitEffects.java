package net.kjentytek303.safe_tf.init;

import net.kjentytek303.safe_tf.SafeTF;
import net.kjentytek303.safe_tf.effect.SuspiciousSerum;
import net.kjentytek303.safe_tf.effect.UnsafeSerum;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class InitEffects {
	public static final DeferredRegister<MobEffect> EFFECT_REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, SafeTF.MODID);

	public static final RegistryObject<MobEffect> UNSAFE_SERUM = EFFECT_REGISTRY.register("unsafe_serum", UnsafeSerum::new );
	public static final RegistryObject<MobEffect> SUSPICIOUS_SERUM = EFFECT_REGISTRY.register("suspicious_serum", SuspiciousSerum::new);

}
