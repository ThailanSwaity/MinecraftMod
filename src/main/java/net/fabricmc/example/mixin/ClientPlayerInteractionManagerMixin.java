package net.fabricmc.example.mixin;

import net.fabricmc.example.ExampleMod;
import net.fabricmc.example.PacketHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.apache.commons.compress.harmony.pack200.NewAttributeBands;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin {

    @Shadow private float currentBreakingProgress;
    @Shadow public abstract boolean breakBlock(BlockPos pos);

    @Shadow private boolean breakingBlock;

    @Shadow private float blockBreakingSoundCooldown;

    @Inject(at = @At("HEAD"), method = "updateBlockBreakingProgress", cancellable = true)
    public void updateBlockBreakingProgress(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (ExampleMod.speedMine.isEnabled()) {
            if (ExampleMod.speedMine.shouldTrigger(pos)) {

                PacketHelper.sendBlockBreak(pos, direction);

                //breakingBlock = false;
                blockBreakingSoundCooldown = 5;
                ExampleMod.LOGGER.info("Speeding up mining progress.");
                cir.setReturnValue(false);
            }
        }

    }

}
