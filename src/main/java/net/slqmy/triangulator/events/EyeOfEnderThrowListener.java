package net.slqmy.triangulator.events;

import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.slqmy.triangulator.Triangulator;
import org.jetbrains.annotations.NotNull;

public class EyeOfEnderThrowListener {

    private final Triangulator triangulator;

    public EyeOfEnderThrowListener(Triangulator triangulator) {
        this.triangulator = triangulator;
    }

    public void registerListener() {
        UseItemCallback.EVENT.register((player, world, hand) -> {
            ItemStack item = player.getStackInHand(hand);
            Item itemType = item.getItem();

            if (itemType == Items.ENDER_EYE) {
                onEyeOfEnderThrow(item, player);
            }

            return TypedActionResult.pass(item);
        });
    }

    private void onEyeOfEnderThrow(ItemStack eyeOfEnderItem, @NotNull PlayerEntity player) {
        Triangulator.LOGGER.info("{} threw an eye of ender.", player.getName().getString());

        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();

        Vec3d location = new Vec3d(x, y, z);

        Triangulator.LOGGER.info("{}'s coordinates: {}.", player.getName(), location);

        triangulator.startingPositionEyeMap.put(location, null);
    }
}
