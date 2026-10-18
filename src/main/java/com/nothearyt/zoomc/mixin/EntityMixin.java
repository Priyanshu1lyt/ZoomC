package com.nothearyt.zoomc.mixin;

import com.nothearyt.zoomc.ZoomHandler;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Entity.class)
public class EntityMixin {
    @ModifyVariable(method = "turn", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private double zoomc$yaw(double value) {
        return ((Object) this instanceof LocalPlayer && ZoomHandler.isActive())
                ? value * ZoomHandler.sensitivityScale() : value;
    }

    @ModifyVariable(method = "turn", at = @At("HEAD"), argsOnly = true, ordinal = 1)
    private double zoomc$pitch(double value) {
        return ((Object) this instanceof LocalPlayer && ZoomHandler.isActive())
                ? value * ZoomHandler.sensitivityScale() : value;
    }
}
