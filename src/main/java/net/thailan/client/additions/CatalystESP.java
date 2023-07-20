package net.thailan.client.additions;

import net.thailan.client.triggers.BlockDetector;
import net.thailan.client.utils.render.Colour;
import net.thailan.client.utils.render.Renderer;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.Camera;
import net.minecraft.util.math.BlockPos;

import java.util.function.BiConsumer;

public class CatalystESP extends RenderedHack implements BlockDetector {
    public CatalystESP() {
        super("CatalystESP");
    }

    public CatalystESP(Hack parentHack) {
        super("CatalystESP", parentHack);
    }

    @Override
    public boolean isBlock(BlockState blockState) {
        return blockState.getBlock() == Blocks.SCULK_CATALYST;
    }

    @Override
    public BiConsumer<BlockPos, BlockState> blockResponse(Camera camera) {
        return ((blockPos, blockState) -> {
            Renderer.drawBoxBoth(blockPos, Colour.WHITE, lineWidth, alpha);
        });
    }
}
