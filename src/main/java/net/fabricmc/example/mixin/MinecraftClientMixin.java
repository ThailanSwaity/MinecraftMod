package net.fabricmc.example.mixin;

import net.fabricmc.example.Colour;
import net.fabricmc.example.EntityHighlighter;
import net.fabricmc.example.EntityOutline;
import net.fabricmc.example.ExampleMod;
import net.fabricmc.example.additions.Hack;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Inject(at = @At("HEAD"), method = "isAmbientOcclusionEnabled()Z", cancellable = true)
    private static void isAmbientOcclusionEnabled(CallbackInfoReturnable<Boolean> ci) {
        if (ExampleMod.xray.isEnabled()) {
            ci.setReturnValue(false);
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "hasOutline", cancellable = true)
    private void hasOutline(Entity entity, CallbackInfoReturnable<Boolean> cir) {

        for (Hack hack : ExampleMod.getInstance().additionManager.getEntityHighlighters()) {
            if (hack.isEnabled()) {
                if (((EntityHighlighter)hack).shouldHighlight(entity)) cir.setReturnValue(true);
            }
        }

    }

}
