package net.thailan.client.additions.killaura;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;

public class KillEverythingStrategy extends AuraStrategy {

    public KillEverythingStrategy() {
        this.name = this.getClass().getName();
    }

    @Override
    public boolean isTarget(Entity entity) {
        if (!(entity instanceof MobEntity)) return false;
        return !(((MobEntity) entity).getHealth() <= 0);
    }

}
