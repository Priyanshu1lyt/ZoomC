package com.nothearyt.zoomc.mixin;

import com.nothearyt.zoomc.ZoomHandler;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(method = "getFov", at = @At("RETURN"), cancellable = true)
    private void zoomc$applyZoom(Camera camera, float partialTick, boolean useFovSetting,
                                 CallbackInfoReturnable<Float> cir) {
        if (!ZoomHandler.isActive()) return;
        cir.setReturnValue((float) (cir.getReturnValue() / ZoomHandler.factor(partialTick)));
    }
}
