package net.fabricmc.example;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;

import java.util.ArrayList;
import java.util.List;

public class WorldUtil {

    protected static MinecraftClient client = MinecraftClient.getInstance();

    public static List<WorldChunk> getLoadedChunks() {
        List<WorldChunk> chunks = new ArrayList<>();

        int viewDist = client.options.getViewDistance().getValue();

        for (int x = -viewDist; x <= viewDist; x++) {
            for (int z = -viewDist; z <= viewDist; z++) {
                WorldChunk chunk = client.world.getChunkManager().getWorldChunk((int)client.player.getX() / 16 + x, (int)client.player.getZ() / 16 + z);

                if (chunk != null) {
                    chunks.add(chunk);
                }
            }
        }
        return chunks;
    }

    public static List<BlockEntity> getBlockEntities() {
        List<BlockEntity> list = new ArrayList<>();
        for (WorldChunk chunk : getLoadedChunks()) {
            list.addAll(chunk.getBlockEntities().values());
        }
        return list;
    }

}
