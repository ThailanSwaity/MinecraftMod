package net.fabricmc.example.additions;

import net.fabricmc.example.BlockDetector;
import net.fabricmc.example.Colour;
import net.fabricmc.example.Renderer;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.Camera;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.function.BiConsumer;

public class PortalTracers extends Hack implements BlockDetector {
    public PortalTracers() {
        super("PortalTracers");
    }

    @Override
    public boolean isBlock(BlockState blockState) {
        return blockState.getBlock() == Blocks.NETHER_PORTAL || blockState.getBlock() == Blocks.END_PORTAL;
    }

    @Override
    public BiConsumer<BlockPos, BlockState> blockResponse(Camera camera) {
        Vec3d viewVec = camera.getFocusedEntity().getRotationVec(0.5f);
        Vec3d cursorPosition = camera.getPos().add(viewVec);
        return ((blockPos, blockState) -> {
            Renderer.drawLine(cursorPosition.getX(), cursorPosition.getY(), cursorPosition.getZ(), blockPos.getX() + 0.5f, blockPos.getY() + 0.5f, blockPos.getZ() + 0.5f, 1f, Colour.PURPLE);
        });
    }
}
