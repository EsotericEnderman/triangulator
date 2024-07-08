package net.slqmy.triangulator.util;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class ChatUtil {
    public static void sendMessage(String message) {
        try (Minecraft minecraft = Minecraft.getInstance()) {
            minecraft.gui.getChat().addMessage(Component.nullToEmpty(message));
        }
    }
}
