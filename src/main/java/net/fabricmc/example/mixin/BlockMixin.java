package net.fabricmc.example.mixin;

import net.fabricmc.example.ExampleMod;
import net.fabricmc.example.additions.Xray;
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
        if (ExampleMod.xray.isEnabled()) {
            if (!ExampleMod.xray.blockIsVisible(state.getBlock())) {
                ci.setReturnValue(false);
                ci.cancel();
            } else if (ExampleMod.xray.getMode() == Xray.ORE_MODE) {
                ci.setReturnValue(true);
                ci.cancel();
            }
        }
    }
}
