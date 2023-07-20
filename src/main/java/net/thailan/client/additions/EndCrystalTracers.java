package net.thailan.client.additions;

import net.thailan.client.utils.render.Colour;
import net.thailan.client.triggers.EntityDetector;
import net.thailan.client.utils.render.Renderer;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.util.math.Vec3d;

public class EndCrystalTracers extends RenderedHack implements EntityDetector {
    public EndCrystalTracers() {
        super("EndCrystalTracers");
    }

    public EndCrystalTracers(Hack parentHack) {
        super("EndCrystalTracers", parentHack);
    }

    @Override
    public boolean isEntity(Entity entity) {
        return entity instanceof EndCrystalEntity;
    }

    @Override
    public void entityResponse(Entity entity, Camera camera) {
        Vec3d viewVec = camera.getFocusedEntity().getRotationVec(0.5f);
        Vec3d cursorPosition = camera.getPos().add(viewVec);
        Renderer.drawLine(cursorPosition.getX(), cursorPosition.getY(), cursorPosition.getZ(), entity.getX(), entity.getY(), entity.getZ(), lineWidth, alpha, Colour.DEEP_PINK);
    }
}
