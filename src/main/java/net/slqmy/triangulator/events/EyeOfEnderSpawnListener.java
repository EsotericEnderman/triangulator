package net.slqmy.triangulator.events;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.EyeOfEnderEntity;
import net.minecraft.util.TranslatableOption;
import net.minecraft.util.Util;
import net.minecraft.util.math.Vec3d;
import net.slqmy.triangulator.Triangulator;
import org.jetbrains.annotations.NotNull;

public class EyeOfEnderSpawnListener {

    private final Triangulator triangulator;

    public EyeOfEnderSpawnListener(Triangulator triangulator) {
        this.triangulator = triangulator;
    }

    public void registerListener() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (entity instanceof EyeOfEnderEntity eyeOfEnder) {
                onEyeOfEnderSpawn(eyeOfEnder);
            }
        });
    }

    private void onEyeOfEnderSpawn(@NotNull EyeOfEnderEntity eyeOfEnder) {
        double x = eyeOfEnder.getX();
        double y = eyeOfEnder.getY();
        double z = eyeOfEnder.getZ();

        Vec3d position = new Vec3d(x, y, z);

        Triangulator.LOGGER.info("Eye of ender spawned at {}, {}, {}.", x, y, z);

        Triangulator.LOGGER.info("Comparing location of eye of ender to known throwing locations.");

        for (Vec3d vector : triangulator.startingPositionEyeMap.keySet()) {
            Triangulator.LOGGER.info("Comparing {} to {}", position, vector);

            if (vector.x == position.x && vector.z == position.z) {
                Triangulator.LOGGER.info("Vectors are equivalent, setting data for eye of ender.");

                triangulator.startingPositionEyeMap.put(vector, eyeOfEnder);

                Triangulator.LOGGER.info("{}: {}", vector, triangulator.startingPositionEyeMap.get(vector));
            }
        }
    }
}