package net.slqmy.triangulator.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class ChatUtil {
    public static void sendMessage(String message) {
        MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.of(message));
    }
}
