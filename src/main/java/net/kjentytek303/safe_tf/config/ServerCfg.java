package net.kjentytek303.safe_tf.config;

import net.minecraftforge.common.ForgeConfigSpec;


public class ServerCfg {

	public static final ForgeConfigSpec SPEC;
	public static final ForgeConfigSpec.Builder BUILDER;

	public static final ForgeConfigSpec.ConfigValue<CAddonHandleMode> UNSAFE_SERUM_CADDON_HANDLING;
	public static final ForgeConfigSpec.ConfigValue<Integer> UNSAFE_SERUM_DURATION;

	public static final ForgeConfigSpec.ConfigValue<CAddonHandleMode> SUSPICIOUS_SERUM_CADDON_HANDLING;
	public static final ForgeConfigSpec.ConfigValue<Integer> SUSPICIOUS_SERUM_DURATION;
	public static final ForgeConfigSpec.ConfigValue<Double> SUSPICIOUS_SERUM_INSTA_TF_CHANCE;
	public static final ForgeConfigSpec.ConfigValue<Double> SUSPICIOUS_SERUM_SAFE_CHANCE;


	static {
		BUILDER = new ForgeConfigSpec.Builder();
		BUILDER.comment("How should Unsafe Serum effect handle CAddon interaction?");
		BUILDER.comment("Default: PLAY_CADDON_MINIGAME");
		BUILDER.comment("Note: CAddon must be installed in order for it to work");
		UNSAFE_SERUM_CADDON_HANDLING = BUILDER.defineEnum("unsafe-serum.caddon-handling", CAddonHandleMode.PLAY_CADDON_MINIGAME);

		BUILDER.comment("Duration of the Unsafe Serum effect when applied via Consciousness Syringe in seconds");
		BUILDER.comment("Default: 60");
		UNSAFE_SERUM_DURATION = BUILDER.defineInRange("unsafe-serum.default-effect-duration", 60, -1, 100000000);


		BUILDER.comment("How should suspicious serum effect handle CAddon interaction?");
		BUILDER.comment("Default: PLAY_CADDON_MINIGAME");
		BUILDER.comment("Note: CAddon must be installed in order for it to work");
		SUSPICIOUS_SERUM_CADDON_HANDLING = BUILDER.defineEnum("suspicious-serum.caddon_handling", CAddonHandleMode.PLAY_CADDON_MINIGAME);

		BUILDER.comment("Duration of the suspicious serum effect when applied via suspicious syringe in seconds");
		BUILDER.comment("Default: 60");
		SUSPICIOUS_SERUM_DURATION = BUILDER.defineInRange("suspicious-serum.default-effect-duration", 60, -1, 100000000);

		BUILDER.comment("Chance that the suspicious serum effect will not kill the player");
		BUILDER.comment("Default: 0.75");
		SUSPICIOUS_SERUM_SAFE_CHANCE = BUILDER.defineInRange("suspicious-serum.is-safe-chance", 0.75, 0, 1);

		BUILDER.comment("Chance to get instantly transfurred by the suspicious serum effect");
		BUILDER.comment("Default: 0.1");
		SUSPICIOUS_SERUM_INSTA_TF_CHANCE = BUILDER.defineInRange("suspicious-serum.insta-tf-chance", 0.1, 0, 1);

		SPEC = BUILDER.build();
	}

	public enum CAddonHandleMode {
		FORCE_SAFE_TF,
		PLAY_CADDON_MINIGAME
	}
}