package net.thailan.client.mixin;

import net.thailan.client.ThaiFoodClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(at = @At("HEAD"), method = "tiltViewWhenHurt", cancellable = true)
    private void tiltViewWhenHurt(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        if (ThaiFoodClient.noDamageTilt.isEnabled()) ci.cancel();
    }

    @Inject(at = @At("HEAD"), method = "renderNausea", cancellable = true)
    private void renderNausea(DrawContext context, float distortionStrength, CallbackInfo ci) {
        ci.cancel();
    }

}
