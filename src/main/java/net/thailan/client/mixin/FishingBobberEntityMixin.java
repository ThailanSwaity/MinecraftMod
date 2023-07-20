package net.thailan.client.mixin;

import net.thailan.client.ThaiFoodClient;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.projectile.FishingBobberEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FishingBobberEntity.class)
public class FishingBobberEntityMixin {

    @Shadow private boolean caughtFish;

    @Inject(at = @At("TAIL"), method = "onTrackedDataSet")
    public void onTrackedDataSet(TrackedData<?> data, CallbackInfo ci) {
        if (ThaiFoodClient.autoFish.isEnabled()) {
            if (caughtFish) {
                ThaiFoodClient.LOGGER.info("Fish caught");
                ThaiFoodClient.autoFish.catchFish();
            }
        }
    }
}
