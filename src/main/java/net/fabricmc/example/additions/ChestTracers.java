package net.fabricmc.example.additions;

import net.fabricmc.example.BlockEntityDetector;
import net.fabricmc.example.Colour;
import net.fabricmc.example.Renderer;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.Camera;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.function.Consumer;

public class ChestTracers extends RenderedHack implements BlockEntityDetector {

    public ChestTracers() {
        super("ChestTracers");
    }
    public ChestTracers(Hack parentHack) {
        this();
        this.parentHack = parentHack;
    }

    @Override
    public boolean isBlockEntity(BlockEntity blockEntity) {
        return blockEntity instanceof ChestBlockEntity || blockEntity instanceof EnderChestBlockEntity;
    }

    @Override
    public void blockEntityResponse(Camera camera, BlockEntity blockEntity) {
        Vec3d viewVec = camera.getFocusedEntity().getRotationVec(0.5f);
        Vec3d cursorPosition = camera.getPos().add(viewVec);
        BlockPos pos = blockEntity.getPos();
        Renderer.drawLine(cursorPosition.getX(), cursorPosition.getY(), cursorPosition.getZ(), pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, lineWidth, alpha, Colour.GREEN);
    }
}
