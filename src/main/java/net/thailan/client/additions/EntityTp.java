package net.thailan.client.additions;

import net.thailan.client.ThaiFoodClient;
import net.thailan.client.triggers.Tickable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class EntityTp extends Hack implements Tickable {

    private MinecraftClient client;

    public EntityTp(MinecraftClient client) {
        super("EntityTp");
        this.client = client;
    }

    public void tick() {
        if (!isEnabled()) return;

        if (client.options.attackKey.isPressed()) {
            ThaiFoodClient.LOGGER.info("Attack key was pressed.");
            Entity targetEntity = getEntityOnCursor();
            if (targetEntity != null) {
                ThaiFoodClient.LOGGER.info("target entity: " + targetEntity.getName());
                Teleport.tp(client.player, targetEntity.getPos());
            }
        }
    }

    public Entity getEntityOnCursor() {
        for (Entity entity : client.world.getEntities()) {
            if (entity == client.player) continue;
            Vec3d vec3d = client.player.getRotationVec(1.0F).normalize();
            Vec3d vec3d2 = new Vec3d(entity.getX() - client.player.getX(), entity.getY() - client.player.getY(), entity.getZ() - client.player.getZ());
            double d = vec3d2.length();
            vec3d2 = vec3d2.normalize();
            double e = vec3d.dotProduct(vec3d2);
            if (e > 1.0D - 0.025D / d) return entity;
        }
        return null;
    }

}
