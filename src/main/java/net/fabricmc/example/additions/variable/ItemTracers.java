package net.fabricmc.example.additions.variable;

import net.fabricmc.example.Colour;
import net.fabricmc.example.EntityDetector;
import net.fabricmc.example.Renderer;
import net.fabricmc.example.additions.Hack;
import net.fabricmc.example.additions.RenderedHack;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.math.Vec3d;

public class ItemTracers extends RenderedHack implements EntityDetector {
    public ItemTracers() {
        super("ItemTracers");
    }

    public ItemTracers(Hack parentHack) {
        super("ItemTracers", parentHack);
    }

    @Override
    public boolean isEntity(Entity entity) {
        return entity instanceof ItemEntity;
    }

    @Override
    public void entityResponse(Entity entity, Camera camera) {
        Vec3d viewVec = camera.getFocusedEntity().getRotationVec(0.5f);
        Vec3d cursorPosition = camera.getPos().add(viewVec);
        Renderer.drawLine(cursorPosition.getX(), cursorPosition.getY(), cursorPosition.getZ(), entity.getX(), entity.getY(), entity.getZ(), lineWidth, alpha, Colour.WHITE);
    }
}
