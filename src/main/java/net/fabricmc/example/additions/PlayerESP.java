package net.fabricmc.example.additions;

import net.fabricmc.example.Colour;
import net.fabricmc.example.EntityDetector;
import net.fabricmc.example.ExampleMod;
import net.fabricmc.example.Renderer;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

public class PlayerESP extends RenderedHack{
    public PlayerESP() {
        super("PlayerESP");
    }

    public PlayerESP(Hack parentHack) {
        super("PlayerESP", parentHack);
    }

//    @Override
//    public boolean isEntity(Entity entity) {
//        return entity instanceof PlayerEntity && entity != ExampleMod.getInstance().client.player;
//    }
//
//    @Override
//    public void entityResponse(Entity entity, Camera camera) {
//        Renderer.drawBoxOutline(entity.getBoundingBox(), Colour.WHITE, lineWidth, alpha);
//    }
}
