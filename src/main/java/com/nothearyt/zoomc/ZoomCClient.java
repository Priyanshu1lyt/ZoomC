package com.nothearyt.zoomc;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public class ZoomCClient implements ClientModInitializer {
    public static final String MOD_ID = "zoomc";

    private static final KeyMapping.Category CATEGORY =
            KeyMapping.Category.register(Identifier.fromNamespaceAndPath(MOD_ID, "main"));

    public static KeyMapping zoomKey;
    public static KeyMapping resetKey;

    @Override
    public void onInitializeClient() {
        zoomKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.zoomc.zoom", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_C, CATEGORY));
        resetKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.zoomc.reset", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, CATEGORY));

        ClientTickEvents.END_CLIENT_TICK.register(client -> ZoomHandler.tick(client));
    }
}
