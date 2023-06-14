package net.fabricmc.example.additions;

import net.fabricmc.example.ExampleMod;
import net.fabricmc.example.PacketHelper;
import net.fabricmc.example.Tickable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class Reach extends Hack implements Tickable {

    private MinecraftClient client;
    private int cooldown = 0;

    public Reach(MinecraftClient client) {
        super("Reach");
        this.client = client;
    }

    public void tick() {
        if (!isEnabled()) return;

        if (cooldown > 0) {
            cooldown--;
            return;
        }
        if (client.options.attackKey.isPressed()) {
            Entity entity = getEntityOnCursor();
            if (entity != null) {
                ExampleMod.LOGGER.info("Tried to teleport");
                // TODO: Sync selected slot
                Vec3d originPos = client.player.getPos();
                Teleport.tp(client.player, entity.getPos());
                PacketHelper.sendPlayerAttack(entity);
                Teleport.tp(client.player, originPos);
                cooldown = 10;
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
