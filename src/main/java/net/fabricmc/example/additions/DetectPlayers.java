package net.fabricmc.example.additions;

import net.fabricmc.example.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;

public class DetectPlayers extends RenderedHack implements EntityDetector, EntityHighlighter {

    private MinecraftClient client;

    public DetectPlayers(MinecraftClient client) {
        super("DetectPlayers");
        this.client = client;
    }

    public DetectPlayers(MinecraftClient client, Hack parentHack) {
        this(client);
        this.parentHack = parentHack;
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

    @Override
    public boolean isEntity(Entity entity) {
        return entity instanceof PlayerEntity && entity != client.player;
    }

    @Override
    public void entityResponse(Entity entity, Camera camera) {
        Vec3d viewVec = camera.getFocusedEntity().getRotationVec(0.5f);
        Vec3d cursorPosition = camera.getPos().add(viewVec);
        if (ExampleMod.friendList.isFriend(entity.getName().getString())) {
            Renderer.drawLine(cursorPosition.getX(), cursorPosition.getY(), cursorPosition.getZ(), entity.getX(), entity.getY(), entity.getZ(), lineWidth, alpha, Colour.BLUE);
        } else {
            Renderer.drawLine(cursorPosition.getX(), cursorPosition.getY(), cursorPosition.getZ(), entity.getX(), entity.getY(), entity.getZ(), lineWidth, alpha, Colour.RED);
        }
    }

    @Override
    public boolean shouldHighlight(Entity entity) {
        return entity instanceof PlayerEntity;
    }
}
