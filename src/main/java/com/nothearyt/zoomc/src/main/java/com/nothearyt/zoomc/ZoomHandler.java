package com.nothearyt.zoomc;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;

public final class ZoomHandler {
    public static final double MIN_ZOOM = 1.5;
    public static final double MAX_ZOOM = 40.0;
    public static final double DEFAULT_ZOOM = 4.0;
    private static final double SCROLL_STEP = 1.15;
    private static final long DOUBLE_TAP_MS = 300;

    private static double targetZoom = DEFAULT_ZOOM;
    private static double prevFactor = 1.0;
    private static double factor = 1.0;
    private static boolean locked = false;
    private static boolean wasDown = false;
    private static long lastPress = 0;

    private ZoomHandler() {}

    public static void tick(Minecraft mc) {
        boolean down = ZoomCClient.zoomKey.isDown();

        if (down && !wasDown) {
            long now = System.currentTimeMillis();
            if (locked) {
                locked = false;
            } else if (now - lastPress < DOUBLE_TAP_MS) {
                locked = true;
            }
            lastPress = now;
        }
        wasDown = down;

        if (ZoomCClient.resetKey.consumeClick()) targetZoom = DEFAULT_ZOOM;
        if (mc.screen != null) locked = false;

        double goal = (down || locked) ? targetZoom : 1.0;
        prevFactor = factor;
        factor += (goal - factor) * 0.4;
        if (Math.abs(goal - factor) < 0.001) factor = goal;
    }

    public static boolean isActive() {
        return factor > 1.001 || prevFactor > 1.001;
    }

    public static boolean isHeld() {
        return ZoomCClient.zoomKey.isDown() || locked;
    }

    public static double factor(float partialTick) {
        return Mth.lerp(partialTick, prevFactor, factor);
    }

    public static void scroll(double amount) {
        targetZoom *= Math.pow(SCROLL_STEP, amount);
        targetZoom = Mth.clamp(targetZoom, MIN_ZOOM, MAX_ZOOM);
    }

    public static double sensitivityScale() {
        return 1.0 / Math.max(1.0, Math.sqrt(factor));
    }
}
