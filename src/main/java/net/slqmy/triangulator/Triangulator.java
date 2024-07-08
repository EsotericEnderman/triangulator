package net.slqmy.triangulator;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.EyeOfEnderEntity;
import net.minecraft.util.math.Vec3d;
import net.slqmy.triangulator.events.EyeOfEnderBreakListener;
import net.slqmy.triangulator.events.EyeOfEnderSpawnListener;
import net.slqmy.triangulator.events.EyeOfEnderThrowListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class Triangulator implements ModInitializer {
	public static final String MOD_ID = "triangulator";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public HashMap<Vec3d, EyeOfEnderEntity> startingPositionEyeMap = new HashMap<>();

	@Override
	public void onInitialize() {
		LOGGER.info("[Triangulator]: Loading Triangulator mod.");

		new EyeOfEnderThrowListener(this).registerListener();
		new EyeOfEnderSpawnListener(this).registerListener();
		new EyeOfEnderBreakListener(this).registerListener();
	}
}