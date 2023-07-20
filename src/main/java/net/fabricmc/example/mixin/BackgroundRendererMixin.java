package net.fabricmc.example.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.example.ExampleMod;
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
        if (ExampleMod.fog.bypassFog()) ci.cancel();
    }

    @Redirect(method = "applyFog", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderFogStart(F)V"))
    private static void setFogStart(float shaderFogStart) {
        RenderSystem.setShaderFogStart((ExampleMod.fog.isEnabled() ? ExampleMod.fog.getFogStart() : shaderFogStart));
    }

    @Redirect(method = "applyFog", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderFogEnd(F)V"))
    private static void setFogEnd(float shaderFogEnd) {
        RenderSystem.setShaderFogEnd((ExampleMod.fog.isEnabled() ? ExampleMod.fog.getFogEnd() : shaderFogEnd));
    }

}
