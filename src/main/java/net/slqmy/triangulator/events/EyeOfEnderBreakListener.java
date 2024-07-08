package net.slqmy.triangulator.events;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.world.entity.projectile.EyeOfEnder;
import net.minecraft.world.phys.Vec3;
import net.slqmy.triangulator.Line;
import net.slqmy.triangulator.Triangulator;
import net.slqmy.triangulator.util.ChatUtil;
import net.slqmy.triangulator.util.MapUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class EyeOfEnderBreakListener {

    private final Triangulator triangulator;

    public EyeOfEnderBreakListener(Triangulator triangulator) {
        this.triangulator = triangulator;
    }

    public void registerListener() {
        ServerEntityEvents.ENTITY_UNLOAD.register((entity, world) -> {
            if (entity instanceof EyeOfEnder eyeOfEnder) {
                onEyeOfEnderSpawn(eyeOfEnder);
            }
        });
    }

    private void onEyeOfEnderSpawn(@NotNull EyeOfEnder eyeOfEnder) {
        double x = eyeOfEnder.getX();
        double y = eyeOfEnder.getY();
        double z = eyeOfEnder.getZ();

        Vec3 endPosition = new Vec3(x, y, z);

        Map<EyeOfEnder, Vec3> eyePositionMap = MapUtil.inverMap(triangulator.startingPositionEyeMap);
        Vec3 startingPosition = null;

        for (Map.Entry<EyeOfEnder, Vec3> entry : eyePositionMap.entrySet()) {
            EyeOfEnder eye = entry.getKey();

            if (eye.getX() == eyeOfEnder.getX() && eye.getY() == eyeOfEnder.getY()) {
                startingPosition = entry.getValue();
                break;
            }
        }

        if (startingPosition == null) {
            return;
        }

        Triangulator.LOGGER.info("Eye of ender broken at {}, {}, {}.", x, y, z);
        Triangulator.LOGGER.info("Its starting position was {}.", startingPosition);

        Vec3 difference = endPosition.subtract(startingPosition);

        double deltaX = difference.x;
        double deltaZ = difference.z;

        Triangulator.LOGGER.info("Δx = {}", deltaX);
        Triangulator.LOGGER.info("Δz = {}", deltaZ);

        // Vector2d initialPoint = new Vector2d(startingPosition.x, startingPosition.z);
        // Vector2d directionVector = new Vector2d(difference.x, difference.z);

        // Triangulator.LOGGER.info("The line l of the path of the eye of ender is defined as follows: l = {({}, {}) + ({}, {})t | t > 0}", initialPoint.x, initialPoint.y, directionVector.x, directionVector.y);

        // z = m(x - x_1) + z_1 = mx - mx_1 + z_1

        double slope = deltaZ / deltaX;
        double zIntercept = -slope * startingPosition.x + startingPosition.z;

        Triangulator.LOGGER.info("The slope m = Δz/Δx = {}", slope);
        Triangulator.LOGGER.info("The z-intercept = {}", zIntercept);

        Line line = new Line(slope, zIntercept, "z");

        Triangulator.LOGGER.info("The line l of the path of the eye of ender is defined as follows: l: {}", line);

        ChatUtil.sendMessage("§7The line §bl §7 which the eye of ender follows is defined as such:");
        ChatUtil.sendMessage("§bl§7: §9z §7= §f" + slope + "§cx §7+ §f" + line.getyIntercept());
    }
}