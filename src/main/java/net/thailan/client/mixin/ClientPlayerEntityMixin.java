package net.thailan.client.mixin;

import net.thailan.client.ThaiFoodClient;
import net.thailan.client.triggers.Tickable;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    @Shadow
    public boolean showsDeathScreen;

    @Inject(at = @At("TAIL"), method="tick()V")
    public void tick(CallbackInfo ci) {
        ThaiFoodClient.getInstance().additionManager.getAdditions().forEach(hack -> {
            if (hack instanceof Tickable) ((Tickable)hack).tick();
        });

        if (ThaiFoodClient.autoRespawn.isEnabled()) showsDeathScreen = false;
        else showsDeathScreen = true;
    }

}
