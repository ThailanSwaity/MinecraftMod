package net.fabricmc.example.mixin;

import net.fabricmc.example.ExampleMod;
import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @Inject(at = @At("HEAD"), method = "renderWeather", cancellable = true)
    private void renderWeather(CallbackInfo ci) {
        if (ExampleMod.noWeather.isEnabled()) {
            ci.cancel();
        }
    }

}
