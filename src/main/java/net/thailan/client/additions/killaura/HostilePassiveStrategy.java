package net.thailan.client.additions.killaura;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;

public class HostilePassiveStrategy extends AuraStrategy {

    public HostilePassiveStrategy() {
        this.name = this.getClass().getName();
    }

    public boolean isTarget(Entity entity) {
        if (!(entity instanceof MobEntity)) return false;
        if (((MobEntity)entity).getHealth() <= 0) return false;
        return (entity instanceof PassiveEntity || entity instanceof HostileEntity);
    }

}
