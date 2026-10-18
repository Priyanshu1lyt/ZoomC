package com.nothearyt.zoomc.mixin;

import com.nothearyt.zoomc.ZoomHandler;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {
    @Inject(method = "onScroll", at = @At("HEAD"), cancellable = true)
    private void zoomc$scroll(long window, double xOffset, double yOffset, CallbackInfo ci) {
        if (ZoomHandler.isHeld() && yOffset != 0) {
            ZoomHandler.scroll(yOffset);
            ci.cancel();
        }
    }
}
