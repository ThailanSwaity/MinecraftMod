package net.thailan.client.triggers;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.Camera;
import net.minecraft.util.math.BlockPos;

import java.util.function.BiConsumer;

public interface BlockDetector {

    public boolean isBlock(BlockState blockState);
    public BiConsumer<BlockPos, BlockState> blockResponse(Camera camera);

}
