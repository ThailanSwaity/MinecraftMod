package net.thailan.client.additions;

import net.thailan.client.triggers.BlockDetector;
import net.thailan.client.triggers.BlockEntityDetector;
import net.thailan.client.utils.render.Colour;
import net.thailan.client.utils.render.Renderer;
import net.minecraft.block.BlockState;
import net.minecraft.block.SignBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.render.Camera;
import net.minecraft.util.math.BlockPos;

import java.util.function.BiConsumer;

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
