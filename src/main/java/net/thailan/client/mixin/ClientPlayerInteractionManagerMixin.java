package net.thailan.client.mixin;

import net.thailan.client.ThaiFoodClient;
import net.thailan.client.utils.PacketHelper;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin {

    @Shadow private float currentBreakingProgress;
    @Shadow public abstract boolean breakBlock(BlockPos pos);

    @Shadow private boolean breakingBlock;

    @Shadow private float blockBreakingSoundCooldown;

    @Inject(at = @At("HEAD"), method = "updateBlockBreakingProgress", cancellable = true)
    public void updateBlockBreakingProgress(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (ThaiFoodClient.speedMine.isEnabled()) {
            if (ThaiFoodClient.speedMine.shouldTrigger(pos)) {

                PacketHelper.sendBlockBreak(pos, direction);

                //breakingBlock = false;
                blockBreakingSoundCooldown = 5;
                ThaiFoodClient.LOGGER.info("Speeding up mining progress.");
                cir.setReturnValue(false);
            }
        }

    }

}
