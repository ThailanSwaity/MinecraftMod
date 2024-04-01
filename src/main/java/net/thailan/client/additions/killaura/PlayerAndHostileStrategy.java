package net.thailan.client.additions.killaura;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.entity.player.PlayerEntity;

public class PlayerAndHostileStrategy extends AuraStrategy {

    public PlayerAndHostileStrategy() {
        this.name = this.getClass().getName();
    }

    public boolean isTarget(Entity entity) {
        if (!(entity instanceof MobEntity)) return false;
        if (((MobEntity)entity).getHealth() <= 0) return false;
        return (entity instanceof PlayerEntity || entity instanceof HostileEntity);
    }

    public void doKilling(Iterable<Entity> entities) {
        for (Entity entity : entities) {
            if (entity == null) continue;
            if (entity == client.player || entity instanceof ZombifiedPiglinEntity) continue;
            double dist = client.player.getPos().distanceTo(entity.getPos());
            if (dist > client.interactionManager.getReachDistance()) continue;

            if (isTarget(entity)) {
                client.interactionManager.attackEntity(client.player, entity);
                return;
            }
        }
    }

}
