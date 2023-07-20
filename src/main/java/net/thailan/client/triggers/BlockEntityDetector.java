package net.thailan.client.triggers;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.Camera;

import java.util.function.Consumer;

public interface BlockEntityDetector {

    public boolean isBlockEntity(BlockEntity blockEntity);
    public void blockEntityResponse(Camera camera, BlockEntity blockEntity);

}
