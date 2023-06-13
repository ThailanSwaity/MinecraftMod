package net.fabricmc.example.additions;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;

public class SpawnCoordinateDisplay extends Hack {

    private MinecraftClient client;

    public SpawnCoordinateDisplay(MinecraftClient client) {
        super("SpawnCoordinateDisplay");
        this.client = client;
    }

    public String getCoordinateString() {
        if (client.world == null) return "No world."; // Shouldn't happen if only triggered from in-game HUD
        BlockPos spawnLocation = client.player.clientWorld.getSpawnPos();
        if (spawnLocation == null) return "No respawn location.";
        String x = "X: " + spawnLocation.getX() + ", ";
        String y = "Y: " + spawnLocation.getY() + ", ";
        String z = "Z: " + spawnLocation.getZ();
        return x + y + z;
    }

}
