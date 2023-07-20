package net.thailan.client.mixin;

import net.minecraft.client.gui.screen.SplashTextRenderer;
import net.minecraft.client.resource.SplashTextResourceSupplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SplashTextResourceSupplier.class)
public class SplashTextResourceSupplierMixin {

    @Inject(at = @At("HEAD"), method = "get", cancellable = true)
    private void get(CallbackInfoReturnable<SplashTextRenderer> cir) {
        cir.setReturnValue(new SplashTextRenderer("TʜᴀɪFᴏᴏᴅ Cʟɪᴇɴᴛ!"));
    }

}
