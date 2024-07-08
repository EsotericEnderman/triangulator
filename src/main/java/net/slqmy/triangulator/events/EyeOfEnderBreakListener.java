package net.slqmy.triangulator.events;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.EyeOfEnderEntity;
import net.minecraft.util.math.Vec3d;
import net.slqmy.triangulator.Line;
import net.slqmy.triangulator.Line.ZeroVectorException;
import net.slqmy.triangulator.Triangulator;
import net.slqmy.triangulator.util.MapUtil;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2d;

import java.util.Map;

public class EyeOfEnderBreakListener {

    private final Triangulator triangulator;

    public EyeOfEnderBreakListener(Triangulator triangulator) {
        this.triangulator = triangulator;
    }

    public void registerListener() {
        ServerEntityEvents.ENTITY_UNLOAD.register((entity, world) -> {
            Triangulator.LOGGER.debug("Entity {} unloaded in world {}.", entity, world);
            if (entity instanceof EyeOfEnderEntity eyeOfEnder) {
                Triangulator.LOGGER.debug("Entity is an eye of ender entity.");
                onEyeOfEnderSpawn(eyeOfEnder);
            }
        });
    }

    private void onEyeOfEnderSpawn(@NotNull EyeOfEnderEntity eyeOfEnder) {
        double x = eyeOfEnder.getX();
        double y = eyeOfEnder.getY();
        double z = eyeOfEnder.getZ();

        Vec3d endPosition = new Vec3d(x, y, z);

        Map<EyeOfEnderEntity, Vec3d> eyePositionMap = MapUtil.inverMap(triangulator.startingPositionEyeMap);
        Vec3d startingPosition = null;

        for (Map.Entry<EyeOfEnderEntity, Vec3d> entry : eyePositionMap.entrySet()) {
            EyeOfEnderEntity eye = entry.getKey();

            if (eye.getX() == eyeOfEnder.getX() && eye.getY() == eyeOfEnder.getY()) {
                startingPosition = entry.getValue();
                break;
            }
        }

        if (startingPosition == null) {
            return;
        }

        Triangulator.LOGGER.debug("Eye of ender broken at {}.", endPosition);
        Triangulator.LOGGER.debug("Its starting position was {}.", startingPosition);

        Vec3d difference = endPosition.subtract(startingPosition);

        Vector2d startingPoint = new Vector2d(startingPosition.x, startingPosition.z);
        Vector2d directionVector = new Vector2d(difference.x, difference.z);

        Line eyeOfEnderDirection;

        try {
            eyeOfEnderDirection = new Line(startingPoint, directionVector);
        } catch (ZeroVectorException exception) {
            exception.printStackTrace();
            return;
        }

        Triangulator.LOGGER.debug("The line l of the path of the eye of ender is defined as follows: l = {}", eyeOfEnderDirection);
    }
}