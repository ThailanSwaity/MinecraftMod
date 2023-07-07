package net.fabricmc.example.additions;

import net.fabricmc.example.BlockEntityDetector;
import net.fabricmc.example.Colour;
import net.fabricmc.example.Renderer;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.client.render.Camera;

import java.util.function.Consumer;

public class ChestESP extends Hack implements BlockEntityDetector {

    public ChestESP() {
        super("ChestESP");
    }

    @Override
    public boolean isBlockEntity(BlockEntity blockEntity) {
        return blockEntity instanceof ChestBlockEntity || blockEntity instanceof EnderChestBlockEntity;
    }

    @Override
    public void blockEntityResponse(Camera camera, BlockEntity blockEntity) {
        if (blockEntity instanceof ChestBlockEntity) Renderer.drawBoxOutline(blockEntity.getPos(), Colour.LIGHT_BLUE, 1f);
        else if (blockEntity instanceof EnderChestBlockEntity) Renderer.drawBoxOutline(blockEntity.getPos(), Colour.PURPLE, 1f);
    }
}
