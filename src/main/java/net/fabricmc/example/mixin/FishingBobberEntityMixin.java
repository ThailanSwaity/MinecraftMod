package net.fabricmc.example.mixin;

import net.fabricmc.example.ExampleMod;
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
        if (ExampleMod.autoFish.isEnabled()) {
            if (caughtFish) {
                ExampleMod.LOGGER.info("Fish caught");
                ExampleMod.autoFish.catchFish();
            }
        }
    }
}
