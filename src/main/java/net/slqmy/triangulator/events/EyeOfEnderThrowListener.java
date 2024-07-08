package net.slqmy.triangulator.events;

import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.slqmy.triangulator.Triangulator;
import org.jetbrains.annotations.NotNull;

public class EyeOfEnderThrowListener {

    private final Triangulator triangulator;

    public EyeOfEnderThrowListener(Triangulator triangulator) {
        this.triangulator = triangulator;
    }

    public void registerListener() {
        UseItemCallback.EVENT.register((player, world, hand) -> {
            ItemStack item = player.getItemInHand(hand);
            Item itemType = item.getItem();

            if (itemType == Items.ENDER_EYE) {
                onEyeOfEnderThrow(item, player);
            }

            return InteractionResultHolder.pass(item);
        });
    }

    private void onEyeOfEnderThrow(ItemStack eyeOfEnderItem, @NotNull Player player) {
        Triangulator.LOGGER.info("{} threw an eye of ender.", player.getName().getString());

        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();

        Vec3 location = new Vec3(x, y, z);

        Triangulator.LOGGER.info("{}'s coordinates: {}.", player.getName(), location);

        triangulator.startingPositionEyeMap.put(location, null);
    }
}
