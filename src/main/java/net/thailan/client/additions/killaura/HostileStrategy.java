package net.thailan.client.additions.killaura;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;

import java.util.Collections;
import java.util.HashSet;

public class HostileStrategy extends AuraStrategy {

    public HostileStrategy() {
        this.name = "HostileStrategy";
    }

    @Override
    public boolean isTarget(Entity entity) {
        if (!(entity instanceof MobEntity)) return false;
        if (((MobEntity)entity).getHealth() <= 0) return false;
        return (entity instanceof Monster);
    }
}
