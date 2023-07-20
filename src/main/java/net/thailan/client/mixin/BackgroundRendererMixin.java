package net.thailan.client.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.thailan.client.ThaiFoodClient;
import net.minecraft.client.render.BackgroundRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {

    @Inject(method = "applyFog", at = @At("HEAD"), cancellable = true)
    private static void bypassFog(CallbackInfo ci) {
        if (ThaiFoodClient.fog.bypassFog()) ci.cancel();
    }

    @Redirect(method = "applyFog", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderFogStart(F)V"))
    private static void setFogStart(float shaderFogStart) {
        RenderSystem.setShaderFogStart((ThaiFoodClient.fog.isEnabled() ? ThaiFoodClient.fog.getFogStart() : shaderFogStart));
    }

    @Redirect(method = "applyFog", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderFogEnd(F)V"))
    private static void setFogEnd(float shaderFogEnd) {
        RenderSystem.setShaderFogEnd((ThaiFoodClient.fog.isEnabled() ? ThaiFoodClient.fog.getFogEnd() : shaderFogEnd));
    }

}
