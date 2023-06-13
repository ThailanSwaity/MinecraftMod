package net.fabricmc.example.additions;

import net.fabricmc.example.ExampleMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.ChunkPos;

public class ChunkTracking extends Hack {

    private MinecraftClient client;

    public ChunkTracking(MinecraftClient client) {
        super("ChunkTracking");
        this.client = client;
    }

    public long playerChunkAge() {
        ChunkPos playerChunk = client.player.getChunkPos();
        return client.world.getChunkManager().getWorldChunk(playerChunk.x, playerChunk.z).getInhabitedTime();
    }

}
