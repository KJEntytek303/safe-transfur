package net.kjentytek303.safe_tf.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


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
