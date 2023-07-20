package net.thailan.client.triggers;

import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;

public interface EntityDetector {

    public boolean isEntity(Entity entity);

    public void entityResponse(Entity entity, Camera camera);

}
