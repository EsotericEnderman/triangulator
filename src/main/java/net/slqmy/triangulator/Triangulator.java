package net.slqmy.triangulator;

import net.fabricmc.api.ModInitializer;

import net.minecraft.world.entity.projectile.EyeOfEnder;
import net.minecraft.world.phys.Vec3;
import net.slqmy.triangulator.events.EyeOfEnderBreakListener;
import net.slqmy.triangulator.events.EyeOfEnderSpawnListener;
import net.slqmy.triangulator.events.EyeOfEnderThrowListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class Triangulator implements ModInitializer {
	public static final String MOD_ID = "triangulator";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public HashMap<Vec3, EyeOfEnder> startingPositionEyeMap = new HashMap<>();

	@Override
	public void onInitialize() {
		LOGGER.info("[Triangulator]: Loading Triangulator mod.");

		new EyeOfEnderThrowListener(this).registerListener();
		new EyeOfEnderSpawnListener(this).registerListener();
		new EyeOfEnderBreakListener(this).registerListener();
	}
}