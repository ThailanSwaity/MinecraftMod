package net.fabricmc.example.mixin;

import net.fabricmc.example.ExampleMod;
import net.fabricmc.example.additions.FullBright;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.render.LightmapTextureManager;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LightmapTextureManager.class)
public class LightmapTextureManagerMixin {

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/GameOptions;getGamma()Lnet/minecraft/client/option/SimpleOption;", opcode = Opcodes.INVOKEVIRTUAL), method = "update(F)V")
    private SimpleOption<Double> getFieldValue(GameOptions options) {
        if (ExampleMod.fullBright.isEnabled() || ExampleMod.xray.isEnabled()) {
            return FullBright.getGammaBypass();
        } else {
            return options.getGamma();
        }
    }

}
