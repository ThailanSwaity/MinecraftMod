package net.thailan.client.additions.killaura;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;

public class KillEverythingStrategy extends AuraStrategy {

    public KillEverythingStrategy() {
        this.name = this.getClass().getName();
    }

    @Override
    public boolean isTarget(Entity entity) {
        if (!(entity instanceof MobEntity)) return false;
        return !(((MobEntity) entity).getHealth() <= 0);
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
