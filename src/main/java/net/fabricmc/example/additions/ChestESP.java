package net.fabricmc.example.additions;

import net.fabricmc.example.BlockEntityDetector;
import net.fabricmc.example.Colour;
import net.fabricmc.example.Renderer;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.client.render.Camera;

public class ChestESP extends RenderedHack implements BlockEntityDetector {

    public ChestESP() {
        super("ChestESP");
    }
    public ChestESP(Hack parentHack) {
        this();
        this.parentHack = parentHack;
    }

    @Override
    public boolean isBlockEntity(BlockEntity blockEntity) {
        return blockEntity instanceof ChestBlockEntity || blockEntity instanceof EnderChestBlockEntity;
    }

    @Override
    public void blockEntityResponse(Camera camera, BlockEntity blockEntity) {
        if (blockEntity instanceof ChestBlockEntity) {
            Renderer.drawBoxBoth(blockEntity.getPos(), Colour.LIGHT_BLUE, lineWidth, alpha);
        }
        else if (blockEntity instanceof EnderChestBlockEntity) {
            Renderer.drawBoxBoth(blockEntity.getPos(), Colour.PURPLE, lineWidth, alpha);
        }
    }

}
