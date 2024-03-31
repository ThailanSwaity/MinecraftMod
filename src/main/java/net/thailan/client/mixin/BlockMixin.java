package net.thailan.client.mixin;

import net.thailan.client.ThaiFoodClient;
import net.thailan.client.additions.Xray;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class BlockMixin {

    @Inject(at = @At("HEAD"), method="shouldDrawSide", cancellable = true)
    private static void shouldDrawSide(BlockState state, BlockView world, BlockPos pos, Direction side, BlockPos otherPos, CallbackInfoReturnable<Boolean> ci) {
        if (ThaiFoodClient.xray.isEnabled()) {
            if (ThaiFoodClient.xray.isCulling()) {
                if (!ThaiFoodClient.xray.blockIsVisible(state.getBlock())) {
                    ci.setReturnValue(false);
                    ci.cancel();
                }
            } else {
                ci.setReturnValue(ThaiFoodClient.xray.blockIsVisible(state.getBlock()));
                ci.cancel();
            }
        }
    }
}
