package net.thailan.client.additions;

import net.thailan.client.utils.render.Colour;
import net.thailan.client.triggers.EntityDetector;
import net.thailan.client.utils.render.Renderer;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;

public class EndCrystalESP extends RenderedHack implements EntityDetector {
    public EndCrystalESP() {
        super("EndCrystalESP");
    }

    public EndCrystalESP(Hack parentHack) {
        super("EndCrystalESP", parentHack);
    }

    @Override
    public boolean isEntity(Entity entity) {
        return entity instanceof EndCrystalEntity;
    }

    @Override
    public void entityResponse(Entity entity, Camera camera) {
        Renderer.drawBoxOutline(entity.getBoundingBox(), Colour.DEEP_PINK, lineWidth, alpha);
    }
}
