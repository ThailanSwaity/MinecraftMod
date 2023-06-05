package net.fabricmc.example.mixin;

import net.fabricmc.example.ExampleMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    @Shadow public abstract TextRenderer getTextRenderer();

    @Shadow private int scaledHeight;

    @Inject(at = @At("TAIL"), method = "render")
    private void render(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        if (ExampleMod.playerCoordinateDisplay.isEnabled()) {
            getTextRenderer().drawWithShadow(matrices, ExampleMod.playerCoordinateDisplay.getCoordinateString(), 3, scaledHeight - 10, 0xFFFFFF);
        }
        if (ExampleMod.deathCoordinateDisplay.isEnabled() && ExampleMod.deathCoordinateDisplay.hasDied()) {
            getTextRenderer().drawWithShadow(matrices, ExampleMod.deathCoordinateDisplay.getDeathString(), 3, scaledHeight - 20, 0xFF0000);
        }

    }

}
