package net.fabricmc.example.additions;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;

public class DetectPlayers extends Hack {

    private MinecraftClient client;

    public DetectPlayers(MinecraftClient client) {
        super("DetectPlayers");
        this.client = client;
    }

    public ArrayList<Text> getNearbyPlayers() {
        ArrayList<Text> players = new ArrayList<>();
        for (Entity entity : client.world.getEntities()) {
            if (entity instanceof PlayerEntity && entity != client.player) {
                players.add(Text.literal(entity.getName().getString() + " " + getEntityCoordsAsString(entity) + ", " + getDistance(entity)));
            }
        }
        return players;
    }

    public String getEntityCoordsAsString(Entity entity) {
        String x = "X: " + String.format("%.2f", entity.getX()) + ", ";
        String y = "Y: " + String.format("%.2f", entity.getY()) + ", ";
        String z = "Z: " + String.format("%.2f", entity.getZ());
        return x + y + z;
    }

    public String getDistance(Entity entity) {
        return String.format("%.2f", client.player.distanceTo(entity));
    }

    public Formatting getDistanceFormat(Entity entity) {
        float dist = client.player.distanceTo(entity);
        return Formatting.AQUA;
    }

}
