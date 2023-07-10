package net.fabricmc.example.additions;

import net.fabricmc.example.*;
import net.minecraft.block.BlockState;
import net.minecraft.block.SignBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.util.math.BlockPos;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class SignESP extends RenderedHack implements BlockDetector, BlockEntityDetector {

    public SignESP() {
        super("SignESP");
    }
    public SignESP(Hack parentHack) {
        this();
        this.parentHack = parentHack;
    }

    @Override
    public boolean isBlockEntity(BlockEntity blockEntity) {
        return blockEntity instanceof SignBlockEntity;
    }

    @Override
    public void blockEntityResponse(Camera camera, BlockEntity blockEntity) {
        Renderer.drawBoxOutline(blockEntity.getPos(), Colour.GREEN, 1f);
    }

    @Override
    public boolean isBlock(BlockState blockState) {
        return blockState.getBlock() instanceof SignBlock;
    }

    @Override
    public BiConsumer<BlockPos, BlockState> blockResponse(Camera camera) {
        return ((blockPos, blockState) -> {
            Renderer.drawBoxOutline(blockPos, Colour.GREEN, lineWidth, alpha);
        });
    }

}
