package net.thailan.client.mixin;

import net.thailan.client.triggers.EntityHighlighter;
import net.thailan.client.ThaiFoodClient;
import net.thailan.client.additions.Hack;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Inject(at = @At("HEAD"), method = "isAmbientOcclusionEnabled()Z", cancellable = true)
    private static void isAmbientOcclusionEnabled(CallbackInfoReturnable<Boolean> ci) {
        if (ThaiFoodClient.xray.isEnabled()) {
            ci.setReturnValue(false);
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "hasOutline", cancellable = true)
    private void hasOutline(Entity entity, CallbackInfoReturnable<Boolean> cir) {

        for (Hack hack : ThaiFoodClient.getInstance().additionManager.getEntityHighlighters()) {
            if (hack.isEnabled()) {
                if (((EntityHighlighter)hack).shouldHighlight(entity)) cir.setReturnValue(true);
            }
        }

    }

}
