package net.fabricmc.example.additions;

import net.fabricmc.example.Colour;
import net.fabricmc.example.EntityDetector;
import net.fabricmc.example.Renderer;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;

public class ItemESP extends RenderedHack implements EntityDetector {
    public ItemESP() {
        super("ItemESP");
    }

    public ItemESP(Hack parentHack) {
        super("ItemESP", parentHack);
    }

    @Override
    public boolean isEntity(Entity entity) {
        return entity instanceof ItemEntity;
    }

    @Override
    public void entityResponse(Entity entity, Camera camera) {
        Renderer.drawBoxOutline(entity.getBoundingBox(), Colour.RED, lineWidth, alpha);
    }
}
