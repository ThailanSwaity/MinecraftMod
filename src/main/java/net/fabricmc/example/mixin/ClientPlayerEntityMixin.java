package net.fabricmc.example.mixin;

import net.fabricmc.example.ExampleMod;
import net.fabricmc.example.Tickable;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @Shadow
    public boolean showsDeathScreen;

    @Inject(at = @At("TAIL"), method="tick()V")
    public void tick(CallbackInfo ci) {
        ExampleMod.getInstance().additionManager.getAdditions().forEach(hack -> {
            if (hack instanceof Tickable) ((Tickable)hack).tick();
        });

        if (ExampleMod.autoRespawn.isEnabled()) showsDeathScreen = false;
        else showsDeathScreen = true;
    }

}
