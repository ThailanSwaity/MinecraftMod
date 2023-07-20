package net.thailan.client.additions;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;

public class DeathCoordinateDisplay extends Hack {

    private MinecraftClient client;
    private Vec3d deathLocation;

    public DeathCoordinateDisplay(MinecraftClient client) {
        super("DeathCoordinateDisplay");
        this.client = client;
    }

    public boolean hasDied() {
        return deathLocation != null;
    }

    public void updateDeath() {
        deathLocation = client.player.getPos();
    }

    public Vec3d getDeathLocation() {
        if (!hasDied()) return null;
        return deathLocation;
    }

    public String getDeathString() {
        if (!hasDied()) return "No deaths";
        String x = "X: " + String.format("%.2f", deathLocation.getX()) + ", ";
        String y = "Y: " + String.format("%.2f", deathLocation.getY()) + ", ";
        String z = "Z: " + String.format("%.2f", deathLocation.getZ());
        return x + y + z;
    }

}
