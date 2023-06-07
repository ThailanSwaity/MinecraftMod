package net.fabricmc.example.mixin;

import net.fabricmc.example.ExampleMod;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {

    @Inject(at = @At("HEAD"), method = "isCustomNameVisible", cancellable = true)
    private void isCustomNameVisible(CallbackInfoReturnable<Boolean> cir) {
        if (ExampleMod.entityNames.isEnabled()) cir.setReturnValue(true);
    }

}
