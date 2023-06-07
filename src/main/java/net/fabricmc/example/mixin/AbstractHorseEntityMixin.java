package net.fabricmc.example.mixin;

import net.fabricmc.example.ExampleMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractHorseEntity.class)
public abstract class AbstractHorseEntityMixin {

    @Shadow protected abstract void setHorseFlag(int bitmask, boolean flag);

    @Inject(at = @At("HEAD"), method = "getControllingPassenger", cancellable = true)
    private void getControllingEntity(CallbackInfoReturnable<LivingEntity> cir) {
//        if (ExampleMod.entityControl.isEnabled()) {
//            Entity e = ((AbstractHorseEntity)(Object)this).getFirstPassenger();
//            if (e instanceof LivingEntity) {
//                LivingEntity le = (LivingEntity)e;
//                cir.setReturnValue(le);
//                cir.cancel();
//            }
//        }
    }

    @Inject(at = @At("TAIL"), method = "updateSaddle", cancellable = true)
    public void setSaddled(CallbackInfo ci) {
        if (ExampleMod.entityControl.isEnabled()) {
            if (!((AbstractHorseEntity)(Object)this).world.isClient) {
                setHorseFlag(4, true);
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "isSaddled", cancellable = true)
    private void isSaddled(CallbackInfoReturnable<Boolean> cir) {
        if (ExampleMod.entityControl.isEnabled()) {
            cir.setReturnValue(true);
        }
    }
}
