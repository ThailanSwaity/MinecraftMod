package net.fabricmc.example.additions;

import net.minecraft.client.MinecraftClient;

public class PlayerCoordinateDisplay extends Hack {

    private MinecraftClient client;

    public PlayerCoordinateDisplay(MinecraftClient client) {
        super("PlayerCoordinateDisplay");
        this.client = client;
    }

    public String getCoordinateString() {
        String x = "X: " + String.format("%.2f", client.player.getX()) + ", ";
        String y = "Y: " + String.format("%.2f", client.player.getY()) + ", ";
        String z = "Z: " + String.format("%.2f", client.player.getZ());
        return x + y + z;
    }

}
