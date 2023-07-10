package net.fabricmc.example;

import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;

public interface EntityDetector {

    public boolean isEntity(Entity entity);

    public void entityResponse(Entity entity, Camera camera);

}
