package net.fabricmc.example.additions;

import net.fabricmc.example.BlockDetector;
import net.fabricmc.example.Colour;
import net.fabricmc.example.Renderer;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.Camera;
import net.minecraft.util.math.BlockPos;

import java.util.function.BiConsumer;

public class ShriekerESP extends RenderedHack implements BlockDetector {

    public ShriekerESP() {
        super("ShriekerESP");
    }
    public ShriekerESP(Hack parentHack) {
        this();
        this.parentHack = parentHack;
    }

    @Override
    public boolean isBlock(BlockState blockState) {
        return blockState.getBlock() == Blocks.SCULK_SHRIEKER;
    }

    @Override
    public BiConsumer<BlockPos, BlockState> blockResponse(Camera camera) {
        return ((blockPos, blockState) -> {
            Renderer.drawBoxOutline(blockPos, Colour.RED, lineWidth, alpha);
        });
    }

}
